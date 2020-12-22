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

public class testVP_Passive {
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
		
		SPhraseSpec passive = factory.createClause("Marco", "leggere");
		NPPhraseSpec object = factory.createNounPhrase("il", "libro");
		passive.setObject(object);
		//riflessivo.setIndirectObject("io");
		passive.setFeature(Feature.TENSE, Tense.PRESENT);
		passive.setFeature(Feature.PASSIVE, true);
		output = realiser.realiseSentence(passive);
		System.out.println(output);
	}
}
