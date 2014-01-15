package net.kaoriya.omusubi.benchmark;

public interface Codec {
    String getName();
    byte[] toBytes(int[] src);
    int[] fromBytes(byte[] src);
}
