package net.kaoriya.omusubi.benchmark.generator;

public abstract class LongDataGenerator {

    public static final int DEFAULT_REPEAT = 5;

    private long[][] data;

    public final long[][] getData() {
        if (this.data == null) {
            this.data = generate();
        }
        return this.data;
    }

    public final void reset() {
        this.data = null;
    }

    public abstract String getName();

    public abstract long[][] generate();

    public int getRepeat() {
        return DEFAULT_REPEAT;
    }
}
