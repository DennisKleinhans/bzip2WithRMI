package de.hft_stuttgart.ip1;

import de.hft_stuttgart.ip1.bzip2.Bzip2;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.*;

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
        if(user.equals("bzip2")) {
            String decode = Bzip2.decode(data);
            byte[] file = decode.getBytes(StandardCharsets.UTF_8);
            files.put(name, file);
        } else {
            files.put(name, data);
        }
    }

    @Override
    public byte[] receiveFile(String name) throws RemoteException {
        byte[] file;
        if(user.equals("bzip2")) {
            String data = new String(files.get(name));
            file = Bzip2.encode(data);
        } else {
            file = files.get(name);
        }
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

    public String getUser(){
        return user;
    }
}
