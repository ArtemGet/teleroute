package aget.teleroute.update;

import java.util.Optional;

public interface Update<Source> {
    Integer id();

    boolean isCommand();

    Optional<String> text();

    Source source();
}
