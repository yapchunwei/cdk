/* Copyright (C) 2006-2007,2010  Egon Willighagen <egonw@users.sf.net>
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
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

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;

/** 
 * @cdk.module nonotify
 * @cdk.githash
 */
public class NNMoleculeSet extends NNAtomContainerSet implements IMoleculeSet {

	private static final long serialVersionUID = 5220158598240759515L;

	public NNMoleculeSet() {
		super();
	}
	
	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    /**
     * Adds an atomContainer to this container.
     *
     * @param  atomContainer  The IMolecule to be added to this container
     * @throws IllegalArgumentException when the passed IAtomContainer is not
     *         an IMolecule.
     */
    public void addAtomContainer(IAtomContainer atomContainer) {
        if (!(atomContainer instanceof IMolecule))
            throw new IllegalArgumentException(
                "Only IMolecule's can be stored in an IMoleculeSet"
            );
        super.addAtomContainer(atomContainer);
    }

    /**
     * Adds an atomContainer to this container with the given
     * multiplier.
     *
     * @param  atomContainer  The atomContainer to be added to this container
     * @param  multiplier     The multiplier of this atomContainer
     * @throws IllegalArgumentException when the passed IAtomContainer is not
     *         an IMolecule.
     */
    public void addAtomContainer(IAtomContainer atomContainer, double multiplier) {
        if (!(atomContainer instanceof IMolecule))
            throw new IllegalArgumentException(
                "Only IMolecule's can be stored in an IMoleculeSet"
            );
        super.addAtomContainer(atomContainer, multiplier);
    }

    /**
     *  Adds an molecule to this container.
     *
     * @param  molecule  The molecule to be added to this container 
     */
    public void addMolecule(IMolecule molecule) {
        super.addAtomContainer(molecule);
    }
    
    /**
     *  Adds all molecules in the MoleculeSet to this container.
     *
     * @param  moleculeSet  The MoleculeSet 
     */
    public void add(IMoleculeSet moleculeSet) {
        for (IAtomContainer mol : moleculeSet.molecules()) {
            addAtomContainer(mol);
        }
    }

    public void setMolecules(IMolecule[] molecules)
    {
        if (atomContainerCount > 0) removeAllAtomContainers();
        for (IMolecule molecule : molecules) {
            addMolecule(molecule);
        }
    }
    
    /**
     *  Returns the array of Molecules of this container.
     *
     * @return    The array of Molecules of this container 
     * @see #setMolecules
     */
    public Iterable<IAtomContainer> molecules() {
        return super.atomContainers();
    }
    
    
    /**
     *  
     * Returns the Molecule at position <code>number</code> in the
     * container.
     *
     * @param  number  The position of the Molecule to be returned. 
     * @return         The Molecule at position <code>number</code> . 
     */
    public IMolecule getMolecule(int number)
    {
        return (IMolecule)super.getAtomContainer(number);
    }
    
    /**
     * Returns the number of Molecules in this Container.
     *
     * @return     The number of Molecules in this Container
     */
    public int getMoleculeCount() {
        return super.getAtomContainerCount();
    }

    /**
     *  Clones this MoleculeSet and its content.
     *
     *@return    the cloned object
     */
    public Object clone() throws CloneNotSupportedException {
        return (NNMoleculeSet)super.clone();
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("MoleculeSet(");
        buffer.append(super.toString());
        buffer.append(')');
        return buffer.toString();
    }
}
