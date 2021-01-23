package de.hft_stuttgart.ip1.bzip2.moveToFront;

import java.util.ArrayList;
import java.util.LinkedList;


public class RunLength {
    public final static int RLEA = 0;
    public final static int RLEB = 1;

    /**
     * Transform the given input according to the algorithm given above for transformation
     */
    public static byte[] transform(byte[] input) {
        LinkedList<Integer> zwischenSpeicher = new LinkedList<>();
        for(int i = 0; i < input.length; i++) {
            byte anzahl;
            if(input[i] == 0) {
                anzahl = 0;
                for(int j = i; j < input.length; j++) {
                    if(input[j] != 0) {
                        while(anzahl > 0) {
                            if(anzahl % 2 == 1) {
                                zwischenSpeicher.add(RLEA);
                            } else {
                                zwischenSpeicher.add(RLEB);
                            }
                            anzahl--;
                            anzahl/=2;
                        }
                        i--;
                        break;
                    }
                    anzahl++;
                    i++;
                }
            } else {
                zwischenSpeicher.add(input[i] + 1);
            }
        }

        byte[] output = new byte[zwischenSpeicher.size()];

        for(int i = 0; i < output.length; i++) {
            output[i] = zwischenSpeicher.get(i).byteValue();
        }
        return output;
    }

    /**
     * Transform the given input according to the algorithm given above for retransformation
     */
    public static byte[] retransform(byte[] input) {
        LinkedList<Integer> zwischenSpeicher = new LinkedList<>();
        for(int i = 0; i < input.length; i++) {
            LinkedList<Byte> dekomp;
            if(input[i] == 0 || input[i] == 1) {
                dekomp = new LinkedList<>();
                for(int j = i; j < input.length; j++) {
                    if(input[j] == RLEA) {
                        dekomp.addFirst(input[j]);
                        i++;
                    } else if(input[j] == RLEB) {
                       dekomp.addFirst(input[j]);
                       i++;
                    } else {
                        byte anzahl = 0;
                        int groesse = dekomp.size();
                        for(int k = 0; k < groesse; k++) {
                            if(dekomp.getLast() == RLEA) {
                                anzahl += Math.pow(2, k);
                                dekomp.removeLast();
                            } else if(dekomp.getLast() == RLEB) {
                                anzahl += 2*Math.pow(2, k);
                                dekomp.removeLast();
                            }
                        }
                        for(int k = 0; k < anzahl; k++) {
                            zwischenSpeicher.add(0);
                        }
                        i--;
                        break;
                    }
                }
            } else {
                zwischenSpeicher.add(input[i] - 1);
            }
        }
        byte[] output = new byte[zwischenSpeicher.size()];
        for(int i = 0; i < output.length; i++) {
            output[i] = zwischenSpeicher.get(i).byteValue();
        }
        return output;
    }
}