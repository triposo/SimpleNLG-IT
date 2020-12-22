package tutorial.italian;

import java.util.List;

import simplenlg_it.features.Feature;
import simplenlg_it.features.Form;
import simplenlg_it.features.Tense;
import simplenlg_it.features.italian.ItalianFeature;
import simplenlg_it.framework.InflectedWordElement;
import simplenlg_it.framework.NLGFactory;
import simplenlg_it.framework.WordElement;
import simplenlg_it.lexicon.Lexicon;
import simplenlg_it.lexicon.italian.ITXMLLexicon;
import simplenlg_it.phrasespec.AdjPhraseSpec;
import simplenlg_it.phrasespec.NPPhraseSpec;
import simplenlg_it.phrasespec.PPPhraseSpec;
import simplenlg_it.phrasespec.SPhraseSpec;
import simplenlg_it.realiser.Realiser;

public class testVP_Modali {
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		
		//prova lessico italiano, 
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		String output = "";
		//realiser.setDebugMode(true);
		
		SPhraseSpec clauseModale = factory.createClause("io", "vendere");
		clauseModale.setFeature(Feature.MODAL, "potere");
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.NEGATED, true);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.TENSE, Tense.PRESENT);
		clauseModale.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.TENSE, Tense.CONDITIONAL);
		clauseModale.setFeature(Feature.PERFECT, false);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
	}
}
