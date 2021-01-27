package de.hft_stuttgart.ip1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileTransfer extends Remote {
    String[] listFiles(String pattern) throws RemoteException;
    void sendFile(String name, byte [] data) throws RemoteException;
    byte[] receiveFile(String name) throws RemoteException;
    boolean deleteFile(String name) throws RemoteException;
}