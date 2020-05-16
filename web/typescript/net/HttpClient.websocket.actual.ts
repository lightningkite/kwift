// Generated by Khrysalis TypeScript converter - this file will be overwritten.
// File: net/HttpClient.websocket.actual.kt
// Package: com.lightningkite.khrysalis.net
// FQImport: okio.ByteString TS ByteString
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket._read TS _read
// FQImport: okhttp3.Request.Builder.url TS url
// FQImport: okhttp3.Headers TS Headers
// FQImport: kotlin.collections.Map TS Map
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.onNext.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket SKIPPED due to same file
// FQImport: language TS getJavaUtilLocaleLanguage
// FQImport: kotlin.Throwable.message TS message
// FQImport: okhttp3.Headers.of TS of
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.onNext.t TS t
// FQImport: kotlin.text.Charsets.UTF_8 TS UTF_8
// FQImport: com.lightningkite.khrysalis.net.webSocket.<anonymous>.it TS it
// FQImport: com.lightningkite.khrysalis.net.WebSocketFrame.binary TS binary
// FQImport: kotlin.ByteArray.size TS size
// FQImport: okhttp3.WebSocketListener TS WebSocketListener
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.onError.e TS e
// FQImport: okio.ByteString.size TS size
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.onMessage.text TS text
// FQImport: java.util.Locale.getDefault TS getDefault
// FQImport: com.lightningkite.khrysalis.net.WebSocketFrame SKIPPED due to same file
// FQImport: okhttp3.Request TS Request
// FQImport: okhttp3.Request.Builder.headers TS headers
// FQImport: okhttp3.OkHttpClient.newWebSocket TS newWebSocket
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.onNext.<anonymous>.binary TS binary
// FQImport: kotlin.Throwable TS Throwable
// FQImport: io.reactivex.subjects.PublishSubject TS PublishSubject
// FQImport: io.reactivex.subjects.PublishSubject.create TS create
// FQImport: com.lightningkite.khrysalis.net.WebSocketFrame.text TS text
// FQImport: okhttp3.WebSocket TS WebSocket
// FQImport: com.lightningkite.khrysalis.net.webSocket.headers TS headers
// FQImport: java.util.Locale TS Locale
// FQImport: okhttp3.Response TS Response
// FQImport: okhttp3.WebSocket.close TS close
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.url TS url
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.onFailure.t TS t
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.onMessage.bytes TS bytes
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.ownConnection TS ownConnection
// FQImport: com.lightningkite.khrysalis.net.HttpClient TS HttpClient
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket TS ConnectedWebSocket
// FQImport: okio.ByteString.of TS of
// FQImport: com.lightningkite.khrysalis.net.webSocket.<anonymous>.out TS out
// FQImport: io.reactivex.Observable TS Observable
// FQImport: okhttp3.Request.Builder.build TS build
// FQImport: com.lightningkite.khrysalis.net.ConnectedWebSocket.underlyingSocket TS underlyingSocket
// FQImport: kotlin.collections.toString TS kotlinByteArrayToString
// FQImport: com.lightningkite.khrysalis.net.HttpClient.threadCorrectly TS ioReactivexObservableThreadCorrectly
// FQImport: okhttp3.WebSocket.send TS send
// FQImport: okio.ByteString.toByteArray TS toByteArray
// FQImport: com.lightningkite.khrysalis.net.webSocket.url TS url
// FQImport: com.lightningkite.khrysalis.net.WebSocketFrame TS WebSocketFrame
// FQImport: io.reactivex.Observable.using TS using
// FQImport: com.lightningkite.khrysalis.net.HttpClient.client TS client
// FQImport: okhttp3.Request.Builder.addHeader TS addHeader
// FQImport: com.lightningkite.khrysalis.bytes.Data TS Data
import { Observable, Observer, SubscriptionLike } from 'rxjs'
import { Data } from './../bytes/Data.actual'
import { HttpClient } from './HttpClient.actual'

