package net.kaoriya.omusubi.benchmark.generator;

import java.util.Random;

public class LongRandomGenerator extends LongDataGenerator
{
    private long seed;
    private int count;
    private int length;
    private long mean;
    private long range;

    public LongRandomGenerator(long seed, int count, int length,
            long mean, long range)
    {
        this.seed   = seed;
        this.count  = count;
        this.length = length;
        this.mean   = mean;
        this.range  = range;
    }

    public String getName() {
        return String.format(
                "Random(length=%4$d mean=%1$d range=%2$d) * %3$d",
                this.mean, this.range, this.count, this.length);
    }

    public long[][] generate() {
        long offset = this.mean - this.range / 2;
        long[][] chunks = new long[this.count][];
        Random r = new Random(this.seed);
        for (int i = 0; i < this.count; ++i) {
            long[] chunk = chunks[i] = new long[this.length];
            for (int j = 0; j < this.length; ++j) {
                chunk[j] = (r.nextLong() % this.range) + offset;
            }
        }
        return chunks;
    }
}
