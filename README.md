[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)

[![Maintainability](https://api.codeclimate.com/v1/badges/1e5c08074d3bc271fbb8/maintainability)](https://codeclimate.com/github/ArtemGet/teleroute/maintainability)
[![codecov](https://codecov.io/gh/ArtemGet/teleroute/graph/badge.svg?token=FCGJORYEN5)](https://codecov.io/gh/ArtemGet/teleroute)

# The goal

The goal of this project is to provide flexible telegram bot update routing.

## Getting started:

1) [TelegramBots integration (TODO)]()
2) [java-telegram-bot-api integration (TODO)]()
3) [your own implementation of telegram bot api (TODO)]()

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
if(!command.isPresent()){
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

#### 2.1)EndRoute
This route is actually just provide command:

```java
Optional<Cmd<YourUpdate, YourSend> command = new EndRoute<>(new YourCmd()).route(new YourUpdate());
//command.isPresent() == true
```

or empty if no command attached to it:

```java
Optional<Cmd<YourUpdate, YourSend> command = new EndRoute<YourUpdate, YourSend>().route(new YourUpdate());
//command.isPresent() == false;
```

#### 2.2)ForkRoute
This route is a fork between two routes, work like if with a condition represented by Match interface ancestor.
There are many options to use this route.

You could fork between two commands:

```java
new ForkRoute<>(
        new YourMatch(),
        new YourCommand(),
        new YourSpareCommand()
        );
```

or two routes:

```java
new ForkRoute<>(
        new YourMatch(),
        new EndRoute<>(new YourCommand()),
        new EndRoute<>(new YourSpareCommand())
        );
```

or one route or command with empty return in case of mismatch:

```java
new ForkRoute<>(
        new YourMatch(),
        new EndRoute<>(new YourCommand())
        );


new ForkRoute<>(
        new YourMatch(),
        new YourCommand()
);

//empty if YourMatch not matches passed update
```

So, you could try building your own routes based on your conditions:

```java
new ForkRoute<>(
        new YourMatch(),
        new ForkRoute<>(
                new YourAnotherMatch(),
                new YourCommand()
        ),
        new ForkRoute<>(
                new YourAnotherMatch(),
                new YourRoute(...),
                new ForkRoute<>(
                        new YourAnotherMatch(),
                        new YourCommand()
                )
        )
);
```

#### 2.3)DfsRoute
This route iterate over routes until suitable command is found:

```java
new DfsRoute<>(
        new ForkRoute<>(
                new YourMatch(),
                new YourCommand(),
                new YourAnotherCommand()
        ),
        new ForkRoute<>(
                new SomeMatch(),
                new DfsRoute<>(
                        ...
                ),
                new ForkRoute<>(
                        ...
                )
        ),
        new DfsRoute<>(
        ...
        ),
        ...,
        new EndRoute<>(new NotFoundCommand())
)
```

#### 2.4)RndRoute
This route pick any random route or command, this could be useful if you want random reaction for specific type of
content, like in [BreadBot](https://github.com/LEVLLN/bread_bot).

```java
new ForkRoute<>(
        new TextContentMatch(),
        new RndRoute<>(
                new TextReactCommand(),
                new AnotherTextReactCommand(),
                ...
        ),
        new ForkRoute<>(
                new StickerContentMatch(),
                new RndRoute<>(
                        new StickerReactCommand(),
                        new AnotherStickerReactCommand(),
                        ... 
                ),
                new ForkRoute<>(
                        ...
                )
        )
);
```

you could achieve same result using DfsRoute:

```java
new DfsRoute<>(
        new ForkRoute<>(
                new TextContentMatch(),
                new RndRoute<>(
                        new TextReactCommand(),
                        new AnotherTextReactCommand(),
                ...
                )   
        ),
        new ForkRoute<>(
                new StickerContentMatch(),
                new RndRoute<>(
                        new StickerReactCommand(),
                        new AnotherStickerReactCommand(),
                        ... 
                )
        ),
        new ForkRoute<>(
                ...
        ),
        ...
);
```

### 3) Matching

TODO
