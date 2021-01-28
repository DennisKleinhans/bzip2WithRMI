package de.hft_stuttgart.ip1;

import java.nio.charset.StandardCharsets;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FileServerImpl implements FileServer{



    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        FileServer fs = new FileServerImpl();

        Registry registry = LocateRegistry.createRegistry(28765);
        UnicastRemoteObject.exportObject(fs, 0);
        registry.bind(FileServer.class.getName(), fs);

        FileTransfer fileTransfer = fs.getTransfer("plain", "12345");

        System.out.println(Arrays.toString(fileTransfer.listFiles("*")));

    }

    @Override
    public FileTransfer getTransfer(String user, String password) throws RemoteException {
        return new FileTransferImpl(user);
    }

}
