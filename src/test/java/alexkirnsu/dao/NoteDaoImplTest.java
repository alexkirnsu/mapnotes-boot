package alexkirnsu.dao;

import alexkirnsu.config.TestAppPropertiesInitializer;
import com.github.alexkirnsu.Application;
import com.github.alexkirnsu.dao.NoteDao;
import com.github.alexkirnsu.entity.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@ContextConfiguration(initializers = {TestAppPropertiesInitializer.class})
@DataJpaTest
public class NoteDaoImplTest {

    private static int OWN_NOTES_COUNT = 2;
    private static int ALIEN_NOTES_COUNT = 1;
    private static int FIRST_OWN_NOTE_ID = 30;
    private static int SECOND_OWN_NOTE_ID = 40;
    private static int ALIEN_NOTE_ID = 50;
    private static int NOTE_ID = 30;
    private static double LAT = 0.0;
    private static double LNG = 0.0;
    private static double PRECISION = 1.0e-8;
    private static String PLACE = "place";
    private static String USER_LOGIN = "alex";

    @Autowired
    NoteDao noteDao;

    @Test
    public void add_IfNoteNecessaryFieldsAreFilled_SaveNoteToDataBase() {
        Note note = new Note();

        note.setPlace(PLACE);
        note.setLat(LAT);
        note.setLng(LNG);
        noteDao.add(note);
    }

    @Test(expected = ConstraintViolationException.class)
    public void add_IfNotePlaceFieldNotFilled_ThrowConstraintViolationException() {
        Note note = new Note();

        note.setLat(LAT);
        note.setLng(LNG);
        noteDao.add(note);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/note.sql")
    public void getById_IfNoteWithIdExists_ReturnNoteWithCorrectData() {
        Note note = noteDao.getById(NOTE_ID);

        assertEquals(PLACE, note.getPlace());
        assertEquals(LAT, note.getLat(), PRECISION);
        assertEquals(LNG, note.getLng(), PRECISION);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/listOfNotes.sql")
    public void getByUserLogin_IfUserLoginExists_ReturnOwnNotes() {
        List<Note> notes = noteDao.getByUserLogin(USER_LOGIN);

        assertEquals(OWN_NOTES_COUNT, notes.size());
        assertTrue(notes.stream().anyMatch(note -> FIRST_OWN_NOTE_ID == note.getId()));
        assertTrue(notes.stream().anyMatch(note -> SECOND_OWN_NOTE_ID == note.getId()));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/listOfNotes.sql")
    public void getOpenedAlienForUserLogin_IfUserLoginExists_ReturnOpenedAlienNotes() {
        List<Note> notes = noteDao.getOpenedAlienForUserLogin(USER_LOGIN);

        assertEquals(ALIEN_NOTES_COUNT, notes.size());
        assertTrue(notes.stream().anyMatch(note -> ALIEN_NOTE_ID == note.getId()));
    }
}