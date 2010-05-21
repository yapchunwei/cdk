/* Copyright (C) 2003-2007,2010  Egon Willighagen <egonw@users.sf.net>
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
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

import java.util.Map;

import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IMapping;
import org.openscience.cdk.nonotify.NNMapping;

/**
 * A Mapping is an relation between two ChemObjects in a non-chemical
 * entity. It is not a Bond, nor a Association, merely a relation.
 * An example of such a mapping, is the mapping between corresponding atoms
 * in a Reaction.
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword reaction, atom mapping
 *
 * @author  Egon Willighagen
 * @cdk.created 2003-08-16
 */
public class Mapping extends NNMapping implements java.io.Serializable, Cloneable, IMapping,
    IChemObjectChangeNotifier {

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -6541914644492043503L;

    /**
     * Constructs an unconnected lone pair.
     *
     * @param objectOne The first IChemObject of the mapping
     * @param objectTwo The second IChemObject of the mapping
     */
    public Mapping(IChemObject objectOne, IChemObject objectTwo) {
        super(objectOne, objectTwo);
    }

    /**
	 * Clones this <code>Mapping</code> and the mapped <code>IChemObject</code>s.
	 *
	 * @return  The cloned object
	 */
	public Object clone() throws CloneNotSupportedException {
		Mapping clone = (Mapping)super.clone();
        return clone;
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

}


