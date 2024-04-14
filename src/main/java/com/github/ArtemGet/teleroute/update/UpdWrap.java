package com.github.ArtemGet.teleroute.update;

import java.util.Optional;

/**
 * Update wrapper, provide data required by routes and matches.
 * Implement on your own risk.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public interface UpdWrap<SrcUpdate> {
    /**
     * Provide update id.
     *
     * @return update id
     */
    Integer id();

    /**
     * Define is this update contains telegram command.
     *
     * @return is command
     */
    Boolean isCommand();

    /**
     * Provide update's text if exists.
     *
     * @return update's text
     */
    Optional<String> text();

    /**
     * Provide telegram update.
     *
     * @return telegram update
     */
    SrcUpdate src();
}
