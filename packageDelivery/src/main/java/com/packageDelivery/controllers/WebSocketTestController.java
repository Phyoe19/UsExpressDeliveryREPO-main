/**
 * 
 */
package com.packageDelivery.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author Acer
 *
 */



@Controller
public class WebSocketTestController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String handleChatMessage(String message) {
        return "Received message: " + message;
    }
}
