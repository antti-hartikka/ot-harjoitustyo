package trainerapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import trainerapp.domain.Score;

import static org.junit.Assert.*;

public class ScoreTest {

    public ScoreTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void scoreGeneratesRightAmountOfNotes() {
        Score s = new Score();
        s.generate(64);
        int noteCount = s.getNotes().length;
        assertEquals(64, noteCount);
    }

    @Test
    public void methodSetScaleSetsScaleCorrectly() {
        Score s = new Score();
        s.setScale(4, "major");
        assertArrayEquals(new int[]{4, 6, 8, 9, 11, 1, 3}, s.getScale());
    }
}