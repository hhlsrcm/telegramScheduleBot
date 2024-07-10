package com.telegramBot.scheduleBot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleItem {
    private String type;
    private String title;
    private String teacher;
    private Integer building;
    private Integer classroom;
}
