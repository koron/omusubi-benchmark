package net.kaoriya.omusubi.benchmark;

import java.util.ArrayList;
import java.util.List;

import net.kaoriya.omusubi.IntAscSDBP;
import net.kaoriya.omusubi.io.IntArrayInputStream;
import net.kaoriya.omusubi.io.IntArrayOutputStream;

public class Main2 {

    final static int NUM_MAX = 1000000;

    public static void main(String[] args) {
        // benchmark set operations in IntAscSDBP.
        benchmark();
    }

    public static void benchmark() {
        benchmarkUnion();
        benchmarkUnionRaw();
        benchmarkIntersect();
        benchmarkIntersectRaw();
        benchmarkDifference();
        benchmarkDifferenceRaw();
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
        run("union (packed)", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.union(mul2, mul3);
            }
        });
    }

    public static void benchmarkUnionRaw() {
        final int[] mul2 = generateIntSet(0, 2, NUM_MAX);
        final int[] mul3 = generateIntSet(0, 3, NUM_MAX);
        run("union    (raw)", new Runnable() {
            public void run() {
                List<IntAscSDBP.Reader> readers = newReaders(mul2, mul3);
                int[] result = IntAscSDBP.union(readers).toIntArray();
            }
        });
    }

    public static void benchmarkIntersect() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("intersect (packed)", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.intersect(mul2, mul3);
            }
        });
    }

    public static void benchmarkIntersectRaw() {
        final int[] mul2 = generateIntSet(0, 2, NUM_MAX);
        final int[] mul3 = generateIntSet(0, 3, NUM_MAX);
        run("intersect    (raw)", new Runnable() {
            public void run() {
                IntAscSDBP.Reader pivot = newReader(mul2);
                List<IntAscSDBP.Reader> readers = newReaders(mul3);
                int[] result =
                    IntAscSDBP.intersect(pivot, readers).toIntArray();
            }
        });
    }

    public static void benchmarkDifference() {
        final byte[] mul2 = packedIntSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedIntSet(0, 3, NUM_MAX);
        run("difference (packed)", new Runnable() {
            public void run() {
                byte[] result = IntAscSDBP.difference(mul2, mul3);
            }
        });
    }

    public static void benchmarkDifferenceRaw() {
        final int[] mul2 = generateIntSet(0, 2, NUM_MAX);
        final int[] mul3 = generateIntSet(0, 3, NUM_MAX);
        run("difference    (raw)", new Runnable() {
            public void run() {
                IntAscSDBP.Reader pivot = newReader(mul2);
                List<IntAscSDBP.Reader> readers = newReaders(mul3);
                int[] result =
                    IntAscSDBP.difference(pivot, readers).toIntArray();
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
