package net.kaoriya.omusubi.benchmark;

public class StopWatch {

    public final static long NANO_SEC = 1000000000L;

    private long startNano;
    private long duration = 0;

    public StopWatch() {}

    public void reset() {
        this.duration = 0;
    }

    public void start() {
        this.startNano = System.nanoTime();
    }

    public long end() {
        return this.duration += System.nanoTime() - this.startNano;
    }

    public long getDuration() {
        return this.duration;
    }

    public boolean isElapsedSecond(long sec) {
        return System.nanoTime() - this.startNano >= sec * NANO_SEC;
    }

    public double getDurationInSeconds() {
        return (double)this.duration / (double)NANO_SEC;
    }
}
