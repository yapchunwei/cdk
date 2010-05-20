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
 * 
 */
package org.openscience.cdk.nonotify;

import java.util.Iterator;

import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMapping;

/**
 * @cdk.module nonotify
 * @cdk.githash
 */
public class NNMapping extends NNChemObject implements IMapping {

	private static final long serialVersionUID = 1211310870986655611L;

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    private IChemObject[] relation;
    
    /**
     * Constructs an unconnected lone pair.
     *
     * @param objectOne The first IChemObject of the mapping
     * @param objectTwo The second IChemObject of the mapping
     */
    public NNMapping(IChemObject objectOne, IChemObject objectTwo) {
        relation = new IChemObject[2];
        relation[0] = objectOne;
        relation[1] = objectTwo;
    }

    /**
     * Returns an Iterable to the two IChemObjects.
     * Iterable.remove() is not implemented.
     *
     * @return An Iterable to two IChemObjects that define the mapping
     */
    public Iterable<IChemObject> relatedChemObjects() {
        return new Iterable<IChemObject>(){
            public Iterator<IChemObject> iterator() {
                return new ChemObjectIterator();
            }
        };
    }
    
    /**
     * The inner Iterator class.
     *
     */
    private class ChemObjectIterator implements Iterator<IChemObject> {

        private int pointer = 0;
        
        public boolean hasNext() {
            return pointer < 2;
        }

        public IChemObject next() {
            return relation[pointer++];
        }

        public void remove() {}
        
    }
    
    /**
     * Retrieves the first or second of the related IChemObjects.
     * 
     * @param   pos  The position of the IChemObject.
     * @return  The IChemObject to retrieve.
     */
    public IChemObject getChemObject(int pos) {
        return relation[pos];
    }
    
    /**
     * Clones this <code>Mapping</code> and the mapped <code>IChemObject</code>s.
     *
     * @return  The cloned object
     */
    public Object clone() throws CloneNotSupportedException {
        NNMapping clone = (NNMapping)super.clone();
        // clone the related IChemObject's
        if (relation != null) {
            clone.relation = new IChemObject[relation.length];
            for (int f = 0; f < relation.length; f++) {
                if (relation[f] != null) {
                    clone.relation[f] = (IChemObject)relation[f].clone();
                }
            }
        }
        return clone;
    }
}


