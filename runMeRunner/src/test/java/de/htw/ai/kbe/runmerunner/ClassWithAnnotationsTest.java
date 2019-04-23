package de.htw.ai.kbe.runmerunner;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test if annotations are handled as expected by runMeRunner
 */
public class ClassWithAnnotationsTest {

    private static final String CLASS_WITH_ANNOTATIONS = "de.htw.ai.kbe.runmerunner.ClassWithAnnotations";
    private String report = null;

    @Before
    public void initializeReport() {
        try {
            report = App.reportMaker(CLASS_WITH_ANNOTATIONS);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            fail("Need this class in proper state for testing: " + CLASS_WITH_ANNOTATIONS);
        }
    }

    @Test
    public void testSuccessfulFindingOfPrivateStaticMethod() {
        assertThat(report, containsString("privateStaticMethod ->  Not invokable: IllegalAccessException"));
    }

    @Test
    public void testSuccessfulFindingOfPackagePrivateMethod() {
        assertThat(report, containsString("packagePrivateMethod ->  Success!"));
    }

    @Test
    public void testSuccessfulFindingOfPackagePrivateStaticMethod() {
        assertThat(report, containsString("packagePrivateStaticMethod ->  Success!"));
    }

    @Test
    public void testSuccessfulFindingOfPublicNotStaticMethod() {
        assertThat(report, containsString("publicNotStaticMethod ->  Success!"));
    }

    @Test
    public void testSuccessfulFindingOfPublicAndStaticMethod() {
        assertThat(report, containsString("publicAndStaticMethod ->  Success!"));
    }

    @Test
    public void testSuccessfulFindingOfMethodThrowsException() {
        assertThat(report, containsString("methodThrowsException ->  Not invokable: InvocationTargetException"));
    }

    @Test
    public void testSuccessfulFindingOfNotAnnotatedMethod() {
        assertThat(report, containsString("notAnnotatedMethod ->  Found no annotation!"));
    }

    @Test
    public void testSuccessfulFindingOfPrivateNotStaticMethod() {
        assertThat(report, containsString("privateNotStaticMethod ->  Not invokable: IllegalAccessException"));
    }

    @Test
    public void testSuccessfulFindingOfNoParamMethod() {
        assertThat(report, containsString("noParamMethod ->  Not invokable: IllegalArgumentException"));
    }

    @Test
    public void testSuccessfulFindingOfProtectedNotStaticMethod() {
        assertThat(report, containsString("protectedNotStaticMethod ->  Success!"));
    }
}

