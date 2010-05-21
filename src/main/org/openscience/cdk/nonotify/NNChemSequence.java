/* Copyright (C) 2006-2007,2010  Egon Willighagen <egonw@users.sf.net>
 *               1997-2007       Christoph Steinbeck <steinbeck@users.sf.net>
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
package org.openscience.cdk.nonotify;

import java.util.Iterator;

import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemSequence;

/** 
 * A sequence of ChemModels, which can, for example, be used to
 * store the course of a reaction. Each state of the reaction would be
 * stored in one ChemModel.
 *
 * @cdk.module nonotify
 * @cdk.githash
 * @cdk.keyword animation
 * @cdk.keyword reaction
 */
public class NNChemSequence extends NNChemObject implements IChemSequence {

	private static final long serialVersionUID = -7462418329721963017L;

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    /**
     *  Array of ChemModels.
     */
    protected IChemModel[] chemModels;
    
    /**
     *  Number of ChemModels contained by this container.
     */
    protected int chemModelCount;
    
    /**
     *  Amount by which the chemModels array grows when elements are added and
     *  the array is not large enough for that. 
     */
    protected int growArraySize = 4;



    /**
     *  Constructs an empty ChemSequence.
     */
    public NNChemSequence()   
    {
        chemModelCount = 0;
        chemModels = new IChemModel[growArraySize];
    }


    
    /**
     *  Adds an chemModel to this container.
     *
     * @param  chemModel  The chemModel to be added to this container
     *
     * @see            #getChemModel
     */
    public void addChemModel(IChemModel chemModel)
    {
        if (chemModelCount + 1 >= chemModels.length)
        {
            growChemModelArray();
        }
        chemModels[chemModelCount] = chemModel;
        chemModelCount++;
    }

    
    /**
     * Remove a ChemModel from this ChemSequence.
     *
     * @param  pos  The position of the ChemModel to be removed.
     */
    public void removeChemModel(int pos) {
        for (int i = pos; i < chemModelCount - 1; i++) {
            chemModels[i] = chemModels[i + 1];
        }
        chemModels[chemModelCount - 1] = null;
        chemModelCount--;
    }

    /**
     * Returns an Iterable to ChemModels in this container.
     *
     * @return    The Iterable to ChemModels in this container
     * @see       #addChemModel
     */
     public Iterable<IChemModel> chemModels() {
         return new Iterable<IChemModel>(){
             public Iterator<IChemModel> iterator() {
                 return new ChemModelIterator();
             }
         };
     }

     /**
      * The inner Iterator class.
      *
      */
     private class ChemModelIterator implements Iterator<IChemModel> {

         private int pointer = 0;
        
         public boolean hasNext() {
             return pointer < chemModelCount;
         }

         public IChemModel next() {
             return chemModels[pointer++];
         }

         public void remove() {
             removeChemModel(--pointer);
         }
        
     }
     
     
    /**
     *
     * Returns the ChemModel at position <code>number</code> in the
     * container.
     *
     * @param  number  The position of the ChemModel to be returned.
     * @return         The ChemModel at position <code>number</code>.
     *
     * @see            #addChemModel
     */
    public IChemModel getChemModel(int number)
    {
        return chemModels[number];
    }
    
    /**
     *  Grows the chemModel array by a given size.
     *
     * @see    growArraySize
     */
    protected void growChemModelArray()
    {
        IChemModel[] newchemModels = new IChemModel[chemModels.length + growArraySize];
        System.arraycopy(chemModels, 0, newchemModels, 0, chemModels.length);
        chemModels = newchemModels;
    }
    

    /**
     * Returns the number of ChemModels in this Container.
     *
     * @return    The number of ChemModels in this Container
     */
    public int getChemModelCount()
    {
        return this.chemModelCount;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer(32);
        buffer.append("ChemSequence(#M=");
        buffer.append(chemModelCount);
        if (chemModelCount > 0) {
            buffer.append(", ");
            for (int i=0; i<chemModelCount; i++) {
                buffer.append(chemModels[i].toString());
            }
        }
        buffer.append(')');
        return buffer.toString();
    }
    
    public Object clone() throws CloneNotSupportedException {
        NNChemSequence clone = (NNChemSequence)super.clone();
        // clone the chemModels
        clone.chemModelCount = getChemModelCount();
        clone.chemModels = new IChemModel[clone.chemModelCount];
        for (int f = 0; f < clone.chemModelCount; f++) {
            clone.chemModels[f] = (IChemModel)((IChemModel)chemModels[f]).clone();
        }
        return clone;
    }
}
