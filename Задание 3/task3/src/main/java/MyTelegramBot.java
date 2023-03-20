import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = "6166612804:AAGnJ1abCwVgrIJFA1lO2K_1oNN1S2YFcVE";
    private static final String BOT_USERNAME = "JAVAFREE10CV_BOT";
    //    private static final String URL = "https://api.nasa.gov/planetary/apod?api_key=2M1rlk4xCyq57D8BtfeKsJc4q374YArKOLfGNFKp";
    private static final String URL = "https://api.nasa.gov/planetary/apod?api_key=2M1rlk4xCyq57D8BtfeKsJc4q374YArKOLfGNFKp&date=2021-03-18";
    private static Long chatId;

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        }

        switch (update.getMessage().getText()) {
            case "/start":
                sendMessage("Привет я бот, который присылает картинку от NASA");
                break;
            case "/help":
                sendMessage("Чтобы получить картинку от NASA, введи /give");
                break;
            case "/give":
                try {
                    sendMessage(Utils.getURL(URL));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                sendMessage("Моя твоя не понимай");
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
