package com.cnu2016.assignment02;
import java.io.*;
import java.util.Scanner;
public class Main {
    private static void readData(String filename) throws IOException {
        Scanner in = new Scanner(new FileReader(filename));
        while(in.hasNext()) {
            int ID, startTime, duration;
            try {
                ID = in.nextInt();
                startTime = in.nextInt();
                duration = in.nextInt();    
                return ID + startTime + duration;
            }
            catch (Exception e) {
                
            }
            finally {
                in.close();
            }
        }
    }
    public static void main(String args[]) throws IOException {
        readData("input.txt");
    }

    
//
//writer.println("The second line");
//writer.close();
}
