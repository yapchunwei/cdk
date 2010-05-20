/* Copyright (C) 2003-2008,2010  Egon Willighagen <egonw@users.sf.net>
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
import java.util.Hashtable;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IMapping;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.nonotify.NNReaction;

/**
 * Represents the idea of a chemical reaction. The reaction consists of 
 * a set of reactants and a set of products.
 *
 * <p>The class mostly represents abstract reactions, such as 2D diagrams,
 * and is not intended to represent reaction trajectories. Such can better
 * be represented with a ChemSequence.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @author      Egon Willighagen <elw38@cam.ac.uk>
 * @cdk.created 2003-02-13
 * @cdk.keyword reaction
 */
public class Reaction extends NNReaction implements IChemObjectChangeNotifier, Serializable, IReaction, Cloneable {

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -554752558363533678L;

    /** {@inheritDoc} */
    public Reaction() {
        super();
    }

    /** {@inheritDoc} */
    public void setReactants(IMoleculeSet setOfMolecules) {
        super.setReactants(setOfMolecules);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setProducts(IMoleculeSet setOfMolecules) {
        setProducts(setOfMolecules);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addAgent(IMolecule agent) {
        super.addAgent(agent);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addReactant(IMolecule reactant, Double coefficient) {
        super.addReactant(reactant, coefficient);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addProduct(IMolecule product, Double coefficient) {
        super.addProduct(product, coefficient);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public boolean setReactantCoefficient(IMolecule reactant, Double coefficient) {
    	boolean result = super.setReactantCoefficient(reactant, coefficient);
    	notifyChanged();
    	return result;
    }

    /** {@inheritDoc} */
    public boolean setProductCoefficient(IMolecule product, Double coefficient) {
        boolean result = super.setProductCoefficient(product, coefficient);
    	notifyChanged();
    	return result;
    }

    /** {@inheritDoc} */
    public boolean setReactantCoefficients(Double[] coefficients) {
        boolean result = super.setReactantCoefficients(coefficients);
    	notifyChanged();
    	return result;
    }
	
    /** {@inheritDoc} */
    public void setDirection(IReaction.Direction direction) {
        super.setDirection(direction);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void addMapping(IMapping mapping) {
        super.addMapping(mapping);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void removeMapping(int pos) {
		removeMapping(pos);
		notifyChanged();
	}
    
	/**
	 * Clones this <code>Reaction</code> and its content.
	 *
	 * @return  The cloned object
	 */
	public Object clone() throws CloneNotSupportedException {
		Reaction clone = (Reaction)super.clone();
        // clone the reactants, products and agents
        clone.reactants = (MoleculeSet)((MoleculeSet)reactants).clone();
        clone.agents = (MoleculeSet)((MoleculeSet)agents).clone();
        clone.products = (MoleculeSet)((MoleculeSet)products).clone();
        // create a Map of corresponding atoms for molecules (key: original Atom, 
        // value: clone Atom)
        Map<IAtom, IAtom> atomatom = new Hashtable<IAtom, IAtom>();
        for (int i = 0; i < reactants.getMoleculeCount(); ++i) {
            Molecule mol = (Molecule)((MoleculeSet)reactants).getMolecule(i);
            Molecule mol2 = (Molecule)clone.reactants.getMolecule(i);
            for (int j = 0; j < mol.getAtomCount(); ++j) atomatom.put(mol.getAtom(j), mol2.getAtom(j));
        }
        
        // clone the maps
		clone.map = new Mapping[map.length];
		for (int f = 0; f < mappingCount; f++) {
			clone.map[f] = new Mapping((ChemObject)atomatom.get(map[f].getChemObject(0)), (ChemObject)atomatom.get(map[f].getChemObject(1)));
		}
		return clone;
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
}
