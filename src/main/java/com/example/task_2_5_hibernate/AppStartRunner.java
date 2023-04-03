package com.example.task_2_5_hibernate;

import com.example.task_2_5_hibernate.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AppStartRunner implements ApplicationRunner {
    private final Storage storage;
    private final ConsoleMenu consoleMenu;
    private final CourseService courseService;
    @Override
    public void run(ApplicationArguments args) {
        courseService.delete(100);
        log.info("Application starting...");
        storage.fillDB();
        consoleMenu.run();
        log.info("Application stopped");
    }
}
