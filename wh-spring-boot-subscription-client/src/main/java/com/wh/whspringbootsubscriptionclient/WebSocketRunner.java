package com.wh.whspringbootsubscriptionclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

@Slf4j
public class WebSocketRunner implements CommandLineRunner {

    private static final String SUBSCRIPTION_URL = "ws://localhost:8080/wh";

    public WebSocketRunner() {
    }

    @Override
    public void run(String... args) throws Exception {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();

        ListenableFuture<StompSession> sessionAsync = stompClient.connect(SUBSCRIPTION_URL, sessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe("/", sessionHandler);
//        stompClient.connect(SUBSCRIPTION_URL, sessionHandler);
        new Scanner(System.in).nextLine(); // Don't close immediately.
    }
}
