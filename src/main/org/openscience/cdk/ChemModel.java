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

import java.io.Serializable;
import java.util.Map;

import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.ICrystal;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IReactionSet;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.nonotify.NNChemModel;

/** 
 * An object containing multiple MoleculeSet and 
 * the other lower level concepts like rings, sequences, 
 * fragments, etc.
 *
 * @cdk.module data
 * @cdk.githash
 */
public class ChemModel extends NNChemModel implements Serializable,
    IChemModel, IChemObjectListener, Cloneable
{

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -5213425310451366185L;

    /** {@inheritDoc} */
	public ChemModel() {
	    super();
	}

    /** {@inheritDoc} */
	public void setMoleculeSet(IMoleculeSet setOfMolecules)
	{
	    if (this.setOfMolecules instanceof IChemObjectChangeNotifier)
	        ((IChemObjectChangeNotifier)this.setOfMolecules).removeListener(this);
		super.setMoleculeSet(setOfMolecules);
		if (this.setOfMolecules instanceof IChemObjectChangeNotifier)
		    ((IChemObjectChangeNotifier)this.setOfMolecules).addListener(this);
		notifyChanged();
	}

    /** {@inheritDoc} */
	public void setRingSet(IRingSet ringSet)
	{
	    if (this.ringSet instanceof IChemObjectChangeNotifier)
	        ((IChemObjectChangeNotifier)this.ringSet).removeListener(this);
		super.setRingSet(ringSet);
		if (this.ringSet instanceof IChemObjectChangeNotifier)
		    ((IChemObjectChangeNotifier)this.ringSet).addListener(this);
		notifyChanged();
	}

    /** {@inheritDoc} */
    public void setCrystal(ICrystal crystal) {
        if (this.crystal instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)this.crystal).removeListener(this);
        super.setCrystal(crystal);
        if (this.crystal instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)this.crystal).addListener(this);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setReactionSet(IReactionSet sor) {
        if (this.setOfReactions instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)this.setOfReactions).removeListener(this);
        super.setReactionSet(sor);
        if (this.setOfReactions instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)this.setOfReactions).addListener(this);
        notifyChanged();
    }
    
	/**
	 * Clones this <code>ChemModel</code> and its content.
	 *
	 * @return  The cloned object
	 */
	public Object clone() throws CloneNotSupportedException {
		ChemModel clone = (ChemModel)super.clone();
        return clone;
	}

    /**
     *  Called by objects to which this object has
     *  registered as a listener.
     *
     *@param  event  A change event pointing to the source of the change
     */
    public void stateChanged(IChemObjectChangeEvent event)
    {
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

