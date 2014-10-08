package net.kaoriya.omusubi.benchmark;

/**
 * Benchmark set operations.
 */
public class Main2 {

    final static int NUM_MAX = 1000000;

    public static void main(String[] args) {
        benchmark();
    }

    public static void benchmark() {
        System.out.println();
        IntSetBenchmark.benchmark();
        System.out.println();
        LongSetBenchmark.benchmark();
    }
}
