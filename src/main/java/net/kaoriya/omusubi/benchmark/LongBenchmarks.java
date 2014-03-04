package net.kaoriya.omusubi.benchmark;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.kaoriya.omusubi.benchmark.generator.LongDataGenerator;
import net.kaoriya.omusubi.benchmark.generator.LongRandomGenerator;

public class LongBenchmarks {

    public static final int DEFAULT_WARMUP = 2;

    private PrintStream log = System.out;

    private LongDataGenerator[] generators;

    private List<Results> results;

    public void prepare() {
        this.generators = new LongDataGenerator[] {
            new LongRandomGenerator(0, 8 * 1024, 1280, 1 << 20, 1 << 10),
            new LongRandomGenerator(0, 20, 1 << 20, 1 << 20, 1 << 10),
            // TODO: add LongDataGenerator
        };
        this.results = new ArrayList<Results>();
    }

    public Results executeOne(LongCodec c, LongDataGenerator g) {
        PerformanceLogger logger = new PerformanceLogger();
        long[][] data = g.getData();
        int repeat = g.getRepeat();

        for (int i = 0; i < repeat; ++i) {
            for (long[] array : data) {
                // compress.
                logger.compressionTimer.start();
                byte[] compressed = c.toBytes(array);
                logger.compressionTimer.end();
                logger.addOriginalSize(array.length);
                logger.addCompressedSize(compressed.length);
                // decompress
                logger.decompressionTimer.start();
                long[] decompressed = c.fromBytes(compressed);
                logger.decompressionTimer.end();
                checkArray(array, decompressed, c.getName());
            }
        }

        return new Results(g, c, logger);
    }

    /**
     * Execute benchmark with warm up.
     */
    public Results execute(LongCodec c, LongDataGenerator g) {
        System.gc();
        for (int i = 0; i < DEFAULT_WARMUP; ++i) {
            executeOne(c, g);
        }
        return executeOne(c, g);
    }

    public void execute(LongCodec c) {
        System.out.println("Benchmarking " + c.getName() + "...");
        for (LongDataGenerator g : this.generators) {
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
            long[] expected,
            long[] actual,
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
