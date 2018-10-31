package alexkirnsu.dao;

import alexkirnsu.config.TestAppPropertiesInitializer;
import com.github.alexkirnsu.Application;
import com.github.alexkirnsu.dao.AuthorityDao;
import com.github.alexkirnsu.entity.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@ContextConfiguration(initializers = {TestAppPropertiesInitializer.class})
public class AuthorityDaoImplTest {

    private static String AUTHORITY = "USER_ROLE";
    private static String LOGIN = "Alex";

    @Autowired
    AuthorityDao authorityDao;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/authority.sql")
    public void getByUserLogin_IfUserWithLoginExists_ReturnItsAuthorities() {
        List<Authority> authorities = authorityDao.getByUserLogin(LOGIN);

        assertTrue(authorities.stream().anyMatch(authority -> AUTHORITY.equals(authority.getRole())));
    }
}
