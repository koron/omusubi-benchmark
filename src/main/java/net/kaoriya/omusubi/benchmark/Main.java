package net.kaoriya.omusubi.benchmark;

import me.lemire.integercompression.BinaryPacking;
import me.lemire.integercompression.DeltaZigzagBinaryPacking2;
import me.lemire.integercompression.JustCopy;
import me.lemire.integercompression.VariableByte;
import net.kaoriya.omusubi.IntDZBP;
import net.kaoriya.omusubi.IntJustCopy;

public class Main {
    public static void main(String[] args) {
        Benchmarks b = new Benchmarks();
        b.prepare();
        b.execute(new OmusubiCodec(new IntDZBP()));
        b.execute(new OmusubiCodec(new IntJustCopy()));
        b.execute(new LemireCodec(new DeltaZigzagBinaryPacking2()));
        b.execute(new LemireCodec(new BinaryPacking()));
        b.execute(new LemireCodec(new JustCopy()));
        b.report(System.out);
    }
}
