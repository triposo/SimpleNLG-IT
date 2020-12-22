/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is "Simplenlg".
 *
 * The Initial Developer of the Original Code is Ehud Reiter, Albert Gatt and Dave Westwater.
 * Portions created by Ehud Reiter, Albert Gatt and Dave Westwater are Copyright (C) 2010-11 The University of Aberdeen. All Rights Reserved.
 *
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Wewstwater, Roman Kutlak, Margaret Mitchell, Pierre-Luc Vaudry.
 */

package simplenlg_it.syntax;

import java.util.List;

import simplenlg_it.features.DiscourseFunction;
import simplenlg_it.features.Feature;
import simplenlg_it.features.InternalFeature;
import simplenlg_it.features.LexicalFeature;
import simplenlg_it.framework.InflectedWordElement;
import simplenlg_it.framework.LexicalCategory;
import simplenlg_it.framework.ListElement;
import simplenlg_it.framework.NLGElement;
import simplenlg_it.framework.PhraseElement;
import simplenlg_it.framework.WordElement;
import simplenlg_it.phrasespec.NPPhraseSpec;

/**
 * Abstract class for the NounPhrase syntax helper.
 * Based on English NounPhrase syntax helper.
 * 
 * @author vaudrypl
 */
public abstract class AbstractNounPhraseHelper {
	/**
	 * The main method for realising noun phrases.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> to be realised.
	 * @return the realised <code>NLGElement</code>.
	 */
	public ListElement realise(PhraseElement phrase) {
		ListElement realisedElement = null;

		if (phrase != null
				&& !phrase.getFeatureAsBoolean(Feature.ELIDED)) {
			// vaudrypl added phrase argument to ListElement constructor
			// to copy all features from the PhraseElement
			realisedElement = new ListElement(phrase);

			// Creates the appropriate pronoun if the noun phrase
			// is pronominal.
			 if (phrase.getFeatureAsBoolean(Feature.PRONOMINAL)) {
				realisedElement.addComponent(createPronoun(phrase));

			} else {
				realiseSpecifier(phrase, realisedElement);
				realisePreModifiers(phrase, realisedElement);
				realiseHeadNoun(phrase, realisedElement);
				phrase.getPhraseHelper().realiseList( realisedElement,
						phrase.getFeatureAsElementList(InternalFeature.COMPLEMENTS ),
						DiscourseFunction.COMPLEMENT);
				realisePostModifiers(phrase, realisedElement);
				//phrase.getPhraseHelper().realiseList( realisedElement,
						//phrase.getPostModifiers(), DiscourseFunction.POST_MODIFIER );
			}
		}
		return realisedElement;
	}

