package com.otukatigayim;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedExecutors;
import javax.enterprise.concurrent.ManagedTaskListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    private static final class MyTask implements Runnable {
        @Override
        public void run() {
            print("[TASK:RUN] MyTask#run");
        }
    }

    private static final class MyTaskListener implements ManagedTaskListener {

        @Override
        public void taskAborted(final Future<?> arg0, final ManagedExecutorService arg1, final Object arg2, final Throwable arg3) {
            print("MyTaskListener#taskAborted");
            if (arg3 != null) {
                arg3.printStackTrace();
            }
        }

        @Override
        public void taskDone(final Future<?> arg0, final ManagedExecutorService arg1, final Object arg2, final Throwable arg3) {
            print("MyTaskListener#taskDone");
        }

        @Override
        public void taskStarting(final Future<?> arg0, final ManagedExecutorService arg1, final Object arg2) {
            print("MyTaskListener#taskStarting");
        }

        @Override
        public void taskSubmitted(final Future<?> arg0, final ManagedExecutorService arg1, final Object arg2) {
            print("MyTaskListener#taskSubmitted");
        }

    }

    @Resource(lookup = "java:comp/DefaultManagedExecutorService")
    ExecutorService executorService;

    private static void print(final String s) {
        System.out.println(s);
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
    }

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        final Runnable task = ManagedExecutors.managedTask(new MyTask(), new MyTaskListener());
        executorService.submit(task);
    }

}
