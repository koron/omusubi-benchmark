package net.kaoriya.omusubi.benchmark;

public class LongOmusubiCodec implements LongCodec {

    private net.kaoriya.omusubi.LongCodec codec;

    public LongOmusubiCodec(net.kaoriya.omusubi.LongCodec codec) {
        this.codec = codec;
    }

    public String getName() {
        return "Omusubi (" + this.codec.getClass().getSimpleName() + ")";
    }

    public byte[] toBytes(long[] src) {
        return this.codec.compress(src);
    }

    public long[] fromBytes(byte[] src) {
        return this.codec.decompress(src);
    }
}
