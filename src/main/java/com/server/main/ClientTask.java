package com.server.main;

import com.server.behavior.Behavior;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTask implements Runnable {

    private final Socket socket;
    private Behavior behavior;

    private String pull(Socket socket) {
        try {
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return inFromClient.readLine();
        } catch (IOException e) {
            push(socket, e.getMessage());
        }
        return null;
    }

    private void push(Socket socket, String token) {
        try {
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            outToClient.writeBytes(token);
        } catch (IOException e) {
            push(socket, e.getMessage());            
        }
    }

    public ClientTask(Socket socket, Behavior behavior) {
        this.socket = socket;
        this.behavior = behavior;
    }

    @Override
    public void run() {
        String token = pull(socket);
        while (token != null) {
            push(socket, behavior.apply(token));
            token = pull(socket);
        }
    }

}
