import { ObservableProperty } from '../ObservableProperty.shared';
import { StandardObservableProperty } from '../StandardObservableProperty.shared';
export declare function androidxRecyclerviewWidgetRecyclerViewWhenScrolledToEnd(this_: HTMLDivElement, action: () => void): void;
export declare function getAndroidxRecyclerviewWidgetRecyclerViewReverseDirection(this_: HTMLDivElement): boolean;
export declare function setAndroidxRecyclerviewWidgetRecyclerViewReverseDirection(this_: HTMLDivElement, value: boolean): void;
export declare function androidxRecyclerviewWidgetRecyclerViewBind<T>(this_: HTMLDivElement, data: ObservableProperty<Array<T>>, defaultValue: T, makeView: (a: ObservableProperty<T>) => HTMLElement): void;
interface RvTypeHandlerHandler<T> {
    type: Array<any>;
    defaultValue: T;
    action: (a: ObservableProperty<T>) => HTMLElement;
    buffer: Array<[StandardObservableProperty<T>, HTMLElement]>;
}
export declare class RVTypeHandler {
    handlers: Array<RvTypeHandlerHandler<any>>;
    handle<T extends any>(T: Array<any>, defaultValue: T, action: (a: ObservableProperty<T>) => HTMLElement): void;
}
export declare function recyclerViewBindMultiType(this_: HTMLDivElement, viewDependency: Window, data: ObservableProperty<Array<any>>, typeHandlerSetup: (a: RVTypeHandler) => void): void;
export declare function androidxRecyclerviewWidgetRecyclerViewBindMulti<T>(this_: HTMLDivElement, data: ObservableProperty<Array<T>>, defaultValue: T, determineType: (a: T) => number, makeView: (a: number, b: ObservableProperty<T>) => HTMLElement): void;
export declare function androidxRecyclerviewWidgetRecyclerViewBindRefresh(this_: HTMLDivElement, loading: ObservableProperty<boolean>, refresh: () => void): void;
export {};
