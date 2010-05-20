/* Copyright (C) 2004-2007  Martin Eklund <martin.eklund@farmbio.uu.se>
 *                    2010  Egon Willighagen <egonw@users.sf.net>
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

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IMonomer;
import org.openscience.cdk.interfaces.IStrand;
import org.openscience.cdk.nonotify.NNStrand;

/**
 * A Strand is an AtomContainer which stores additional strand specific
 * informations for a group of Atoms.
 *
 * @cdk.module  data
 * @cdk.githash
 * @cdk.created 2004-12-20
 * @author      Martin Eklund <martin.eklund@farmbio.uu.se>
 * @author      Ola Spjuth <ola.spjuth@farmbio.uu.se>
 */
public class Strand extends NNStrand implements java.io.Serializable, IStrand,
    IChemObjectListener, IChemObjectChangeNotifier {
	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 4200943086350928356L;

	/** {@inheritDoc} */
	public Strand () {
		super();
	}
	
	/** {@inheritDoc} */
	public void setStrandName(String cStrandName) {
		super.setStrandName(cStrandName);
		notifyChanged();
	}
	
	/** {@inheritDoc} */
	public void setStrandType(String cStrandType) {
		super.setStrandType(cStrandType);
	}
	
	/** {@inheritDoc} */
	public void addAtom(IAtom oAtom, IMonomer oMonomer) {
		super.addAtom(oAtom, oMonomer);
		notifyChanged();
	}
	
	/** {@inheritDoc} */
	public void removeMonomer(String name)	{
	    super.removeMonomer(name);
	    notifyChanged();
	}
	
    public String toString() {
        StringBuffer stringContent = new StringBuffer(32);
        stringContent.append("NNStrand(");
        stringContent.append(this.hashCode());
        if (getStrandName() != null) {
        	stringContent.append(", N:").append(getStrandName());
        }
        if (getStrandType() != null) {
            stringContent.append(", T:").append(getStrandType()).append(", ");
        }
        stringContent.append(super.toString());
        stringContent.append(')');
        return stringContent.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        Strand clone = (Strand)super.clone();
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

    /** {@inheritDoc} */
    public void stateChanged(IChemObjectChangeEvent event) {
        notifyChanged(event);
    }

}
