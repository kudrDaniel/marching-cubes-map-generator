package ru.duckcoder.marchingcubes.mapgenerator.util;

import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Util class that used to read, write, compress, decompress files.
 */
@Log4j2
public final class IOHelper {
    /**
     * Decompress byte array data;
     * @param compressedData data that need to be decompressed.
     * @return decompressed byte array if success, otherwise null;
     */
    public static byte[] decompress(byte[] compressedData) {
        byte[] decompressedData = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
             GZIPInputStream gis = new GZIPInputStream(bais)) {
            decompressedData = gis.readAllBytes();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return decompressedData;
    }

    /**
     * Compress byte array data.
     * @param uncompressedData data that need to be compressed.
     * @return return compressed byte array if success, otherwise null.
     */
    public static byte[] compress(byte[] uncompressedData) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             GZIPOutputStream gos = new GZIPOutputStream(baos)) {
            gos.write(uncompressedData);
            gos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Reads file specified by path, splits by regular expression and returns string array;
     * @param path absolute normalized path string to file.
     * @param charset charset that be used to decode byte array to string. If null then uses UTF-8 encoding.
     * @param regex regular expression that be used to split string into string array.
     * @return string array if success, null otherwise.
     */
    @Nullable
    public static String[] readFile(@Nonnull String path, Charset charset, @Nonnull String regex) {
        String string = readFile(path, charset);
        if (string != null) {
            return string.split(regex);
        }
        log.error("Received string is null");
        return null;
    }

    /**
     * Reads the file specified by path and returns string.
     * @param path absolute normalized path string to file.
     * @param charset charset that be used to decode byte array to string. If null then uses UTF-8 encoding.
     * @return string if success, null otherwise.
     */
    @Nullable
    public static String readFile(@Nonnull String path, Charset charset) {
        byte[] bytes = readFile(path);
        if (charset == null) {
            log.info("Received charset is null, using UTF-8");
            charset = StandardCharsets.UTF_8;
        }
        if (bytes != null) {
            return new String(bytes, charset);
        }
        log.error("Received bytes is null");
        return null;
    }

    /**
     * Reads the file specified by path and returns byre array.
     * @param path absolute normalized path string to file.
     * @return byte array if success, null otherwise.
     */
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
            log.error(e.getMessage());
        }
        return bytes;
    }

    /**
     * Joins string array to string by delimiter and writes to file.
     * @param path absolute normalized path string to file.
     * @param strings string array to be written to file.
     * @param charset charset that be used to encode string to byte array. If null then uses UTF-8 encoding.
     * @param delimiter string or character that be pasted between strings.
     * @return true if success, otherwise false.
     */
    public static boolean writeFile(@Nonnull String path, @Nonnull String[] strings, Charset charset, CharSequence delimiter) {
        return writeFile(path, String.join(delimiter, strings), charset);
    }

    /**
     * Writes string to file.
     * @param path absolute normalized path string to file.
     * @param string string to be written to file.
     * @param charset charset that be used to encode string to byte array. If null then uses UTF-8 encoding.
     * @return true if success, otherwise false.
     */
    public static boolean writeFile(@Nonnull String path, @Nonnull String string, Charset charset) {
        if (charset == null) {
            log.info("Received charset is null, using UTF-8");
            charset = StandardCharsets.UTF_8;
        }
        return writeFile(path, string.getBytes(charset));
    }

    /**
     * Writes byte array to file.
     * @param path absolute normalized path string to file.
     * @param bytes byte array to be written to write.
     * @return true if success, otherwise false.
     */
    public static boolean writeFile(@Nonnull String path, @Nonnull byte[] bytes) {
        try (FileOutputStream fos = new FileOutputStream(path);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(bytes);
            bos.flush();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * Use to get absolute normalized path to file.
     * @param rootPath application root path.
     * @param parts sub-folders and filename.
     * @return path string specified by args.
     */
    public static String getFilePath(@Nonnull String rootPath, String... parts) {
        return Paths.get(rootPath, parts).toAbsolutePath().normalize().toString();
    }
}
