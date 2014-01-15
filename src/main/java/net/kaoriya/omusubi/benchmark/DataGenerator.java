package net.kaoriya.omusubi.benchmark;

public abstract class DataGenerator {

    public static final int DEFAULT_REPEAT = 5;

    private int[][] data;

    public final int[][] getData() {
        if (this.data == null) {
            this.data = generate();
        }
        return this.data;
    }

    public final void reset() {
        this.data = null;
    }

    public abstract String getName();

    public abstract int[][] generate();

    public int getRepeat() {
        return DEFAULT_REPEAT;
    }
}
