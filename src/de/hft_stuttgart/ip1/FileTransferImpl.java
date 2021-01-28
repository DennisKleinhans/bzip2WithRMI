package de.hft_stuttgart.ip1;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTransferImpl implements FileTransfer {

    private Map<String, byte[]> files;
    private String user;

    public FileTransferImpl(String user) {
        this.user = user;
        files = new HashMap<>();

        files.put("file1", new byte[256]);
        files.put("file2", new byte[468]);
        files.put("empty", new byte[0]);
        files.put("Hello World!", "Hello World!".getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String[] listFiles(String pattern) throws RemoteException {
        if (pattern.equals("*")) {
            String[] fileNames = new String[files.size()];
            int i = 0;
            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                fileNames[i] = entry.getKey();
                i++;
            }
            return fileNames;
        } else {
            return null;
        }
    }

    @Override
    public void sendFile(String name, byte[] data) throws RemoteException {
        files.put(name, data);
    }

    @Override
    public byte[] receiveFile(String name) throws RemoteException {

        byte[] file = files.get(name);
        return file;
    }

    @Override
    public boolean deleteFile(String name) throws RemoteException {
        byte[] val = files.remove(name);
        if (val != null) {
            return true;
        } else {
            return false;
        }
    }
}
