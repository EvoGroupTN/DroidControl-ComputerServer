package evo.server;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class Server {
 
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    
    private static String message;
 
    public static void main(String[] args) {
 
        try {
            serverSocket = new ServerSocket(4444);  //Server socket
            
        } catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
        }
 
        System.out.println("Server started. Listening to the port 4444");
        new Thread(new Runnable() {
            public void run() {
            	while (true) {
                    try {
         
                           //accept the client connection
                    	clientSocket = serverSocket.accept();
                    	
                       while(true){
                        	//wait(100);
                        	
                    	   inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                        bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                        message = bufferedReader.readLine();
                        System.out.println(message);
                        //Runtime        //String[] coords = message.split(":");
                        
                        	String[] coord;
                        	/*do{
                        		inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                                bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                                message = bufferedReader.readLine();
                                System.out.println(message);
                                coord = message.split(":");
                                try {
                                    Robot r = new Robot();
                                    float x = Float.parseFloat(coord[1]);
                                    float y = Float.parseFloat(coord[2]);
                                    r.mouseMove((int) x,(int) y);
                                } catch(AWTException e) {}
                        	}while((coord[0].compareToIgnoreCase("mouse")==0));*/
                        	
                        
                        Runtime r = Runtime.getRuntime();
                        Process p = r.exec(message);
                        p.waitFor();
                        BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line = "";
                        PrintWriter printwriter;
                        printwriter = new PrintWriter(clientSocket.getOutputStream(),true);
                        while ((line = b.readLine()) != null) {
                          System.out.println(line);
                          
                 	     
                 	     printwriter.write(line);  //write the message to output stream
                 	     printwriter.flush();
                 	     
                        }
                        
                        printwriter.close();
                       }
                        //inputStreamReader.close();
                        
                        //clientSocket.close();
         
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }).start();
        
 
    }
}
