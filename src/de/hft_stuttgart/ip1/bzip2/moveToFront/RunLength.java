package de.hft_stuttgart.ip1.bzip2.moveToFront;

import java.util.ArrayList;


public class RunLength {
    public final static int RLEA = 0;
    public final static int RLEB = 1;

    /**
     * Transform the given input according to the algorithm given above for transformation
     */
    public static short[] transform(short[] input) {
        ArrayList<Short> list = new ArrayList<>();

        for (int i = 0, count = 1; i < input.length; i++) {
            if (i + 1 < input.length && input[i] == 0 && input[i+1] == 0){
                count++;
            }
            else if(input[i] != 0 && count == 1){
                list.add((short) (input[i] +1));
            }
            else{
                if(count == 1){
                    list.add(input[i]);
                }
                // berechnung
                while(count != RLEA && count != RLEB){
                    if(count % 2 == 0){
                        if(count == 2)
                        {
                            list.add((short) RLEA);
                            count = count - 2;
                        }
                        else{
                            list.add((short) RLEB);
                            count = count - 2;
                        }
                    }
                    else {
                        list.add((short) RLEA);
                        count = count - 1;
                    }
                }
                count = 1;
            }
        }
        short[] result = new short[list.size()];
        for(int i = 0; i < result.length; i++){
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * Transform the given input according to the algorithm given above for retransformation
     */
    public static short[] retransform(short[] input) {
        ArrayList<Short> resultList = new ArrayList<>();
        ArrayList<Short> list = new ArrayList<>();

        for(int i = 0, count = 0; i < input.length; i++){
            if(input[i] == 1 || input[i] == 0){
                if(count != 0){
                    list.add(input[i]);
                    count++;

                    if(i + 1 < input.length && input[i+1] > 1){
                        short tmp = 0;
                        for(int j = 0; j < list.size(); j++){
                            if(list.get(j) == RLEB){
                                tmp = (short) (tmp + 2*(j+1));
                            }
                            else if(list.get(j) == RLEA){
                                tmp = (short) (tmp + Math.pow(2, j));
                            }
                        }
                        for(int k = 0; k < tmp; k++){
                            resultList.add((short)0);
                        }
                        count = 0;
                        list.removeAll(list);
                    }
                }
                else {
                    if(i + 1 < input.length && input[i+1] > 1){
                        resultList.add(input[i]);
                    }
                    else {
                        list.add(input[i]);
                        count++;
                    }
                }
            }
            else {
                resultList.add((short) (input[i] - 1));
            }
        }
        short[] result = new short[resultList.size()];
        for(int i = 0; i < result.length; i++){
            result[i] = resultList.get(i);
        }
        return result;
    }
}