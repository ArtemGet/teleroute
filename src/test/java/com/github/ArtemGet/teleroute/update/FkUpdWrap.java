package com.github.ArtemGet.teleroute.update;

import java.util.Optional;

public class FkUpdWrap implements UpdWrap<String> {
    private static final Integer ID = 123;
    private static final Boolean IS_COMMAND = true;
    private static final String TEXT = "text";
    private static final String SRC = "src";

    private final Integer id;
    private final Boolean isCommand;
    private final String text;
    private final String src;

    public FkUpdWrap() {
        this(
                FkUpdWrap.ID,
                FkUpdWrap.IS_COMMAND,
                FkUpdWrap.TEXT,
                FkUpdWrap.SRC
        );
    }

    public FkUpdWrap(
            Integer id,
            Boolean isCommand,
            String text,
            String src
    ) {
        this.id = id;
        this.isCommand = isCommand;
        this.text = text;
        this.src = src;
    }

    @Override
    public Integer id() {
        return this.id;
    }

    @Override
    public Boolean isCommand() {
        return this.isCommand;
    }

    @Override
    public Optional<String> text() {
        return Optional.of(this.text);
    }

    @Override
    public String src() {
        return this.src;
    }
}
