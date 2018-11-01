package alexkirnsu.service;

import com.github.alexkirnsu.dao.CommentDao;
import com.github.alexkirnsu.dto.CommentDto;
import com.github.alexkirnsu.entity.*;
import com.github.alexkirnsu.service.CommentService;
import com.github.alexkirnsu.service.impl.CommentServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    private static final String CURRENT_USER_NAME = "Alex";
    private static final String OTHER_USER_NAME = "Sasa";
    private static final int NOTE_ID = 1;

    private Note testNote = new Note();
    private Comment testComment = new Comment();
    private CommentDto testCommentDto = new CommentDto();
    private List<Comment> comments = new ArrayList<>();

    @Mock
    ModelMapper modelMapper;

    @Mock
    CommentDao commentDao;

    @InjectMocks
    CommentService commentService = spy(new CommentServiceImpl());

    @Before
    public void setUp() {
      testNote.setId(NOTE_ID);

      testComment.setNote(testNote);

        doReturn(comments).when(commentDao).getByNoteId(anyInt());
        doReturn(testCommentDto).when(modelMapper).map(testComment, CommentDto.class);
    }

    @Test
    public void getForNote_IfCommentOwnerIsCurrentUser_CommentDtoIsNotAlien() {
        testCommentDto.setOwner(CURRENT_USER_NAME);
        comments.add(testComment);

        for (CommentDto commentDto : commentService.getByNoteIdAndUserLogin(NOTE_ID, CURRENT_USER_NAME)) {
            assertFalse(commentDto.isAlien());
        }
    }

    @Test
    public void getForNote_IfCommentOwnerIsNotCurrentUser_CommentDtoIsAlien() {
        testCommentDto.setOwner(OTHER_USER_NAME);

        for (CommentDto commentDto : commentService.getByNoteIdAndUserLogin(NOTE_ID, CURRENT_USER_NAME)) {
            assertTrue(commentDto.isAlien());
        }
    }
}
