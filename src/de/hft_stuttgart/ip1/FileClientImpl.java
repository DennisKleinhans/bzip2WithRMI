package de.hft_stuttgart.ip1;

import de.hft_stuttgart.ip1.bzip2.BurrowsWheelerTransformation.BurrowsWheelerTransformation;
import de.hft_stuttgart.ip1.bzip2.Bzip2;
import de.hft_stuttgart.ip1.bzip2.moveToFront.AtFront;
import de.hft_stuttgart.ip1.bzip2.moveToFront.RunLength;

import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Scanner;

public class FileClientImpl {
    public static String user;

    public static void main(String[] args) throws RemoteException, NotBoundException {

        String password;

        Scanner sc = new Scanner(System.in);
        System.out.println("username: ");
        user = sc.next();
        System.out.println("password: ");
        password = sc.next();


        Registry registry = LocateRegistry.getRegistry(  28765);  //lida2.fkc.hft-stuttgart.de
        FileServer fileServer = (FileServer) registry.lookup(FileServer.class.getName());
        FileTransfer fileTransfer = fileServer.getTransfer(user, password);

        if(fileTransfer == null) {
            throw new IllegalArgumentException("username or password incorrect");
        }

        System.out.println("Logged in");

        String command;

        while (true) {
            command = sc.nextLine();
            String[] s = command.split(":");

            switch (s[0]) {
                case "list":
                    System.out.println(Arrays.toString(fileTransfer.listFiles(s[1])));
                    break;
                case "send":
                    if(user.equals(FileTransferImpl.PLAIN)){
                        fileTransfer.sendFile(s[1], s[2].getBytes(StandardCharsets.UTF_8));
                    }
                    if(user.equals(FileTransferImpl.BWT)) {
                        fileTransfer.sendFile(s[1], BurrowsWheelerTransformation.transform(s[2]).getBytes(StandardCharsets.UTF_8));
                    }
                    if(user.equals(FileTransferImpl.RLE)) {
                        fileTransfer.sendFile(s[1], RunLength.transform(s[2].getBytes(StandardCharsets.UTF_8)));
                    }
                    if(user.equals(FileTransferImpl.MTF)) {
                        fileTransfer.sendFile(s[1], AtFront.transform(s[2].getBytes(StandardCharsets.UTF_8)));
                    }
                    if(user.equals(FileTransferImpl.BZIP2)) {
                        fileTransfer.sendFile(s[1], Bzip2.encode(s[2]));
                    }
                    break;
                case "receive":
                    String originalData;
                    byte[] data;
                    if(user.equals(FileTransferImpl.PLAIN)){
                        originalData = new String(fileTransfer.receiveFile(s[1]));
                        System.out.println(originalData);
                    }
                    if(user.equals(FileTransferImpl.BWT)) {
                        data = fileTransfer.receiveFile(s[1]);
                        originalData = BurrowsWheelerTransformation.retransform(new String(data));
                        System.out.println(originalData);
                    }
                    if(user.equals(FileTransferImpl.RLE)) {
                        data = fileTransfer.receiveFile(s[1]);
                        originalData = new String(RunLength.retransform(data));
                        System.out.println(originalData);
                    }
                    if(user.equals(FileTransferImpl.MTF)) {
                        data = fileTransfer.receiveFile(s[1]);
                        originalData = new String(AtFront.retransform(data));
                        System.out.println(originalData);
                    }
                    if(user.equals(FileTransferImpl.BZIP2)) {
                        data = fileTransfer.receiveFile(s[1]);
                        originalData = Bzip2.decode(data);
                        System.out.println(originalData);
                    }
                    break;
                case "delete":
                    fileTransfer.deleteFile(s[1]);
                    break;
            }
        }
    }
}
