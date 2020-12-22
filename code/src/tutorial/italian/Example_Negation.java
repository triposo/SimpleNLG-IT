package tutorial.italian;

import java.util.*;

//GENERAL 
import simplenlg_it.features.*;
import simplenlg_it.framework.*;
import simplenlg_it.phrasespec.*;
import simplenlg_it.lexicon.Lexicon;

//ITALIAN
//importo feature italiane
import simplenlg_it.features.italian.*;
//importo lessico italiano
import simplenlg_it.lexicon.italian.*;
//importo il realizer francese che richiama i metodi 
//realiseSyntax e realiseMorphology degli elementi linguistici
import simplenlg_it.realiser.Realiser;

public class Example_Negation {

	public static void main(String[] args) {
		
		/*########LESSICO##########*/
		
		//prova lessico italiano, 
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		String output = null;
		
		NPPhraseSpec one_subj = factory.createNounPhrase("il", "contadino");
		NPPhraseSpec one_obj =  factory.createNounPhrase("il", "mela");
		SPhraseSpec one = factory.createClause(one_subj, "mangiare", one_obj);
		
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		SPhraseSpec one_negated = factory.createClause(one_subj, "mangiare", one_obj);
		one_negated.setFeature(Feature.NEGATED, true);
		output = realiser.realiseSentence(one_negated);
		System.out.println(output);
		
		WordElement più = lexIta.lookupWord("più", LexicalCategory.ADVERB);
		one_negated.setFeature(ItalianFeature.NEGATION_AUXILIARY, più);
		output = realiser.realiseSentence(one_negated);
		System.out.println(output);
	}

}
