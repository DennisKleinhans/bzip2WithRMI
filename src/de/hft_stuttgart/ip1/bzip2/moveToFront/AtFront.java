package de.hft_stuttgart.ip1.bzip2.moveToFront;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class AtFront {
    /**
     Transform the given input according to the algorithm given above for transformation
     */
    public static byte[] transform(byte []input) {
        byte[] finalBytes = new byte[input.length];
        byte[] sortInput = Arrays.copyOf(input, input.length);
        Arrays.sort(sortInput);
        byte[] bytes = new byte[sortInput[sortInput.length - 1] + 1];
        LinkedList<Byte> list = new LinkedList<>();

        // Liste mit den Input Werten am richtigen index füllen
        for(byte i : input){
            bytes[i] = i;
        }

        for(byte i : bytes){
            list.add(i);
        }

        int index = 0;

        // Neues Byte j kodieren und an den index 0 speichern.
        // Byte j zum Ergebnis hinzufügen
        for(byte i = 0; i < input.length; i++){
            for(byte k = 0; k < bytes.length; k++){
                if(list.get(k) == input[index]){
                    byte j = k;
                    finalBytes[index] = j;

                    list.addFirst(list.get(k));
                    list.remove(k+1);

                    index++;
                    break;
                }
            }
        }
        return finalBytes;
    }

    /**
     Transform the given input according to the algorithm given above for retransformation
     */
    public static byte[] retransform(byte [] input) {
        byte[] sortInput = Arrays.copyOf(input, input.length);
        Arrays.sort(sortInput);
        byte[] bytes = new byte[sortInput[sortInput.length - 1] + 1];
        ArrayList<Byte> list = new ArrayList<>();

        byte[] result = new byte[input.length];

        // Liste mit den Bytes am richtigen Index füllen
        for(byte i : input){
            bytes[i] = (byte) i;
        }

        for(byte i : bytes){
            list.add(i);
        }

        int index = 0;

        // Index in der Liste ermitteln und das Byte j damit decodieren. Byte j an Position 0 in der Liste verschieben
        // Byte j zum Ergebnis hinzufügen
        for(short i : input){
            byte j = list.get(i);
            result[index] = j;
            list.remove(i);
            list.add(0, j);
            index++;
        }
        return result;
    }
}
