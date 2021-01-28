package de.hft_stuttgart.ip1.bzip2.moveToFront;

import java.util.LinkedList;


public class AtFront {
    /**
     Transform the given input according to the algorithm given above for transformation
     */
    public static byte[] transform(byte []input) {
        byte[] output = new byte[input.length];
        LinkedList<Byte> position = new LinkedList<>();
        for(int i = 0; i < input.length; i++) {
            byte aktuell = input[i];
            if(position.contains(aktuell)) {
                byte merken = aktuell;
                aktuell = (byte) position.indexOf(aktuell);
                position.remove(aktuell);
                position.addFirst(merken);
                output[i] = aktuell;
            } else {
                position.addFirst(aktuell);
                output[i] = aktuell;
            }
        }
        return output;
    }

    /**
     Transform the given input according to the algorithm given above for retransformation
     */
    public static byte[] retransform(byte [] input) {
       byte[] output = new byte[input.length];
       LinkedList<Byte> position = new LinkedList<>();
       for(int i = 0; i < input.length; i++) {
           byte aktuell = input[i];
           if(Math.abs(aktuell) <= position.size()) {
               byte merken = position.get(Math.abs(aktuell));
               position.remove(aktuell);
               position.addFirst(merken);
               output[i] = merken;
           } else {
               position.addFirst(aktuell);
               output[i] = aktuell;
           }
       }
       return output;
    }
}
