package aget.teleroute.update;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class TelegramBotsUpdWrap implements UpdWrap<Update> {
    private final Update telegramBotsUpdate;

    public TelegramBotsUpdWrap(final Update telegramBotsUpdate) {
        this.telegramBotsUpdate = telegramBotsUpdate;
    }

    @Override
    public Integer id() {
        return this.telegramBotsUpdate.getUpdateId();
    }

    @Override
    public Boolean isCommand() {
        return Optional.ofNullable(this.telegramBotsUpdate.getMessage())
                .map(Message::isCommand)
                .orElse(false);
    }

    @Override
    public Optional<String> text() {
        return Optional.ofNullable(this.telegramBotsUpdate.getMessage())
                .map(Message::getText);
    }

    @Override
    public Update src() {
        return this.telegramBotsUpdate;
    }
}
