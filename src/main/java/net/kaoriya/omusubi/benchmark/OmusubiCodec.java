package net.kaoriya.omusubi.benchmark;

import net.kaoriya.omusubi.IntCodec;

public class OmusubiCodec implements Codec {

    private IntCodec codec;

    public OmusubiCodec(IntCodec codec) {
        this.codec = codec;
    }

    public String getName() {
        return "Omusubi (" + this.codec.getClass().getSimpleName() + ")";
    }

    public byte[] toBytes(int[] src) {
        return this.codec.compress(src);
    }

    public int[] fromBytes(byte[] src) {
        return this.codec.decompress(src);
    }
}
