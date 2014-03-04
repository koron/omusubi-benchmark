package net.kaoriya.omusubi.benchmark;

public interface LongCodec {
    String getName();
    byte[] toBytes(long[] src);
    long[] fromBytes(byte[] src);
}
