package com.github.ArtemGet.teleroute.match.common;

import com.github.ArtemGet.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FullTxtMatchTest {

    @Test
    void match_shouldMatch_whenFullTextMatch() {
        Assertions.assertTrue(
                new FullTxtMatch<String>("text").match(new FkUpdWrap())
        );
    }

    @Test
    void match_shouldNotMatch_whenFullTextNotMatch() {
        Assertions.assertFalse(
                new FullTxtMatch<String>("not").match(new FkUpdWrap())
        );
    }
}
