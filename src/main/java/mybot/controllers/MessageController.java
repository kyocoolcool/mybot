package mybot.controllers;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.mybot.mybot.Boss;
import com.mybot.mybot.util.BossProperty;
import com.mybot.mybot.util.Master;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@LineMessageHandler
public class MessageController {

    @Autowired
    LineMessagingClient lineMessagingClient;

    @Autowired
    BossProperty bossProperty;

    @EventMapping
    public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent) throws ExecutionException, InterruptedException, IOException {
        LocalDate now = LocalDate.now();
        Map<Integer, Boss> bossMap = bossProperty.getBossMap();
        String[] s = messageEvent.getMessage().getText().split(" ");

        /* Retrieve information from the message */
        Source source = messageEvent.getSource();
        String lineId = source.getUserId();
        String replyToken = messageEvent.getReplyToken();
        String message = messageEvent.getMessage().getText();
        String groupId = messageEvent.getSource().getSenderId();
//        /* Get user's display name */
//        String displayName = lineMessagingClient
//                .getProfile(lineId)
//                .get()
//                .getDisplayName();
        Path path = Path.of("./" + groupId);
        boolean exists = Files.exists(path);
        if (!exists) {
            Path file = Files.createFile(path);
//            ArrayList<String> bossList = new ArrayList<>();
//            StringBuilder stringBuilder = new StringBuilder();
            bossMap.forEach((u, v) ->
            {
                try {
                    Files.writeString(path, new StringBuilder().append(v.getOrder()).append(" ").append(v.getType()).append(" ").append(v.getName()).append(" ").append(v.getTime()).append("\n").toString(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
//                    bossList.add(stringBuilder.append(v.getOrder()).append(" ").append(v.getType()).append(" ").append(v.getName()).append(v.getTime()).append("\n").toString()));
//            Files.writeString(path, bossList.toString());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        ;
        ;
        List<String> result = Files.readAllLines(path);
        Map<String, Boss> readBossMap = new HashMap<String, Boss>();
        result.forEach(details -> {
            String[] detail = details.split(" ");
            readBossMap.put(detail[0], new Boss(Integer.valueOf(detail[0]), detail[1], detail[2], detail[3] + " " + detail[4], LocalDateTime.parse(detail[3] + " " + detail[4], formatter)));
        });

        LinkedHashMap<String, Boss> collect = readBossMap.entrySet().stream()
                .sorted((x, y) -> x.getValue().getLocalDateTime().compareTo(y.getValue().getLocalDateTime())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));


        StringBuilder responseStringBuilder = new StringBuilder();

        if (s[0].equalsIgnoreCase("boss")) {
            collect.forEach((u, v) -> responseStringBuilder.append(v.getName()).append(" ").append(v.getTime()).append("\n"));
            //TextMessage responseMessage = new TextMessage(answer);
            TextMessage responseMessage = new TextMessage(responseStringBuilder.toString());
            /* Sending the respone */
            lineMessagingClient.replyMessage(new ReplyMessage(replyToken, responseMessage));
        } else if (s[0].equalsIgnoreCase("kill") || s[0].equalsIgnoreCase("k")) {
            Stream<Master> masterStream = EnumSet.allOf(Master.class).stream().filter(x -> x.name().equals(s[1]));
            if (masterStream.count() > 0) {
                Master master = Master.valueOf(s[1]);
                LocalDateTime bossBirthTime = null;
                if (s.length == 3) {
                    Integer killHour = Integer.valueOf(s[2].substring(0, 2));
                    Integer killMinute = Integer.valueOf(s[2].substring(2, 4));
                    bossBirthTime = now.atTime(killHour, killMinute).plusHours(12);
                } else if (s.length == 4) {
                    String bossDeadTime = s[2] + " " + s[3];
                    bossBirthTime = LocalDateTime.parse(bossDeadTime, formatter).plusHours(12);
                }

//            LocalTime bossTime = LocalTime.of(hh, mm);

//                LocalTime localTime = bossDeadTime.plusHours(12);
//            String format = localTime.toString().replace(":", "");
//                String format = formatter.format(localTime);
//                System.out.println(format);
                Boss boss = readBossMap.get(String.valueOf(master.label));
                boss.setLocalDateTime(bossBirthTime);
                boss.setTime(bossBirthTime.format(formatter));
                readBossMap.put(String.valueOf(master.label), boss);
                Files.deleteIfExists(path);
                Files.createFile(path);
                readBossMap.forEach((u, v) ->
                {
                    try {
                        Files.writeString(path, new StringBuilder().append(v.getOrder()).append(" ").append(v.getType()).append(" ").append(v.getName()).append(" ").append(v.getTime()).append("\n").toString(), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
//
//                StringBuilder stringBuilder2 = new StringBuilder();
//                result.forEach(x -> stringBuilder2.append(x + "\n"));
//                Files.writeString(path, stringBuilder2.toString()); // UTF 8
                //        TextMessage responseMessage = new TextMessage(answer);
                TextMessage responseMessage = new TextMessage(s[1] + " 下一次更新時間:" + bossBirthTime.format(formatter));
                /* Sending the respone */
                lineMessagingClient.replyMessage(new ReplyMessage(replyToken, responseMessage));

            }
        }
    }

    /* Building the response */
//        String answer = String.format("Hello, %s! Your message is %s", displayName, message);
}
