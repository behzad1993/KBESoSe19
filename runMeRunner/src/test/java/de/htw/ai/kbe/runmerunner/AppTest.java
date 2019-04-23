package de.htw.ai.kbe.runmerunner;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Unit test for App.
 */
public class AppTest {

    @Test
    public void mainTest_missingInFileNameArg() throws ParseException {
        String[] args = {"-c", "de.htw.ai.kbe.runmerunner.ClassWithAnnotations"};
        String[] strings = App.parseArguments(args);
    }

    @Test(expected = ParseException.class)
    public void mainTest_missingArgs() throws ParseException {
        String[] args = {""};
        App.parseArguments(args);
    }

    @Test
    public void parseArgumentsTest_wrongInFileName() {
        String[] args = {"", "fileName"};
        App.main(args);
    }

    @Test
    public void parseArgumentsTest_rightArgs() throws ParseException {
        String[] args = {"-c", "de.htw.ai.kbe.runmerunner.ClassWithAnnotations", "-o", "report.txt"};
        String[] checkedArgs = App.parseArguments(args);
        String[] expectedArgs = {"de.htw.ai.kbe.runmerunner.ClassWithAnnotations", "report.txt"};

        String checkedInFileName = checkedArgs[0];
        String checkedOutFileName = checkedArgs[1];
        String expectedInFileName = expectedArgs[0];
        String expectedOutFileName = expectedArgs[1];

        assertEquals(checkedArgs.length, expectedArgs.length);
        assertEquals(checkedInFileName, expectedInFileName);
        assertEquals(checkedOutFileName, expectedOutFileName);
    }

    @Test(expected = ClassNotFoundException.class)
    public void reportMakerTest_emptyClassName() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        App.reportMaker("");
    }

    @Test(expected = ClassNotFoundException.class)
    public void reportMakerTest_wrongClassName() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        try {
            App.reportMaker("notExistingClass");
        } catch (ClassNotFoundException e) {
            String message = "notExistingClass";
            assertEquals(message, e.getMessage());
            throw e;
        }
    }

    @Test(expected = ClassNotFoundException.class)
    public void reportMakerTest_null() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        try {
            App.reportMaker(null);
        } catch (ClassNotFoundException e) {
            assertNull(e.getMessage());
            throw e;
        }
    }

    @Test
    public void reportMakerTest_getOriginalReport() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String report = App.reportMaker("de.htw.ai.kbe.runmerunner.ClassWithAnnotations");
        assertNotNull(report);
        assertTrue(report.length() > 0);
    }

    @Test
    public void reportMakerTest_EmtpyClass() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String report = App.reportMaker("de.htw.ai.kbe.runmerunner.EmptyClass");
        assertNotNull(report);
        assertEquals(0, report.length());
    }

    @Test(expected = InstantiationException.class)
    public void reportMakerTest_EmtpyClassConstructor() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String report = App.reportMaker("de.htw.ai.kbe.runmerunner.EmptyClassConstructor");
        assertNotNull(report);
        assertEquals(0, report.length());
    }

    @Test(expected = InstantiationException.class)
    public void reportMakerTest_AbstractEmtpyClass() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String report = App.reportMaker("de.htw.ai.kbe.runmerunner.AbstractEmptyClass");
        assertNotNull(report);
        assertEquals(0, report.length());
    }

    @Test(expected = InstantiationException.class)
    public void reportMakerTest_EmtpyInterface() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String report = App.reportMaker("de.htw.ai.kbe.runmerunner.EmptyInterface");
        assertNotNull(report);
        assertEquals(0, report.length());
    }

    @Test
    public void saveToFileTest_createFile() {
        String message = "message";
        String fileName = "testFile";
        App.saveToFile(fileName, message);
        File file = new File(fileName);
        assertTrue(file.exists());
        file.deleteOnExit();
    }
}
