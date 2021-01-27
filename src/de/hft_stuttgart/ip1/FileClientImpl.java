package de.hft_stuttgart.ip1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class FileClientImpl {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("lida2.fkc.hft-stuttgart.de",28765);
        FileServer fileServer = (FileServer) registry.lookup(FileServer.class.getName());   // de.hft_stuttgart.ip1.common.FileServer
        FileTransfer fileTransfer = fileServer.getTransfer("plain", "12345");
        Arrays.toString(fileTransfer.listFiles("*"));

    }
}
