/* Copyright (C)      2007  Miguel Rojasch <miguelrojasch@users.sf.net>
 *               2008,2010  Egon Willighagen <egonw@users.sf.net>
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.nonotify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;

/**
 *  Class defining an set object of MolecularFormulas. It maintains
 *   a list of list IMolecularFormula.<p>
 *
 * @cdk.keyword molecular formula
 * @cdk.module  nonotify
 * @cdk.githash
 */
public class NNMolecularFormulaSet implements Iterable<IMolecularFormula>, IMolecularFormulaSet {
	
	public IChemObjectBuilder getBuilder() {
	    return NoNotificationChemObjectBuilder.getInstance();
    }

    /**  Internal List of IMolecularFormula. */
    private List<IMolecularFormula> components;
    
    /**
     * Constructs an empty MolecularFormulaSet.
     *  
     * @see #MolecularFormulaSet(IMolecularFormula)
     */
    public NNMolecularFormulaSet() {
        components = new ArrayList<IMolecularFormula>();
    }
    
    /**
     * Constructs a MolecularFormulaSet with a copy MolecularFormulaSet of another 
     * MolecularFormulaSet (A shallow copy, i.e., with the same objects as in
     * the original MolecularFormulaSet).
     *
     *  @param  formula  An MolecularFormula to copy from
     *  @see             #MolecularFormulaSet()
     */
    public NNMolecularFormulaSet(IMolecularFormula formula) {
        components = new ArrayList<IMolecularFormula>();
        components.add(0, formula);
    }
    /**
     *  Adds all molecularFormulas in the MolecularFormulaSet to this chemObject.
     *
     * @param  formulaSet  The MolecularFormulaSet 
     */
    @TestMethod("testAdd_IMolecularFormulaSet")
    public void add(IMolecularFormulaSet formulaSet) {
        for (IMolecularFormula mf : formulaSet.molecularFormulas()){
            addMolecularFormula(mf);
        }
        /*
         *  notifyChanged() is called by addAtomContainer()
         */
    }
    /**
     * Adds an molecularFormula to this chemObject.
     *
     * @param  formula  The molecularFormula to be added to this chemObject
     */
    @TestMethod("testAdd_IMolecularFormula")
    public void addMolecularFormula(IMolecularFormula formula) {
        components.add(formula);
    }
    
    /**
     *  Returns an Iterable for looping over all IMolecularFormula
     *   in this MolecularFormulaSet.
     *
     * @return    An Iterable with the IMolecularFormula in this MolecularFormulaSet
     */
    @TestMethod("testMolecularFormulas")
    public Iterable<IMolecularFormula> molecularFormulas() {
        return components;
    }
    
    /**
     *  Returns an Iterator for looping over all IMolecularFormula
     *   in this MolecularFormulaSet.
     *
     * @return    An Iterator with the IMolecularFormula in this MolecularFormulaSet
     */
    @TestMethod("testIterator")
    public Iterator<IMolecularFormula> iterator() {
        return components.iterator();
    }
    
    /**
     * Returns the number of MolecularFormulas in this MolecularFormulaSet.
     *
     * @return     The number of MolecularFormulas in this MolecularFormulaSet
     */
    @TestMethod("testSize")
    public int size() {
        return components.size();
    }

    /**
     *  True, if the MolecularFormulaSet contains the given IMolecularFormula object.
     *
     * @param  formula  The IMolecularFormula this MolecularFormulaSet is searched for
     * @return          True, if the MolecularFormulaSet contains the given IMolecularFormula object
     */
    @TestMethod("testContains_IMolecularFormula")
    public boolean contains(IMolecularFormula formula) {
        return components.contains(formula);
    }
    
    /**
     *  
     * Returns the MolecularFormula at position <code>number</code> in the
     * chemObject.
     *
     * @param  position The position of the IMolecularFormula to be returned. 
     * @return          The IMolecularFormula at position <code>number</code> . 
     */
    @TestMethod("testGetMolecularFormula_int")
    public  IMolecularFormula getMolecularFormula(int position) {
        return components.get(position);
    }

    
    /**
     * Removes all IMolecularFormula from this chemObject.
     */
    @TestMethod("testRemoveAllMolecularFormulas")
    public void removeAllMolecularFormulas() {
        components.clear();
    }

    /**
     * Removes an IMolecularFormula from this chemObject.
     *
     * @param  formula  The IMolecularFormula to be removed from this chemObject
     */
    @TestMethod("testRemoveMolecularFormula_IMolecularFormula")
    public void removeMolecularFormula(IMolecularFormula formula) {
        components.remove(formula);
    }

    /**
     * Removes an MolecularFormula from this chemObject.
     *
     * @param  position  The position of the MolecularFormula to be removed from this chemObject
     */
    @TestMethod("testRemoveMolecularFormula_int")
    public void removeMolecularFormula(int position) {
        components.remove(position);
    }
    
    /**
     * Clones this MolecularFormulaSet object and its content.
     *
     * @return    The cloned object
     */
    @TestMethod("testClone")
    public Object clone() throws CloneNotSupportedException {
        NNMolecularFormulaSet clone = (NNMolecularFormulaSet)super.clone();
        for (IMolecularFormula mf : this.molecularFormulas()){
            clone.addMolecularFormula((IMolecularFormula) mf.clone());
        }
        return clone;
    }

}
