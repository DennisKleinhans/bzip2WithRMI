package de.hft_stuttgart.ip1;

import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Scanner;

public class FileClientImpl {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry( 28765);  //lida2.fkc.hft-stuttgart.de
        FileServer fileServer = (FileServer) registry.lookup(FileServer.class.getName());   // de.hft_stuttgart.ip1.common.FileServer
        FileTransfer fileTransfer = fileServer.getTransfer("plain", "12345");

        System.out.println("Logged in");
        Scanner sc = new Scanner(System.in);
        String command;
        while (true) {
            command = sc.nextLine();
            String[] s = command.split(":");
            if(s[0].equals("list")){
                System.out.println(Arrays.toString(fileTransfer.listFiles(s[1])));
            } else if(s[0].equals("delete")) {
                fileTransfer.deleteFile(s[1]);
            } else if(s[0].equals("send")) {
                fileTransfer.sendFile(s[1], s[2].getBytes(StandardCharsets.UTF_8));
            } else if(s[0].equals("receive")) {
                fileTransfer.receiveFile(s[1]);
            } else {
                System.err.println("command not found");
            }
        }
    }
}
