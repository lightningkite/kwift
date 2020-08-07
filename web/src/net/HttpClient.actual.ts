// Generated by Khrysalis TypeScript converter
// File: net/HttpClient.actual.kt
// Package: com.lightningkite.khrysalis.net
import {from, Observable, SchedulerLike, using} from 'rxjs'
import {ConnectedWebSocket} from "./ConnectedWebSocket.actual";
import {HttpBody} from "./HttpBody.actual";

//! Declares com.lightningkite.khrysalis.net.HttpClient
export class HttpClient {
    public static INSTANCE = new HttpClient();

    public readonly GET = "GET";
    public readonly POST = "POST";
    public readonly PUT = "PUT";
    public readonly PATCH = "PATCH";
    public readonly DELETE = "DELETE";

    //--- HttpClient.ioScheduler
    public ioScheduler: SchedulerLike | null = null

    //--- HttpClient.responseScheduler
    public responseScheduler: SchedulerLike | null = null

    call(
        url: string,
        method: string = HttpClient.INSTANCE.GET,
        headers: Map<string, string> = new Map([]),
        body: (HttpBody | null) = null
    ): Observable<Response> {
        let h = new Array(...headers.entries());
        if(body !== null){
            h.push(["Content-Type", body.type]);
        }
        return from(fetch(url, {
            body: body?.data,
            cache: "no-cache",
            credentials: "omit",
            headers: h,
            method: method
        }))
    }
    
    webSocket(url: string): Observable<ConnectedWebSocket>{
        return using<ConnectedWebSocket>(
            ()=> {
                const out = new ConnectedWebSocket(url)
                // out.underlyingSocket =
                return out
            },
            (r) => (r as ConnectedWebSocket).ownConnection
        )
    }
}
