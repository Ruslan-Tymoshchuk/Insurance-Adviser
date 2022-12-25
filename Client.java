package com.adviser.insurance;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static final String SOME_STRING = "";
    public static final String BYE_TEXT = "bye";
    
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);  
        System.out.println("Provide with the hostname");
        String hostname = scanner.nextLine();
        System.out.println("Provide with the port number");
        int port = Integer.parseInt(scanner.nextLine()); 
        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            Console console = System.console();
            String text = SOME_STRING;
            while(!text.equals(BYE_TEXT)) {
             text = console.readLine("Provide with the user name: ");
                writer.println(text); 
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String result = reader.readLine();
                System.out.println(result);   
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}