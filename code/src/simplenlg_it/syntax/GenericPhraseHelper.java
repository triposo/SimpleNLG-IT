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
import simplenlg_it.framework.PhraseCategory;
import simplenlg_it.framework.PhraseElement;
import simplenlg_it.framework.Language;
import simplenlg_it.lexicon.Lexicon;
import simplenlg_it.phrasespec.NPPhraseSpec;

/**
 * Generic class for the Phrase syntax helper.
 * Based on English PhraseHelper.
 * 
 * @author vaudrypl
 */
public abstract class GenericPhraseHelper {
	
	/**
	 * The main method for realising phrases.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> to be realised.
	 * @return the realised <code>NLGElement</code>.
	 */
	public NLGElement realise(PhraseElement phrase) {
		ListElement realisedElement = null;

		if (phrase != null) {
			// vaudrypl added phrase argument to ListElement constructor
			// to copy all features from the PhraseElement
			realisedElement = new ListElement(phrase);

			realiseList(realisedElement, phrase.getPreModifiers(),
					DiscourseFunction.PRE_MODIFIER);

			realiseHead(phrase, realisedElement);
			realiseComplements(phrase, realisedElement);

			realiseList(realisedElement, phrase
					.getPostModifiers(), DiscourseFunction.POST_MODIFIER);
		}
		return realisedElement;
	}

	/**
	 * Realises the complements of the phrase adding <em>and</em> where
	 * appropriate.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	protected void realiseComplements(PhraseElement phrase,
			ListElement realisedElement) {

		boolean firstProcessed = false;
		NLGElement currentElement = null;

		for (NLGElement complement : phrase
				.getFeatureAsElementList(InternalFeature.COMPLEMENTS)) {
			currentElement = complement.realiseSyntax();
			if (currentElement != null) {
				currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
						DiscourseFunction.COMPLEMENT);
				//for correcting inflecting pronouns for morpholohy that doesn't inflect COMPLEMENT
				if(phrase.getFeature(InternalFeature.DISCOURSE_FUNCTION) == DiscourseFunction.INDIRECT_OBJECT && 
						currentElement.getCategory() == PhraseCategory.NOUN_PHRASE && 
						phrase.getLanguage() == Language.ITALIAN){
						
					currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
							DiscourseFunction.INDIRECT_OBJECT);
					
				}
				
				if (firstProcessed) {
					// changed by vaudrypl to make more generic
					Lexicon lexicon = phrase.getFactory().getLexicon();
					realisedElement.addComponent(new InflectedWordElement(
//							"and", LexicalCategory.CONJUNCTION)); //$NON-NLS-1$
							lexicon.getAdditionCoordConjunction()));
				} else {
					firstProcessed = true;
				}
				realisedElement.addComponent(currentElement);
			}
		}
	}

	/**
	 * Realises the head element of the phrase.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	protected void realiseHead(PhraseElement phrase,
			ListElement realisedElement) {

		NLGElement head = phrase.getHead();
		if (head != null) {
			//System.out.println("Test Generic Phrase helper comp:" + phrase.getFeatureAsBoolean(Feature.IS_COMPARATIVE));
			//System.out.println("Test Generic Phrase helper sup:" + phrase.getFeatureAsBoolean(Feature.IS_SUPERLATIVE));
			//if (phrase.hasFeature(Feature.IS_COMPARATIVE)) {
				head.setFeature(Feature.IS_COMPARATIVE, phrase
						.getFeature(Feature.IS_COMPARATIVE));
			//} else if (phrase.hasFeature(Feature.IS_SUPERLATIVE)) {
				head.setFeature(Feature.IS_SUPERLATIVE, phrase
						.getFeature(Feature.IS_SUPERLATIVE));
			//}
			
			head = head.realiseSyntax();
			head.setFeature(InternalFeature.DISCOURSE_FUNCTION,
					DiscourseFunction.HEAD);
			realisedElement.addComponent(head);
		}
	}

	/**
	 * Iterates through a <code>List</code> of <code>NLGElement</code>s
	 * realisation each element and adding it to the on-going realisation of
	 * this clause.
	 * 
	 * @param realisedElement
	 *            the current realisation of the clause.
	 * @param elementList
	 *            the <code>List</code> of <code>NLGElement</code>s to be
	 *            realised.
	 * @param function
	 *            the <code>DiscourseFunction</code> each element in the list is
	 *            to take. If this is <code>null</code> then the function is not
	 *            set and any existing discourse function is kept.
	 */
	public void realiseList(ListElement realisedElement, List<NLGElement> elementList,
			DiscourseFunction function) {

		// AG: Change here: the original list structure is kept, i.e. rather
		// than taking the elements of the list and putting them in the realised
		// element, we now add the realised elements to a new list and put that
		// in the realised element list. This preserves constituency for
		// orthography and morphology processing later.
		
		// vaudrypl added realisedElement as argument to the constructor
		// and setComponents(null)
		ListElement realisedList = new ListElement(realisedElement);
		realisedList.setComponents(null);
		
		NLGElement currentElement = null;
		for (NLGElement eachElement : elementList) {
			currentElement = eachElement.realiseSyntax();

			if (currentElement != null) {
				currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
						function);
				
				// realisedElement.addComponent(currentElement);
				realisedList.addComponent(currentElement);
			}
		}

		if (!realisedList.getChildren().isEmpty()) {
			realisedElement.addComponent(realisedList);
		}
	}

	/**
	 * Determines if the given phrase has an expletive as a subject.
	 * based on english PhraseHelper
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> to be examined.
	 * @return <code>true</code> if the phrase has an expletive subject.
	 */
	public boolean isExpletiveSubject(PhraseElement phrase) {
		List<NLGElement> subjects = phrase
				.getFeatureAsElementList(InternalFeature.SUBJECTS);
		boolean expletive = false;

		if (subjects.size() == 1) {
			NLGElement subjectNP = subjects.get(0);

			if (subjectNP.isA(PhraseCategory.NOUN_PHRASE)) {
				expletive = subjectNP.getFeatureAsBoolean(
						LexicalFeature.EXPLETIVE_SUBJECT).booleanValue();
			}
		}
		return expletive;
	}
}
