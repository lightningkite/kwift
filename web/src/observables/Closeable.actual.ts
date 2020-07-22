// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: observables/Closeable.actual.kt
// Package: com.lightningkite.khrysalis.observables
// FQImport: com.lightningkite.khrysalis.observables.Closeable TS CloseableDefaults
// FQImport: com.lightningkite.khrysalis.observables.Closeable.close TS close
// FQImport: com.lightningkite.khrysalis.observables.Close.closer TS closer
// FQImport: com.lightningkite.khrysalis.observables.Close.disposed TS disposed
// FQImport: com.lightningkite.khrysalis.observables.Closeable TS Closeable

//! Declares com.lightningkite.khrysalis.observables.Closeable
export interface Closeable {
    
    isDisposed(): boolean 
    
    dispose(): void 
    
    close(): void 
}
export namespace CloseableDefaults {
    export function isDisposed(this_: Closeable): boolean {
        return false;
    }
    export function dispose(this_: Closeable): void {
        this_.close();
    }
}

//! Declares com.lightningkite.khrysalis.observables.Close
export class Close implements Closeable {
    public static implementsInterfaceComLightningkiteKhrysalisObservablesCloseable = true;
    public static implementsInterfaceIoReactivexDisposablesDisposable = true;
    public readonly closer:  () => void;
    public constructor(closer:  () => void) {
        this.closer = closer;
        this.disposed = false;
    }
    
    public disposed: boolean;
    
    public isDisposed(): boolean {
        return this.disposed;
    }
    
    public close(): void {
        this.disposed = true;
        this.closer();
    }
    public dispose(): void { return CloseableDefaults.dispose(this); }
}

