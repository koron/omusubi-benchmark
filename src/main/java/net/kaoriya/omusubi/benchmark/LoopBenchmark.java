package net.kaoriya.omusubi.benchmark;


public class LoopBenchmark {

    final Runnable proc;

    private long warmSec = 1;

    private long measureSec = 1;

    private double countPerSec = 0f;

    public LoopBenchmark(Runnable proc) {
        this.proc = proc;
    }

    public double run() {
        StopWatch sw = new StopWatch();

        // warm up.
        sw.start();
        while (!sw.isElapsedSecond(this.warmSec)) {
            this.proc.run();
        }
        sw.end();
        sw.reset();

        // measure.
        int count = 0;
        sw.start();
        while (!sw.isElapsedSecond(this.measureSec)) {
            this.proc.run();
            ++count;
        }
        sw.end();
        return this.countPerSec = (double)count / sw.getDurationInSeconds();
    }

    public double getCountPerSec() {
        return this.countPerSec;
    }
}
