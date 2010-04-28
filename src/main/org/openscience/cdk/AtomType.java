/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 *
 *  Copyright (C) 2001-2007  Christoph Steinbeck <steinbeck@users.sf.net>
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *  All we ask is that proper credit is given for our work, which includes
 *  - but is not limited to - adding the above copyright notice to the beginning
 *  of your source code files, and to any copyright notice that you may distribute
 *  with programs based on this work.
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

import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.nonotify.NNAtomType;

/**
 * The base class for atom types. Atom types are typically used to describe the
 * behaviour of an atom of a particular element in different environment like 
 * sp<sup>3</sup>
 * hybridized carbon C3, etc., in some molecular modelling applications.
 *
 * @author       steinbeck
 * @cdk.created  2001-08-08
 * @cdk.module   data
 * @cdk.githash
 * @cdk.keyword  atom, type
 */
public class AtomType extends NNAtomType implements IAtomType, IChemObjectChangeNotifier, Serializable, Cloneable
{

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide/serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -7950397716808229972L;

    /**
	 * Constructor for the AtomType object.
     *
     * Defaults to a zero formal charge. All
     * other fields are set to {@link org.openscience.cdk.CDKConstants.UNSET}.
     *
     * @param elementSymbol  Symbol of the atom
	 */
	public AtomType(String elementSymbol) {
		super(elementSymbol);
	}


	/**
	 * Constructor for the AtomType object. Defaults to a zero formal charge.
	 *
	 * @param  identifier     An id for this atom type, like C3 for sp3 carbon
	 * @param  elementSymbol  The element symbol identifying the element to which this atom type applies
	 */
	public AtomType(String identifier, String elementSymbol)
	{
		super(identifier, elementSymbol);
	}

	/**
	 * Constructs an isotope by copying the symbol, atomic number,
	 * flags, identifier, exact mass, natural abundance and mass 
	 * number from the given IIsotope. It does not copy the
	 * listeners and properties. If the element is an instanceof
	 * IAtomType, then the maximum bond order, bond order sum,
	 * van der Waals and covalent radii, formal charge, hybridization,
	 * electron valency, formal neighbour count and atom type name
	 * are copied too.
	 * 
	 * @param element IIsotope to copy information from
	 */
	public AtomType(IElement element) {
		super(element);
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
    public void setAtomTypeName(String identifier)
    {
        super.setAtomTypeName(identifier);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setMaxBondOrder(IBond.Order maxBondOrder)
    {
        super.setMaxBondOrder(maxBondOrder);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setBondOrderSum(Double bondOrderSum)
    {
        super.setBondOrderSum(bondOrderSum);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setFormalCharge(Integer charge) {
        super.setFormalCharge(charge);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setFormalNeighbourCount(Integer count) {
        super.setFormalNeighbourCount(count);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void setHybridization(IAtomType.Hybridization hybridization) {
        super.setHybridization(hybridization);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void setCovalentRadius(Double radius) {
        super.setCovalentRadius(radius);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void setValency(Integer valency) {
        super.setValency(valency);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setNaturalAbundance(Double naturalAbundance) {
        super.setNaturalAbundance(naturalAbundance);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setExactMass(Double exactMass) {
        super.setExactMass(exactMass);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setMassNumber(Integer massNumber) {
        super.setMassNumber(massNumber);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setAtomicNumber(Integer atomicNumber) {
        super.setAtomicNumber(atomicNumber);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setSymbol(String symbol) {
        super.setSymbol(symbol);
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
        AtomType clone = (AtomType)super.clone();
        // delete all listeners
        clone.notifier = new ChemObjectNotifier();
        return clone;
    }
}

