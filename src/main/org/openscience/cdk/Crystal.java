/* Copyright (C) 2002-2007,2010  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk;

import java.io.Serializable;
import java.util.Map;

import javax.vecmath.Vector3d;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.ICrystal;
import org.openscience.cdk.interfaces.ILonePair;
import org.openscience.cdk.interfaces.ISingleElectron;
import org.openscience.cdk.nonotify.NNCrystal;

/**
 * Class representing a molecular crystal.
 * The crystal is described with molecules in fractional
 * coordinates and three cell axes: a,b and c.
 *
 * <p>The crystal is designed to store only the asymmetric atoms.
 * Though this is not enforced, it is assumed by all methods.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword crystal
 */
public class Crystal extends NNCrystal implements IChemObjectListener, IChemObjectChangeNotifier, Serializable, ICrystal, Cloneable
{

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide/serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 5919649450390509278L;

    /** {@inheritDoc} */
    public Crystal() {
    	super();
    }

    /** {@inheritDoc} */
    public Crystal(IAtomContainer container) {
        super(container);
    }

    /** {@inheritDoc} */
    public void setA(Vector3d newAxis) {
        super.setA(newAxis);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setB(Vector3d newAxis) {
        super.setB(newAxis);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setC(Vector3d newAxis) {
        super.setC(newAxis);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setSpaceGroup(String group) {
        super.setSpaceGroup(group);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setZ(Integer value) {
        super.setZ(value);
        notifyChanged();
    }

    /**
     *  Makes a clone of this crystal.
     *
     * @return The cloned crystal.
     */
    public Object clone() throws CloneNotSupportedException {
        Crystal clone = (Crystal)super.clone();
        return clone;
    }

    /**
     *  Sets the array of atoms of this AtomContainer.
     *
     *@param  atoms  The array of atoms to be assigned to this AtomContainer
     *@see           #getAtom
     */
    public void setAtoms(IAtom[] atoms)
    {
        super.setAtoms(atoms);
        for (IAtom atom : atoms) {
            if (atom instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)atom).addListener(this);
        }
        notifyChanged();
    }

    /**
     * Sets the array of bonds of this AtomContainer.
     *
     * @param  bonds  The array of bonds to be assigned to
     *                             this AtomContainer
     * @see  #getBond
     */
    public void setBonds(IBond[] bonds)
    {
        super.setBonds(bonds);
        for (IBond bond : bonds) {
            if (bond instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)bond).addListener(this);
        }
    }

    /**
     *  Sets the atom at position <code>number</code> in [0,..].
     *
     *@param  number  The position of the atom to be set.
     *@param  atom    The atom to be stored at position <code>number</code>
     *@see            #getAtom(int)
     */
    public void setAtom(int number, IAtom atom)
    {
        super.setAtom(number, atom);
        notifyChanged();
    }

    /**
     *  Adds all atoms and electronContainers of a given atomcontainer to this
     *  container.
     *
     *@param  atomContainer  The atomcontainer to be added
     */
    public void add(IAtomContainer atomContainer)
    {
        super.add(atomContainer);
        notifyChanged();
    }

    /**
     *  Adds an atom to this container.
     *
     *@param  atom  The atom to be added to this container
     */
    public void addAtom(IAtom atom)
    {
        super.addAtom(atom);
        if (atom instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)atom).addListener(this);
        notifyChanged();
    }


    /**
     *  Adds a Bond to this AtomContainer.
     *
     *@param  bond  The bond to added to this container
     */
    public void addBond(IBond bond)
    {
        super.addBond(bond);
        notifyChanged();
    }

    /**
     *  Adds a lone pair to this AtomContainer.
     *
     *@param  lonePair  The LonePair to added to this container
     */
    public void addLonePair(ILonePair lonePair)
    {
        super.addLonePair(lonePair);
        notifyChanged();
    }
    
    /**
     *  Adds a single electron to this AtomContainer.
     *
     *@param  singleElectron  The SingleElectron to added to this container
     */
    public void addSingleElectron(ISingleElectron singleElectron)
    {
        super.addSingleElectron(singleElectron);
        notifyChanged();
    }
    
    /**
     *  Removes the atom at the given position from the AtomContainer. Note that
     *  the electronContainers are unaffected: you also have to take care of
     *  removing all electronContainers to this atom from the container manually.
     *
     *@param  position  The position of the atom to be removed.
     */
    public void removeAtom(int position)
    {
        super.removeAtom(position);
        notifyChanged();
    }
    
    /**
     *  Removes the bond at the given position from the AtomContainer.
     *
     *@param  position  The position of the bond to be removed.
     */
    public IBond removeBond(int position)
    {
        IBond bond = super.removeBond(position);
        notifyChanged();
        return bond;
    }
    
    /**
     *  Removes the lone pair at the given position from the AtomContainer.
     *
     *@param  position  The position of the LonePair to be removed.
     */
    public ILonePair removeLonePair(int position)
    {
        ILonePair lp = super.removeLonePair(position);
        notifyChanged();
        return lp;
    }
    
    /**
     *  Removes the single electron at the given position from the AtomContainer.
     *
     *@param  position  The position of the SingleElectron to be removed.
     */
    public ISingleElectron removeSingleElectron(int position)
    {
        ISingleElectron se = super.removeSingleElectron(position);
        notifyChanged();
        return se;
    }
    
    /**
     *  Removes the given atom and all connected electronContainers from the
     *  AtomContainer.
     *
     *@param  atom  The atom to be removed
     */
    public void removeAtomAndConnectedElectronContainers(IAtom atom)
    {
        super.removeAtomAndConnectedElectronContainers(atom);
        notifyChanged();
    }

    /**
     * Removes all atoms and bond from this container.
     */
    public void removeAllElements() {
        super.removeAllElements();
        notifyChanged();
    }


    /**
     *  Removes electronContainers from this container.
     */
    public void removeAllElectronContainers()
    {
        super.removeAllElectronContainers();
        notifyChanged();
    }

    /**
     *  Removes all Bonds from this container.
     */
    public void removeAllBonds() {
        super.removeAllBonds();
        notifyChanged();
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
