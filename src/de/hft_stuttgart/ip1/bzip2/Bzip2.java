package de.hft_stuttgart.ip1.bzip2;


import de.hft_stuttgart.ip1.bzip2.BurrowsWheelerTransformation.BurrowsWheelerTransformation;
import de.hft_stuttgart.ip1.bzip2.huffman.HuffmanDistribution;
import de.hft_stuttgart.ip1.bzip2.huffman.HuffmanTree;
import de.hft_stuttgart.ip1.bzip2.moveToFront.AtFront;
import de.hft_stuttgart.ip1.bzip2.moveToFront.RunLength;
import java.nio.charset.StandardCharsets;


public class Bzip2 {

    private HuffmanTree huffmanTree;

    public byte[] encode(String code){
        String burrowsWheelerTransform = BurrowsWheelerTransformation.transform(code);

        byte[] atFrontTransform = AtFront.transform(burrowsWheelerTransform.getBytes(StandardCharsets.UTF_8));

        byte[] runLengthTransform = RunLength.transform(atFrontTransform);

        huffmanTree = new HuffmanTree(HuffmanDistribution.getDistribution(runLengthTransform));
        byte[] huffmanCode = huffmanTree.encode(runLengthTransform);

        return huffmanCode;
    }

    public void decode(byte[] code){

        byte[] huffmanRetransform = huffmanTree.decode(code);

        byte[] runLengthRetransform = RunLength.retransform(huffmanRetransform);

        byte[] atFrontRetransform = AtFront.retransform(runLengthRetransform);

        String atFrontRetransformAsString = new String(atFrontRetransform);

        String originalString = BurrowsWheelerTransformation.retransform(atFrontRetransformAsString);
    }
}
