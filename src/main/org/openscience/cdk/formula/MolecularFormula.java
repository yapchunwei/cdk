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
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.nonotify.NNMolecularFormula;

/**
 * Class defining a molecular formula object. It maintains
 * a list of list {@link IIsotope}.
 *   
 * <p>Examples:
 * <ul>
 *   <li><code>[C<sub>5</sub>H<sub>5</sub>]-</code></li>
 *   <li><code>C<sub>6</sub>H<sub>6</sub></code></li>
 *   <li><code><sup>12</sup>C<sub>5</sub><sup>13</sup>CH<sub>6</sub></code></li>
 * </ul>
 * 
 * @cdk.module  data
 * @author      miguelrojasch
 * @cdk.created 2007-11-20
 * @cdk.keyword molecular formula
 */
@TestClass("org.openscience.cdk.formula.MolecularFormulaTest")
public class MolecularFormula extends NNMolecularFormula implements IMolecularFormula {

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is imcompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -2011407700837295287L;

	/**
	 *  Constructs an empty MolecularFormula.
	 */
	public MolecularFormula() {
		super();
	}
	
    public IChemObjectBuilder getBuilder() {
        return DefaultChemObjectBuilder.getInstance();
    }

}
