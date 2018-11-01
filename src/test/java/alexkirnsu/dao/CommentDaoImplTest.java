package alexkirnsu.dao;

import alexkirnsu.config.TestAppPropertiesInitializer;
import com.github.alexkirnsu.Application;
import com.github.alexkirnsu.dao.CommentDao;
import com.github.alexkirnsu.entity.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import org.hibernate.exception.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@ContextConfiguration(initializers = {TestAppPropertiesInitializer.class})
@DataJpaTest
public class CommentDaoImplTest {

    private static int NOTE_ID = 30;
    private static int COMMENT_ID = 20;
    private static int FIRST_COMMENT_ID = 20;
    private static int SECOND_COMMENT_ID = 25;
    private static int COMMENT_COUNT_FOR_NOTE_ID = 2;
    private static String OWNER = "Alex";
    private static String TEXT = "Text";
    private static String UPDATED_TEXT = "UpdatedText";

    @Autowired
    private CommentDao commentDao;

    @Test
    public void add_IfCommentNecessaryFieldsAreFilled_SaveCommentToDataBase() {
        Comment comment = new Comment();

        comment.setText(TEXT);
        comment.setOwner(OWNER);
        commentDao.add(comment);
    }

    @Test(expected = ConstraintViolationException.class)
    public void add_IfCommentTextFieldNotFilled_ThrowConstraintViolationException() {
        Comment comment = new Comment();

        comment.setOwner(OWNER);
        commentDao.add(comment);
    }

    @Test(expected = ConstraintViolationException.class)
    public void add_IfCommentOwnerFieldNotFilled_ThrowConstraintViolationException() {
        Comment comment = new Comment();

        comment.setOwner(TEXT);
        commentDao.add(comment);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/comment.sql")
    public void getById_IfCommentWithIdExists_ReturnCommentWithCorrectData() {
        Comment comment = commentDao.getById(COMMENT_ID);

        assertEquals(TEXT, comment.getText());
        assertEquals(OWNER, comment.getOwner());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/comment.sql")
    public void updateText_IfCommentWithIdExists_UpdateTextOfComment() {
        commentDao.updateText(COMMENT_ID, UPDATED_TEXT);
        assertEquals(UPDATED_TEXT, commentDao.getById(COMMENT_ID).getText());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/listOfComments.sql")
    public void getByNoteId_IfCommentWithNoteIdExists_ReturnListOfThoseComments() {
        List<Comment> comments = commentDao.getByNoteId(NOTE_ID);

        assertEquals(COMMENT_COUNT_FOR_NOTE_ID, comments.size());
        assertTrue(comments.stream().anyMatch(comment -> FIRST_COMMENT_ID == comment.getId()));
        assertTrue(comments.stream().anyMatch(comment -> SECOND_COMMENT_ID == comment.getId()));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/comment.sql")
    public void deleteById_IfCommentWithIdExists_deleteCommentFromDatabase() {
        commentDao.deleteById(COMMENT_ID);
        assertNull(commentDao.getById(COMMENT_ID));
    }
}