package alexkirnsu.service;

import com.github.alexkirnsu.dao.NoteDao;
import com.github.alexkirnsu.entity.Comment;
import com.github.alexkirnsu.entity.Note;
import com.github.alexkirnsu.entity.User;
import com.github.alexkirnsu.service.NoteService;
import com.github.alexkirnsu.service.impl.NoteServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceImplTest {

    private static final int NOTE_ID = 1;
    private static final String CURRENT_USER_NAME = "Alex";
    private static final String OTHER_USER_NAME = "Sasa";

    private User user = new User();
    private Note testNote = new Note();
    private Comment testComment = new Comment();
    private List<Comment> comments = new ArrayList<>();

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private NoteDao noteDao;

    @InjectMocks
    private NoteService noteService= spy(new NoteServiceImpl());


    @Before
    public void setUp() {
        user.setLogin(CURRENT_USER_NAME);
        testNote.setUser(user);

        doReturn(testNote).when(noteDao).getById(anyInt());
        doNothing().when(noteDao).deleteById(anyInt());
    }

    @Test
    public void delete_IfNoteOwnerIsNotCurrentUser_NoteIsNotDeleted() {
        user.setLogin(OTHER_USER_NAME);
        testNote.setUser(user);
        assertFalse(noteService.deleteById(NOTE_ID, CURRENT_USER_NAME));
    }

    @Test
    public void delete_IfAlienComment_NoteIsNotDeleted() {
        testComment.setOwner(OTHER_USER_NAME);
        comments.add(testComment);
        testNote.setComments(comments);

        assertFalse(noteService.deleteById(NOTE_ID, CURRENT_USER_NAME));
    }

    @Test
    public void delete_IfNoteOwnerIsCurrentUserAndNoAlienComment_NoteIsDeleted() {
        testComment.setOwner(CURRENT_USER_NAME);
        comments.add(testComment);
        testNote.setComments(comments);

        assertTrue(noteService.deleteById(NOTE_ID, CURRENT_USER_NAME));
    }
}