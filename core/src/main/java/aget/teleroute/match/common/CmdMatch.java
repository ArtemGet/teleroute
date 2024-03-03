package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.UpdateWrap;

/**
 * Check update contains command.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class CmdMatch<SrcUpdate> implements Match<SrcUpdate> {
    @Override
    public Boolean match(final UpdateWrap<SrcUpdate> updateWrap) {
        return updateWrap.isCommand();
    }
}
