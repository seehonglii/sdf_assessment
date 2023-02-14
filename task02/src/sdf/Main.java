package sdf;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) throws IOException {
        String fileName = "";
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a file name");
        fileName = scanner.nextLine();       

        // check if file exists
        File file = new File("./" + fileName + ".txt");

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File not found.");
        } else {
            //create a file hanlder
            FileHandler fh = new FileHandler(file);  
            fh.TextProcessor();            
        }        
    scanner.close();
    }

}



