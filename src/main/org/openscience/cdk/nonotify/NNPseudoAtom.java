/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2006-2007  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.nonotify;

import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IPseudoAtom;

/**
 * @cdk.module nonotify
 * @cdk.githash
 */
public class NNPseudoAtom extends NNAtom implements IPseudoAtom {

	private static final long serialVersionUID = -3952239302344875360L;

    private String label;
    
    /**
     * Constructs an empty PseudoAtom.
     */
    public NNPseudoAtom() {
        this("*");
    }
    
    /**
     * Constructs an Atom from a String containing an element symbol.
     *
     * @param   label  The String describing the PseudoAtom
     */
    public NNPseudoAtom(String label) {
        super("R");
        this.label = label;
        super.fractionalPoint3d = null;
        super.point3d = null;
        super.point2d = null;

        // set these default, unchangeable values
        super.stereoParity = 0;
    }

    /**
     * Constructs an PseudoAtom from a IAtom.
     *
     * @param   element  IAtom from which the PseudoAtom is constructed
     */
    public NNPseudoAtom(IElement element) {
        super(element);
        if (element instanceof IPseudoAtom) {
            this.label = ((IPseudoAtom)element).getLabel();     
        } else {
            super.symbol = "R";
            this.label = element.getSymbol();
        }
    }

    /**
     * Constructs an Atom from an Element and a Point3d.
     *
     * @param   label  The String describing the PseudoAtom
     * @param   point3d         The 3D coordinates of the atom
     */
    public NNPseudoAtom(String label, javax.vecmath.Point3d point3d) {
        this(label);
        this.point3d = point3d;
    }

    /**
     * Constructs an Atom from an Element and a Point2d.
     *
     * @param   label  The String describing the PseudoAtom
     * @param   point2d         The Point
     */
    public NNPseudoAtom(String label, javax.vecmath.Point2d point2d) {
        this(label);
        this.point2d = point2d;
    }

    /**
     * Returns the label of this PseudoAtom.
     *
     * @return The label for this PseudoAtom
     * @see    #setLabel
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this PseudoAtom.
     *
     * @param label The new label for this PseudoAtom
     * @see   #getLabel
     */
    public void setLabel(String label) {
        this.label = label;
    }

  
    /**
     * Dummy method: the stereo parity is undefined, final.
     */
    public void setStereoParity(Integer stereoParity) {
        // this is undefined, always
    }

    /**
     * Returns a one line string representation of this Atom.
     * Methods is conform RFC #9.
     *
     * @return  The string representation of this Atom
     */
    public String toString() {
        StringBuffer description = new StringBuffer();
        description.append("PseudoAtom(");
        description.append(this.hashCode());
        if (getLabel() != null) {
            description.append(", ").append(getLabel());
        }
        description.append(", ").append(super.toString());
        description.append(')');
        return description.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}
}





