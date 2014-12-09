package edu.uga.dawgtrades;

import javax.servlet.*;
import java.util.*;
import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.*;
import edu.uga.dawgtrades.persistence.impl.*;
import edu.uga.dawgtrades.persistence.*;

public class DawgTradesContextListener implements ServletContextListener {
    private class WorkerThreadClass implements Runnable {
        public boolean running = true;
        public void run() {
            while(running) {
                Thread.sleep(10000);
            }
        }
    }
    private WorkerThreadClass myThread = null;

    public void contextInitialized(ServletContextEvent sce) {
        if ((myThread == null) || (!myThread.isAlive())) {
            myThread = new MyThreadClass();
            myThread.start();
        }
    }

    public void contextDestroyed(ServletContextEvent sce){
        try {
            myThread.doShutdown();
            myThread.running = false;
            myThread.interrupt();
        } catch (Exception ex) {
        }
    }
}