package net.kaoriya.omusubi.benchmark;

import me.lemire.integercompression.BinaryPacking;
import me.lemire.integercompression.DeltaZigzagBinaryPacking2;
import me.lemire.integercompression.JustCopy;
import me.lemire.integercompression.VariableByte;
import net.kaoriya.omusubi.IntDZBP;
import net.kaoriya.omusubi.IntJustCopy;
import net.kaoriya.omusubi.LongDZBP;
import net.kaoriya.omusubi.LongJustCopy;

public class Main {
    public static void main(String[] args) {
        System.out.println("");
        System.gc();
        benchmarkInt();
        System.out.println("");
        System.gc();
        benchmarkLong();
    }

    public static void benchmarkInt() {
        Benchmarks b = new Benchmarks();
        b.prepare();
        b.execute(new OmusubiCodec(new IntDZBP()));
        b.execute(new OmusubiCodec(new IntJustCopy()));
        b.execute(new LemireCodec(new DeltaZigzagBinaryPacking2()));
        b.execute(new LemireCodec(new BinaryPacking()));
        b.execute(new LemireCodec(new JustCopy()));
        b.report(System.out);
    }

    public static void benchmarkLong() {
        LongBenchmarks b = new LongBenchmarks();
        b.prepare();
        b.execute(new LongOmusubiCodec(new LongDZBP()));
        b.execute(new LongOmusubiCodec(new LongJustCopy()));
        b.report(System.out);
    }

}
