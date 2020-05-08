## Player async messaging

### Topics
1. Installation
2. Running the clients
3. Sending a message

#### Installation
Use the easy provided installation tool `player-installer.sh`.

The requirements are:
- Docker
- Unix system
- (Optional) Java 11 for compilation
- (Optional) Maven 3.+ for compilation

Run the installer
```
# sh player-installer.sh
```

As a result 3 docker containers are created:
- `player-async_server_1`
- `player-async_player1_1`
- `player-async_player2_1`

#### Running the clients
After the installation 2 clients are running as docker containers `player-async_player1_1` and `player-async_player2_1`. Use `docker attach ...` in different terminals to have access to each service.

Terminal 1:
```
docker attach player-async_player1_1 --detach-keys=ctrl-c
```
Terminal 2:
```
docker attach player-async_player2_1 --detach-keys=ctrl-c
```

Press `Enter` in both terminals, it shows a menu of options like this:
```
Menu (<player name>)
1. Send message
2. Show inbox
Exit with Ctrl+C
Select [1-2]: 
``` 

The `player name` is the name that identifies the player.

####Sending a message
In order to send a message is needed to provided the `adressee` and the `message payload`.
Press `1` and then `Enter` and an interactive form will be shown:

```
Write message
From: player1
To: <write player2>
Message: <write a message such as: Hi!!!>
```

After filling the values the service will show again the main menu.

To see the messages received select the option `2`.

```
Inbox - player1 messages (10)
Received form player2: Hi!!! : 1
Received form player2: Hi!!! : 2
Received form player2: Hi!!! : 3
Received form player2: Hi!!! : 4
Received form player2: Hi!!! : 5
Received form player2: Hi!!! : 6
Received form player2: Hi!!! : 7
Received form player2: Hi!!! : 8
Received form player2: Hi!!! : 9
Received form player2: Hi!!! : 10
```