package com.adviser.insurance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import com.adviser.insurance.dao.DaoException;
import com.adviser.insurance.dao.InsuranceOfferDao;
import com.adviser.insurance.domain.InsuranceOffer;
import com.adviser.insurance.domain.Vehicle;

public class Server extends Thread {

    public static final String SOME_STRING = "";
    public static final String BYE_TEXT = "bye";
    public static final String LOW_LINE = "_";
    public static final String TEXT_DELIMETER = " ";
    
    private final Socket socket;
    private final InsuranceOfferDao insuranceOfferDao;
    
    public Server(Socket socket, InsuranceOfferDao insuranceOfferDao) {
        this.socket = socket;
        this.insuranceOfferDao = insuranceOfferDao;
    }
    
    @Override
    public void run() {
        try (socket){
              InputStream input = socket.getInputStream();
              BufferedReader reader = new BufferedReader(new InputStreamReader(input));
              OutputStream output = socket.getOutputStream();
              PrintWriter writer = new PrintWriter(output, true);  
              String text = SOME_STRING;
              while(!text.equals(BYE_TEXT)) {
               text = reader.readLine();
                  List<InsuranceOffer> offers = insuranceOfferDao.findOffersByUserName(text);
                  StringBuilder result = new StringBuilder();
                  offers.forEach(offer -> {
                      Vehicle vehicle = offer.getVehicle();
                      result.append(vehicle.getBrand());
                      result.append(LOW_LINE);
                      result.append(vehicle.getModel());
                      result.append(LOW_LINE);
                      result.append(offer.getInsurer());
                      result.append(LOW_LINE);
                      result.append(offer.getPrice());
                      result.append(TEXT_DELIMETER);
                  });
                  writer.println("Server: " + result);  
              }  
      } catch (IOException | DaoException ex) {
          System.out.println("Server exception: " + ex.getMessage());
      }
      }
    }