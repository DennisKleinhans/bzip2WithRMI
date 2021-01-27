package de.hft_stuttgart.ip1;

import java.rmi.RemoteException;

public class FileTransferImpl implements FileTransfer {

    @Override
    public String[] listFiles(String pattern) throws RemoteException {
        return new String[0];
    }

    @Override
    public void sendFile(String name, byte[] data) throws RemoteException {

    }

    @Override
    public byte[] receiveFile(String name) throws RemoteException {
        return new byte[0];
    }

    @Override
    public boolean deleteFile(String name) throws RemoteException {
        return false;
    }
}
