package mybot;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;



public class Boss {
    private Integer order;
    private String type;
    private String name;
    @DateTimeFormat(pattern = "MM/dd HH:mm")
    private String time;
    private LocalDateTime localDateTime;

    public Boss() {
    }

    public Boss(Integer order, String type, String name, String time) {
        this.order = order;
        this.type = type;
        this.name = name;
        this.time = time;
    }

    public Boss(Integer order, String type, String name, String time, LocalDateTime localDateTime) {
        this.order = order;
        this.type = type;
        this.name = name;
        this.time = time;
        this.localDateTime = localDateTime;
    }

    public Integer getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "order=" + order +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
