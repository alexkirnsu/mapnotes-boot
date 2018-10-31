package alexkirnsu.dao;

import alexkirnsu.config.TestAppPropertiesInitializer;
import com.github.alexkirnsu.Application;
import com.github.alexkirnsu.dao.UserDao;
import com.github.alexkirnsu.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@ContextConfiguration(initializers = {TestAppPropertiesInitializer.class})
@DataJpaTest
public class UserDaoImplTest {

    private static String LOGIN = "Alex";
    private static String PASSWORD = "password";
    private User testUser;

    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        testUser = new User();
        testUser.setLogin(LOGIN);
        testUser.setPassword(PASSWORD);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/user.sql")
    public void getByLogin_IfUserWithCurrentLoginExists_ReturnUserWithCorrectDecryptedPassword() {
        User user = userDao.getByLogin(LOGIN);

        assertTrue(passwordEncoder.matches(PASSWORD, user.getPassword()));
    }
}
