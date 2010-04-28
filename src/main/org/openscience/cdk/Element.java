/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 1997-2007  Christoph Steinbeck <steinbeck@users.sf.net>
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
package org.openscience.cdk;

import java.io.Serializable;
import java.util.Map;

import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.nonotify.NNElement;

/**
 * Implements the idea of an element in the periodic table.
 * 
 * <p>Use the IsotopeFactory to get a ready-to-use elements
 * by symbol or atomic number:
 * <pre>
 *   IsotopeFactory if = IsotopeFactory.getInstance(new Element().getNewBuilder());
 *   Element e1 = if.getElement("C");
 *   Element e2 = if.getElement(12);
 * </pre>
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword element
 *
 * @see org.openscience.cdk.config.IsotopeFactory
 */
public class Element extends NNElement implements Serializable, IElement,
    IChemObjectChangeNotifier, Cloneable
{

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 3062529834691231436L;

    /**
     * Constructs an empty Element.
     */
    public Element() {
        super();
    }

    /**
     * Constructs an empty by copying the symbol, atomic number,
     * flags, and identifier from the given IElement. It does
     * not copy the listeners and properties.
     * 
     * @param element IElement to copy information from
     */
    public Element(IElement element) {
    	super(element);
    }
    
    /**
     * Constructs an Element with a given 
     * element symbol.
     *
     * @param   symbol The element symbol that this element should have.  
     */
    public Element(String symbol) {
        super(symbol);
    }

    /**
     * Constructs an Element with a given element symbol, 
     * atomic number and atomic mass.
     *
     * @param   symbol  The element symbol of this element.
     * @param   atomicNumber  The atomicNumber of this element.
     */
    public Element(String symbol, int atomicNumber) {
        super(symbol, atomicNumber);
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
        Element clone = (Element)super.clone();
        // delete all listeners
        clone.notifier = new ChemObjectNotifier();
        return clone;
    }

}
