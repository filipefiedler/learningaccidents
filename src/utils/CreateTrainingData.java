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
   
	/* directory to process */
	private File inDir;
    private File[] flist;
    private BufferedReader reader;
    private String [] accidents;
    private String [] identifiers;
    private String floc;
    private String line;
    private String text;
    private PrintWriter out;
	
	public CreateTrainingData (){
		init();
		createArff();
		//Closing the connection to the file
		out.close();
		out.flush();
	}
	
	
	private void init (){
		try {
            out = new PrintWriter(new FileWriter(new File("./resources/full_traning_data.arff")));
            
            //Writing the head of the file
            out.println("@relation category");
            out.println();
            out.println("@attribute Text string");
            out.println("@attribute category {airplane,quake,train}");
            out.println();
            out.println("@data");
            out.println();
		}catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		//initialize String arrays with accident names and there ids that are put after the sentence.
		accidents = new String [3];
		identifiers = new String [3];
		
		accidents[0]= "airplane_accident";
		accidents[1]= "earthquake";
		accidents[2]= "train_accident";
		identifiers[0]="airplane";
		identifiers[1]="quake";
		identifiers[2]="train";
		
		
	}
    /* CREATE YOUR OWN TRAINING  ARFF FILE  */
    public static void main(String[] args) {
        
    	CreateTrainingData ctd = new CreateTrainingData();
 
    }
    private void createArff (){
    	
    	
    	for (int i =0;i<3;i++){
    	inDir=new File("./resources/DATA_AIW/text_files/"+accidents[i]);
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
                //Printing each line on the file
                out.println("'"+text+"',"+identifiers[i]);
                //System.out.println("'"+text+"',"+"quake");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    }
    
}
