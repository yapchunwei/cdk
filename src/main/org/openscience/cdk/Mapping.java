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

import org.openscience.cdk.interfaces.IChemObject;
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
public class Mapping extends NNMapping implements java.io.Serializable, Cloneable, IMapping {

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
}


