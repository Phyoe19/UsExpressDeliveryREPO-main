import SockJS from "sockjs-client";
import { Stomp } from '@stomp/stompjs';

let stompClient = null;
let reconnectTimeout = null;

export function connectWebSocket(funCallback) {
  const socket = new SockJS('http://localhost:7071/websocket');
  stompClient = Stomp.over(socket);
  
  const onConnect = () => {
    console.log("WebSocket connected");
    if (funCallback && typeof funCallback === 'function') {
      funCallback();
    }
  };

  const onWebSocketError = (error) => {
    console.log("WebSocketError : ", error);
    // Handle WebSocket error
    reconnect();
  };

  const reconnect = () => {
    if (reconnectTimeout) {
      return;
    }

    console.log("WebSocket connection lost. Reconnecting...");
    reconnectTimeout = setTimeout(() => {
      reconnectTimeout = null;
      connectWebSocket(funCallback);
    }, 2000); // Adjust the delay between reconnection attempts as needed
  };

  stompClient.connect({}, onConnect, onWebSocketError);
}

export function sendMessage(destination, body) {
  if (stompClient && stompClient.connected) {
    stompClient.send(destination, {}, body);
  } else {
    console.log("WebSocket not connected. Message not sent.");
  }
}

export function subscribeToDestination(destination, callback) {
  if (stompClient && stompClient.connected) {
    stompClient.subscribe(destination, callback);
  } else {
    console.log("WebSocket not connected. Subscription not established.");
  }
}

export function disconnectWebSocket(funCallback) {
  if (stompClient) {
    stompClient.disconnect(() => {
      console.log('WebSocket disconnected');
      if (funCallback && typeof funCallback === 'function') {
        funCallback();
      }
      stompClient = null;
    });
  }
}