	/**
	 * Realises the head noun of the noun phrase.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	protected void realiseHeadNoun(PhraseElement phrase,
			ListElement realisedElement) {
		NLGElement headElement = phrase.getHead();

		if (headElement != null) {
			// vaudrypl changed : features are given to currentElement
			// after realiseSyntax() instead of headElement before realiseSyntax()
			NLGElement currentElement = headElement.realiseSyntax();
			if (currentElement != null) {
				currentElement.setFeature(LexicalFeature.GENDER, phrase
						.getFeature(LexicalFeature.GENDER));
				currentElement.setFeature(InternalFeature.ACRONYM, phrase
						.getFeature(InternalFeature.ACRONYM));
				currentElement.setFeature(Feature.NUMBER, phrase
						.getFeature(Feature.NUMBER));
				currentElement.setFeature(Feature.PERSON, phrase
						.getFeature(Feature.PERSON));
				currentElement.setFeature(Feature.POSSESSIVE, phrase
						.getFeature(Feature.POSSESSIVE));
				currentElement.setFeature(Feature.PASSIVE, phrase
						.getFeature(Feature.PASSIVE));
				currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
						DiscourseFunction.SUBJECT);
				realisedElement.addComponent(currentElement);
			}
		}
	}

	/**
	 * Realises the pre-modifiers of the noun phrase. Before being realised,
	 * pre-modifiers undergo some basic sorting based on adjective ordering.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	protected void realisePreModifiers(PhraseElement phrase,
			ListElement realisedElement) {

		List<NLGElement> preModifiers = phrase.getPreModifiers();
		if (phrase.getFeatureAsBoolean(Feature.ADJECTIVE_ORDERING)
				.booleanValue()) {
			preModifiers = sortNPPreModifiers(preModifiers);
		}
		phrase.getPhraseHelper().realiseList(realisedElement, preModifiers,
				DiscourseFunction.PRE_MODIFIER);
	}

	/**Added by Cristina B.
	 * Realises the post-modifiers of the noun phrase. Before being realised,
	 * post-modifiers undergo some basic sorting based on adjective ordering.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	protected void realisePostModifiers(PhraseElement phrase,
			ListElement realisedElement) {

		List<NLGElement> postModifiers = phrase.getPostModifiers();
		if (phrase.getFeatureAsBoolean(Feature.ADJECTIVE_ORDERING)
				.booleanValue()) {
			postModifiers = sortNPPostModifiers(postModifiers);
		}
		phrase.getPhraseHelper().realiseList(realisedElement, postModifiers,
				DiscourseFunction.POST_MODIFIER);
		//phrase.getPhraseHelper().realiseList( realisedElement,
				//phrase.getPostModifiers(), DiscourseFunction.POST_MODIFIER );
	}
	/**
	 * Realises the specifier of the noun phrase.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	protected void realiseSpecifier(PhraseElement phrase,
			ListElement realisedElement) {
		
		//Cristina B.
		//System.out.println("REALIZING-SPECIFIERS " + phrase.printTree(" "));
		NLGElement specifierElement = 
			phrase.getFeatureAsElement(InternalFeature.SPECIFIER);

		if (specifierElement != null
				&& !phrase.getFeatureAsBoolean(InternalFeature.RAISED)) {
			
			//System.out.println("Calling realizing syntax on specifiers");
			//System.out.println(specifierElement.printTree(" "));
			//this could be a WordElement, InflectedWordElement,etc.
			NLGElement currentElement = specifierElement.realiseSyntax();
			//System.out.println("AfterRealixing syntax");
			//System.out.println(currentElement.printTree(" "));
			
			// number feature given to currentElement instead of specifierElement
			// by vaudrypl
			if (!specifierElement.isA(LexicalCategory.PRONOUN)) {
				currentElement.setFeature(Feature.NUMBER,
						phrase.getFeature(Feature.NUMBER));
				//System.out.println("prova Prase NP feature " + phrase.getFeatureAsString(LexicalFeature.GENDER));
				
				//Cristina B.
				//setting the gender for the agreement when there is just an NP without a SP parent
				currentElement.setFeature(LexicalFeature.GENDER,
						phrase.getFeature(LexicalFeature.GENDER));
				//System.out.println("set feature!"); 
				//System.out.println(currentElement.printTree(" "));
				//System.out.println("parent?");
				//no perché il parent non ha più info gender dopo la sintassi
				//currentElement.setParent(phrase);
				//System.out.println(currentElement.getParent().printTree(""));
				
			}
			
			if (currentElement != null) {
				currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
						DiscourseFunction.SPECIFIER);
				realisedElement.addComponent(currentElement);
			}
		}
	}

	/**
	 * Sorts the list of premodifiers for this noun phrase.
	 * The default implementation returns the argument unchanged.
	 * It should be kept this way if premodifiers sorting doesn't apply
	 * to a particular language, like French, but overridden if it does,
	 * like in English.
	 * 
	 * @param originalModifiers
	 *            the original listing of the premodifiers.
	 * @return the sorted <code>List</code> of premodifiers.
	 * 
	 * @author vaudrypl
	 */
	protected List<NLGElement> sortNPPreModifiers(List<NLGElement> originalModifiers) {
		return originalModifiers;
	}

	/**
	 * Retrieves the correct representation of the word from the element. This
	 * method will find the <code>WordElement</code>, if it exists, for the
	 * given phrase or inflected word.
	 * 
	 * @param element
	 *            the <code>NLGElement</code> from which the head is required.
	 * @return the <code>WordElement</code>
	 */
	protected WordElement getHeadWordElement(NLGElement element) {
		WordElement head = null;

		if (element instanceof WordElement)
			head = (WordElement) element;
		else if (element instanceof InflectedWordElement) {
			head = (WordElement) element.getFeature(InternalFeature.BASE_WORD);
		} else if (element instanceof PhraseElement) {
			head = getHeadWordElement(((PhraseElement) element).getHead());
		}
		return head;
	}

	/**
	 * Creates the appropriate pronoun for the noun phrase.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @return the <code>NLGElement</code> representing the pronominal.
	 * 
	 * @author vaudrypl
	 */
	abstract protected NLGElement createPronoun(PhraseElement phrase);

	/**
	 * Add a modifier to a noun phrase. Use heuristics to decide where it goes.
	 * 
	 * @param nounPhrase
	 * @param modifier
	 * 
	 */
	abstract public void addModifier(NPPhraseSpec nounPhrase, Object modifier);

	protected List<NLGElement> sortNPPostModifiers(List<NLGElement> originalModifiers) {
		return originalModifiers;
	}
}