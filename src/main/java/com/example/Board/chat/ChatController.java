package com.example.Board.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay

        System.out.println(message.getName());
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
    }
    @GetMapping("/chat/")
    public String chat(){
        return "chat";
    }
}
