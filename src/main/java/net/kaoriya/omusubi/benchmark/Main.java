package net.kaoriya.omusubi.benchmark;

import me.lemire.integercompression.DeltaZigzagBinaryPacking2;

public class Main {
    public static void main(String[] args) {
        Benchmarks b = new Benchmarks();
        b.prepare();
        b.execute(new OmusubiCodec());
        b.execute(new LemireCodec(new DeltaZigzagBinaryPacking2()));
        b.report(System.out);
    }
}
