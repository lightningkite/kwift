// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/binding/AutoCompleteTextView.binding.actual.kt
// Package: com.lightningkite.khrysalis.observables.binding
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getView.convertView TS convertView
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getView.view TS view
// FQImport: android.widget.AutoCompleteTextView.textColors TS getAndroidWidgetAutoCompleteTextViewTextColors
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.filter TS getComLightningkiteKhrysalisObservablesBindingBind<no name provided>Filter
// FQImport: android.os.Build.VERSION_CODES.LOLLIPOP TS LOLLIPOP
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getView.position TS position
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getFilter.<no name provided>.performFiltering.<anonymous>.<anonymous>.<anonymous>.q TS q
// FQImport: android.os.Build TS Build
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getFilter.<no name provided>.performFiltering.<anonymous>.query TS query
// FQImport: com.lightningkite.khrysalis.observables.subscribeBy TS comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy
// FQImport: android.view.ViewGroup TS ViewGroup
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getFilter.<no name provided>.convertResultToString.resultValue TS resultValue
// FQImport: kotlin.CharSequence.toString TS toString
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.toString TS toString
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getView.<anonymous>.size TS size
// FQImport: com.lightningkite.khrysalis.rx.until TS ioReactivexDisposablesDisposableUntil
// FQImport: android.widget.Filter.FilterResults.values TS values
// FQImport: android.widget.AutoCompleteTextView.setAdapter TS setAdapter
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getFilter.<no name provided>.performFiltering.<anonymous>.newFilteredOptions TS newFilteredOptions
// FQImport: android.widget.AutoCompleteTextView.textSize TS getAndroidWidgetAutoCompleteTextViewTextSize
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getFilter.<no name provided>.publishResults.p1 TS p1
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getItem.position TS position
// FQImport: android.widget.TextView.letterSpacing TS setAndroidWidgetTextViewLetterSpacing
// FQImport: com.lightningkite.khrysalis.rx.removed TS getAndroidViewViewRemoved
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getFilter.<no name provided>.performFiltering.<anonymous>.<anonymous>.it TS it
// FQImport: android.widget.TextView.resources TS getAndroidWidgetTextViewResources
// FQImport: android.content.res.Resources.displayMetrics TS getAndroidContentResResourcesDisplayMetrics
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.<init>.<anonymous>.text TS text
// FQImport: android.content.Context.resources TS getAndroidContentContextResources
// FQImport: android.widget.Filterable TS Filterable
// FQImport: android.os.Build.VERSION.SDK_INT TS SDK_INT
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.notifyDataSetChanged TS notifyDataSetChanged
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getFilter.<no name provided>.performFiltering.p0 TS p0
// FQImport: android.widget.AutoCompleteTextView.maxLines TS getAndroidWidgetAutoCompleteTextViewMaxLines
// FQImport: android.os.Build.VERSION_CODES TS VERSION_CODES
// FQImport: android.widget.Filter.FilterResults TS FilterResults
// FQImport: android.widget.AutoCompleteTextView.letterSpacing TS getAndroidWidgetAutoCompleteTextViewLetterSpacing
// FQImport: android.widget.TextView.context TS getAndroidWidgetTextViewContext
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.options TS options
// FQImport: android.widget.BaseAdapter TS BaseAdapter
// FQImport: kotlin.CharSequence TS CharSequence
// FQImport: android.widget.Filter.FilterResults.count TS count
// FQImport: android.os.Build.VERSION_CODES.JELLY_BEAN TS JELLY_BEAN
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.T TS T
// FQImport: android.widget.TextView.textSize TS setAndroidWidgetTextViewTextSize
// FQImport: kotlin.text.contains TS kotlinCharSequenceContains
// FQImport: android.widget.TextView.setTextColor TS setTextColor
// FQImport: kotlin.collections.List.toString TS toString
// FQImport: android.widget.TextView.maxLines TS setAndroidWidgetTextViewMaxLines
// FQImport: android.widget.Filter TS Filter
// FQImport: android.widget.Filter.filter TS filter
// FQImport: android.os.Build.VERSION TS VERSION
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty TS ObservableProperty
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<no name provided>.getItemId.position TS position
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.onItemSelected TS onItemSelected
// FQImport: android.util.DisplayMetrics.scaledDensity TS scaledDensity
// FQImport: android.util.DisplayMetrics.density TS density
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.filtered TS filtered
// FQImport: kotlin.collections.getOrNull TS kotlinCollectionsListGetOrNull
// FQImport: com.lightningkite.khrysalis.observables.binding.bind.<anonymous>.index TS index
// FQImport: android.widget.AutoCompleteTextView.setOnItemClickListener TS setOnItemClickListener
// FQImport: com.lightningkite.khrysalis.observables.ObservableProperty.value TS value
import { comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy } from './../ObservableProperty.ext.shared'
import { also, takeUnless, tryCastClass, tryCastInterface, tryCastPrimitive } from 'Kotlin'
import { getAndroidViewViewRemoved, ioReactivexDisposablesDisposableUntil } from './../../rx/DisposeCondition.actual'
import { ObservableProperty } from './../ObservableProperty.shared'

