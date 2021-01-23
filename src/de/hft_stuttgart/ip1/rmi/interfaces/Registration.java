package de.hft_stuttgart.ip1.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Registration extends Remote {
    Sender registerReceiver(Receiver receiver) throws RemoteException;
}