//! Declares com.lightningkite.khrysalis.net.webSocket
export function comLightningkiteKhrysalisNetHttpClientWebSocket(this_: HttpClient, url: string, headers: Map<string, string> = new Map([])): Observable<ConnectedWebSocket>{
    return Observable.using<ConnectedWebSocket, ConnectedWebSocket>(() => {
            const out = new ConnectedWebSocket(url);
            
            out.underlyingSocket = this_.client.newWebSocket(Request.Builder.constructor()
                .url(url.replace("http", "ws"))
                .headers(Headers.of(headers))
                .addHeader("Accept-Language", getJavaUtilLocaleLanguage(Locale.getDefault()))
            .build(), out);
            return out;
    }, (it) => it.ownConnection, (it) => it.complete());
}

//! Declares com.lightningkite.khrysalis.net.ConnectedWebSocket
export class ConnectedWebSocket extends WebSocketListener implements Observer<WebSocketFrame> {
    public static implementsInterfaceIoReactivexObserver = true;
    public readonly url: string;
    public constructor(url: string) {
        super();
        this.url = url;
        this.underlyingSocket = null;
        this._read = PublishSubject.create<WebSocketFrame>();
        this.ownConnection = PublishSubject.create<ConnectedWebSocket>();
        this.read = ((this_) => this_.ioReactivexObservableThreadCorrectly(this._read))(HttpClient.INSTANCE);
    }
    
    internal underlyingSocket: (WebSocket | null) = null;
    
    private readonly _read = PublishSubject.create<WebSocketFrame>();
    
    public readonly ownConnection = PublishSubject.create<ConnectedWebSocket>();
    
    public readonly read: Observable<WebSocketFrame> = ((this_) => this_.ioReactivexObservableThreadCorrectly(this._read))(HttpClient.INSTANCE);
    
    public onOpen(webSocket: WebSocket, response: Response){
        console.log(`Socket to ${this.url} opened successfully.`);
        this.ownConnection.next(this);
    }
    
    public onFailure(webSocket: WebSocket, t: Throwable, response: (Response | null)){
        console.log(`Socket to ${this.url} failed with ${t}.`);
        this.ownConnection.error(t);
        this._read.error(t);
    }
    
    public onClosing(webSocket: WebSocket, code: number, reason: string){
        console.log(`Socket to ${this.url} closing.`);
        this.ownConnection.complete();
        this._read.complete();
    }
    
    public onMessage(webSocket: WebSocket, text: string){
        console.log(`Socket to ${this.url} got message '${text}'.`);
        this._read.next(new WebSocketFrame(undefined, text));
    }
    
    public onMessage(webSocket: WebSocket, bytes: ByteString){
        console.log(`Socket to ${this.url} got binary message of length ${bytes.size()}.`);
        this._read.next(new WebSocketFrame(bytes.toByteArray(), undefined));
    }
    
    public onClosed(webSocket: WebSocket, code: number, reason: string){
        console.log(`Socket to ${this.url} closed.`);
    }
    
    public onComplete(){
        this.underlyingSocket?.close(1000, null);
    }
    
    public onSubscribe(d: SubscriptionLike){
    }
    
    public onNext(t: WebSocketFrame){
        const temp344 = t.text;
        if(temp344 !== null) ((it) => this.underlyingSocket?.send(it))(temp344);
        const temp346 = t.binary;
        if(temp346 !== null) ((binary) => this.underlyingSocket?.send(ByteString.of(binary, 0, binary.size)))(temp346);
    }
    
    public onError(e: Throwable){
        this.underlyingSocket?.close(1011, e.message);
    }
}

//! Declares com.lightningkite.khrysalis.net.WebSocketFrame
export class WebSocketFrame {
    public readonly binary: (Data | null);
    public readonly text: (string | null);
    public constructor(binary: (Data | null) = null, text: (string | null) = null) {
        this.binary = binary;
        this.text = text;
    }
    
    public toString(): string{
        return this.text ?: ((_it)=>{
                if(_it === null) return null;
                return kotlinByteArrayToString(_it, Charsets.INSTANCE.UTF_8)
        })(this.binary) ?: "<Empty Frame>";
    }
}