//! Declares com.lightningkite.khrysalis.observables.binding.bind
export function androidWidgetAutoCompleteTextViewBind<T>(this_: HTML_Element, options: ObservableProperty<Array<T>>, toString: (a: T) => string, onItemSelected: (a: T) => void): void{
    const filtered = Array.from(options.value);
    
    this_.setAdapter(new class Anon extends BaseAdapter implements Filterable {
            public static implementsInterfaceAndroidWidgetFilterable = true;
            public constructor() {
                super();
                ioReactivexDisposablesDisposableUntil(comLightningkiteKhrysalisObservablesObservablePropertySubscribeBy(options, undefined, undefined, (text) => {
                            getComLightningkiteKhrysalisObservablesBindingBind<no name provided>Filter(this).filter(text.toString())
                }), getAndroidViewViewRemoved(this_));
            }
            
            
            getFilter(): Filter{ return new class Anon extends Filter {
                    public constructor() {
                        super();
                    }
                    
                    performFiltering(p0: (CharSequence | null)): FilterResults{ return also(FilterResults.constructor(), (this_1) => {
                                const query = ((_it)=>{
                                        if(_it === null) return null;
                                        return takeUnless(_it, (it) => it.length === 0)
                                })(((_it)=>{
                                            if(_it === null) return null;
                                            return _it.split(" ")
                                })(p0?.toString()));
                                
                                if (!(query.equals(null))) {
                                    const newFilteredOptions = options.value.filter((it) => query.every((q) => kotlinCharSequenceContains((toString)(it), q, true)));
                                    
                                    this_1.values = newFilteredOptions;
                                    this_1.count = newFilteredOptions.length;
                                } else {
                                    this_1.values = options.value;
                                    this_1.count = options.value.length;
                                }
                    }); }
                    
                    convertResultToString(resultValue: (any | null)): CharSequence{
                        return ((_it)=>{
                                if(_it === null) return null;
                                return (toString)(_it)
                        })((tryCastPrimitive<T>(resultValue, "any"))) ?? "";
                    }
                    
                    publishResults(p0: (CharSequence | null), p1: (FilterResults | null)): void{
                        filtered.length = 0;
                        filtered.push(...tryCastInterface<Array<T>>(p1?.values, "KotlinCollectionsList") ?? []);
                        this.notifyDataSetChanged();
                    }
            }(); }
            
            
            
            getView(position: number, convertView: (HTMLElement | null), parent: (ViewGroup | null)): HTMLElement{
                const view = (tryCastClass<HTMLElement>(convertView, HTMLElement)) ?? also(document.createElement('p'), (this_1) => {
                        this_1.setTextColor(getAndroidWidgetAutoCompleteTextViewTextColors(this_));
                        setAndroidWidgetTextViewTextSize(this_1, getAndroidWidgetAutoCompleteTextViewTextSize(this_) / getAndroidContentResResourcesDisplayMetrics(getAndroidWidgetTextViewResources(this_1)).scaledDensity);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            setAndroidWidgetTextViewMaxLines(this_1, getAndroidWidgetAutoCompleteTextViewMaxLines(this_));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            setAndroidWidgetTextViewLetterSpacing(this_1, getAndroidWidgetAutoCompleteTextViewLetterSpacing(this_));
                        }
                        const size = Math.floor((getAndroidContentResResourcesDisplayMetrics(getAndroidContentContextResources(getAndroidWidgetTextViewContext(this_1))).density * 8));
                        
                        this_1.style.padding = `${size}px ${size}px ${size}px ${size}px `;
                });
                
                view.innerText = ((_it)=>{
                        if(_it === null) return null;
                        return (toString)(_it)
                })(kotlinCollectionsListGetOrNull(filtered, position));
                return view;
            }
            
            getItem(position: number): (any | null){ return kotlinCollectionsListGetOrNull(filtered, position); }
            getItemId(position: number): number{ return position; }
            getCount(): number{ return filtered.length; }
    }());
    this_.setOnItemClickListener((adapterView, view, index, id) => {
            const temp323 = kotlinCollectionsListGetOrNull(filtered, index);
            if(temp323 !== null) (onItemSelected)(temp323)
    });
}

