package de.hft_stuttgart.ip1;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServer extends Remote {
    FileTransfer getTransfer(String user, String password) throws RemoteException;
}
