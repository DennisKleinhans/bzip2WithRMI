package de.hft_stuttgart.ip1.bzip2.volltext;

import java.nio.IntBuffer;
import java.util.*;
import java.util.stream.IntStream;

public class Volltext {
    private final static String endMarker = "\uFFED";
    /*
        Class to help implementing rotated String comparison
     */
    private static class Substring implements Comparable<Substring> {
        private final String value;
        private final int from;
        private final int to;

        public Substring(String value, int from, int to) {
           this.value = value;
           this.from = from;
           this.to = to;
        }

        @Override
        public int compareTo(Substring other) {
            return this.toString().compareTo(other.toString());
        }

        @Override
        public String toString() {
            return value.substring(from, to);
        }
    }

    /**
     Transform the given input according to the algorithm given above for transformation
     */
    public static String transform(String input) {
        String doubl = input + endMarker + input;
        Substring[] array = new Substring[input.length() + 1];

        for (int i = 0; i < input.length() + 1; i++){
            Substring stri = new Substring(doubl, i, i + input.length() + 1);
            array[i] = stri;
        }

        Arrays.sort(array, Substring::compareTo);
        StringBuilder sb = new StringBuilder();

        int length = array[0].toString().toCharArray().length;

        for(int i = 0; i < array.length; i++){
            sb.append(array[i].toString().charAt(length - 1));
        }

        return sb.toString();
    }
    /**
     Transform the given input according to the algorithm given above for retransformation
     */
    public static String retransform(String input) {
        char[] sortArray = input.toCharArray();
        Arrays.sort(sortArray);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < sortArray.length; i++){
            sb.append(sortArray[i]);
        }

        String sortedText = sb.toString();

        // befüll die Map mit den Werten
        HashMap<Character, ArrayList<Integer>> map = new HashMap<>();

        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            ArrayList<Integer> list = new ArrayList<>();
            list.add(i);

            // Falls der Buchstabe bereits gespeichert wurde, füge den nächsten index hinzu
            if(map.containsKey(c)){
                map.get(c).add(i);
            } else {
                map.put(c, list);
            }
        }

        StringBuilder finalString = new StringBuilder();
        int indexEndMarker = input.indexOf(endMarker);
        int index = indexEndMarker;

        do {
            char sign = sortedText.charAt(index);
            finalString.append(sign);
            int countSortedText = 0;
            int i = 0;

            // Vorkommen im sortierten Text ermitteln
            while (i != index){
                if(sortedText.charAt(i) == sign){
                    countSortedText++;
                }
                i++;
            }

            index = map.get(sign).get(countSortedText);

        } while (index != indexEndMarker);

        // lösche endMarker und gib den finalen String zurück
        return finalString.deleteCharAt(finalString.length()-1).toString();
    }
}