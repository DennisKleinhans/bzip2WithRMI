package de.hft_stuttgart.ip1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Scanner;



public class FileServerImpl implements FileServer{

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        Registry registry = LocateRegistry.createRegistry(28765);
        FileServer fs = (FileServer) UnicastRemoteObject.exportObject(new FileServerImpl(), 0);
        registry.bind(FileServer.class.getName(), fs);
    }

    @Override
    public FileTransfer getTransfer(String user, String password) throws RemoteException {
        if(user.equals(FileTransferImpl.PLAIN) && password.equals("12345")) {
            return new FileTransferImpl(FileTransferImpl.PLAIN);
        }
        if (user.equals(FileTransferImpl.BWT) && password.equals("12345")) {
            return new FileTransferImpl(FileTransferImpl.BWT);
        }
        if(user.equals(FileTransferImpl.MTF) && password.equals("12345")) {
            return new FileTransferImpl(FileTransferImpl.MTF);
        }
        if(user.equals(FileTransferImpl.RLE) && password.equals("12345")) {
            return new FileTransferImpl(FileTransferImpl.RLE);
        }
        if(user.equals(FileTransferImpl.BZIP2) && password.equals("12345")) {
            return new FileTransferImpl(FileTransferImpl.BZIP2);
        } else {
            return null;
        }
    }

}
