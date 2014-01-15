package net.kaoriya.omusubi.benchmark;

import net.kaoriya.omusubi.IntDZBP;

public class OmusubiCodec implements Codec {

    private IntDZBP codec = new IntDZBP();

    public String getName() {
        return "Omusubi";
    }

    public byte[] toBytes(int[] src) {
        return this.codec.compress(src);
    }

    public int[] fromBytes(byte[] src) {
        return this.codec.decompress(src);
    }
}
