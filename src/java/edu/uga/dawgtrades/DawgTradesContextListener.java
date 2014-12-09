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
                try{
                    Thread.sleep(10000);
                }
                catch(InterruptedException e) {return;}
            }
        }
    }
    private Thread myThread = null;
    private WorkerThreadClass worker = null;

    public void contextInitialized(ServletContextEvent sce) {
        if ((myThread == null) || (!myThread.isAlive())) {
            worker = new WorkerThreadClass();
            myThread = new Thread(worker);
            myThread.start();
        }
    }

    public void contextDestroyed(ServletContextEvent sce){
        try {
            worker.running = false;
            myThread.interrupt();
        } catch (Exception ex) {
        }
    }
}