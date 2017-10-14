/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author UPF
 */
public class CreateTrainingData {
    
    /* CREATE YOUR OWN TRAINING  ARFF FILE  */
    public static void main(String[] args) {
        
        /* directory to process */
        File inDir;
        File[] flist;
        BufferedReader reader;
        String floc;
        String line;
        String text;
        /* airplane accidents */
        inDir=new File("./resources/DATA_AIW/text_files/airplane_accident");
        flist=inDir.listFiles();
        
        //Creating a Printwriter object and connecting it to the file
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(new File("./output.arff")));
            
            //Writing the head of the file
            out.println("@relation category");
            out.println();
            out.println("@attribute Text string");
            out.println("@attribute category {airplane,quake,train}");
            out.println();
            out.println("@data");
            out.println();
            
            for(int f=0;f<flist.length;f++) {
                floc=flist[f].getAbsolutePath();
                try {
                    reader=new BufferedReader(new FileReader(floc));
                    text="";
                    while((line=reader.readLine())!=null) {
                        line=line.replaceAll("'", " ");
                        text=text+line+" ";
                    }
                    //Printing each line on the file
                    out.println("'"+text+"',"+"airplane");
                    //System.out.println("'"+text+"',"+"airplane");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            //Closing the connection to the file
            out.flush();
            out.close();
            
        } catch (IOException ex) {
            Logger.getLogger(CreateTrainingData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
