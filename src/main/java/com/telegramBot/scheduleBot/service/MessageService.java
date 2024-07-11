package com.telegramBot.scheduleBot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;


@Service
public class MessageService {
    @Autowired
    ScheduleService scheduleService;

    public SendMessage updateHandler(Update update) {
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().startsWith("faculty")) {
                return groupKeyBoard(update.getCallbackQuery().getMessage().getChatId());
            } else if (update.getCallbackQuery().getData().startsWith("group")) {
                return scheduleService.getSchedule(update.getCallbackQuery().getMessage().getChatId());
            }
        } else if (update.getMessage().getText().equals("/start")) {
            return SendMessage.builder().text("Привет, " + update.getMessage().getFrom().getUserName()).chatId(String.valueOf(update.getMessage().getChatId())).build();
        } else if (update.getMessage().getText().startsWith("/schedule")) {
            return facultyKeyBoard(update.getMessage().getChatId());
        } else {
            return SendMessage.builder().text("Неизвестная команда").chatId(String.valueOf(update.getMessage().getChatId())).build();
        }
        return null;
    }

    private SendMessage groupKeyBoard(Long chatId) {
        var group1 = InlineKeyboardButton.builder()
                .text("111").callbackData("group111")
                .build();
        var group2 = InlineKeyboardButton.builder()
                .text("112").callbackData("group112")
                .build();
        InlineKeyboardMarkup keyboardForGroup = InlineKeyboardMarkup.builder().keyboardRow(List.of(group1)).keyboardRow(List.of(group2)).build();

        return SendMessage.builder().text("Выберите группу").replyMarkup(keyboardForGroup).chatId(String.valueOf(chatId)).parseMode("HTML").build();
    }

    private SendMessage facultyKeyBoard(Long chatId) {
        var faculty1 = InlineKeyboardButton.builder()
                .text("Ифиж").callbackData("faculty1")
                .build();
        var faculty2 = InlineKeyboardButton.builder()
                .text("Мех-мат").callbackData("faculty2")
                .build();
        InlineKeyboardMarkup keyboardForFaculty = InlineKeyboardMarkup.builder().keyboardRow(List.of(faculty1)).keyboardRow(List.of(faculty2)).build();

        return SendMessage.builder().text("Выберите факультет").replyMarkup(keyboardForFaculty).chatId(String.valueOf(chatId)).parseMode("HTML").build();
    }
}