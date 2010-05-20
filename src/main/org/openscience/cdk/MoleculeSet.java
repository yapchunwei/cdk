/* Copyright (C) 1997-2007  Christoph Steinbeck <steinbeck@users.sf.net>
 *                    2010  Egon Willighagen <egonw@users.sf.net>
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

import java.util.Map;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.nonotify.NNMoleculeSet;

/** 
 * A set of molecules, for example those taking part in a reaction.
 *
 * To retrieve the molecules from the set, there are two options:
 *
 * <pre>
 * Molecule[] mols = setOfMolecules.getMolecules();
 * for (int i=0; i < mols.length; i++) {
 *     Molecule mol = mols[i];
 * }
 * </pre>
 *
 * and
 *
 * <pre>
 * for (int i=0; i < setOfMolecules.getMoleculeCount(); i++) {
 *    Molecule mol = setOfMolecules.getMolecule(i);
 * }
 * </pre>
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword reaction
 * @cdk.keyword molecule
 */
public class MoleculeSet extends NNMoleculeSet
implements IMoleculeSet, Cloneable, IChemObjectChangeNotifier, IChemObjectListener {

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 * 
	 */
	private static final long serialVersionUID = -861287315770869699L;

	/** {@inheritDoc} */
	public MoleculeSet() {}
	
    /** {@inheritDoc} */
    public void add(IMoleculeSet moleculeSet) {
        super.add(moleculeSet);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setMolecules(IMolecule[] molecules)
    {
	    super.setMolecules(molecules);
	    notifyChanged();
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

    /** {@inheritDoc} */
    public void addAtomContainer(IAtomContainer atomContainer) {
        if (!(atomContainer instanceof IMolecule))
            throw new IllegalArgumentException(
                "Only IMolecule's can be stored in an IMoleculeSet"
            );
        if (atomContainer instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)atomContainer).addListener(this);
        addAtomContainer(atomContainer, 1.0);
    }

    /** {@inheritDoc} */
    public void removeAtomContainer(IAtomContainer atomContainer) {
        super.removeAtomContainer(atomContainer);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllAtomContainers() {
        super.removeAllAtomContainers();
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void removeAtomContainer(int pos) {
        super.removeAtomContainer(pos);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void replaceAtomContainer(int position, IAtomContainer container) {
        IAtomContainer old = getAtomContainer(position);
        if (old instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)old).removeListener(this);
        super.replaceAtomContainer(position, container);
        if (container instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)container).addListener(this);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public boolean setMultiplier(IAtomContainer container, Double multiplier) {
        super.setMultiplier(container, multiplier);
        return false;
    }

    /** {@inheritDoc} */
    public void setMultiplier(int position, Double multiplier) {
        super.setMultiplier(position, multiplier);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public boolean setMultipliers(Double[] newMultipliers) {
        boolean results = super.setMultipliers(newMultipliers);
        notifyChanged();
        return results;
    }

    /** {@inheritDoc} */
    public void addAtomContainer(IAtomContainer atomContainer, double multiplier) {
        if (atomContainer instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)atomContainer).addListener(this);
        super.addAtomContainer(atomContainer, multiplier);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void add(IAtomContainerSet atomContainerSet) {
        for (IAtomContainer iter : atomContainerSet.atomContainers()) {
            addAtomContainer(iter);
        }
    }

    /**
     *  Clones this AtomContainerSet and its content.
     *
     * @return    the cloned Object
     */
    public Object clone() throws CloneNotSupportedException {
        MoleculeSet clone = (MoleculeSet)super.clone();
        return clone;
    }

    /**
     * Called by objects to which this object has
     * registered as a listener.
     *
     * @param  event  A change event pointing to the source of the change
     */
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

    /**
     *  Sets a property for a IChemObject.
     *
     *@param  description  An object description of the property (most likely a
     *      unique string)
     *@param  property     An object with the property itself
     *@see                 #getProperty
     *@see                 #removeProperty
     */
    public void setProperty(Object description, Object property)
    {
        super.setProperty(description, property);
        notifyChanged();
    }


    /**
     *  Removes a property for a IChemObject.
     *
     *@param  description  The object description of the property (most likely a
     *      unique string)
     *@see                 #setProperty
     *@see                 #getProperty
     */
    public void removeProperty(Object description) {
        super.removeProperty(description);
        notifyChanged();
    }

    /**
     *  Sets the identifier (ID) of this object.
     *
     *@param  identifier  a String representing the ID value
     *@see                #getID
     */
    public void setID(String identifier)
    {
        super.setID(identifier);
        notifyChanged();
    }


    /**
     *  Sets the value of some flag.
     *
     *@param  flag_type   Flag to set
     *@param  flag_value  Value to assign to flag
     *@see                #getFlag
     */
    public void setFlag(int flag_type, boolean flag_value)
    {
        super.setFlag(flag_type, flag_value);
        notifyChanged();
    }

    /**
     *  Sets the properties of this object.
     *
     *@param  properties  a Hashtable specifying the property values
     *@see                #getProperties
     */
    public void setProperties(Map<Object,Object> properties)
    {
        super.setProperties(properties);
        notifyChanged();
    }
  
    /**
     * Sets the whole set of flags.
     *
     * @param  flagsNew    the new flags.
     * @see                #getFlags
     */
    public void setFlags(boolean[] flagsNew){
        super.setFlags(flagsNew);
    }

    /**
     * Clones this <code>IChemObject</code>, but preserves references to <code>Object</code>s.
     *
     * @return    Shallow copy of this IChemObject
     * @see       #clone
     */
    public Object shallowCopy()
    {
        Object copy = null;
        try {
            copy = super.clone();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return copy;
    }
    
    public IChemObjectBuilder getBuilder() {
        return DefaultChemObjectBuilder.getInstance();
    }
}
