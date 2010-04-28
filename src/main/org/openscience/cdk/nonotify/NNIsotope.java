/* Copyright (C) 2006-2007  Egon Willighagen <egonw@users.sf.net>
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
 */
package org.openscience.cdk.nonotify;

import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IIsotope;

/**
 * @cdk.module nonotify
 * @cdk.githash
 */
public class NNIsotope extends NNElement implements IIsotope {

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
     */
	private static final long serialVersionUID = -5357674661448129426L;

    /** Exact mass of this isotope. */
    private Double exactMass;
    /** Natural abundance of this isotope. */
    private Double naturalAbundance;
    /** The mass number for this isotope. */
    private Integer massNumber;

    /**
     * Constructor for the {@link IIsotope} object.
     *
     * @param  elementSymbol  The element symbol, "O" for Oxygen, etc.
     */
	public NNIsotope(String elementSymbol) {
		super(elementSymbol);
	}
	
    /**
     * Constructor for this {@link IIsotope} object.
     *
     * @param  atomicNumber   The atomic number of the isotope
     * @param  elementSymbol  The element symbol, "O" for Oxygen, etc.
     * @param  massNumber     The atomic mass of the isotope, 16 for Oxygen, e.g.
     * @param  exactMass      The exact mass of the isotope, be a little more explicit here :-)
     * @param  abundance      The natural abundance of the isotope
     */
	public NNIsotope(int atomicNumber, String elementSymbol, int massNumber, double exactMass, double abundance) {
		this(atomicNumber, elementSymbol, exactMass, abundance);
        this.massNumber = massNumber;
	}

	public NNIsotope(int atomicNumber, String elementSymbol, double exactMass, double abundance) {
		super(elementSymbol, atomicNumber);
        this.exactMass = exactMass;
        this.naturalAbundance = abundance;
	}

    /**
     * Constructor for the {@link IIsotope} object.
     *
     * @param  elementSymbol  The element symbol, "O" for Oxygen, etc.
     * @param  massNumber     The atomic mass of the isotope, 16 for Oxygen, e.g.
     */
	public NNIsotope(String elementSymbol, int massNumber) {
		super(elementSymbol);
        this.massNumber = massNumber;
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
	public NNIsotope(IElement element) {
		super(element);
        if (element instanceof IIsotope) {
            this.exactMass = ((IIsotope)element).getExactMass();
            this.naturalAbundance = ((IIsotope)element).getNaturalAbundance();
            this.massNumber = ((IIsotope)element).getMassNumber();
        }
	}

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    /**
     *  Sets the NaturalAbundance attribute of the Isotope object.
     *
     * @param  naturalAbundance  The new NaturalAbundance value
     *
     * @see       #getNaturalAbundance
     */
    public void setNaturalAbundance(Double naturalAbundance) {
        this.naturalAbundance = naturalAbundance;
    }

    /**
     *  Sets the ExactMass attribute of the Isotope object.
     *
     * @param  exactMass  The new ExactMass value
     *
     * @see       #getExactMass
     */
    public void setExactMass(Double exactMass) {
        this.exactMass = exactMass;
    }

    /**
     *  Gets the NaturalAbundance attribute of the Isotope object.
     *  
     *  <p>Once instantiated all field not filled by passing parameters
     * to the constructor are null. Isotopes can be configured by using
     * the IsotopeFactory.configure() method:
     * <pre>
     *   Isotope isotope = new Isotope("C", 13);
     *   IsotopeFactory if = IsotopeFactory.getInstance(isotope.getNewBuilder());
     *   if.configure(isotope);
     * </pre>
     * </p>
     *
     * @return    The NaturalAbundance value
     *
     * @see       #setNaturalAbundance
     */
    public Double getNaturalAbundance() {
        return this.naturalAbundance;
    }


    /**
     *  Gets the ExactMass attribute of the Isotope object.
     *  <p>Once instantiated all field not filled by passing parameters
     * to the constructor are null. Isotopes can be configured by using
     * the IsotopeFactory.configure() method:
     * <pre>
     *   Isotope isotope = new Isotope("C", 13);
     *   IsotopeFactory if = IsotopeFactory.getInstance(isotope.getNewBuilder());
     *   if.configure(isotope);
     * </pre>
     * </p>
     *
     * @return    The ExactMass value
     *
     * @see       #setExactMass
     */
    public Double getExactMass() {
        return this.exactMass;
    }

    /**
     * Returns the atomic mass of this element.
     * 
     * <p>Once instantiated all field not filled by passing parameters
     * to the constructor are null. Isotopes can be configured by using
     * the IsotopeFactory.configure() method:
     * <pre>
     *   Isotope isotope = new Isotope("C", 13);
     *   IsotopeFactory if = IsotopeFactory.getInstance(isotope.getNewBuilder());
     *   if.configure(isotope);
     * </pre>
     * </p>
     *
     * @return The atomic mass of this element
     *
     * @see    #setMassNumber(Integer)
     */
    public Integer getMassNumber() {
        return this.massNumber;
    }

    /**
     * Sets the atomic mass of this element.
     *
     * @param   massNumber The atomic mass to be assigned to this element
     *
     * @see    #getMassNumber
     */
    public void setMassNumber(Integer massNumber) {
        this.massNumber = massNumber;
    }

    /**
     *  A string representation of this isotope.
     *
     * @return    A string representation of this isotope
     */
    public String toString() {
        StringBuffer resultString = new StringBuffer(32);
        resultString.append("Isotope(").append(hashCode());
        if (massNumber != null) {
            resultString.append(", MN:").append(massNumber);
        }
        if (exactMass != null) {
            resultString.append(", EM:"); resultString.append(exactMass);
        }
        if (naturalAbundance != null) {
            resultString.append(", AB:"); resultString.append(naturalAbundance);
        }
        resultString.append(", ").append(super.toString());
        resultString.append(')');
        return resultString.toString();
    }
    
    /**
     * Compares a atom type with this atom type.
     *
     * @param  object Object of type AtomType
     * @return        true if the atom types are equal
     */
    public boolean compare(Object object) {
        if (!(object instanceof IIsotope)) {
            return false;
        }
        if (!super.compare(object)) {
            return false;
        }
        NNIsotope isotope = (NNIsotope)object;
        return massNumber == isotope.massNumber &&
                exactMass == isotope.exactMass &&
                naturalAbundance == isotope.naturalAbundance;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

