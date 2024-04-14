package aget.teleroute.match;

import aget.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AllMatchTest {

    @Test
    void match_shouldMatch_whenNoConditionSpecified() {
        Assertions.assertTrue(
                new AllMatch<String>().match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldMatch_whenAllMatch() {
        Assertions.assertTrue(
                new AllMatch<>(
                        new FkMatch(),
                        new FkMatch()
                )
                .match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldNotMatch_whenAnyNotMatch() {
        Assertions.assertFalse(
                new AllMatch<>(
                        new FkMatch(false),
                        new FkMatch()
                )
                .match(new FkUpdWrap())
        );
    }
}
