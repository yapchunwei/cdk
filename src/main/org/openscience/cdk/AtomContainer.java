/*  Copyright (C) 1997-2007  Christoph Steinbeck
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
package org.openscience.cdk;

import java.io.Serializable;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.ILonePair;
import org.openscience.cdk.interfaces.ISingleElectron;
import org.openscience.cdk.nonotify.NNAtomContainer;

/**
 *  Base class for all chemical objects that maintain a list of Atoms and
 *  ElectronContainers. <p>
 *
 *  Looping over all Bonds in the AtomContainer is typically done like: <pre>
 * Iterator iter = atomContainer.bonds();
 * while (iter.hasNext()) {
 *   IBond aBond = (IBond) iter.next();
 * }
 *
 *  </pre>
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @author steinbeck
 * @cdk.created 2000-10-02
 */
public class AtomContainer extends NNAtomContainer 
  implements IAtomContainer, IChemObjectListener, IChemObjectChangeNotifier,
             Serializable, Cloneable {

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 5678100348445919254L;

	/**
	 *  Constructs an empty AtomContainer.
	 */
	public AtomContainer() {
        this(10, 10, 0, 0);
	}


	/**
	 * Constructs an AtomContainer with a copy of the atoms and electronContainers
	 * of another AtomContainer (A shallow copy, i.e., with the same objects as in
	 * the original AtomContainer).
	 *
	 * @param  container  An AtomContainer to copy the atoms and electronContainers from
	 */
	public AtomContainer(IAtomContainer container) {
	    super(container);
		for (IAtom atom : atoms())
			if (atom instanceof IChemObjectChangeNotifier)
			    ((IChemObjectChangeNotifier)atom).addListener(this);
		for (IBond bond : bonds()) {
			if (bond instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)bond).addListener(this);
		}
		for (ILonePair lp : lonePairs())
			if (lp instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)lp).addListener(this);
		for (ISingleElectron se : singleElectrons())
			if (se instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)se).addListener(this);
	}

	/**
	 *  Constructs an empty AtomContainer that will contain a certain number of
	 *  atoms and electronContainers. It will set the starting array lengths to the
	 *  defined values, but will not create any Atom or ElectronContainer's.
	 *
	 *@param  atomCount        Number of atoms to be in this container
	 *@param  bondCount        Number of bonds to be in this container
	 *@param  lpCount          Number of lone pairs to be in this container
	 *@param  seCount          Number of single electrons to be in this container
	 *
	 */
	public AtomContainer(int atomCount, int bondCount, int lpCount, int seCount)
	{
		super(atomCount, bondCount, lpCount, seCount);
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

	/**
	 * Clones this AtomContainer object and its content.
	 *
	 * @return    The cloned object
	 * @see       #shallowCopy
	 */
	public Object clone() throws CloneNotSupportedException {
		IAtomContainer clone = (IAtomContainer) super.clone();
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

    private ChemObjectNotifier notifier = new ChemObjectNotifier();

    /** {@inheritDoc} */
    public void addListener(IChemObjectListener col) {
        notifier.addListener(col);
    }

    /** {@inheritDoc} */
    public int getListenerCount() {
        return notifier.getListenerCount();
    }

    /** {@inheritDoc} */
    public void removeListener(IChemObjectListener col) {
        notifier.removeListener(col);
    }

    /** {@inheritDoc} */
    public void notifyChanged() {
        notifier.notifyChanged();
    }

    /** {@inheritDoc} */
    public void notifyChanged(IChemObjectChangeEvent evt) {
        notifier.notifyChanged(evt);
    }
}


