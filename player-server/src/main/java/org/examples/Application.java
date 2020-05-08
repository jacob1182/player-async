package org.examples;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author jacob1182
 * */
public class Application {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        var port = Integer.parseInt(System.getenv("RMI_SERVER_PORT"));
        var senderAddress = System.getenv("RMI_SENDER_ADDRESS");

        var sender = (MessageSender) UnicastRemoteObject.exportObject(new MessageSenderImpl(), 0);
        var registry = LocateRegistry.createRegistry(port);

        registry.bind(senderAddress, sender);

        System.out.println("Sender is connected at port " + port);
    }
}
