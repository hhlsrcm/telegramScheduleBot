package com.telegramBot.scheduleBot.service;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class MessageService {
    public SendMessage updateHandler(Update update) {
        var chatId = update.getMessage().getChatId();
        var userName = update.getMessage().getFrom().getUserName();
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            return SendMessage.builder().text("Привет, " + userName).chatId(String.valueOf(chatId)).build();
        } else if (update.getMessage().getText().startsWith("/schedule")) {
            return null;
        }
        else {
            return SendMessage.builder().text("Неизвестная команда").chatId(String.valueOf(chatId)).build();
        }
    }

}
