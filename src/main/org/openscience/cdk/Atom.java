/* $Revision$ $Author$ $Date$
 *
 * Copyright (C) 2000-2007  Christoph Steinbeck <steinbeck@users.sf.net>
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
import java.util.Map;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.nonotify.NNAtom;

/**
 * Represents the idea of an chemical atom.
 *
 * <p>An Atom class is instantiated with at least the atom symbol:
 * <pre>
 *   Atom a = new Atom("C");
 * </pre>
 *
 * <p>Once instantiated all field not filled by passing parameters
 * to the constructor are null. Atoms can be configured by using
 * the IsotopeFactory.configure() method:
 * <pre>
 *   IsotopeFactory if = IsotopeFactory.getInstance(a.getNewBuilder());
 *   if.configure(a);
 * </pre>
 *
 * <p>More examples about using this class can be found in the
 * Junit test for this class.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @author     steinbeck
 * @cdk.created    2000-10-02
 * @cdk.keyword    atom
 *
 * @see  org.openscience.cdk.config.IsotopeFactory#getInstance(org.openscience.cdk.interfaces.IChemObjectBuilder)
 */
public class Atom extends NNAtom implements IAtom, IChemObjectChangeNotifier, Serializable, Cloneable  {
    
	/* Let's keep this exact specification
	 * of what kind of point2d we're talking of here,
	 * since there are so many around in the java standard api */

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -3137373012494608794L;
	
    /**
     * Constructs an completely unset Atom.
     */
    public Atom() {
        super();
    }

	/**
	 * Constructs an Atom from a String containing an element symbol.
	 *
	 * @param elementSymbol The String describing the element for the Atom
	 */
	public Atom(String elementSymbol) {
	    super(elementSymbol);      
	}

	/**
	 * Constructs an Atom from an Element and a Point3d.
	 *
	 * @param   elementSymbol   The symbol of the atom
	 * @param   point3d         The 3D coordinates of the atom
	 */
	public Atom(String elementSymbol, javax.vecmath.Point3d point3d)
	{
	    super(elementSymbol, point3d);
	}

	/**
	 * Constructs an Atom from an Element and a Point2d.
	 *
	 * @param   elementSymbol   The Element
	 * @param   point2d         The Point
	 */
	public Atom(String elementSymbol, javax.vecmath.Point2d point2d)
	{
	    super(elementSymbol, point2d);
	}

	/**
	 * Constructs an isotope by copying the symbol, atomic number,
	 * flags, identifier, exact mass, natural abundance, mass 
	 * number, maximum bond order, bond order sum, van der Waals
	 * and covalent radii, formal charge, hybridization, electron
	 * valency, formal neighbour count and atom type name from the 
	 * given IAtomType. It does not copy the listeners and
	 * properties. If the element is an instanceof
	 * IAtom, then the 2D, 3D and fractional coordinates, partial
	 * atomic charge, hydrogen count and stereo parity are copied
	 * too.
	 * 
	 * @param element IAtomType to copy information from
	 */
	public Atom(IElement element) {
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
    public void setCharge(Double charge) {
        super.setCharge(charge);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setHydrogenCount(Integer hydrogenCount) {
        super.setHydrogenCount(hydrogenCount);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setPoint2d(javax.vecmath.Point2d point2d) {
        super.setPoint2d(point2d);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setPoint3d(javax.vecmath.Point3d point3d) {
        super.setPoint3d(point3d);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setFractionalPoint3d(javax.vecmath.Point3d point3d) {
        super.setFractionalPoint3d(point3d);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setStereoParity(Integer stereoParity) {
        super.setStereoParity(stereoParity);
        notifyChanged();
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
        Atom clone = (Atom)super.clone();
        // delete all listeners
        clone.notifier = new ChemObjectNotifier();
        return clone;
    }
}





