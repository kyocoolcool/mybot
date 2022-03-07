package mybot.util;

import lombok.Getter;
import lombok.Setter;
import mybot.Boss;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties( prefix = "bosses")
public class BossProperty {
    private Map<Integer, Boss> bossMap;

    public Map<Integer, Boss> getBossMap() {
        return bossMap;
    }

    public void setBossMap(Map<Integer, Boss> bossMap) {
        this.bossMap = bossMap;
    }
}
