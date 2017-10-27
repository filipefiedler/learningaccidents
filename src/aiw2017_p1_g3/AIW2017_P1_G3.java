/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiw2017_p1_g3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import IE.CallMyGATEApp;
import classification.TextClassifier;
import utils.CreateTrainingData;

/**
 *
 * @author Filipe Fiedler & Nick Schneider
 */
public class AIW2017_P1_G3 {

	/**
	 * @param args
	 *            the command line arguments
	 */
	TextClassifier txtCl;
	CallMyGATEApp gateDoc;
	CreateTrainingData trainDat;

	private AIW2017_P1_G3() {
		init();

	}

	private void init() {
		txtCl = new TextClassifier();
		gateDoc = new CallMyGATEApp();
		trainDat = new CreateTrainingData();
		trainDat.init();
	}

	private void classifying() {
		try {

			/* YOU NEED TO USE an ARFF CREATED BY YOU */
			// String trainFile="./resources/few_instances.arff"; // this file
			// is provided by your teacher,
			// you have to replace it with your own file with the FULL training
			// data
			String trainFile = "./resources/full_traning_data.arff";
			String testTXTFile = "./resources/testing_classifier.txt";

			System.out.println("Creating classifier...");

			txtCl.initClassifier();
			System.out.println("Training classifier...");
			txtCl.loadTrainingInstances(trainFile);
			txtCl.createTestInstances();

			String txt;
			String topic;

			/**
			 * Iterative testing the classifier
			 * 
			 * 
			 */
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter your  text: ");

			// read text
			txt = in.readLine();
			/*
			 * String temp = "";
			 * 
			 * while ((temp = in.readLine())!=null){ txt = temp; }
			 */

			while (!txt.toUpperCase().equals("QUIT")) {
				if (txt.equalsIgnoreCase("")) { // if the text is empty ask
												// again to enter the text

				} else {
					topic = txtCl.classify(txt);
					if (topic.equalsIgnoreCase("quake") || topic.equalsIgnoreCase("airplane")) {
						// if the topic is not one of the given ones ask to
						// enter the text again
						System.out.println("It is about " + topic);

						txtCl.removeInstance();
						// call MyGateApp
						System.out.println("Calling the extraction system");
						gateDoc.init(txt, topic);

					}else{
						System.out.println("The text does not contain a topic of the database");
					}
				}
				System.out.print("Enter your  text: ");
				txt = in.readLine();
			}

			/**
			 * Batch testing of the classifier
			 */
			/*
			 * BufferedReader reader=new BufferedReader(new
			 * FileReader(testTXTFile)); while((txt=reader.readLine())!=null) {
			 * System.out.println("TEXT >>> "+txt);
			 * topic=classifier.classify(txt);
			 * System.out.println("It is about "+topic);
			 * classifier.removeInstance();
			 * 
			 * }
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Text Classification and Extraction App. This App recognizes texts belonging to\n"
				+ "the following two domain: airplane and earthquake accidents"
		// first call the textclassifier
		//
		);
		AIW2017_P1_G3 myApp = new AIW2017_P1_G3();
		myApp.classifying();

	}

}
