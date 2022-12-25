package com.adviser.insurance;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import com.adviser.insurance.dao.InsuranceOfferDao;
import com.adviser.insurance.util.ConnectionManager;

public class Main {

    public static void main(String[] args) {      
        ConnectionManager connectionManager = new ConnectionManager();
        InsuranceOfferDao insuranceOfferDao = new InsuranceOfferDao(connectionManager);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide with the server port: ");
        int serverPort = scanner.nextInt();
        scanner.close();
        System.out.println("Server is listening on port " + serverPort);
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            while (true) {
                new Server(serverSocket.accept(), insuranceOfferDao).start();
                System.out.println("New client connected");
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        }
    }
}