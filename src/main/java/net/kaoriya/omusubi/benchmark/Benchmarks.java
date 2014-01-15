package net.kaoriya.omusubi.benchmark;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.kaoriya.omusubi.benchmark.generator.DataGenerator;
import net.kaoriya.omusubi.benchmark.generator.RandomGenerator;

public class Benchmarks {

    public static final int DEFAULT_WARMUP = 2;

    private PrintStream log = System.out;

    private DataGenerator[] generators;

    private List<Results> results;

    public void prepare() {
        this.generators = new DataGenerator[] {
            new RandomGenerator(0, 8 * 1024, 1280, 1 << 20, 1 << 10),
            new RandomGenerator(0, 20, 1 << 20, 1 << 20, 1 << 10),
            // TODO: add DataGenerator
        };
        this.results = new ArrayList<Results>();
    }

    public Results executeOne(Codec c, DataGenerator g) {
        PerformanceLogger logger = new PerformanceLogger();
        int[][] data = g.getData();
        int repeat = g.getRepeat();

        for (int i = 0; i < repeat; ++i) {
            for (int[] array : data) {
                // compress.
                logger.compressionTimer.start();
                byte[] compressed = c.toBytes(array);
                logger.compressionTimer.end();
                logger.addOriginalSize(array.length);
                logger.addCompressedSize(compressed.length);
                // decompress
                logger.decompressionTimer.start();
                int[] decompressed = c.fromBytes(compressed);
                logger.decompressionTimer.end();
                checkArray(array, decompressed, c.getName());
            }
        }

        return new Results(g, c, logger);
    }

    /**
     * Execute benchmark with warm up.
     */
    public Results execute(Codec c, DataGenerator g) {
        System.gc();
        for (int i = 0; i < DEFAULT_WARMUP; ++i) {
            executeOne(c, g);
        }
        return executeOne(c, g);
    }

    public void execute(Codec c) {
        System.out.println("Benchmarking " + c.getName() + "...");
        for (DataGenerator g : this.generators) {
            this.results.add(execute(c, g));
        }
    }

    public void report(PrintStream out) {
        if (this.results == null || this.results.size() == 0) {
            out.println("No benchmarks executed.");
            return;
        }
        out.println("Benchmarks Results:");
        for (Results r : this.results) {
            out.println(r.toReportString());
        }
    }

    public static void checkArray(
            int[] expected,
            int[] actual,
            String name)
    {
        if (actual.length != expected.length) {
            throw new RuntimeException("Length mismatch:" +
                    " expected=" + expected.length +
                    " actual=" + actual.length +
                    " name=" + name);
        }
        for (int i = 0; i < expected.length; ++i) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException("Value mismatch: " +
                        " where=" + i + " expected=" + expected[i] +
                        " actual=" + actual[i] +
                        " name=" + name);
            }
        }
    }

}
