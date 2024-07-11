package com.telegramBot.scheduleBot.bot;


import com.telegramBot.scheduleBot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@PropertySource("classpath:application.properties")
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private MessageService messageService;
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
        if (update.hasMessage() || update.hasCallbackQuery()) {
            var sendMessage = messageService.updateHandler(update);
            sendAnswerMessage(sendMessage);
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
