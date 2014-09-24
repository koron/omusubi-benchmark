package net.kaoriya.omusubi.benchmark;

import java.util.ArrayList;
import java.util.List;

import net.kaoriya.omusubi.LongAscSDBP;
import net.kaoriya.omusubi.io.LongArrayInputStream;
import net.kaoriya.omusubi.io.LongArrayOutputStream;

public class LongSetBenchmark {

    final static int NUM_MAX = 1000000;

    public static void benchmark() {
        benchmarkUnion();
        benchmarkUnionRaw();
        benchmarkLongersect();
        benchmarkLongersectRaw();
        benchmarkDifference();
        benchmarkDifferenceRaw();
        printLegend();
    }

    public static void run(String name, Runnable proc, int readLen) {
        LoopBenchmark b = new LoopBenchmark(proc);
        double cps = b.run();
        double mlsr = cps * readLen * 1e-6;
        System.out.printf("%-24s: C/S=%.3f MlS(R)=%.3f", name, cps, mlsr);
        System.out.println();
    }

    public static void printLegend() {
        System.out.println();
        System.out.println("- C/S : processing count per second");
        System.out.println("- MlS(R) : processsed mega-long per second");
        System.out.println("- In  : input data size in byte for a proc.");
        System.out.println("- Out : output data size in byte for a proc.");
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
        final byte[] mul2 = packedLongSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedLongSet(0, 3, NUM_MAX);
        run("union (long,packed)", new Runnable() {
            public void run() {
                byte[] result = LongAscSDBP.union(mul2, mul3);
            }
        }, mul2.length + mul3.length);

        byte[] result = LongAscSDBP.union(mul2, mul3);
        printSize(mul2.length, mul3.length, result.length);
    }

    public static void benchmarkUnionRaw() {
        final long[] mul2 = generateLongSet(0, 2, NUM_MAX);
        final long[] mul3 = generateLongSet(0, 3, NUM_MAX);
        run("union (long,raw)", new Runnable() {
            public void run() {
                List<LongAscSDBP.Reader> readers = newReaders(mul2, mul3);
                long[] result = LongAscSDBP.union(readers).toLongArray();
            }
        }, mul2.length + mul3.length);

        List<LongAscSDBP.Reader> readers = newReaders(mul2, mul3);
        long[] result = LongAscSDBP.union(readers).toLongArray();
        printSize(mul2.length * 8, mul3.length * 8, result.length * 8);
    }

    public static void benchmarkLongersect() {
        final byte[] mul2 = packedLongSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedLongSet(0, 3, NUM_MAX);
        run("intersect (long,packed)", new Runnable() {
            public void run() {
                byte[] result = LongAscSDBP.intersect(mul2, mul3);
            }
        }, mul2.length + mul3.length);

        byte[] result = LongAscSDBP.intersect(mul2, mul3);
        printSize(mul2.length, mul3.length, result.length);
    }

    public static void benchmarkLongersectRaw() {
        final long[] mul2 = generateLongSet(0, 2, NUM_MAX);
        final long[] mul3 = generateLongSet(0, 3, NUM_MAX);
        run("intersect (long,raw)", new Runnable() {
            public void run() {
                LongAscSDBP.Reader pivot = newReader(mul2);
                List<LongAscSDBP.Reader> readers = newReaders(mul3);
                long[] result =
                    LongAscSDBP.intersect(pivot, readers).toLongArray();
            }
        }, mul2.length + mul3.length);

        LongAscSDBP.Reader pivot = newReader(mul2);
        List<LongAscSDBP.Reader> readers = newReaders(mul3);
        long[] result = LongAscSDBP.intersect(pivot, readers).toLongArray();
        printSize(mul2.length * 8, mul3.length * 8, result.length * 8);
    }

    public static void benchmarkDifference() {
        final byte[] mul2 = packedLongSet(0, 2, NUM_MAX);
        final byte[] mul3 = packedLongSet(0, 3, NUM_MAX);
        run("difference (long,packed)", new Runnable() {
            public void run() {
                byte[] result = LongAscSDBP.difference(mul2, mul3);
            }
        }, mul2.length + mul3.length);

        byte[] result = LongAscSDBP.difference(mul2, mul3);
        printSize(mul2.length, mul3.length, result.length);
    }

    public static void benchmarkDifferenceRaw() {
        final long[] mul2 = generateLongSet(0, 2, NUM_MAX);
        final long[] mul3 = generateLongSet(0, 3, NUM_MAX);
        run("difference (long,raw)", new Runnable() {
            public void run() {
                LongAscSDBP.Reader pivot = newReader(mul2);
                List<LongAscSDBP.Reader> readers = newReaders(mul3);
                long[] result =
                    LongAscSDBP.difference(pivot, readers).toLongArray();
            }
        }, mul2.length + mul3.length);

        LongAscSDBP.Reader pivot = newReader(mul2);
        List<LongAscSDBP.Reader> readers = newReaders(mul3);
        long[] result = LongAscSDBP.difference(pivot, readers).toLongArray();
        printSize(mul2.length * 8, mul3.length * 8, result.length * 8);
    }

    static long[] generateLongSet(int start, int offset, int max) {
        LongArrayOutputStream os = new LongArrayOutputStream();
        for (int i = start; i < max; i += offset) {
            os.write(i);
        }
        return os.toLongArray();
    }

    static byte[] packedLongSet(int start, int offset, int max) {
        return LongAscSDBP.toBytes(generateLongSet(start, offset, max));
    }

    static LongAscSDBP.Reader newReader(long[] data) {
        LongAscSDBP.Reader r =
            new LongAscSDBP.Reader(new LongArrayInputStream(data));
        r.read();
        return r;
    }

    static List<LongAscSDBP.Reader> newReaders(long[] ...data) {
        ArrayList<LongAscSDBP.Reader> readers =
            new ArrayList<LongAscSDBP.Reader>(data.length);
        for (long[] d : data) {
            readers.add(newReader(d));
        }
        return readers;
    }
}
