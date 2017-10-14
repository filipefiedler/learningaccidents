/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classification;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author UPF
 */
public class TextClassifier {
    
    
   StringToWordVector filter;
   Classifier naiveClassifier; 
   Instances trainInstances;
   Instances testInstances;
   ArrayList attributes;  
   ArrayList classValues;
   
   
   public void TextClassifier() {
       
   }
   
   public void initClassifier() {
        filter = new StringToWordVector();
        
        naiveClassifier = new NaiveBayes();        
   }
   
   public String classify(String txt) throws Exception {
        Instance instance;
        instance=new DenseInstance(2);
      
        
        instance.setValue((Attribute)testInstances.attribute(0),txt);
        
        instance.setValue((Attribute)testInstances.attribute(1),trainInstances.
                attribute(trainInstances.classIndex()).value(0));
        testInstances.add(instance);
        
        Instances m_Test2 = Filter.useFilter(testInstances, filter);
        System.out.println(testInstances.instance(0).stringValue(0));
        double index = naiveClassifier.classifyInstance(m_Test2.instance(0));
        
      
        
        String className=m_Test2.classAttribute().value((int)index);
        
        
       
        System.out.println(className);
        return className;
   }
   
   public void removeInstance() {
       testInstances.delete(0);
   }
   
   public void createTestInstances() {
        ArrayList fvClassVal = new ArrayList();
        String value;
        Enumeration enu = trainInstances.attribute(trainInstances.classIndex()).enumerateValues();
        while(enu.hasMoreElements()) {
            value=(String)enu.nextElement();
            fvClassVal.add(value);
        }        
        Attribute classAttribute = new Attribute("topic", fvClassVal);
        ArrayList fvWekaAttributes=new ArrayList();
        Attribute textAttribute = new Attribute("text",(Vector) null);
        fvWekaAttributes.add(textAttribute);       
        fvWekaAttributes.add(classAttribute);
        testInstances = new Instances("Rel", fvWekaAttributes, 1); 
   }
   
   public void loadTrainingInstances(String training_file) {
       
       try {
           trainInstances = new Instances(new BufferedReader(new FileReader(training_file)));
          
           int lastIndex = trainInstances.numAttributes() - 1;
           trainInstances.setClassIndex(lastIndex); 
           filter.setInputFormat(trainInstances);
           trainInstances = 
                   Filter.useFilter(trainInstances, filter);
           System.out.println(trainInstances.numAttributes());
           naiveClassifier.buildClassifier(trainInstances);
           ArrayList fvWekaAttributes = new ArrayList();
           
           
       } catch (FileNotFoundException ex) {
           ex.printStackTrace();
       } catch (IOException ex) {
           ex.printStackTrace();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }
   
   
   public static void main(String[] args) {
       try {
           
           /* YOU NEED TO USE an ARFF CREATED BY YOU */
           String trainFile="./resources/few_instances.arff"; // this file is provided by your teacher, 
           // you have to replace it with your own file with the FULL training data
           String testTXTFile="./resources/testing_classifier.txt";
           
           System.out.println("Creating classifier...");
           TextClassifier classifier=new TextClassifier();
          
           classifier.initClassifier();
           System.out.println("Training classifier...");
           classifier.loadTrainingInstances(trainFile);
           classifier.createTestInstances();
        
          
           String txt;
           String topic;
           
           /**
            *  Iterative testing the classifier
            */
          
            
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your  text: ");

            // read text
            txt = in.readLine();

           
           
          
            while(!txt.toUpperCase().equals("QUIT")) {
           
               topic=classifier.classify(txt);
               System.out.println("It is about "+topic);
               classifier.removeInstance();
               System.out.print("Enter your  text: ");
               txt = in.readLine();
           }
           
          
           
           
           /**
            *  Batch testing of the classifier 
            */
           /*
           BufferedReader reader=new BufferedReader(new FileReader(testTXTFile));
           while((txt=reader.readLine())!=null) {
               System.out.println("TEXT >>> "+txt);
               topic=classifier.classify(txt);
               System.out.println("It is about "+topic);
               classifier.removeInstance();
               
           }
           */
           
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }
   
 
    
}
