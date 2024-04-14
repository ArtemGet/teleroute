package aget.teleroute.match;


import aget.teleroute.update.UpdWrap;

/**
 * Match condition.
 * Feel free to implement.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public interface Match<U> {
    /**
     * Check if provided update matches condition.
     *
     * @param update provide data required by routes and matches.
     * @return condition match
     */
    Boolean match(UpdWrap<U> update);
}
