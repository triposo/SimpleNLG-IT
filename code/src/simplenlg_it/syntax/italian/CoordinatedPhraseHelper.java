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
package simplenlg_it.syntax.italian;

import java.util.List;

import simplenlg_it.framework.NLGElement;

/**
 * This class contains static methods to help the syntax processor realise
 * coordinated phrases for French.
 * 
 * @author vaudrypl
 */
public class CoordinatedPhraseHelper
	extends simplenlg_it.syntax.AbstractCoordinatedPhraseHelper {

	/**
	 * Checks to see if the specifier can be raised and then raises it.
	 * This doesn't apply in most cases in French, so this method does
	 * nothing.
	 * 
	 * @param children
	 *            the <code>List</code> of coordinates in the
	 *            <code>CoordinatedPhraseElement</code>
	 */
	@Override
	protected void raiseSpecifier(List<NLGElement> children) {}

}
