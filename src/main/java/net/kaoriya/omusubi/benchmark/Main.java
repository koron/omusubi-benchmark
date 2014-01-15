package net.kaoriya.omusubi.benchmark;

public class Main {
    public static void main(String[] args) {
        Benchmarks b = new Benchmarks();
        b.prepare();
        b.execute(new OmusubiCodec());
        b.execute(new LemireCodec());
        b.report(System.out);
    }
}
