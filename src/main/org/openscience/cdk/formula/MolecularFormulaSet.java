/*  Copyright (C) 2007  Miguel Rojasch <miguelrojasch@users.sf.net>
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.formula;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;
import org.openscience.cdk.nonotify.NNMolecularFormulaSet;

/**
 *  Class defining an set object of MolecularFormulas. It maintains
 *   a list of list IMolecularFormula.<p>
 *   
 * @cdk.module  data
 * @author      miguelrojasch
 * @cdk.created 2007-11-20
 * @cdk.keyword molecular formula
 */
@TestClass("org.openscience.cdk.formula.MolecularFormulaSetTest")
public class MolecularFormulaSet extends NNMolecularFormulaSet implements Iterable<IMolecularFormula>, IMolecularFormulaSet, Cloneable {

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is imcompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -2043178712150212550L;

	/**
	 *  Constructs an empty MolecularFormulaSet.
	 *  
	 *  @see #MolecularFormulaSet(IMolecularFormula)
	 */
	public MolecularFormulaSet() {
		super();
	}

	/** {@inheritDoc} */
	public MolecularFormulaSet(IMolecularFormula formula) {
	    super(formula);
	}
	
	public IChemObjectBuilder getBuilder() {
	    return DefaultChemObjectBuilder.getInstance();
    }

}
