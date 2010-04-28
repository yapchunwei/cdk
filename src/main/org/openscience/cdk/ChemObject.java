/* $Revision$ $Author$ $Date$
 *
 *  Copyright (C) 1997-2007  Christoph Steinbeck <steinbeck@users.sf.net>
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.nonotify.NNChemObject;

/**
 *  The base class for all chemical objects in this cdk. It provides methods for
 *  adding listeners and for their notification of events, as well a a hash
 *  table for administration of physical or chemical properties
 *
 *@author        steinbeck
 * @cdk.githash
 *@cdk.module    data
 */
public class ChemObject extends NNChemObject
    implements Serializable, IChemObject, IChemObjectChangeNotifier, Cloneable {

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 2798134548764323328L;

	/**
	 *  Constructs a new IChemObject.
	 */
	public ChemObject() {
        super();
	}

	/**
	 * Constructs a new IChemObject by copying the flags, and the
	 * identifier. It does not copy the listeners and properties.
	 * 
	 * @param chemObject the object to copy
	 */
	public ChemObject(IChemObject chemObject) {
	    super(chemObject);
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
	 *  Clones this <code>IChemObject</code>. It clones the identifier, flags,
	 *  properties and pointer vectors. The ChemObjectListeners are not cloned, and
	 *  neither is the content of the pointer vectors.
	 *
	 *@return    The cloned object
	 */
	public Object clone() throws CloneNotSupportedException
	{
		ChemObject clone = (ChemObject)super.clone();
		// clone the flags
		boolean[] newFlags = new boolean[CDKConstants.MAX_FLAG_INDEX + 1];
        System.arraycopy(super.getFlags(), 0, newFlags, 0, super.getFlags().length);
        clone.setFlags(newFlags);
        // clone the properties
        Map<Object, Object> properties = super.getProperties();
		if (properties != null) {
			Map<Object, Object> clonedHashtable = new HashMap<Object, Object>();
			Iterator<Object> keys = properties.keySet().iterator();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = properties.get(key);
				clonedHashtable.put(key, value);
			}
			clone.setProperties(clonedHashtable);
		}
		// delete all listeners
		clone.notifier = new ChemObjectNotifier();
		return clone;
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


