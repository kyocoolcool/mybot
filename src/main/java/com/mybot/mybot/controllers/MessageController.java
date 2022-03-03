package com.mybot.mybot.controllers;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class MessageController {

    @Autowired
    LineMessagingClient lineMessagingClient;

    @EventMapping
    public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent) throws ExecutionException, InterruptedException, IOException {
        String[] s = messageEvent.getMessage().getText().split(" ");

        /* Retrieve information from the message */
        Source source = messageEvent.getSource();
        String lineId = source.getUserId();
        String replyToken = messageEvent.getReplyToken();
        String message = messageEvent.getMessage().getText();
        String groupId = messageEvent.getSource().getSenderId();
        /* Get user's display name */
        String displayName = lineMessagingClient
                .getProfile(lineId)
                .get()
                .getDisplayName();
        Path path = Path.of("./" + groupId);
        boolean exists = Files.exists(path);
        System.out.println(exists);
        if (!exists) {
            Path file = Files.createFile(path);
            System.out.println(file);
            ArrayList<String> strings = new ArrayList<>();
            strings.add("1 克洛林 0000");
            strings.add("2 奈克 0000");
            strings.add("3 烏勒 0000");
            strings.add("4 四色 0000");
            strings.add("5 大黑 0000");
            strings.add("6 螞蟻 0000");
            strings.add("7 蛇女 0000");
            StringBuilder stringBuilder3 = new StringBuilder();
            strings.forEach(x -> stringBuilder3.append(x + "\n"));
            Files.writeString(path, stringBuilder3.toString()); // UTF 8
        }
        List<String> result = Files.readAllLines(path);
        StringBuilder stringBuilder = new StringBuilder();
        if (s[0].equals("boss")) {
            result.forEach(x -> stringBuilder.append(x + "\n"));
            //        TextMessage responseMessage = new TextMessage(answer);
            TextMessage responseMessage = new TextMessage(stringBuilder.toString());
            /* Sending the respone */
            lineMessagingClient.replyMessage(new ReplyMessage(replyToken, responseMessage));
        } else if (s[0].equals("kill")) {
            Integer hh = Integer.valueOf(s[2].substring(0, 2));
            Integer mm = Integer.valueOf(s[2].substring(2, 4));
            LocalTime bossTime = LocalTime.of(hh, mm);
            LocalTime localTime = bossTime.plusHours(12);
            String format = localTime.toString().replace(":", "");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
//            String format = formatter.format(localTime);
            System.out.println(format);
            switch (s[1]) {
                case "克洛林":
                    result.set(0, "1 " + s[1] + " " + format);
                    break;
                case "奈克":
                    result.set(1, "2 " + s[1] + " " + format);
                    break;
                case "烏勒":
                    result.set(2, "3 " + s[1] + " " + format);
                    break;
                case "四色":
                    result.set(3, "4 " + s[1] + " " + format);
                    break;
                case "大黑":
                    result.set(4, "5 " + s[1] + " " + format);
                    break;
                case "螞蟻":
                    result.set(5, "6 " + s[1] + " " + format);
                    break;
                case "蛇女":
                    result.set(6, "7 " + s[1] + " " + format);
                    break;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            result.forEach(x -> stringBuilder2.append(x + "\n"));
            Files.writeString(path, stringBuilder2.toString()); // UTF 8
            //        TextMessage responseMessage = new TextMessage(answer);
            TextMessage responseMessage = new TextMessage(s[1] + " 下一次更新時間:" + format);
            /* Sending the respone */
            lineMessagingClient.replyMessage(new ReplyMessage(replyToken, responseMessage));

        }
        ;
    }

    /* Building the response */
//        String answer = String.format("Hello, %s! Your message is %s", displayName, message);
}
