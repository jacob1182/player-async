package org.examples;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import static java.lang.String.format;

/**
 * @author jacob1182
 * */
public class Application {

    static int messageResendLimit = 10;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        var playerName = getPlayerName(args);
        var player = registerPlayer(playerName);

        var scanner = new Scanner(System.in);

        while (true) {
            System.out.println(format("Menu (%s)", playerName));
            System.out.println("1. Send message");
            System.out.println("2. Show inbox");
            System.out.println("Exit with Ctrl+C");
            System.out.print("Select [1-2]: ");
            var option = scanner.nextLine();

            clearScreen();
            if (option.equals("1"))
                sendMessage(player, scanner);
            if (option.equals("2"))
                showInbox(player, scanner);
            clearScreen();
        }
    }

    /**
     * Get the player name from the application arguments
     *
     * @param args the application arguments
     * */
    private static String getPlayerName(String... args) {
        if (args.length < 1) {
            System.err.println("Should provide the player name.");
            System.exit(1);
        }

        return args[0];
    }

    /**
     * Perform the message sending by the player
     *
     * @param player the player
     * @param scanner the scanner
     * */
    private static void sendMessage(Player player, Scanner scanner) {
        System.out.println("Write message");
        System.out.println("From: " + player.getName());
        System.out.print("To: ");
        String to = scanner.nextLine();
        System.out.print("Message: ");
        String payload = scanner.nextLine();
        try {
            player.sendMessage(to, new Message(payload));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            scanner.nextLine();
        }
    }

    /**
     * Show the received messages.
     *
     * @param player the player
     * @param scanner the scanner
     * */
    private static void showInbox(Player player, Scanner scanner) {
        var messages = player.getInbox();
        System.out.println(format("Inbox - %s messages (%d)", player.getName(), messages.size()));
        messages.forEach(System.out::println);
        scanner.nextLine();
    }

    /**
     * Register the current player in the message sender service
     *
     * @param playerName the player name
     * */
    private static Player registerPlayer(String playerName) throws RemoteException, NotBoundException {
        return createNewPlayer(playerName, getMessageSender());
    }

    /**
     * Create a new player
     *
     * @apiNote
     * This method suggest the need of a factory in the future for player creation.
     * */
    private static Player createNewPlayer(String name, MessageSender sender) throws RemoteException {
        return new Player(name, sender, new MessageCounter(messageResendLimit));
    }

    /**
     * Retrieve the {@see MessageSender} instance
     * */
    private static MessageSender getMessageSender() throws RemoteException, NotBoundException {
        var port = Integer.parseInt(System.getenv("RMI_SERVER_PORT"));
        var host = System.getenv("RMI_REMOTE_HOST");
        var sender = System.getenv("RMI_SENDER_ADDRESS");
        var registry = LocateRegistry.getRegistry(host, port);

        return (MessageSender) registry.lookup(sender);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
