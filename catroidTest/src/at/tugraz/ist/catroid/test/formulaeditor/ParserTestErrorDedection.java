/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010-2011 The Catroid Team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid_license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *   
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.test.formulaeditor;

import java.util.LinkedList;
import java.util.List;

import android.test.AndroidTestCase;
import at.tugraz.ist.catroid.formulaeditor.FormulaElement;
import at.tugraz.ist.catroid.formulaeditor.InternFormulaParser;
import at.tugraz.ist.catroid.formulaeditor.InternToken;
import at.tugraz.ist.catroid.formulaeditor.InternTokenType;

public class ParserTestErrorDedection extends AndroidTestCase {

	private static final double DELTA = 0.01;

	public void testTooManyOperators() {
		List<InternToken> internTokenList = new LinkedList<InternToken>();

		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "-"));
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "-"));
		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.42"));
		InternFormulaParser internParser = new InternFormulaParser(internTokenList);
		FormulaElement parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed: - - 42.42", parseTree);
		int errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 1, errorTokenIndex);

		internTokenList = new LinkedList<InternToken>();
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "+"));
		internParser = new InternFormulaParser(internTokenList);
		parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed: +", parseTree);
		errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 0, errorTokenIndex);

		internTokenList = new LinkedList<InternToken>();
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "-"));
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "+"));
		internParser = new InternFormulaParser(internTokenList);
		parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed: + -", parseTree);
		errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 1, errorTokenIndex);

		internTokenList = new LinkedList<InternToken>();
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "*"));
		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.53"));
		internParser = new InternFormulaParser(internTokenList);
		parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed:  * 42.53", parseTree);
		errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 0, errorTokenIndex);

		internTokenList = new LinkedList<InternToken>();
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "-"));
		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.42"));
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "-"));
		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.42"));
		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "-"));
		internParser = new InternFormulaParser(internTokenList);
		parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed: - 42.42 - 42.42 -", parseTree);
		errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 5, errorTokenIndex);

	}

	public void testOperatorMissing() {
		List<InternToken> internTokenList = new LinkedList<InternToken>();

		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.53"));
		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.42"));
		InternFormulaParser internParser = new InternFormulaParser(internTokenList);
		FormulaElement parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed:  42.53 42.42", parseTree);
		int errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 1, errorTokenIndex);
	}

	public void testNumberMissing() {
		List<InternToken> internTokenList = new LinkedList<InternToken>();

		internTokenList.add(new InternToken(InternTokenType.OPERATOR, "*"));
		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.53"));
		InternFormulaParser internParser = new InternFormulaParser(internTokenList);
		FormulaElement parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed:  * 42.53", parseTree);
		int errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 0, errorTokenIndex);
	}

	public void testRightBracketMissing() {
		List<InternToken> internTokenList = new LinkedList<InternToken>();

		internTokenList.add(new InternToken(InternTokenType.BRACKET_OPEN, "("));
		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.53"));

		InternFormulaParser internParser = new InternFormulaParser(internTokenList);
		FormulaElement parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed:   (42.53", parseTree);
		int errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 2, errorTokenIndex);
	}

	public void testLefttBracketMissing() {
		List<InternToken> internTokenList = new LinkedList<InternToken>();

		internTokenList.add(new InternToken(InternTokenType.NUMBER, "42.53"));
		internTokenList.add(new InternToken(InternTokenType.BRACKET_CLOSE, ")"));

		InternFormulaParser internParser = new InternFormulaParser(internTokenList);
		FormulaElement parseTree = internParser.parseFormula();
		assertNull("Invalid formula parsed:   42.53)", parseTree);
		int errorTokenIndex = internParser.getErrorTokenIndex();
		assertEquals("Error Token Index is not as expected", 1, errorTokenIndex);
	}

}
