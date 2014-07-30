package net.kaoriya.omusubi.benchmark;

import net.kaoriya.omusubi.IntArrayOutputStream;
import net.kaoriya.omusubi.IntAscSDBP;

public class Main2 {

    final static int NUM_MAX = 1000000;

    public static void main(String[] args) {
        // benchmark set operations in IntAscSDBP.
        benchmark();
    }

    public static void benchmark() {
        benchmarkUnion();
        benchmarkIntersect();
        benchmarkDifference();
    }

    public static void run(String name, Runnable proc) {
        LoopBenchmark b = new LoopBenchmark(proc);
        double cps = b.run();
        System.out.printf("%s: C/S=%f", name, cps);
        System.out.println();
    }

    public static void benchmarkUnion() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("union", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.union(mul2, mul3);
            }
        });
    }

    public static void benchmarkIntersect() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("intersect", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.intersect(mul2, mul3);
            }
        });
    }

    public static void benchmarkDifference() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("difference", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.difference(mul2, mul3);
            }
        });
    }

    static int[] generateIntSet(int start, int offset, int max) {
        IntArrayOutputStream os = new IntArrayOutputStream();
        for (int i = start; i < max; i += offset) {
            os.write(i);
        }
        return os.toIntArray();
    }

    static byte[] packedIntSet(int start, int offset, int max) {
        return IntAscSDBP.toBytes(generateIntSet(start, offset, max));
    }
}
