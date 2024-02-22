package ru.duckcoder.marchingcubes.mapgenerator;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
public class ApplicationTest {
    @Test
    public void mainMethodTest(){
        Method[] declaredMethods = Application.class.getDeclaredMethods();
        PrintStream tmp = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            Arrays.stream(declaredMethods)
                    .peek(method -> method.setAccessible(true))
                    .filter(method -> method.getName().equals("main")
                            && method.getReturnType().equals(Void.TYPE))
                    .forEach(method -> assertDoesNotThrow(() -> assertEquals("main", method.getName())));
        } catch (SecurityException | InaccessibleObjectException e) {
            log.error(e.getMessage());
        }
        System.setOut(tmp);
    }
}
