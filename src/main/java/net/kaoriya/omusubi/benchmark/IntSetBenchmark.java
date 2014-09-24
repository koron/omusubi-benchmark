package net.kaoriya.omusubi.benchmark;

import java.util.ArrayList;
import java.util.List;

import net.kaoriya.omusubi.IntAscSDBP;
import net.kaoriya.omusubi.io.IntArrayInputStream;
import net.kaoriya.omusubi.io.IntArrayOutputStream;

public class IntSetBenchmark {

    final static int NUM_MAX = 1000000;

    public static void benchmark() {
        benchmarkUnion();
        benchmarkUnionRaw();
        benchmarkIntersect();
        benchmarkIntersectRaw();
        benchmarkDifference();
        benchmarkDifferenceRaw();
        printLegend();
    }

    public static void run(String name, Runnable proc, int readLen) {
        LoopBenchmark b = new LoopBenchmark(proc);
        double cps = b.run();
        double misr = cps * readLen * 1e-6;
        System.out.printf("%-24s: C/S=%.3f MiS(R)=%.3f", name, cps, misr);
        System.out.println();
    }

    public static void printLegend() {
        System.out.println();
        System.out.println("- C/S    : processing count per second");
        System.out.println("- MiS(R) : processsed mega-ints per second");
        System.out.println("- In     : input data size in byte for a proc.");
        System.out.println("- Out    : output data size in byte for a proc.");
    }

    public static void printSize(
            int inSize1,
            int inSize2,
            int outSize)
    {
        System.out.printf(
                "  In:%d+%d bytes Out:%d bytes",
                inSize1, inSize2, outSize);
        System.out.println();
    }

    public static void benchmarkUnion() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("union (int,packed)", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.union(mul2, mul3);
            }
        }, mul2.length + mul3.length);

        byte[] result = IntAscSDBP.union(mul2, mul3);
        printSize(mul2.length, mul3.length, result.length);
    }

    public static void benchmarkUnionRaw() {
        final int[] mul2 = generateIntSet(0, 2, NUM_MAX);
        final int[] mul3 = generateIntSet(0, 3, NUM_MAX);
        run("union (int,raw)", new Runnable() {
            public void run() {
                List<IntAscSDBP.Reader> readers = newReaders(mul2, mul3);
                int[] result = IntAscSDBP.union(readers).toIntArray();
            }
        }, mul2.length + mul3.length);

        List<IntAscSDBP.Reader> readers = newReaders(mul2, mul3);
        int[] result = IntAscSDBP.union(readers).toIntArray();
        printSize(mul2.length * 4, mul3.length * 4, result.length * 4);
    }

    public static void benchmarkIntersect() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("intersect (int,packed)", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.intersect(mul2, mul3);
            }
        }, mul2.length + mul3.length);

        byte[] result = IntAscSDBP.intersect(mul2, mul3);
        printSize(mul2.length, mul3.length, result.length);
    }

    public static void benchmarkIntersectRaw() {
        final int[] mul2 = generateIntSet(0, 2, NUM_MAX);
        final int[] mul3 = generateIntSet(0, 3, NUM_MAX);
        run("intersect (int,raw)", new Runnable() {
            public void run() {
                IntAscSDBP.Reader pivot = newReader(mul2);
                List<IntAscSDBP.Reader> readers = newReaders(mul3);
                int[] result =
                    IntAscSDBP.intersect(pivot, readers).toIntArray();
            }
        }, mul2.length + mul3.length);

        IntAscSDBP.Reader pivot = newReader(mul2);
        List<IntAscSDBP.Reader> readers = newReaders(mul3);
        int[] result = IntAscSDBP.intersect(pivot, readers).toIntArray();
        printSize(mul2.length * 4, mul3.length * 4, result.length * 4);
    }

    public static void benchmarkDifference() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("difference (int,packed)", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.difference(mul2, mul3);
            }
        }, mul2.length + mul3.length);

        byte[] result = IntAscSDBP.difference(mul2, mul3);
        printSize(mul2.length, mul3.length, result.length);
    }

    public static void benchmarkDifferenceRaw() {
        final int[] mul2 = generateIntSet(0, 2, NUM_MAX);
        final int[] mul3 = generateIntSet(0, 3, NUM_MAX);
        run("difference (int,raw)", new Runnable() {
            public void run() {
                IntAscSDBP.Reader pivot = newReader(mul2);
                List<IntAscSDBP.Reader> readers = newReaders(mul3);
                int[] result =
                    IntAscSDBP.difference(pivot, readers).toIntArray();
            }
        }, mul2.length + mul3.length);

        IntAscSDBP.Reader pivot = newReader(mul2);
        List<IntAscSDBP.Reader> readers = newReaders(mul3);
        int[] result = IntAscSDBP.difference(pivot, readers).toIntArray();
        printSize(mul2.length * 4, mul3.length * 4, result.length * 4);
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

    static IntAscSDBP.Reader newReader(int[] data) {
        IntAscSDBP.Reader r =
            new IntAscSDBP.Reader(new IntArrayInputStream(data));
        r.read();
        return r;
    }

    static List<IntAscSDBP.Reader> newReaders(int[] ...data) {
        ArrayList<IntAscSDBP.Reader> readers =
            new ArrayList<IntAscSDBP.Reader>(data.length);
        for (int[] d : data) {
            readers.add(newReader(d));
        }
        return readers;
    }
}
