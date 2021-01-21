package de.hft_stuttgart.ip1.bzip2.huffman;

import java.util.Arrays;

public class HuffmanDistribution {
    public static int[] getDistribution(byte[] input) {
        int [] result = new int[256];
        for (byte b : input) {
            result[b&0xFF]++;
        }
        return result;
    }
}
