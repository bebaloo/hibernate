package task_2_5_hibernate.service;

import com.example.task_2_5_hibernate.service.GroupService;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GroupService.class})
class GroupServiceTest {
   /* @MockBean
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
    }*/
}