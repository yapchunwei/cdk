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
 * All I ask is that proper credit is given for my work, which includes
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
import java.util.Map;

import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.nonotify.NNIsotope;

/**
 * Used to store and retrieve data of a particular isotope.
 * For example, an carbon 13 isotope can be created with:
 * <pre>
 *   Isotope carbon = new Isotope("C", 13);
 * </pre>
 *
 * <p>A full specification can be constructed with:
 * <pre>
 *   // make deuterium
 *   Isotope carbon = new Isotope(1, "H", 2, 2.01410179, 100.0);
 * </pre>
 * 
 * <p>Once instantiated all field not filled by passing parameters
 * to the constructor are null. Isotopes can be configured by using
 * the IsotopeFactory.configure() method:
 * <pre>
 *   Isotope isotope = new Isotope("C", 13);
 *   IsotopeFactory if = IsotopeFactory.getInstance(isotope.getNewBuilder());
 *   if.configure(isotope);
 * </pre>
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @author     steinbeck
 * @cdk.created    2001-08-21
 *
 * @cdk.keyword     isotope
 */
public class Isotope extends NNIsotope implements Serializable, IIsotope, IChemObjectChangeNotifier, Cloneable 
{

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 6389365978927575858L;

	/**
	 *  Constructor for the Isotope object.
	 *
	 * @param  elementSymbol  The element symbol, "O" for Oxygen, etc.
	 */
	public Isotope(String elementSymbol) {
		super(elementSymbol);
	}
	
	/**
	 *  Constructor for the Isotope object.
	 *
	 * @param  atomicNumber   The atomic number of the isotope
	 * @param  elementSymbol  The element symbol, "O" for Oxygen, etc.
	 * @param  massNumber     The atomic mass of the isotope, 16 for Oxygen, e.g.
	 * @param  exactMass      The exact mass of the isotope, be a little more explicit here :-)
	 * @param  abundance      The natural abundance of the isotope
	 */
	public Isotope(int atomicNumber, String elementSymbol, int massNumber, double exactMass, double abundance) {
		super(atomicNumber, elementSymbol, massNumber, exactMass, abundance);
	}


	/**
	 *  Constructor for the Isotope object.
	 *
	 * @param  atomicNumber   The atomic number of the isotope, 8 for Oxygen
	 * @param  elementSymbol  The element symbol, "O" for Oxygen, etc.
	 * @param  exactMass      The exact mass of the isotope, be a little more explicit here :-)
	 * @param  abundance      The natural abundance of the isotope
	 */
	public Isotope(int atomicNumber, String elementSymbol, double exactMass, double abundance) {
		super(atomicNumber, elementSymbol, exactMass, abundance);
	}

	/**
	 * Constructor for the Isotope object.
	 *
	 * @param  elementSymbol  The element symbol, "O" for Oxygen, etc.
	 * @param  massNumber     The atomic mass of the isotope, 16 for Oxygen, e.g.
	 */
	public Isotope(String elementSymbol, int massNumber) {
		super(elementSymbol, massNumber);
	}

	/**
     * Constructs an empty by copying the symbol, atomic number,
     * flags, and identifier from the given IElement. It does
     * not copy the listeners and properties. If the element is
     * an instanceof IIsotope, then the exact mass, natural
     * abundance and mass number are copied too.
	 * 
	 * @param element IElement to copy information from
	 */
	public Isotope(IElement element) {
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
        Isotope clone = (Isotope)super.clone();
        // delete all listeners
        clone.notifier = new ChemObjectNotifier();
        return clone;
    }
}
