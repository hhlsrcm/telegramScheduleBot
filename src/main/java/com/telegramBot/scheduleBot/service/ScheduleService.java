package com.telegramBot.scheduleBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ScheduleService {
    public SendMessage getSchedule(Long chatId){
        return SendMessage.builder().text("расписание будет но не сегодня").chatId(String.valueOf(chatId)).build();
    }
}
