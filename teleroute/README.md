[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)

[![Build](https://github.com/ArtemGet/teleroute/actions/workflows/maven.yaml/badge.svg)](https://github.com/ArtemGet/teleroute/actions/workflows/maven.yaml)
[![](https://jitpack.io/v/ArtemGet/teleroute.svg)](https://jitpack.io/#ArtemGet/teleroute)

[![Maintainability](https://api.codeclimate.com/v1/badges/1e5c08074d3bc271fbb8/maintainability)](https://codeclimate.com/github/ArtemGet/teleroute/maintainability)

# The goal

The goal of this project is to provide flexible telegram bot update routing.

## Getting started:
In case you want to use teleroute with other telegram library:

1) [TelegramBots integration](https://github.com/ArtemGet/teleroute.telegrambots)
2) [C# port](https://github.com/varya-kot/teleroute)

This library is distributed via [jitpack.io](https://jitpack.io/#ArtemGet/teleroute)

## Understanding of concept:

### 1) The idea:

As we get an update from telegram there always some routine to match given request to proper command processor,
so this library is a one of possible solutions to do it easy, solid and extensible.

To match update and command you should pass it to route, if update is suitable to any command that you configured
then route will return it or else Optional.empty(), so you could ignore request or process not found operation:

```java
Optional<YourCommand> commandOpt = route.route(update);
```

After update matched to command all you have to do is execute it and get Send as a result:

```java
if(command.isEmpty()) {
//not found processing or just ignore
}

YourCommand command = commandOpt.get();
YourSend send = command.execute(update);
```

In case you actually want to send the result to telegram, you should pass the telegram client to Send:

```java
send.send(yourTgClient);
```

YourCommand, YourSend, update and yourTgClient is probably something that you implement yourself, or is provided by
other telegram bot libraries. There will be plug-ins for this library supporting most popular java telegram libraries
soon, so you would not need to implement anything of that except commands, of course.

### 2) Routes

There some routes provided by this library that could decorate each other like matryoshkas:

#### 2.1)RouteEnd
This route is actually just provide command:

```java
new RouteEnd<>(new YourCmd())
    .route(new YourUpdate())
    .isEmpty();
//false
```

or empty if no command attached to it:

```java
new RouteEnd<YourUpdate, YourSend>()
    .route(new YourUpdate())
    .isEmpty();
//true;
```

#### 2.2)RouteFork
This route is a fork between two routes, work like if with a condition represented by Match interface ancestor.
There are many options to use this route.

You could fork between two commands:

```java
new RouteFork<>(
        new YourMatch(),
        new YourCommand(),
        new YourSpareCommand()
        );
```

or two routes:

```java
new RouteFork<>(
        new YourMatch(),
        new RouteEnd<>(new YourCommand()),
        new RouteEnd<>(new YourSpareCommand())
        );
```

or one route or command with empty return in case of mismatch:

```java
new RouteFork<>(
        new YourMatch(),
        new RouteEnd<>(new YourCommand())
        );


new RouteFork<>(
        new YourMatch(),
        new YourCommand()
);

//empty if YourMatch not matches passed update
```

So, you could try building your own routes based on your conditions:

```java
new RouteFork<>(
        new YourMatch(),
        new RouteFork<>(
                new YourAnotherMatch(),
                new YourCommand()
        ),
        new RouteFork<>(
                new YourAnotherMatch(),
                new YourRoute(...),
                new RouteFork<>(
                        new YourAnotherMatch(),
                        new YourCommand()
                )
        )
);
```

#### 2.3)RouteDfs
This route iterate over routes until suitable command is found:

```java
new RouteDfs<>(
        new RouteFork<>(
                new YourMatch(),
                new YourCommand(),
                new YourAnotherCommand()
        ),
        new RouteFork<>(
                new SomeMatch(),
                new RouteDfs<>(
                        ...
                ),
                new RouteFork<>(
                        ...
                )
        ),
        new RouteDfs<>(
        ...
        ),
        ...,
        new RouteEnd<>(new NotFoundCommand())
)
```

#### 2.4)RouteRnd
This route pick any random route or command, this could be useful if you want random reaction for specific type of
content, like in [BreadBot](https://github.com/LEVLLN/bread_bot).

```java
new RouteFork<>(
        new TextContentMatch(),
        new RouteRnd<>(
                new TextReactCommand(),
                new AnotherTextReactCommand(),
                ...
        ),
        new RouteFork<>(
                new StickerContentMatch(),
                new RouteRnd<>(
                        new StickerReactCommand(),
                        new AnotherStickerReactCommand(),
                        ... 
                ),
                new RouteFork<>(
                        ...
                )
        )
);
```

you could achieve same result using DfsRoute:

```java
new RouteDfs<>(
        new RouteFork<>(
                new TextContentMatch(),
                new RouteRnd<>(
                        new TextReactCommand(),
                        new AnotherTextReactCommand(),
                ...
                )   
        ),
        new RouteFork<>(
                new StickerContentMatch(),
                new RouteRnd<>(
                        new StickerReactCommand(),
                        new AnotherStickerReactCommand(),
                        ... 
                )
        ),
        new RouteFork<>(
                ...
        ),
        ...
);
```

### 3) Matching (Docs in progress)

