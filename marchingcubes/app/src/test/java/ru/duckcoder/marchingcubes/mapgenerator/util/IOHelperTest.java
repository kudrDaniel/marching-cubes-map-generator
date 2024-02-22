package ru.duckcoder.marchingcubes.mapgenerator.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
public class IOHelperTest {
    private static final String resourcePath;

    static {
        try {
            resourcePath = Path.of(IOHelperTest.class.getClassLoader().getResource("unit-tests").toURI()).toAbsolutePath().normalize().toString();
        } catch (NullPointerException | URISyntaxException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "readfixture0",
            "readfixture1",
            "readfixture2",
            "readfixture3",
            "readfixture4"
    })
    public void readBytesTest(String fileName) {
        byte[] expected = readFile(fileName);

        byte[] actual = IOHelper.readFile(getFixturePath(fileName));

        assertNotNull(actual);
        assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "writeFixture0",
            "writeFixture1",
            "writeFixture2",
            "writeFixture3",
            "writeFixture4"
    })
    public void writeBytesTest(String fileName) {
        try {
            Files.createFile(Path.of(getFixturePath(fileName)));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        boolean actual = IOHelper.writeFile(getFixturePath(fileName), fileName.getBytes(StandardCharsets.UTF_8));
        boolean expected = Arrays.equals(readFile(fileName), fileName.getBytes(StandardCharsets.UTF_8));

        assertEquals(expected, actual);

        try {
            Files.delete(Path.of(getFixturePath(fileName)));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static byte[] readFile(String fileName) {
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(getFixturePath(fileName))) {
            bytes = fis.readAllBytes();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public static String getFixturePath(@Nonnull String fileName) {
        return Path.of(resourcePath, "util", "iohelper", fileName).toAbsolutePath().normalize().toString();
    }
}
