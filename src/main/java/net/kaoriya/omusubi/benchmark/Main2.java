package net.kaoriya.omusubi.benchmark;

import java.util.ArrayList;
import java.util.List;

import net.kaoriya.omusubi.IntAscSDBP;
import net.kaoriya.omusubi.io.IntArrayInputStream;
import net.kaoriya.omusubi.io.IntArrayOutputStream;

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
