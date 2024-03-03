package aget.teleroute.match;


import aget.teleroute.update.UpdateWrap;

/**
 * Match condition.
 * Feel free to implement.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public interface Match<SrcUpdate> {
    /**
     * Check if provided update matches condition.
     *
     * @param updateWrap update wrapper, provide data required by routes and matches.
     * @return condition match
     */
    Boolean match(UpdateWrap<SrcUpdate> updateWrap);
}
