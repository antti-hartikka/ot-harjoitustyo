package trainerapp.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import trainerapp.dao.DBUserDao;
import trainerapp.dao.UserDao;
import java.sql.*;


public class DBUserDaoTest {

    UserDao dao = new DBUserDao("jdbc:sqlite:test.db");
    Connection db;

    public DBUserDaoTest() {
        try {
            db = DriverManager.getConnection("jdbc:sqlite:test.db");
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
            s.execute("DELETE FROM Users");
        } catch (SQLException ignored) {

        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void createUserAddsNewUserToDB() {
        assertTrue(dao.createUser("newUser"));
        assertTrue(dao.getAllUsers().contains("newUser"));
    }

    @Test
    public void createUserReturnsFalseIfUsernameIsTaken() {
        assertTrue(dao.createUser("newUser"));
        assertFalse(dao.createUser("newUser"));
    }

    @Test
    public void getAllUsersReturnsCorrectAmountOfUsers() {
        assertTrue(dao.createUser("user1"));
        assertTrue(dao.createUser("user2"));
        assertTrue(dao.createUser("user3"));
        assertTrue(dao.createUser("user4"));
        assertEquals(4, dao.getAllUsers().size());
    }

}

