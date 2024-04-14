package com.github.ArtemGet.teleroute.match;

import com.github.ArtemGet.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AnyMatchTest {

    @Test
    void match_shouldMatch_whenNoConditionSpecified() {
        Assertions.assertTrue(
                new AnyMatch<String>().match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldMatch_whenAllMatch() {
        Assertions.assertTrue(
                new AnyMatch<>(
                        new FkMatch(),
                        new FkMatch()
                )
                .match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldMatch_whenAnyMatch() {
        Assertions.assertTrue(
                new AnyMatch<>(
                        new FkMatch(false),
                        new FkMatch()
                )
                .match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldNotMatch_whenNoneMatch() {
        Assertions.assertFalse(
                new AnyMatch<>(
                        new FkMatch(false),
                        new FkMatch(false)
                )
                .match(new FkUpdWrap())
        );
    }
}
