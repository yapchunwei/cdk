/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2004-2007  Egon Willighagen <egonw@users.sf.net>
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
 * 
 */
package org.openscience.cdk;

import java.io.Serializable;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.ISingleElectron;
import org.openscience.cdk.nonotify.NNSingleElectron;

/**
 * A Single Electron is an orbital which is occupied by only one electron.
 * A radical in CDK is represented by an AtomContainer that contains an Atom
 * and a SingleElectron type ElectronContainer:
 * <pre>
 *   AtomContainer radical = new AtomContainer();
 *   Atom carbon = new Atom("C");
 *   carbon.setImplicitHydrogens(3);
 *   radical.addElectronContainer(new SingleElectron(carbon));
 * </pre> 
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword radical
 * @cdk.keyword electron, unpaired
 */
public class SingleElectron extends NNSingleElectron implements Serializable, ISingleElectron,
    IChemObjectChangeNotifier, Cloneable
{

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 7796574734668490940L;

	/** {@inheritDoc} */
    public SingleElectron(IAtom atom) {
        super(atom);
    }

    /** {@inheritDoc} */
    public SingleElectron() {
        this(null);
    }

	/**
	 * Sets the associated Atom.
	 *
	 * @param atom the Atom this SingleElectron will be associated with
     *
     * @see    #getAtom
	 */
	public void setAtom(IAtom atom) {
		this.atom = atom;
		notifyChanged();
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
    public void setElectronCount(Integer electronCount) {
        super.setElectronCount(electronCount);
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

    /** {@inheritDoc} */
    public Object clone() throws CloneNotSupportedException
    {
        SingleElectron clone = (SingleElectron)super.clone();
        // delete all listeners
        clone.notifier = null;
        return clone;
    }
}


