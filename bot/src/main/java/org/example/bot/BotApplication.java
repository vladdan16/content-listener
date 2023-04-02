package org.example.bot;

import org.example.bot.configuration.ApplicationConfig;
import org.example.bot.core.MyBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        MyBot bot = new MyBot(ctx, config);
        //bot.close();
        bot.start();
        System.out.println(config);
    }
}
