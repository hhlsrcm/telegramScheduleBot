package com.telegramBot.scheduleBot.bot;


import java.util.ArrayList;

import com.telegramBot.scheduleBot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

//TODO: исправить баг закрыть callback
@Component
@PropertySource("classpath:application.properties")
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    ScheduleService scheduleService;
    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;


    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            var chatId = update.getMessage().getChatId();
            var msg = update.getMessage().getText();
            switch (msg) {
                case "/start" -> sendMenu(update.getMessage().getChatId());
                case "Расписание" -> sendFacultySelection(chatId);
                case "Помощь" -> sendAnswerMessage(SendMessage.builder()
                        .text("Данный бот выдает расписание университета СГУ, нажмите на расписание, выберите факулитет и группу. У вас все получится!")
                        .chatId(String.valueOf(chatId))
                        .build());
                default -> sendAnswerMessage(SendMessage.builder()
                        .text("Такой команды нет")
                        .chatId(String.valueOf(chatId))
                        .build());
            }
        } else if (update.hasCallbackQuery()) {
            handleCallBackQuery(update.getCallbackQuery());
        } else {
            sendAnswerMessage(SendMessage.builder()
                    .text("Данный формат сообщений не поддерживается")
                    .chatId(String.valueOf(update.getMessage().getChatId()))
                    .build());
        }
    }

    private void sendMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> menu = new ArrayList<>();
        KeyboardRow commandList = new KeyboardRow();
        commandList.add(new KeyboardButton("Расписание"));
        commandList.add(new KeyboardButton("Помощь"));
        menu.add(commandList);
        replyKeyboardMarkup.setKeyboard(menu);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        sendAnswerMessage(SendMessage.builder()
                .text("Добро прожаловать, завтра на пары?")
                .chatId(String.valueOf(chatId))
                .replyMarkup(replyKeyboardMarkup)
                .build());
    }

    private void sendFacultySelection(long chatId) {
        var faculty1 = InlineKeyboardButton.builder().text("Ифиж").callbackData("faculty_ифиж").build();
        var faculty2 = InlineKeyboardButton.builder().text("Мех-мат").callbackData("faculty_мех-мат").build();
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboardRow(List.of(faculty1, faculty2)).build();
        sendAnswerMessage(SendMessage.builder()
                .text("Выберите факультет")
                .chatId(String.valueOf(chatId))
                .replyMarkup(inlineKeyboardMarkup)
                .build());
    }

    private void sendGroupSelection(long chatId, String faculty) {
        var group1 = InlineKeyboardButton.builder().text("111").callbackData("group111").build();
        var group2 = InlineKeyboardButton.builder().text("112").callbackData("group112").build();
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboardRow(List.of(group1, group2)).build();
        sendAnswerMessage(SendMessage.builder()
                .text("Выберите группу")
                .chatId(String.valueOf(chatId))
                .replyMarkup(inlineKeyboardMarkup)
                .build());
    }

    private void handleCallBackQuery(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().startsWith("faculty")) {
            sendGroupSelection(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getText());
        } else if (callbackQuery.getData().startsWith("group")) {
            sendAnswerMessage(SendMessage.builder()
                    .text(scheduleService.getSchedule())
                    .chatId(String.valueOf(callbackQuery.getMessage().getChatId()))
                    .parseMode("Html")
                    .build());
        }
    }

    private void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                // TODO
                e.printStackTrace();
            }
        }
    }

}
