
[![Maintainability](https://api.codeclimate.com/v1/badges/ef71ad44b66ffce3437d/maintainability)](https://codeclimate.com/github/ArtemGet/teleroute.telegrambots/maintainability)
[![](https://jitpack.io/v/ArtemGet/teleroute.telegrambots.svg)](https://jitpack.io/#ArtemGet/teleroute.telegrambots)

# Teleroute - TelegramBots integration plug-in
[Check teleroute repository](https://github.com/ArtemGet/teleroute)

[Check TelegramBots repository](https://github.com/rubenlagus/TelegramBots)

# Supported features:

### 1)Long polling
### 2)Sending all ancestors of BotApiMethod, e.g: SendMessage, SendPoll, SendContact...
### 3)Sending stickers via SendSticker

## Getting started:
This library is distributed via [jitpack.io](https://jitpack.io/#ArtemGet/teleroute.telegrambots)

## Usage example:
Imagine our goal is to write long polling telegram bot that greet newcomers or greet already existing users
by command - /greetmeplease. 

[Check Example source code (TODO)]()

Lets start by describing our bot class:

```java
public class Bot extends TelegramLongPollingBot {
    private final String name;
    private final Route<Update, AbsSender> route;

    public Bot(String botToken, String name, Route<Update, AbsSender> route) {
        super(botToken);
        this.name = name;
        this.route = route;
    }

    @Override
    public void onUpdateReceived(Update update) {
        this.route.route(new TelegramBotsUpdWrap(update))
                .flatMap(command -> command.execute(update))
                .ifPresent(send -> send.send(this));
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }
}
```

As you can see, in order to route update to suitable response we attached Route<Update, AbsSender> route to our bot,
that means we could route all TelegramBots Update classes to commands, execute them and send response via AbsSender
(for e.g TelegramLongPollingBot implements AbsSender, so you could pass just this to send method).

The possible Route configuration could be similar to:

```java
public class Example {
    public static void io(String[] args) throws TelegramApiException {
        Users users = new PgUsers("your_db_connection");
        new TelegramBotsApi(DefaultBotSession.class)
                .registerBot(
                        new Bot(
                                "your_token",
                                "your_bot_name",
                                new DfsRoute<>(
                                        new ForkRoute<>(
                                                new FirstLoginMatch(users),
                                                new NewGreetCommand(users)
                                        ),
                                        new ForkRoute<>(
                                                new AllMatch<>(
                                                        new CmdMatch<>(),
                                                        new FullTxtMatch<>("/greetmeplease")
                                                ),
                                                new OldGreetCommand(users)
                                        )
                                )
                        )
                );
    }
}
```

Where match condition implements like:

```java
public class FirstLoginMatch implements Match<Update> {
    private final Users users;

    public FirstLoginMatch(Users users) {
        this.users = users;
    }

    @Override
    public Boolean match(UpdWrap<Update> wrap) {
        return this.users.get(
                        wrap
                                .src()
                                .getMessage()
                                .getFrom()
                                .getId()
                )
                .isEmpty();
    }
}
```

Newcomers greet command:

```java
public class NewGreetCommand implements Cmd<Update, AbsSender> {
private final Users users;

    public NewGreetCommand(Users users) {
        this.users = users;
    }

    @Override
    public Optional<Send<AbsSender>> execute(Update update) {
        return Optional.of(
                new SendMessageWrap<>(
                        new SendMessage(
                                update.getMessage().getFrom().getId().toString(),
                                String.format(
                                        "Greetings! %s!",
                                        this.users.add(
                                                update.getMessage().getFrom().getId(),
                                                update.getMessage().getFrom().getUserName()
                                        ).name()
                                )
                        )
                )
        );
    }
}
```

Existing users command:

```java
public class OldGreetCommand implements Cmd<Update, AbsSender> {
    private final Users users;

    public OldGreetCommand(Users users) {
        this.users = users;
    }

    @Override
    public Optional<Send<AbsSender>> execute(Update update) {
        return Optional.of(
                new SendMessageWrap<>(
                        new SendMessage(
                                update.getMessage().getFrom().getId().toString(),
                                String.format(
                                        "Welcome back! %s!",
                                        this.users
                                                .get(update.getMessage().getFrom().getId())
                                                .orElse(
                                                        this.users.add(
                                                                update.getMessage().getFrom().getId(),
                                                                update.getMessage().getFrom().getUserName()
                                                        )
                                                )
                                )
                        )
                )
        );
    }
}
```

And business interfaces:

```java
public interface User {
    Long id();
    String name();
}
```

```java
public interface Users {
    Optional<User> get(Long id);

    User add(Long id, String name);
}
```
