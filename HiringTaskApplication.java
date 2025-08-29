package com.healthrx.hiringtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.healthrx.hiringtask.service.HiringTaskService;

@SpringBootApplication
public class HiringTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiringTaskApplication.class, args);
    }

    @Component
    public static class TaskRunner {
        
        private final HiringTaskService hiringTaskService;
        
        public TaskRunner(HiringTaskService hiringTaskService) {
            this.hiringTaskService = hiringTaskService;
        }
        
        @EventListener(ApplicationReadyEvent.class)
        public void runTaskOnStartup() {
            System.out.println("Starting hiring task execution...");
            hiringTaskService.executeHiringTask();
        }
    }
}
