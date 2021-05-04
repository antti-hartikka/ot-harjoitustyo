package trainerapp.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import trainerapp.dao.SessionDao;
import trainerapp.dao.UserDao;

import static org.junit.Assert.*;

public class TrainerSessionTest {

    TrainerSession trainerSession;
    DataService dataService;
    Score score;

    public TrainerSessionTest() {
        UserDao userDao = new FakeUserDao();
        SessionDao sessionDao = new FakeSessionDao();
        dataService = new DataService(userDao, sessionDao);
        trainerSession = new TrainerSession("testuser", dataService);
        score = new Score();
        score.generate(32);
        trainerSession.setScore(score);
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
    public void trainingSessionIsSavedToDaoWhenEnded() {
        trainerSession.resetSession();
        for (int i = 0; i < 10; i++) {
            trainerSession.noteInput(60);
        }
        trainerSession.endSession();
        assertEquals(1, dataService.getSessions("testuser").size());
    }

}
