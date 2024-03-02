package aget.teleroute.update;

import java.util.Optional;

/**
 * Update wrapper, provide data required by routes and matches.
 * Implement on your own risk.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public interface Update<SrcUpdate> {
    /**
     * Provide update id.
     *
     * @return update id
     */
    Integer id();

    /**
     * Define is this update contains tg command.
     *
     * @return is command
     */
    boolean isCommand();

    /**
     * Provide update's text if exists.
     *
     * @return update's text
     */
    Optional<String> text();

    /**
     * Provide tg update.
     *
     * @return tg update
     */
    SrcUpdate source();
}
