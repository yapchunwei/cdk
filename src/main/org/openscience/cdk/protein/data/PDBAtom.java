/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2005-2007  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.protein.data;

import java.util.Map;

import org.openscience.cdk.Atom;
import org.openscience.cdk.ChemObjectNotifier;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IPDBAtom;
import org.openscience.cdk.nonotify.NNPDBAtom;

/**
 * Represents the idea of an atom as used in PDB files. It contains extra fields
 * normally associated with atoms in such files.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @see  Atom
 */
public class PDBAtom extends NNPDBAtom implements Cloneable, IPDBAtom, IChemObjectChangeNotifier {

    /**
     * Determines if a deserialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href="http://java.sun.com/products/jdk/1.1/docs/guide/serialization/spec/version.doc.html">details</a>.
	 */
	private static final long serialVersionUID = 7670650135045832543L;

	/** {@inheritDoc} */
    public PDBAtom(IElement element) {
        super(element);
    }
    
    /** {@inheritDoc} */
    public PDBAtom(String elementSymbol) {
        super(elementSymbol);
    }
    
    /** {@inheritDoc} */
    public PDBAtom(String elementSymbol, javax.vecmath.Point3d point3d) {
        super(elementSymbol, point3d);
    }
        
    /** {@inheritDoc}} */
    public void setRecord(String newRecord) {
        super.setRecord(newRecord);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setTempFactor(Double newTempFactor) {
        super.setTempFactor(newTempFactor);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setResName(String newResName) {
        super.setResName(newResName);
        notifyChanged();
    }
    
    /** {@inheritDoc}} */
    public void setICode(String newICode) {
        super.setICode(newICode);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setName(String newName) {
        super.setName(newName);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setChainID(String newChainID) {
        super.setChainID(newChainID);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setAltLoc(String newAltLoc) {
        super.setAltLoc(newAltLoc);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setSegID(String newSegID) {
        super.setSegID(newSegID);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setSerial(Integer newSerial) {
        super.setSerial(newSerial);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public void setResSeq(String newResSeq) {
        super.setResSeq(newResSeq);
        notifyChanged();
    }
    
    /** {@inheritDoc}} */
    public void setOxt(Boolean newOxt) {
        super.setOxt(newOxt);
        notifyChanged();
    }
    
    /** {@inheritDoc}} */
    public void setHetAtom(Boolean newHetAtom) {
        super.setHetAtom(newHetAtom);
        notifyChanged();
    }
    
    /** {@inheritDoc}} */
    public void setOccupancy(Double newOccupancy) {
        super.setOccupancy(newOccupancy);
        notifyChanged();
    }

    /** {@inheritDoc}} */
    public String toString() {
        StringBuffer description = new StringBuffer();
        description.append("PDBAtom(");
        description.append(this.hashCode()).append(", ");
        description.append("altLoc=").append(getAltLoc()).append(", ");
        description.append("chainID=").append(getChainID()).append(", ");
        description.append("iCode=").append(getICode()).append(", ");
        description.append("name=").append(getName()).append(", ");
        description.append("resName=").append(getResName()).append(", ");
        description.append("resSeq=").append(getResSeq()).append(", ");
        description.append("segID=").append(getSegID()).append(", ");
        description.append("serial=").append(getSerial()).append(", ");
        description.append("tempFactor=").append(getTempFactor()).append(", ");
        description.append("oxt=").append(getOxt()).append(", ");
        description.append("hetatm=").append(getHetAtom()).append(", ");
        description.append(super.toString());
        description.append(")");
        return description.toString();
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
        PDBAtom clone = (PDBAtom)super.clone();
        // delete all listeners
        clone.notifier = null;
        return clone;
    }
}





