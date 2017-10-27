package IE;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loading in JAVA a GATE gapp and executing it over a document
 * 
 * @author UPF
 */
public class CallMyGATEApp {

	public CorpusController application;

	public CallMyGATEApp() {
		super();
	}

	public void loadMyGapp(String pathToGapp) throws IOException, ResourceInstantiationException, PersistenceException {

		// load the GAPP
		this.application = (CorpusController) PersistenceManager.loadObjectFromFile(new File(pathToGapp));
	}

	public void setCorpus(Corpus c) {
		this.application.setCorpus(c);

	}

	public void executeMyGapp() throws ExecutionException {

		this.application.execute();
	}

	public static void main(String[] args) {
		String pathToMyGapp = "./gapps/NLPAnalyzer.gapp";
		String pathToIE = "./gapps/LEARNING.gapp";
		try {
			// initialize GATE
			Gate.init();
			// one instance of my class
			CallMyGATEApp myanalyser = new CallMyGATEApp();
			CallMyGATEApp myLearning = new CallMyGATEApp();

			// load the application
			myanalyser.loadMyGapp(pathToMyGapp);
			myLearning.loadMyGapp(pathToIE);
			// create a GATE corpus
			Corpus corpus = Factory.newCorpus("");
			// create a GATE document from a string
			Document document = Factory.newDocument(
					"December 20 American Airlines Flight 965 , a Boeing 757 , crashes into a mountain while approaching Santiago de Cali, Colombia ; of the 164 people on board, only 4 people and a dog survive.");
			// put document in corpus
			corpus.add(document);
			// pass corpus to app
			myanalyser.setCorpus(corpus);
			myLearning.setCorpus(corpus);

			// show annotations before call
			System.out.println(">>>> annotations before call <<<<<");
			System.out.println(document.getAnnotations());
			// execute app
			myanalyser.executeMyGapp();
			myLearning.executeMyGapp();

			// get all annotations in default annotation Set
			AnnotationSet defaultAnnotations = document.getAnnotations();

			// select annotations of type NE
			AnnotationSet NEs = defaultAnnotations.get("Mention");

			// variable to hold each annotation to be processed
			Annotation ne;

			// start and end of annotations in the text
			Long start, end;

			// features of annotation
			FeatureMap fm;

			// document content
			String dc = document.getContent().toString();

			// iterate on each annotation

			Iterator<Annotation> ite = NEs.iterator();

			while (ite.hasNext()) {

				// next NE
				ne = ite.next();

				// get features
				fm = ne.getFeatures();

				// get start end offset
				start = ne.getStartNode().getOffset();
				end = ne.getEndNode().getOffset();

				// get feature type of NE
				System.out.println(fm.get("type") + "=" + dc.substring(start.intValue(), end.intValue()));
			}

			/*
			 * // show annotations after call
			 * System.out.println(">>>> annotations after call <<<<<");
			 * System.out.println(document.getAnnotations()); // do stuff with
			 * your document...
			 */

		} catch (GateException ge) {
			ge.printStackTrace();
		} catch (IOException ex) {
			Logger.getLogger(CallMyGATEApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}