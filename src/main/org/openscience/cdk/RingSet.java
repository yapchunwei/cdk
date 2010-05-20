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
 */

package org.openscience.cdk;

import java.io.Serializable;

import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.nonotify.NNRingSet;

/**
 * Maintains a set of Ring objects.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword     ring, set of
 */
public class RingSet extends NNRingSet implements IAtomContainerSet, IChemObjectChangeNotifier, Serializable, IRingSet, Cloneable {

	private static final long serialVersionUID = 7168431521057961434L;
	
	/**
	 * The constructor.
	 */
	public RingSet() {
		super();
	}
	
	/**
	 * Adds all rings of another RingSet if they are not already part of this ring set.
     *
     * If you want to add a single ring to the set use {@link #addAtomContainer(org.openscience.cdk.interfaces.IAtomContainer)} 
	 *
	 * @param   ringSet  the ring set to be united with this one.
	 */
	public void add(IRingSet ringSet) {
		super.add(ringSet);
		notifyChanged();
	}

	/**
	 * Clones this <code>RingSet</code> including the Rings.
	 *
	 * @return  The cloned object
	 */
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
	}

    /**
     * Returns the String representation of this RingSet.
     *
     * @return The String representation of this RingSet
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(32);
        buffer.append("RingSet(");
        buffer.append(this.hashCode());
        if (getAtomContainerCount() > 0) {
        	buffer.append(", R=").append(getAtomContainerCount()).append(", ");
        	for (int i = 0; i < atomContainerCount; i++) {
        		IRing possibleRing = (IRing)atomContainers[i];
        		buffer.append(possibleRing.toString());
        		if (i+1 < atomContainerCount) {
        			buffer.append(", ");
        		}
        	}
        }
        buffer.append(')');
        return buffer.toString();
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

}
