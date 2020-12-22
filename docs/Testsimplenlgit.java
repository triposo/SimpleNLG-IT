import simplenlg_it.framework.*;
import simplenlg_it.lexicon.*;
import simplenlg_it.lexicon.italian.*;
import simplenlg_it.realiser.*;
import simplenlg_it.phrasespec.*;
import simplenlg_it.features.*;


public class Testsimplenlgit {

    public static void main(String[] args) {
	Lexicon lexIta = new ITXMLLexicon();
	NLGFactory nlgFactory = new NLGFactory(lexIta);
	Realiser realiser = new Realiser();

	//realiser.setDebugMode(true);
	String output = null;
		
	SPhraseSpec clause = nlgFactory.createClause("loro", "essere", "bello");
	clause.setFeature(Feature.TENSE, Tense.PAST);
	//clause.setFeature(Feature.PERFECT, true);
	output = realiser.realiseSentence(clause);
	System.out.println(output);
	
    }

}
