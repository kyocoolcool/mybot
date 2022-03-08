package mybot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class Hello {
    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

}
