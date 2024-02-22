package ru.duckcoder.marchingcubes.mapgenerator.config;

import com.jme3.system.AppSettings;
import lombok.Getter;

@Getter
public class Config {
    public static final Config DEFAULT = new Config(
            AppSettings.LWJGL_OPENGL45,
            false,
            1600,
            900,
            false,
            60,
            120,
            AntiAliasing.AA_DISABLE.getValue(),
            true,
            32,
            8,
            24,
            true
    );

    private final String title = "Duck Coder's map generator";

    private String renderer;
    private boolean fullScreen;
    private int width;
    private int height;
    private boolean vSync;
    private int frequency;
    private int frameRate;
    private int samples;
    private boolean gammaCorrection;
    private int bitsPerPixel;
    private int stencilBits;
    private int depthBits;

    private boolean stereo3D;

    public Config(
            String renderer, boolean fullScreen, int width, int height, boolean vSync, int frequency, int frameRate, int samples,
            boolean gammaCorrection, int bitsPerPixel, int stencilBits, int depthBits,
            boolean stereo3D
    ) {
        this.renderer = renderer;
        this.fullScreen = fullScreen;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.frequency = frequency;
        this.frameRate = frameRate;
        this.samples = samples;
        this.gammaCorrection = gammaCorrection;
        this.bitsPerPixel = bitsPerPixel;
        this.stencilBits = stencilBits;
        this.depthBits = depthBits;
        this.stereo3D = stereo3D;
    }
}
