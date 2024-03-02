package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.Update;

/**
 * Check update contains command.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class CmdMatch<SrcUpdate> implements Match<SrcUpdate> {
    @Override
    public boolean match(Update<SrcUpdate> update) {
        return update.isCommand();
    }
}
