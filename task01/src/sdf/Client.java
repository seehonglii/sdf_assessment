package sdf;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client{
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // opening a socket to connect to the server on port 5000
        Socket socket = new Socket("localhost", 5000);

        try (OutputStream os = socket.getOutputStream()) {
            DataOutputStream out = new DataOutputStream(os);
            ObjectOutputStream oos = new ObjectOutputStream(out);

            try (InputStream is = socket.getInputStream()) {
                DataInputStream in = new DataInputStream(is);
                
                // Receive the list of numbers from the server
                int size = in.readInt();
                List<Double> numbers = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    numbers.add(in.readDouble());
                }

                // Calculate the mean and standard deviation
                double sum = 0;
                for (double number : numbers) {
                    sum += number;
                }
                double mean = sum / size;
                double variance = 0;
                for (double number : numbers) {
                    variance += (number - mean) * (number - mean);
                }
                double stdDev = Math.sqrt(variance / size);

                // Send the name, email, mean, and standard deviation back to the server
                oos.writeUTF("See Hong Lii"); 
                oos.writeUTF("lii.see@gmail.com"); 
                oos.writeFloat((float) mean);
                oos.writeFloat((float) stdDev);
                oos.flush();

            } catch (IOException e) {
                e.printStackTrace(); 
            }                     
        
        } 
        socket.close();
    } 
}