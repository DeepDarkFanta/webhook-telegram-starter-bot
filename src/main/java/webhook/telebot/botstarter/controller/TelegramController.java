package webhook.telebot.botstarter.controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import webhook.telebot.botstarter.service.MainWebhookBotService;

@RestController
public class TelegramController {
    private MainWebhookBotService mainWebhookBotService;

    public TelegramController(MainWebhookBotService mainWebhookBotService) {
        this.mainWebhookBotService = mainWebhookBotService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return mainWebhookBotService.onWebhookUpdateReceived(update);
    }
}
