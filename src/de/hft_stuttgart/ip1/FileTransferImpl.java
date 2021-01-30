package de.hft_stuttgart.ip1;

import de.hft_stuttgart.ip1.bzip2.BurrowsWheelerTransformation.BurrowsWheelerTransformation;
import de.hft_stuttgart.ip1.bzip2.Bzip2;
import de.hft_stuttgart.ip1.bzip2.moveToFront.AtFront;
import de.hft_stuttgart.ip1.bzip2.moveToFront.RunLength;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.*;

public class FileTransferImpl implements FileTransfer {

    public static final String PLAIN = "plain";
    public static final String BWT = "bwt";
    public static final String MTF = "mtf";
    public static final String RLE = "rle";
    public static final String BZIP2 = "bzip2";

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
        byte[] originalData;

        switch (user) {
            case PLAIN:
                files.put(name, data);
                break;
            case BWT:
                String stringData = new String(data);
                String retransformed = BurrowsWheelerTransformation.retransform(stringData);
                originalData = retransformed.getBytes(StandardCharsets.UTF_8);
                files.put(name, originalData);
                break;
            case RLE:
                originalData = RunLength.retransform(data);
                files.put(name, originalData);
                break;
            case MTF:
                originalData = AtFront.retransform(data);
                files.put(name, originalData);
                break;
            case BZIP2:
                originalData = Bzip2.decode(data).getBytes(StandardCharsets.UTF_8);
                files.put(name, originalData);
                break;
        }
    }

    @Override
    public byte[] receiveFile(String name) throws RemoteException {
        byte[] file;

        switch (user) {
            case PLAIN:
                file = files.get(name);
                break;
            case BWT:
                byte[] data = files.get(name);
                String stringData = BurrowsWheelerTransformation.transform(new String(data));
                file = stringData.getBytes(StandardCharsets.UTF_8);
                break;
            case RLE:
                file = RunLength.transform(files.get(name));
                break;
            case MTF:
                file = AtFront.transform(files.get(name));
                break;
            case BZIP2:
                file = Bzip2.encode(new String(files.get(name)));
                break;
            default:
                file = "no success".getBytes(StandardCharsets.UTF_8);
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
}
