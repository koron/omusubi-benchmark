package net.kaoriya.omusubi.benchmark;

import net.kaoriya.omusubi.benchmark.generator.DataGenerator;
import net.kaoriya.omusubi.benchmark.generator.LongDataGenerator;

public class Results {
    public String dataName;
    public String codecName;
    public double bitPerInt;
    public double compressSpeed;
    public double decompressSpeed;
    private String unit = "MiS";

    public Results(
            String dataName,
            String codecName,
            double bitPerInt,
            double compressSpeed,
            double decompressSpeed)
    {
        this.dataName = dataName;
        this.codecName = codecName;
        this.bitPerInt = bitPerInt;
        this.compressSpeed = compressSpeed;
        this.decompressSpeed = decompressSpeed;
    }

    public Results(
            DataGenerator g,
            Codec c,
            PerformanceLogger l)
    {
        this.dataName = g.getName();
        this.codecName = c.getName();
        this.bitPerInt = l.getBitPerInt();
        this.compressSpeed = l.getCompressSpeed();
        this.decompressSpeed = l.getDecompressSpeed();
    }

    public Results(
            LongDataGenerator g,
            LongCodec c,
            PerformanceLogger l)
    {
        this.dataName = g.getName();
        this.codecName = c.getName();
        this.bitPerInt = l.getBitPerInt();
        this.compressSpeed = l.getCompressSpeed();
        this.decompressSpeed = l.getDecompressSpeed();
        this.unit = "MlS";
    }

    public String toReportString() {
        return String.format(
                "Codec:  %2$s\n" +
                "  Data: %1$s\n" + 
                "  Density:          %3$6.2f bits/int\n" +
                "  Compress Speed:   %4$6.0f %6$s\n" +
                "  Decompress Speed: %5$6.0f %6$s",
                this.dataName, this.codecName,
                this.bitPerInt, this.compressSpeed, this.decompressSpeed,
                this.unit);
    }
}
