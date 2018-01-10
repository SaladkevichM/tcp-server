package com.server.main;

import com.server.behavior.Behavior;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final ExecutorService POOL = Executors.newFixedThreadPool(8);
    private static final int PORT_NUMBER = 23;
    private static boolean loop = true;
    private static Logger log = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {

        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Behavior behavior = (Behavior) context.getBean("weather");

        try (ServerSocket point = new ServerSocket(PORT_NUMBER)) {
            while (loop) {
                POOL.submit(new ClientTask(point.accept(), behavior));
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }

        context.close();

    }
}
