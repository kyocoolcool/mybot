package mybot.controllers;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import mybot.util.BossProperty;
import mybot.util.GCS;
import mybot.util.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public BossService bossService;

    public MessageController(BossService bossService) {
        this.bossService = bossService;
    }


        //    @Value("gs://${gcs-resource-test-bucket}/test")
//    private Resource gcsFile;
//    @Autowired
//    GCS gcs;

    @EventMapping
    public void handleTextEvent(MessageEvent<TextMessageContent> messageEvent) throws ExecutionException, InterruptedException, IOException {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Taipei"));
        String[] s = messageEvent.getMessage().getText().split(" ");
        Source source = messageEvent.getSource();
        String replyToken = messageEvent.getReplyToken();
        String groupId = messageEvent.getSource().getSenderId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        StringBuilder responseStringBuilder = new StringBuilder();
        List<mybot.controllers.Boss> allBoss = bossService.getAllBoss();
        if (s[0].equalsIgnoreCase("boss")) {
            ArrayList<Boss> collect = allBoss.stream().sorted(Comparator.comparing((t) -> {
                return t.getTime();
            })).collect(Collectors.toCollection(ArrayList::new));
            collect.forEach((u) -> responseStringBuilder.append(u.getName()).append(" ").append(u.getTime()).append("\n"));
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
                mybot.controllers.Boss boss = bossService.getBoss(String.valueOf(master.label));
                //NC改到古六時間
                if (boss.getBossId().equals("1")) {
                    bossBirthTime =bossBirthTime.plusHours(2);
                    bossBirthTime =bossBirthTime.plusMinutes(48);
                }
                boss.setTime(bossBirthTime.format(formatter));
                bossService.updateBoss(boss);
                //        TextMessage responseMessage = new TextMessage(answer);
                TextMessage responseMessage = new TextMessage(s[1] + " 下一次更新時間:" + bossBirthTime.format(formatter));
                /* Sending the respone */
                lineMessagingClient.replyMessage(new ReplyMessage(replyToken, responseMessage));
            }
        }
    }
}
