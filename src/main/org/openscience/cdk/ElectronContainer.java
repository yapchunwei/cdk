/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 *
 * Copyright (C) 1997-2007  Christoph Steinbeck <steinbeck@users.sf.net>
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

import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IElectronContainer;
import org.openscience.cdk.nonotify.NNElectronContainer;

/**
 * Base class for entities containing electrons, like bonds, orbitals, lone-pairs.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword orbital
 * @cdk.keyword lone-pair
 * @cdk.keyword bond
 */
public class ElectronContainer extends NNElectronContainer
implements Serializable, IElectronContainer, IChemObjectChangeNotifier, Cloneable 
{
	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -2207894536767670743L;

    /**
     * Constructs an empty ElectronContainer.
     */
    public ElectronContainer() {
        super();
    }

    public String toString() {
        StringBuffer resultString = new StringBuffer(64);
        resultString.append("ElectronContainer(").append(hashCode());
        if (getElectronCount() > -1) {
        	resultString.append("EC:").append(getElectronCount());
        }
        resultString.append(')');
        return resultString.toString(); 
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
        ElectronContainer clone = (ElectronContainer)super.clone();
        // delete all listeners
        clone.notifier = null;
        return clone;
    }
}


