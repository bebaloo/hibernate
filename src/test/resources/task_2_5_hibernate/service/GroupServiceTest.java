package task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.dao.GroupDao;
import com.example.task_2_5_hibernate.entity.Group;
import com.example.task_2_5_hibernate.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GroupService.class})
class GroupServiceTest {
    @MockBean
    private GroupDao groupDao;
    @Autowired
    private GroupService groupService;

    @Test
    void getWithLessStudentsNumber_studentsNumber_returnsGroups() {
        Group group = new Group(1, "aa-11");

        List<Group> groups = new ArrayList<>();
        groups.add(group);

        when(groupDao.findWithLessStudentsNumber(any(Integer.TYPE))).thenReturn(groups);

        assertTrue(groupService.getWithLessStudentsNumber(1).contains(group));
        verify(groupDao).findWithLessStudentsNumber(any(Integer.TYPE));
    }
}