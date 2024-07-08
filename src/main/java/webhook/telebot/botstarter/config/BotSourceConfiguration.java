package webhook.telebot.botstarter.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import webhook.telebot.botstarter.service.MainWebhookBotService;

import java.io.IOException;

@Configuration
@Slf4j
public class BotSourceConfiguration {
    @Value("${telegrambot.webHookPath}")
    private String webhookPath;
    @Value("${telegrambot.userName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;

    @PostConstruct
    @Primary
    public void registerWebHook() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("https://api.telegram.org/bot" + botToken + "/setWebhook?url=" + webhookPath);
            HttpResponse response = client.execute(post);
            if (response == null) {
                log.error("from register webhook have response == null");
            }
            log.info("telegram: " + EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public MainWebhookBotService MySuperStealerBot() {
        DefaultBotOptions options = new DefaultBotOptions();
        MainWebhookBotService stealerBot = new MainWebhookBotService(options, botToken);
        stealerBot.setBotUserName(botUserName);
        stealerBot.setBotToken(botToken);
        stealerBot.setWebHookPath(webhookPath);

        return stealerBot;
    }
}
