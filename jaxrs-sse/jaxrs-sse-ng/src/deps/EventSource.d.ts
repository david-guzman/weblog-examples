interface Callback { (data: any): void; }

//interface Options {
//    capture: boolean;
//    once: boolean;
//    passive: boolean;
//}

declare class EventSource {

    constructor(url: string);

    onerror: Callback;
    onmessage: Callback;
    onopen: Callback;
    readonly readyState : number;
    readonly url: string;

    close(): void;

    // From EventTarget
    addEventListener(type: string, listener: Callback): void;
//    addEventListener(type: string, listener: Callback, options?: Options): void;
    addEventListener(type: string, listener: Callback, useCapture?: boolean): void;
    
}