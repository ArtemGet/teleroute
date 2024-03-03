package aget.teleroute.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Send sticker wrap
 */
public class SendStickerWrap implements Send<AbsSender> {
    private static final Logger log = LoggerFactory.getLogger(SendStickerWrap.class);
    private final SendSticker sendSticker;

    /**
     * Main constructor. Construct SendStickerWrap.
     *
     * @param sendSticker telegrambots SendSticker
     */
    public SendStickerWrap(final SendSticker sendSticker) {
        this.sendSticker = sendSticker;
    }

    @Override
    public void send(final AbsSender send) {
        try {
            send.execute(this.sendSticker);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }
}
