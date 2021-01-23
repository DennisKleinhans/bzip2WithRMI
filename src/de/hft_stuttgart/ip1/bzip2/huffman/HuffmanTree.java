package de.hft_stuttgart.ip1.bzip2.huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class HuffmanTree {
    int[] distribution;
    HuffmanEntry entry;

    public HuffmanTree(int[] distribution) {
        this.distribution = distribution;
        HuffmanEntry[] array = new HuffmanEntry[distribution.length];


        for (int i = 0; i < 256; i++) {
            array[i] = new HuffmanEntry((byte) i, distribution[i]);
        }

        Arrays.sort(array, HuffmanEntry::compareToReversed);

        for (int i = distribution.length - 1; i > 0; i--) {
            Arrays.sort(array, 0, i + 1, HuffmanEntry::compareToReversed);
            if (i == 1) {
                array[0] = new HuffmanEntry(array[0], array[1]);
                entry = array[0];
                break;
            }
            array[i - 1] = new HuffmanEntry(array[i - 1], array[i]);
        }
    }

    public byte[] encode(byte[] input) {

        ArrayList<Byte> resultList = new ArrayList<>();
        byte b = 0;
        int counter = 0;
        for (int i = 0; i < input.length; i++) {
            HuffmanEntry tmp = entry;

            while (tmp.right != null | tmp.left != null) {
                if (tmp.isLeft(input[i])) {
                    tmp = tmp.left;
                } else {
                    b |= 1;
                    tmp = tmp.right;
                }

                counter++;

                if (counter == 8) {
                    resultList.add(b);
                    b = 0;
                    counter = 0;
                } else {
                    b <<= 1;
                }
            }
        }

        if (counter < 8) {
            b |= 1;
            b <<= 8 - counter - 1;
            resultList.add(b);
        }

        byte[] result = new byte[resultList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    public byte[] decode(byte[] input) {
        LinkedList<Byte> bits = new LinkedList<>();
        ArrayList<Byte> resultList = new ArrayList<>();

        int endIndex = 0;

        for (int i = 0; i < input.length; i++) {
            byte b = input[i];
            for (int j = 0; j < 8; j++) {
                bits.add((byte) (b >>> (7 - j) & 1));
            }
        }

        for (int k = bits.size() - 1; k > 0; k--) {
            if (bits.get(k) == 1) {
                endIndex = k;
                break;
            }
        }

        HuffmanEntry tmp = entry;
        int counter = 0;

        loop:
        while (counter != endIndex) {
            while (tmp.data.length > 1) {

                if (counter == endIndex) {
                    break loop;
                }


                if (bits.get(counter) == 0) {
                    tmp = tmp.left;
                } else {
                    tmp = tmp.right;
                }
                counter++;
            }

            resultList.add(tmp.data[0]);
            tmp = entry;

        }

        byte[] result = new byte[resultList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

    private int encode(byte input) {

        return 0;
    }

    private byte decode(int input) {
        return 0;
    }

    static private boolean get(byte[] buffer, int index) {
        return false;
    }

    static private void set(byte[] buffer, int index, boolean value) {

    }
}
