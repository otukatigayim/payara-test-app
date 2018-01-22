package com.otukatigayim;

import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    private static final class MyTask implements Runnable {
        public void run() {
            print("[TASK:RUN] MyTask#run");
        }
    }

    @Resource(lookup = "java:comp/DefaultManagedExecutorService")
    ExecutorService executorService;

    private static void print(final String s) {
        System.out.println(s);
    }

    public void contextDestroyed(final ServletContextEvent sce) {
    }

    public void contextInitialized(final ServletContextEvent sce) {
        executorService.submit(new MyTask());
    }

}
