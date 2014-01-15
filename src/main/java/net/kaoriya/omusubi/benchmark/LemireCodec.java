package net.kaoriya.omusubi.benchmark;

import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class LemireCodec implements Codec {

    private IntegerCODEC codec;

    public LemireCodec(IntegerCODEC codec) {
        this.codec = codec;
    }

    public String getName() {
        return "Lemire (" + this.codec.toString() + ")";
    }

    public byte[] toBytes(int[] src) {
        // Compress int array (src) to "dst" and set its length to "outSize".
        int[] dst = new int[src.length * 4 + 1024];
        IntWrapper inpos = new IntWrapper();
        IntWrapper outpos = new IntWrapper();
        this.codec.compress(src, inpos, src.length, dst, outpos);
        int outSize = outpos.get();
        // Convert int array to byte array.
        return toByteArray(dst, 0, outSize);
    }

    public int[] fromBytes(byte[] src) {
        // Decompress byte to int
        int[] srcInt = toIntArray(src, 0, src.length);
        int[] dst = new int[src.length * 4 + 1024];
        IntWrapper inpos = new IntWrapper();
        IntWrapper outpos = new IntWrapper();
        this.codec.uncompress(srcInt, inpos, srcInt.length, dst, outpos);
        int outSize = outpos.get();
        // Cut trailing buffer.
        int[] fitDst = new int[outSize];
        System.arraycopy(dst, 0, fitDst, 0, outSize);
        return fitDst;
    }

    public static byte[] toByteArray(int[] array, int off, int len) {
        byte[] raw = new byte[len * 4];
        ByteBuffer dstByteBuf = ByteBuffer.wrap(raw);
        IntBuffer dstIntBuf = dstByteBuf.asIntBuffer();
        dstIntBuf.put(array, off, len);
        return raw;
    }

    public static int[] toIntArray(byte[] src, int off, int len) {
        ByteBuffer srcByteBuf = ByteBuffer.wrap(src, off, len);
        IntBuffer srcIntBuf = srcByteBuf.asIntBuffer();
        int[] raw = new int[len / 4];
        IntBuffer dstIntBuf = IntBuffer.wrap(raw);
        dstIntBuf.put(srcIntBuf);
        return raw;
    }
}
