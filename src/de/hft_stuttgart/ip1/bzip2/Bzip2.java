package de.hft_stuttgart.ip1.bzip2;


import de.hft_stuttgart.ip1.bzip2.BurrowsWheelerTransformation.BurrowsWheelerTransformation;
import de.hft_stuttgart.ip1.bzip2.huffman.HuffmanDistribution;
import de.hft_stuttgart.ip1.bzip2.huffman.HuffmanTree;
import de.hft_stuttgart.ip1.bzip2.moveToFront.AtFront;
import de.hft_stuttgart.ip1.bzip2.moveToFront.RunLength;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;



public class Bzip2 {

    public static byte[] encode(String code){
        String burrowsWheelerTransform = BurrowsWheelerTransformation.transform(code);

        byte[] atFrontTransform = AtFront.transform(burrowsWheelerTransform.getBytes(StandardCharsets.UTF_8));

        byte[] runLengthTransform = RunLength.transform(atFrontTransform);

        int[] distro = HuffmanDistribution.getDistribution(runLengthTransform);

        byte[] distroData = new byte[distro.length*4];
        for(int i = 0; i < distro.length; i++) {
            distroData[4*i+0] = (byte)((distro[i]>>24) & 0xFF);
            distroData[4*i+1] = (byte)((distro[i]>>16) & 0xFF);
            distroData[4*i+2] = (byte)((distro[i]>>8) & 0xFF);
            distroData[4*i+3] = (byte)((distro[i]>>0) & 0xFF);
        }

        HuffmanTree tree = new HuffmanTree(distro);
        byte[] huffmanCode = tree.encode(runLengthTransform);

        // distro an den Anfang des Arrays schreiben
        byte[] result = Arrays.copyOf(distroData,distroData.length + huffmanCode.length);
        System.arraycopy(huffmanCode, 0, result, distroData.length, huffmanCode.length);

       return result;
    }

    public static String decode(byte[] input){

        byte[] distro = Arrays.copyOf(input, 256*4);
        byte[] code = Arrays.copyOfRange(input,distro.length, input.length);

        int[] distroData = new int[distro.length/4];
        for(int i = 0; i < distroData.length; i++) {
            distroData[i] = ((distro[4*i+0]&0xFF)<<24) | ((distro[4*i+1]&0xFF)<<16) | ((distro[4*i+2]&0xFF)<<8) | ((distro[4*i+3]&0xFF)<<0);
        }

        HuffmanTree tree = new HuffmanTree(distroData);

        byte[] huffmanRetransform = tree.decode(code);

        byte[] runLengthRetransform = RunLength.retransform(huffmanRetransform);

        byte[] atFrontRetransform = AtFront.retransform(runLengthRetransform);

        String atFrontRetransformAsString = new String(atFrontRetransform);

        String originalString = BurrowsWheelerTransformation.retransform(atFrontRetransformAsString);

        return originalString;
    }

    public static void main(String[] args) {
        byte[] encode1 = Bzip2.encode("Otto");
        String decode1 = Bzip2.decode(encode1);
        byte[] encode2 = Bzip2.encode(decode1);
        String decode2 = Bzip2.decode(encode2);

        System.out.println(decode2);
        byte[] data = new byte[]{-17,-65,-83,116,0,111,0};
        System.out.println(Arrays.toString("otto".getBytes(StandardCharsets.UTF_8)));
        String s = new String(data);
        System.out.println(s);
    }
}
