interface Callback { (data: any): void; }

declare class EventSource {
    onmessage: Callback;
    onerror: Callback;
    addEventListener(event: string, cb: Callback): void;
    constructor(name: string);
    close(): void;
}