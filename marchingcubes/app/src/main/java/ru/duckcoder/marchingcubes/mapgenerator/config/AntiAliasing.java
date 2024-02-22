package ru.duckcoder.marchingcubes.mapgenerator.config;

public enum AntiAliasing {
    AA_DISABLE(0),
    AA_1(1),
    AA_2(2),
    AA_4(4),
    AA_6(6),
    AA_8(8),
    AA_16(16);

    private final int value;

    public int getValue() {
        return this.value;
    }

    AntiAliasing(int value) {
        this.value = value;
    }
}
