package de.hft_stuttgart.ip1.bzip2.huffman;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class HuffmanEntry implements Comparable<HuffmanEntry> {
    HuffmanEntry left;
    HuffmanEntry right;
    byte[] data;
    int count;

    public HuffmanEntry(HuffmanEntry left, HuffmanEntry right) {
       this.left = left;
       this.right = right;
       this.count += left.count + right.count;

        byte[] allByteArray = new byte[left.data.length + right.data.length];

        ByteBuffer buff = ByteBuffer.wrap(allByteArray);
        buff.put(left.data);
        buff.put(right.data);

        this.data = buff.array();

        Arrays.sort(data);
    }

    public HuffmanEntry(byte data, int count) {
        this.data = new byte[]{data};
        this.count = count;
    }

    public boolean isLeft(byte value) {
        if(left != null){
            Arrays.sort(left.data);
            int tmp = Arrays.binarySearch(left.data, value);
            if(tmp >= 0) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean isRight(byte value) {
        if(right != null){
            Arrays.sort(right.data);
            if(Arrays.binarySearch(right.data, value) >= 0) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    private int getCount(){
        return count;
    }

    private int getDataLength(){ return data.length; }

    private byte getFirstDataField(){
        return data[0];
    }

    public int compareTo(HuffmanEntry other) {
       Comparator<HuffmanEntry> compare = Comparator.comparing(HuffmanEntry::getCount).thenComparing(HuffmanEntry::getDataLength).thenComparing(HuffmanEntry::getFirstDataField);
       return Objects.compare(this, other, compare);
    }

    public int compareToReversed(HuffmanEntry other){
        Comparator<HuffmanEntry> compare = Comparator.comparing(HuffmanEntry::getCount).thenComparing(HuffmanEntry::getDataLength).thenComparing(HuffmanEntry::getFirstDataField).reversed();
        return Objects.compare(this, other, compare);
    }
}
