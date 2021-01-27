package de.hft_stuttgart.ip1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;


public class FileServerImpl implements FileServer{

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

    }

    @Override
    public FileTransfer getTransfer(String user, String password) throws RemoteException {
        return null;
    }
}
