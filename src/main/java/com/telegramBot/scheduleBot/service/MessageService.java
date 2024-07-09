package com.telegramBot.scheduleBot.service;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class MessageService {
    public SendMessage updateHandler(Update update) {
        var chatId = update.getMessage().getChatId();
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            return SendMessage.builder().text("Привет, " + update.getMessage().getFrom().getUserName()).chatId(String.valueOf(chatId)).build();
        } else if (update.getMessage().getText().startsWith("Faculty")) {
            return null;
        } else if (update.getMessage().getText().startsWith("Group")) {
            return null;
        }
        return null;
    }
}
