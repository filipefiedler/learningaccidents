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
import java.io.IOException;


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
        for(int f=0;f<flist.length;f++) {
            floc=flist[f].getAbsolutePath();
            try {
                reader=new BufferedReader(new FileReader(floc));
                text="";
                while((line=reader.readLine())!=null) {
                    line=line.replaceAll("'", " ");
                    text=text+line+" ";
                }
                System.out.println("'"+text+"',"+"airplane");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
       
        

    
    }
    
    
}
