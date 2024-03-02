package aget.teleroute.update;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class TgBotsUpdateWrap implements UpdateWrap<Update> {
    private final Update telegramBotsUpdate;

    public TgBotsUpdateWrap(Update telegramBotsUpdate) {
        this.telegramBotsUpdate = telegramBotsUpdate;
    }

    @Override
    public Integer id() {
        return this.telegramBotsUpdate.getUpdateId();
    }

    @Override
    public boolean isCommand() {
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
    public Update source() {
        return this.telegramBotsUpdate;
    }
}
