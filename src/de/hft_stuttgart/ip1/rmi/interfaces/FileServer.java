package de.hft_stuttgart.ip1.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServer extends Remote {
    FileTransfer getTransfer(String user, String password) throws RemoteException;
}
