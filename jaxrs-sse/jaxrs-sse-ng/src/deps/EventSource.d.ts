interface Callback { (data: any): void; }

declare enum ReadyState {CONNECTING = 0, OPEN = 1, CLOSED = 2}

declare class EventSource extends EventTarget {

    constructor(url: string);

    onerror: (event: ErrorEvent) => any;
    onmessage: Callback;
    onopen: (event: Event) => any;
    readonly readyState : ReadyState;
    readonly url: string;

    close: () => void;

    // From EventTarget
    addEventListener(type: string, listener: Callback): void;
    addEventListener(type: string, listener: Callback, useCapture?: boolean): void;
    
}