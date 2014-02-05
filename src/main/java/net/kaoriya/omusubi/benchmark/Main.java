package net.kaoriya.omusubi.benchmark;

import me.lemire.integercompression.BinaryPacking;
import me.lemire.integercompression.DeltaZigzagBinaryPacking2;
import me.lemire.integercompression.JustCopy;
import me.lemire.integercompression.VariableByte;

public class Main {
    public static void main(String[] args) {
        Benchmarks b = new Benchmarks();
        b.prepare();
        b.execute(new OmusubiCodec());
        b.execute(new LemireCodec(new DeltaZigzagBinaryPacking2()));
        b.execute(new LemireCodec(new BinaryPacking()));
        b.execute(new LemireCodec(new JustCopy()));
        b.report(System.out);
    }
}
