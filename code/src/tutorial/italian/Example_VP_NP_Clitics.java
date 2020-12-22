package tutorial.italian;

import java.util.List;

import junit.framework.Assert;
import simplenlg_it.features.DiscourseFunction;
import simplenlg_it.features.Feature;
import simplenlg_it.features.Form;
import simplenlg_it.features.InternalFeature;
import simplenlg_it.features.Tense;
import simplenlg_it.features.italian.ItalianFeature;
import simplenlg_it.features.italian.ItalianLexicalFeature;
import simplenlg_it.framework.InflectedWordElement;
import simplenlg_it.framework.NLGFactory;
import simplenlg_it.framework.WordElement;
import simplenlg_it.lexicon.Lexicon;
import simplenlg_it.lexicon.italian.ITXMLLexicon;
import simplenlg_it.phrasespec.AdjPhraseSpec;
import simplenlg_it.phrasespec.NPPhraseSpec;
import simplenlg_it.phrasespec.PPPhraseSpec;
import simplenlg_it.phrasespec.SPhraseSpec;
import simplenlg_it.phrasespec.VPPhraseSpec;
import simplenlg_it.realiser.Realiser;

public class Example_VP_NP_Clitics {
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
		
		SPhraseSpec clausePassato = factory.createClause("tu", "vendere");
		NPPhraseSpec leCase = factory.createNounPhrase("la", "casa");
		leCase.setPlural(true);
		clausePassato.setFeature(Feature.TENSE, Tense.PAST);
		clausePassato.setObject(leCase);
		clausePassato.setIndirectObject("loro");
		//output = realiser.realiseSentence(clausePassato);
		//System.out.println(output);
		
		leCase.setFeature(Feature.PRONOMINAL, true);
		output = realiser.realiseSentence(clausePassato);
		System.out.println(output);
		
		clausePassato.setIndirectObject("loro");
		output = realiser.realiseSentence(clausePassato);
		System.out.println(output);
		
		clausePassato.setIndirectObject("Maria");
		output = realiser.realiseSentence(clausePassato);
		System.out.println(output);
		
		clausePassato.setFeature(Feature.FORM, Form.IMPERATIVE);
		clausePassato.setFeature(Feature.TENSE, Tense.PRESENT);
		leCase.setFeature(Feature.PRONOMINAL, false);
		output = realiser.realiseSentence(clausePassato);
		System.out.println(output);
		
		leCase.setFeature(Feature.PRONOMINAL, true);
		output = realiser.realiseSentence(clausePassato);
		System.out.println(output);
		
		clausePassato.setFeature(Feature.NEGATED, true);
		output = realiser.realiseSentence(clausePassato);
		System.out.println(output);
		
		SPhraseSpec weakForm = factory.createClause("lui", "legge");
		weakForm.setObject("io");
		weakForm.setFeature(Feature.TENSE, Tense.PRESENT);
		output = realiser.realiseSentence(weakForm);
		System.out.println(output);
		
		weakForm = factory.createClause("tu", "leggere");
		weakForm.setFeature(Feature.FORM, Form.IMPERATIVE);
		weakForm.setObject("io");
		output = realiser.realiseSentence(weakForm);
		System.out.println(output);
		
		weakForm = factory.createClause("tu", "leggere");
		weakForm.setFeature(Feature.FORM, Form.IMPERATIVE);
		weakForm.setIndirectObject("io");
		output = realiser.realiseSentence(weakForm);
		System.out.println(output);
		
		SPhraseSpec riflessivo = factory.createClause("lui", "arrabbiare");
		riflessivo.setFeature(Feature.TENSE, Tense.PRESENT);
		output = realiser.realiseSentence(riflessivo);
		System.out.println(output);
		
		 riflessivo = factory.createClause("io", "arrabbiare");
		riflessivo.setFeature(Feature.TENSE, Tense.PRESENT);
		output = realiser.realiseSentence(riflessivo);
		System.out.println(output);
		
		
		
		SPhraseSpec proposition = factory.createClause("io", "volere");
		NPPhraseSpec pane = factory.createNounPhrase("del", "pane");
		proposition.setObject(pane);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		pane.setFeature(Feature.PRONOMINAL, true);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setObject("ne");
		proposition.setFeature(Feature.NEGATED,true);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition = factory.createClause("io", "lavare");
		proposition.setFeature(ItalianLexicalFeature.REFLEXIVE, true);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition = factory.createClause("Gianni", "lavare");
		proposition.setFeature(ItalianLexicalFeature.REFLEXIVE, true);
		String leMani = "le mani";
		proposition.setObject(leMani);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition = factory.createClause("noi", "lavare");
		proposition.setObject("ci");
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition = factory.createClause("si", "chiamare");
		proposition.setComplement("Federico");
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition = factory.createClause("io", "essere");
		proposition.setObject("ci");
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		VPPhraseSpec leggere = factory.createVerbPhrase("leggere");
		leggere.setObject("noi");
		leggere.setIndirectObject("lei");
		output = realiser.realiseSentence(leggere);
		System.out.println(output);
		
	}
}
