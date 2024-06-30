package com.learn.blog.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @MessageMapping("/firstMessage")
    public String handle(String message) {
        System.out.println(String.format("my first message come from client is %s", message));
        return "OK";
    }
}
