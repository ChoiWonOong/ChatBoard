package com.example.Board.chat;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Greeting {

    private String contents;

    public Greeting() {
    }

    public Greeting(String content) {
        this.contents = content;
    }

    public String getContent() {
        return contents;
    }

}
