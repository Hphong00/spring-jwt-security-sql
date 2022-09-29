//package com.example.springjwtsecuritysql.web;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//
//public class NotificationsResource {
//    private final NotificationDispatcher dispatcher;
//    @Autowired
//    public NotificationsController(NotificationDispatcher dispatcher) {
//        this.dispatcher = dispatcher;
//    }
//    @MessageMapping("/start")
//    public void start(StompHeaderAccessor stompHeaderAccessor) {
//        dispatcher.add(stompHeaderAccessor.getSessionId());
//    }
//    @MessageMapping("/stop")
//    public void stop(StompHeaderAccessor stompHeaderAccessor) {
//        dispatcher.remove(stompHeaderAccessor.getSessionId());
//    }
//}
