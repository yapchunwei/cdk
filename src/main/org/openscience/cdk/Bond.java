/*  $RCSfile$
 *  $Author$
 *  $Date$
 *  $Revision$
 *
 *  Copyright (C) 1997-2007  Christoph Steinbeck <steinbeck@users.sf.net>
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
 *
 */
package org.openscience.cdk;

import java.io.Serializable;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.nonotify.NNBond;

/**
 * Implements the concept of a covalent bond between two or more atoms. A bond is
 * considered to be a number of electrons connecting two or more  of atoms.
 * <p/>
 * It should be noted that the majority of applications will consider 2-center bonds,
 * especially since the bond orders currently supported are really only valid for
 * 2-center bonds. However the code does support multi-center bonds, though the
 * orders may not make sense at this point.
 * <p/>
 * In general code that assumes bonds are 2-centered can use this class seamlessly, as
 * the semantics are identical to the older versions. Care shoud be exercised when
 * using multi-center bonds using this class as the orders may not make sense.
 *
 * @author steinbeck
 * @cdk.module data
 * @cdk.githash
 * @cdk.created 2003-10-02
 * @cdk.keyword bond
 * @cdk.keyword atom
 * @cdk.keyword electron
 */
public class Bond extends NNBond implements IBond, IChemObjectChangeNotifier, Serializable, Cloneable {
    /**
     * Determines if a de-serialized object is compatible with this class.
     * <p/>
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
     */
    private static final long serialVersionUID = 7057060562283387384L;

    public Bond() {
        this(null, null, null, IBond.Stereo.NONE);
    }

    public Bond(IAtom atom1, IAtom atom2) {
        this(atom1, atom2, IBond.Order.SINGLE, IBond.Stereo.NONE);
    }

    public Bond(IAtom atom1, IAtom atom2, IBond.Order order) {
        this(atom1, atom2, order, IBond.Stereo.NONE);
    }

    public Bond(IAtom atom1, IAtom atom2, IBond.Order order,
                  IBond.Stereo stereo) {
        super(atom1, atom2, order, stereo);
    }

    public Bond(IAtom[] atoms) {
        super(atoms);
    }

    public Bond(IAtom[] atoms, IBond.Order order) {
        super(atoms, order);
    }

    /** {@inheritDoc} */
    public void setAtoms(IAtom[] atoms) {
        super.setAtoms(atoms);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setAtom(IAtom atom, int position) {
        super.setAtom(atom, position);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setOrder(Order order) {
        super.setOrder(order);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setStereo(IBond.Stereo stereo) {
        super.setStereo(stereo);
        notifyChanged();
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
        Bond clone = (Bond)super.clone();
        // delete all listeners
        clone.notifier = new ChemObjectNotifier();
        return clone;
    }
}

