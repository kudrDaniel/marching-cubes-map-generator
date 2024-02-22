package ru.duckcoder.marchingcubes.mapgenerator.util;

import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;

@Log4j2
public final class IOHelper {
    @Nullable
    public static String[] readFile(@Nonnull String path, Charset charset,@Nonnull String regex) {
        String string = readFile(path, charset);
        if (string != null) {
            return string.split(regex);
        }
        log.error("Received string is null");
        return null;
    }

    @Nullable
    public static String readFile(@Nonnull String path, Charset charset) {
        byte[] bytes = readFile(path);
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        if (bytes != null) {
            return new String(bytes, charset);
        }
        log.error("Received bytes is null");
        return null;
    }

    @Nullable
    public static byte[] readFile(@Nonnull String path) {
        byte[] bytes = null;
        try (FileInputStream fis = new FileInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            bytes = new byte[1024];
            int ch, i = 0;
            while ((ch = bis.read()) != -1) {
                if (i >= bytes.length) {
                    bytes = Arrays.copyOf(bytes, bytes.length * 2);
                }
                bytes[i] = (byte) ch;
                i++;
            }
            bytes = Arrays.copyOfRange(bytes, 0, i);
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        }
        return bytes;
    }

    public static String getFilePath(String rootPath, String... parts) {
        return Paths.get(rootPath, parts).toAbsolutePath().normalize().toString();
    }
}
