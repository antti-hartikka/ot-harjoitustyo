package trainerapp.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import trainerapp.dao.DBSessionDao;
import trainerapp.dao.DBUserDao;
import trainerapp.dao.SessionDao;
import trainerapp.dao.UserDao;
import trainerapp.domain.Session;

import static org.junit.Assert.*;

import java.sql.*;
import java.time.LocalDate;

public class DBSessionDaoTest {

    SessionDao dao;
    Connection db;

    public DBSessionDaoTest() {
        try {
            db = DriverManager.getConnection("jdbc:sqlite:test.db");
            dao = new DBSessionDao("jdbc:sqlite:test.db");
        } catch (SQLException ignored) {
        }
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            Statement s = db.createStatement();
            s.execute("DROP TABLE Sessions");
            s.execute("DROP TABLE Users");
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE)");
            s.execute(
                    "CREATE TABLE Sessions (" +
                            "id INTEGER PRIMARY KEY, " +
                            "user_id INTEGER, " +
                            "average_miss REAL, " +
                            "date DATE, " +
                            "FOREIGN KEY (user_id) REFERENCES Users(id)" +
                            ")"
            );
            s.execute("INSERT INTO Users (username) VALUES ('testUser')");
        } catch (SQLException ignored) {

        }
    }

    @After
    public void tearDown() {

    }

    @Test
    public void addingSessionAddsSessionToDB() {
        Session session = new Session("testUser", LocalDate.now(), 0.23);
        dao.addSession(session);
        assertEquals(1, dao.getSessions("testUser").size());
    }

    @Test
    public void getSessionsReturnsCorrectAmountOfSessions() {
        dao.addSession(new Session("testUser", LocalDate.now(), 1.34));
        dao.addSession(new Session("testUser", LocalDate.now(), 4.345));
        dao.addSession(new Session("testUser", LocalDate.now(), 0.62));
        dao.addSession(new Session("testUser", LocalDate.now(), 8.957));
        assertEquals(4, dao.getSessions("testUser").size());
    }

}


