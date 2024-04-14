package com.github.ArtemGet.teleroute.match.common;

import com.github.ArtemGet.teleroute.match.Match;
import com.github.ArtemGet.teleroute.update.UpdWrap;

/**
 * Check update contains command.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class CmdMatch<U> implements Match<U> {
    @Override
    public Boolean match(final UpdWrap<U> update) {
        return update.isCommand();
    }
}
