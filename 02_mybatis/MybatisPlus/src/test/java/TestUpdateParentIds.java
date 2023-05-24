import com.myexam.Application;
import com.myexam.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest(classes = Application.class)
public class TestUpdateParentIds {

    @Resource
    private NoteService noteService;

    @Test
    public void UpdateParentIds() {
        noteService.updateParentIds();
    }



}
