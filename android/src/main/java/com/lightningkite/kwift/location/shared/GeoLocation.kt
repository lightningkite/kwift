package com.lightningkite.kwift.location.shared


data class GeoLocation(
    val coordinate: GeoCoordinate,
    val name: String?,
    val street: String?,
    val subLocality: String?,
    val locality: String?
)
