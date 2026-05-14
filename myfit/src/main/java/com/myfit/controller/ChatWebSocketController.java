package com.myfit.controller;

import com.myfit.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatWebSocketController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message broadcast(Message message) {

        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}
