/* Copyright (C) 2006-2007,2010  Egon Willighagen <egonw@users.sf.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.protein.data;

import java.util.Map;

import org.openscience.cdk.ChemObjectNotifier;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.nonotify.NNPDBStructure;

/**
 * Holder for secundary protein structure elements. Lously modeled after
 * the Jmol Structure.java.
 * 
 * @author     egonw
 * 
 * @cdk.module data
 * @cdk.githash
 */
public class PDBStructure extends NNPDBStructure implements IChemObjectChangeNotifier {
	
	private static final long serialVersionUID = -1877529009319324448L;
	
    /** {@inheritDoc} */
    public void setEndChainID(Character endChainID) {
    	super.setEndChainID(endChainID);
    	notifyChanged();
    }

    /** {@inheritDoc} */
    public void setEndInsertionCode(Character endInsertionCode) {
    	super.setEndInsertionCode(endInsertionCode);
    	notifyChanged();
    }

    /** {@inheritDoc} */
    public void setEndSequenceNumber(Integer endSequenceNumber) {
    	super.setEndSequenceNumber(endSequenceNumber);
    	notifyChanged();
    }

    /** {@inheritDoc} */
    public void setStartChainID(Character startChainID) {
        setStartChainID(startChainID);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setStartInsertionCode(Character startInsertionCode) {
    	setStartInsertionCode(startInsertionCode);
    	notifyChanged();
    }

    /** {@inheritDoc} */
    public void setStartSequenceNumber(Integer startSequenceNumber) {
    	super.setStartSequenceNumber(startSequenceNumber);
    	notifyChanged();
    }

    /** {@inheritDoc} */
    public void setStructureType(String structureType) {
    	super.setStructureType(structureType);
    	notifyChanged();
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

    /**
     *  Sets a property for a IChemObject.
     *
     *@param  description  An object description of the property (most likely a
     *      unique string)
     *@param  property     An object with the property itself
     *@see                 #getProperty
     *@see                 #removeProperty
     */
    public void setProperty(Object description, Object property)
    {
        super.setProperty(description, property);
        notifyChanged();
    }


    /**
     *  Removes a property for a IChemObject.
     *
     *@param  description  The object description of the property (most likely a
     *      unique string)
     *@see                 #setProperty
     *@see                 #getProperty
     */
    public void removeProperty(Object description) {
        super.removeProperty(description);
        notifyChanged();
    }

    /**
     *  Sets the identifier (ID) of this object.
     *
     *@param  identifier  a String representing the ID value
     *@see                #getID
     */
    public void setID(String identifier)
    {
        super.setID(identifier);
        notifyChanged();
    }

    /**
     *  Sets the value of some flag.
     *
     *@param  flag_type   Flag to set
     *@param  flag_value  Value to assign to flag
     *@see                #getFlag
     */
    public void setFlag(int flag_type, boolean flag_value)
    {
        super.setFlag(flag_type, flag_value);
        notifyChanged();
    }

    /**
     *  Sets the properties of this object.
     *
     *@param  properties  a Hashtable specifying the property values
     *@see                #getProperties
     */
    public void setProperties(Map<Object,Object> properties)
    {
        super.setProperties(properties);
        notifyChanged();
    }
  
    /**
     * Sets the whole set of flags.
     *
     * @param  flagsNew    the new flags.
     * @see                #getFlags
     */
    public void setFlags(boolean[] flagsNew){
        super.setFlags(flagsNew);
    }

    /**
     * Clones this <code>IChemObject</code>, but preserves references to <code>Object</code>s.
     *
     * @return    Shallow copy of this IChemObject
     * @see       #clone
     */
    public Object shallowCopy()
    {
        Object copy = null;
        try {
            copy = super.clone();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return copy;
    }
    
    public IChemObjectBuilder getBuilder() {
        return DefaultChemObjectBuilder.getInstance();
    }
}
