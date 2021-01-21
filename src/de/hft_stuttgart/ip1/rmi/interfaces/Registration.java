package de.hft_stuttgart.ip1.rmi.interfaces;

import de.hft_stuttgart.ip1.Sender;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Registration extends Remote {
    Sender registerReceiver(Receiver receiver) throws RemoteException;
}
