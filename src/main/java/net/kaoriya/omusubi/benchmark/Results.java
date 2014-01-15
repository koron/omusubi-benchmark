package net.kaoriya.omusubi.benchmark;

public class Results {
    public String dataName;
    public String codecName;
    public double bitPerInt;
    public double compressSpeed;
    public double decompressSpeed;

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

    public String toReportString() {
        return String.format(
                "Data:%1$s Codec:%2$s, BPI:%3$.2f CSPD:%4$.0f, DSPD:%5$.0f",
                this.dataName, this.codecName,
                this.bitPerInt, this.compressSpeed, this.decompressSpeed);
    }
}
