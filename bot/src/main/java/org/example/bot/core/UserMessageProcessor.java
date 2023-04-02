package org.example.bot.core;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.example.bot.client.ScrapperClient;
import org.example.bot.client.dto.AddLinkRequest;
import org.example.bot.client.dto.LinkResponse;
import org.example.bot.client.dto.ListLinksResponse;
import org.example.bot.client.dto.RemoveLinkRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to process messages from telegram bot
 */
public class UserMessageProcessor {

    private final ScrapperClient scrapperClient;

    public UserMessageProcessor(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    /**
     * List of all possible commands
     * @return List<Command>
     */
    public List<? extends Command> commands() {
        return Arrays.asList(
                new StartCommand(),
                new HelpCommand(),
                new TrackCommand(),
                new UntrackCommand(),
                new ListCommand()
        );
    }

    /**
     * Process update from telegram bot to reply to user
     * @param update Update from bot
     * @return SendMessage instance for our bot
     */
    SendMessage process(Update update) {
        if (update.message() == null) { // do nothing
            return null;
        }
        long chatId = update.message().chat().id();

        String command = update.message().text().split(" ")[0];

        if (command.startsWith("/")) {
            for (Command c : commands()) {
                if (c.supports(update)) {
                    return c.handle(update);
                }
            }
        }
        return new SendMessage(chatId, "Sorry, I don't understand you. Try /help to see list of commands");
    }

    /**
     * Class for /start command
     */
    private class StartCommand implements Command {
        @Override
        public String command() {
            return "/start";
        }

        @Override
        public String description() {
            return "Register user";
        }

        @Override
        public SendMessage handle(Update update) {
            long chatId = update.message().chat().id();
            scrapperClient.registerChat(chatId);
            return new SendMessage(chatId, "Welcome! Type /help for a list of commands.");
        }
    }

    /**
     * Class for /help command
     */
    private class HelpCommand implements Command {
        @Override
        public String command() {
            return "/help";
        }

        @Override
        public String description() {
            return "Display a list of commands";
        }

        @Override
        public SendMessage handle(Update update) {
            StringBuilder builder = new StringBuilder();
            builder.append("Available commands:\n");
            for (Command c : commands()) {
                builder.append(c.command());
                builder.append(" - ");
                builder.append(c.description());
                builder.append("\n");
            }
            return new SendMessage(update.message().chat().id(), builder.toString());
        }
    }

    /**
     * Class for /track command
     */
    private class TrackCommand implements Command {

        @Override
        public String command() {
            return "/track";
        }

        @Override
        public String description() {
            return "Start tracking a link. To track use command in the following format \"/track LINK_TO_TRACK\"";
        }

        @Override
        public SendMessage handle(Update update) {
            long chatId = update.message().chat().id();
            String message = update.message().text();
            String link = message.substring(command().length()).trim();
            scrapperClient.addLink(chatId, new AddLinkRequest(link));
            return new SendMessage(chatId, "Started tracking " + link);
        }

        @Override
        public boolean supports(Update update) {
            String message = update.message().text();
            String[] msg = message.split(" ");
            return msg.length == 2 && msg[0].equals(command());
        }
    }

    /**
     * Class for /untrack command
     */
    private class UntrackCommand implements Command {

        @Override
        public String command() {
            return "/untrack";
        }

        @Override
        public String description() {
            return "Stop tracking a link. To untrack use command in the following format \"/untrack LINK_TO_UNTRACK\"";
        }

        @Override
        public SendMessage handle(Update update) {
            long chatId = update.message().chat().id();
            String message = update.message().text();
            String link = message.substring(command().length()).trim();
            // TODO: implement untracking
            scrapperClient.removeLink(chatId, new RemoveLinkRequest(link));
            return new SendMessage(chatId, "Link was successfully removed");
        }

        @Override
        public boolean supports(Update update) {
            String message = update.message().text();
            String[] msg = message.split(" ");
            return msg.length == 2 && msg[0].equals(command());
        }
    }

    /**
     * Class for /list command
     */
    private class ListCommand implements Command {

        @Override
        public String command() {
            return "/list";
        }

        @Override
        public String description() {
            return "Command to list all tracking links";
        }

        @Override
        public SendMessage handle(Update update) {
            long chatId = update.message().chat().id();
            ListLinksResponse response = scrapperClient.getLinks(chatId);
            List<String> links = new ArrayList<>();
            for (LinkResponse r : response.links()) {
                links.add(r.url().toString());
            }
            if (links.isEmpty()) {
                return new SendMessage(chatId, "There are no links to track");
            }
            StringBuilder builder = new StringBuilder();
            builder.append("Here is the list of all tracked links\n");
            for (String link : links) {
                builder.append(link).append("\n");
            }
            return new SendMessage(chatId, builder.toString());
        }
    }
}
