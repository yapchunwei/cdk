/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2003-2007  Egon Willighagen <egonw@users.sf.net>
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
 *
 */
package org.openscience.cdk;

import java.io.Serializable;

import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IPseudoAtom;
import org.openscience.cdk.nonotify.NNPseudoAtom;

/**
 * Represents the idea of a non-chemical atom-like entity, like Me,
 * R, X, Phe, His, etc.
 *
 * <p>This should be replaced by the mechanism explained in RFC #8.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @see  Atom
 */
public class PseudoAtom extends NNPseudoAtom 
  implements Serializable, Cloneable, IPseudoAtom, IChemObjectChangeNotifier 
{

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs an empty PseudoAtom.
     */
    public PseudoAtom() {
        super();
    }
    
    /**
     * Constructs an Atom from a String containing an element symbol.
     *
     * @param   label  The String describing the PseudoAtom
     */
    public PseudoAtom(String label) {
        super(label);
    }

    /**
     * Constructs an PseudoAtom from a IAtom.
     *
     * @param   element  IAtom from which the PseudoAtom is constructed
     */
    public PseudoAtom(IElement element) {
        super(element);
    }

    /**
     * Constructs an Atom from an Element and a Point3d.
     *
     * @param   label  The String describing the PseudoAtom
     * @param   point3d         The 3D coordinates of the atom
     */
    public PseudoAtom(String label, javax.vecmath.Point3d point3d) {
        super(label, point3d);
    }

    /**
     * Constructs an Atom from an Element and a Point2d.
     *
     * @param   label  The String describing the PseudoAtom
     * @param   point2d         The Point
     */
    public PseudoAtom(String label, javax.vecmath.Point2d point2d) {
        super(label, point2d);
    }

    /**
     * Sets the label of this PseudoAtom.
     *
     * @param label The new label for this PseudoAtom
     * @see   #getLabel
     */
    public void setLabel(String label) {
        super.setLabel(label);
        notifyChanged();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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





