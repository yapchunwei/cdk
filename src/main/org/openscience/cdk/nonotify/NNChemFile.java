/*  Copyright (C) 2006-2007  Egon Willighagen <egonw@users.sf.net>
 *                1997-2007  Christoph Steinbeck <steinbeck@users.sf.net>
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.nonotify;

import java.util.Iterator;

import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemSequence;

/**
 * @cdk.module nonotify
 * @cdk.githash
 */
public class NNChemFile extends NNChemObject implements IChemFile {
	
	private static final long serialVersionUID = 8514363803531977947L;

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    /**
     *  Array of ChemSquences.
     */
    protected IChemSequence[] chemSequences;

    /**
     *  Number of ChemSequences contained by this container.
     */
    protected int chemSequenceCount;

    /**
     *  Amount by which the chemsequence array grows when elements are added and
     *  the array is not large enough for that.
     */
    protected int growArraySize = 4;


    /**
     *  Constructs an empty ChemFile.
     */
    public NNChemFile()
    {
        chemSequenceCount = 0;
        chemSequences = new IChemSequence[growArraySize];
    }

    /**
     *  Adds a ChemSequence to this container.
     *
     *@param  chemSequence  The chemSequence to be added to this container
     *@see                  #chemSequences
     */
    public void addChemSequence(IChemSequence chemSequence)
    {
        if (chemSequenceCount + 1 >= chemSequences.length)
        {
            growChemSequenceArray();
        }
        chemSequences[chemSequenceCount] = chemSequence;
        chemSequenceCount++;
    }

    /**
     *  Removes a ChemSequence from this container.
     *
     * @param  pos  The position from which to remove
     * @see   #chemSequences
     * @see #addChemSequence(org.openscience.cdk.interfaces.IChemSequence)
     */
    public void removeChemSequence(int pos)
    {
        for (int i = pos; i < chemSequenceCount - 1; i++) {
            chemSequences[i] = chemSequences[i + 1];
        }
        chemSequences[chemSequenceCount - 1] = null;
        chemSequenceCount--;
    }

    /**
     *  Returns the Iterable to ChemSequences of this container.
     *
     *@return    The Iterable to ChemSequences of this container
     *@see       #addChemSequence
     */
    public Iterable<IChemSequence> chemSequences()
    {
        return new Iterable<IChemSequence>(){
            public Iterator<IChemSequence> iterator() {
                return new ChemSequenceIterator();
            }
        };
    }

    /**
     * The inner Iterator class.
     *
     */
    private class ChemSequenceIterator implements Iterator<IChemSequence> {

        private int pointer = 0;
        
        public boolean hasNext() {
            return pointer < chemSequenceCount;
        }

        public IChemSequence next() {
            return chemSequences[pointer++];
        }

        public void remove() {
            removeChemSequence(--pointer);
        }
        
    }
    
    /**
     *  Returns the ChemSequence at position <code>number</code> in the container.
     *
     *@param  number  The position of the ChemSequence to be returned.
     *@return         The ChemSequence at position <code>number</code>.
     *@see            #addChemSequence
     */
    public IChemSequence getChemSequence(int number)
    {
        return chemSequences[number];
    }


    /**
     *  Grows the ChemSequence array by a given size.
     *
     *@see    #growArraySize
     */
    protected void growChemSequenceArray()
    {
        growArraySize = chemSequences.length;
        IChemSequence[] newchemSequences = new IChemSequence[chemSequences.length + growArraySize];
        System.arraycopy(chemSequences, 0, newchemSequences, 0, chemSequences.length);
        chemSequences = newchemSequences;
    }


    /**
     *  Returns the number of ChemSequences in this Container.
     *
     *@return    The number of ChemSequences in this Container
     */
    public int getChemSequenceCount()
    {
        return this.chemSequenceCount;
    }


    /**
     * Returns a String representation of this class. It implements
         * RFC #9.
     *
     *@return    String representation of the Object
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ChemFile(#S=");
        buffer.append(chemSequenceCount);
        if (chemSequenceCount > 0) {
            for (IChemSequence iChemSequence : chemSequences()) {
                buffer.append(", ");
                buffer.append(iChemSequence.toString());
            }
        }
        buffer.append(')');
        return buffer.toString();
    }


    /**
     *  Allows for getting an clone of this object.
     *
     *@return    a clone of this object
     */
    public Object clone() throws CloneNotSupportedException
    {
        NNChemFile clone = (NNChemFile) super.clone();
        // clone the chemModels
        clone.chemSequenceCount = getChemSequenceCount();
        clone.chemSequences = new IChemSequence[clone.chemSequenceCount];
        for (int f = 0; f < clone.chemSequenceCount; f++)
        {
            clone.chemSequences[f] = (IChemSequence)(chemSequences[f]).clone();
        }
        return clone;
    }
}

