@file:Suppress("NAME_SHADOWING")

package com.lightningkite.khrysalis.maps

import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.lightningkite.khrysalis.observables.ObservableProperty
import com.lightningkite.khrysalis.observables.subscribeBy
import com.lightningkite.khrysalis.rx.DisposeCondition
import com.lightningkite.khrysalis.rx.forever
import com.lightningkite.khrysalis.rx.until
import com.lightningkite.khrysalis.views.ViewDependency
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import java.util.*

class PlacesAutocomplete(dependency: ViewDependency) {
    private val client = Places.createClient(dependency.context)
    private var token: AutocompleteSessionToken? = AutocompleteSessionToken.newInstance()
    private var working = false
    private var cachedRequest: List<AutocompletePrediction> = listOf()
    private val detailFields = listOf(
        Place.Field.ID,
        Place.Field.LAT_LNG,
        Place.Field.NAME,
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.ADDRESS
    )

    private fun buildRequest(query: String, filter: TypeFilter? = null): FindAutocompletePredictionsRequest {
        if (token == null) {
            token = AutocompleteSessionToken.newInstance()
        }
        return FindAutocompletePredictionsRequest.builder()
            .setCountry("us")
            .setSessionToken(token)
            .setTypeFilter(filter)
            .setQuery(query)
            .build()
    }

    fun request(query: String, filter: TypeFilter? = null): Single<List<AutocompletePrediction>> {
        return Single.create { emitter ->
            if (working) {
                emitter.onSuccess(cachedRequest)
            } else {
                working = true
                client.findAutocompletePredictions(buildRequest(query, filter))
                    .addOnSuccessListener { results ->
                        working = false
                        cachedRequest = results.autocompletePredictions
                        emitter.onSuccess(cachedRequest)
                    }
                    .addOnFailureListener {
                        working = false
                        emitter.onError(it)
                    }
            }
        }
    }

    fun request(query: ObservableProperty<String>, disposeCondition:DisposeCondition, filter: TypeFilter? = null): Observable<List<AutocompletePrediction>> {
        val subject = PublishSubject.create<List<AutocompletePrediction>>()
        query.subscribeBy { query ->
            if (!working) {
                working = true
                client.findAutocompletePredictions(buildRequest(query, filter))
                    .addOnSuccessListener { results ->
                        working = false
                        subject.onNext(results.autocompletePredictions)
                    }
                    .addOnFailureListener {
                        working = false
                    }
            }
        }.until(disposeCondition)

        return subject
    }

    fun details(id: String, details: List<Place.Field>? = null): Single<Place> {
        return Single.create { emitter ->
            working = true
            val request = FetchPlaceRequest.builder(id, details ?: detailFields)
                .setSessionToken(token)
                .build()
            client.fetchPlace(request)
                .addOnSuccessListener { response ->
                    token = null
                    emitter.onSuccess(response.place)
                    working = false
                }
                .addOnFailureListener { exception ->
                    token = null
                    emitter.onError(exception)
                    working = false
                }
        }
    }
}
