/* $Revision$ $Author$ $Date$
 *
 * Copyright (C) 2005-2007  Egon Willighagen <e.willighagen@science.ru.nl>
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
package org.openscience.cdk;

import java.io.Serializable;
import java.util.Map;

import org.openscience.cdk.interfaces.IAminoAcid;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.ILonePair;
import org.openscience.cdk.interfaces.ISingleElectron;
import org.openscience.cdk.nonotify.NNAminoAcid;

/**
 * A AminoAcid is Monomer which stores additional amino acid specific 
 * informations, like the N-terminus atom.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @author      Egon Willighagen <e.willighagen@science.ru.nl>
 * @cdk.created 2005-08-11
 * @cdk.keyword amino acid
 */
public class AminoAcid extends NNAminoAcid implements IAminoAcid,
    IChemObjectChangeNotifier, IChemObjectListener, Serializable, Cloneable {

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -5032283549467862509L;
	
    public AminoAcid() {
        super();
    }
    
    /**
     * Clones this AminoAcid object.
     *
     * @return    The cloned object
     */
    public Object clone() throws CloneNotSupportedException {
        AminoAcid clone = (AminoAcid) super.clone();
        // copying the new N-terminus and C-terminus pointers
        if (getNTerminus() != null)
        	clone.addNTerminus(clone.getAtom(getAtomNumber(getNTerminus())));
        if (getCTerminus() != null)
        	clone.addCTerminus(clone.getAtom(getAtomNumber(getCTerminus())));
        // remove the listeners
        clone.notifier = null;
        return clone;
    }
    
    /** {@inheritDoc} */
    public IChemObjectBuilder getBuilder() {
        return DefaultChemObjectBuilder.getInstance();
    }

    /** {@inheritDoc} */
    public void stateChanged(IChemObjectChangeEvent event) {
        notifyChanged(event);
    }

    private ChemObjectNotifier notifier = null;

    /** {@inheritDoc} */
    public void addListener(IChemObjectListener col) {
        if (notifier == null) notifier = new ChemObjectNotifier(this);
        notifier.addListener(col);
    }

    /** {@inheritDoc} */
    public int getListenerCount() {
        if (notifier == null) return 0;
        return notifier.getListenerCount();
    }

    /** {@inheritDoc} */
    public void removeListener(IChemObjectListener col) {
        if (notifier == null) return;
        notifier.removeListener(col);
    }

    /** {@inheritDoc} */
    public void notifyChanged() {
        if (notifier == null) return;
        notifier.notifyChanged();
    }

    /** {@inheritDoc} */
    public void notifyChanged(IChemObjectChangeEvent evt) {
        if (notifier == null) return;
        notifier.notifyChanged(evt);
    }

    /** {@inheritDoc} */
    public void setMonomerName(String cMonomerName) {
        super.setMonomerName(cMonomerName);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void setMonomerType(String cMonomerType) {
        super.setMonomerType(cMonomerType);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setAtoms(IAtom[] atoms) {
        super.setAtoms(atoms);
        for (IAtom atom : atoms) {
            if (atom instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)atom).addListener(this);
        }
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setBonds(IBond[] bonds) {
        super.setBonds(bonds);
        for (IBond bond : bonds) {
            if (bond instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)bond).addListener(this);
        }
    }

    /** {@inheritDoc} */
    public void setAtom(int number, IAtom atom) {
        super.setAtom(number, atom);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void add(IAtomContainer atomContainer) {
        super.add(atomContainer);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addAtom(IAtom atom) {
        super.addAtom(atom);
        if (atom instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)atom).addListener(this);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addBond(IBond bond) {
        super.addBond(bond);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addLonePair(ILonePair lonePair) {
        super.addLonePair(lonePair);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void addSingleElectron(ISingleElectron singleElectron) {
        super.addSingleElectron(singleElectron);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void removeAtom(int position) {
        super.removeAtom(position);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public IBond removeBond(int position) {
        IBond bond = super.removeBond(position);
        notifyChanged();
        return bond;
    }
    
    /** {@inheritDoc} */
    public ILonePair removeLonePair(int position) {
        ILonePair lp = super.removeLonePair(position);
        notifyChanged();
        return lp;
    }
    
    /** {@inheritDoc} */
    public ISingleElectron removeSingleElectron(int position) {
        ISingleElectron se = super.removeSingleElectron(position);
        notifyChanged();
        return se;
    }
    
    /** {@inheritDoc} */
    public void removeAtomAndConnectedElectronContainers(IAtom atom) {
        super.removeAtomAndConnectedElectronContainers(atom);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllElements() {
        super.removeAllElements();
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllElectronContainers() {
        super.removeAllElectronContainers();
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllBonds() {
        super.removeAllBonds();
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setProperty(Object description, Object property) {
        super.setProperty(description, property);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setID(String identifier) {
        super.setID(identifier);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setFlag(int flag_type, boolean flag_value) {
        super.setFlag(flag_type, flag_value);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setProperties(Map<Object,Object> properties) {
        super.setProperties(properties);
        notifyChanged();
    }
  
    /** {@inheritDoc} */
    public void setFlags(boolean[] flagsNew){
        super.setFlags(flagsNew);
        notifyChanged();
    }
}
