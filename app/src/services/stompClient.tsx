import Stomp from "stompjs";

export function initStompClient(url: string) {
    return (stompClient = Stomp.client(url));
}

export let stompClient : Stomp.Client