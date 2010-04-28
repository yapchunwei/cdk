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
import org.openscience.cdk.interfaces.IElectronContainer;

/**
 * @cdk.module nonotify
 * @cdk.githash
 */
public class NNElectronContainer extends NNChemObject implements IElectronContainer {

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide/serialization/spec/version.doc.html>details</a>.
     */
	private static final long serialVersionUID = -8552693424697947353L;

	/** Number of electrons in the ElectronContainer. */
    protected Integer electronCount;


	public NNElectronContainer() {
	    electronCount = 0;
    }

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    /**
     * Returns the number of electrons in this electron container.
     *
     * @return The number of electrons in this electron container.
     *
     * @see     #setElectronCount
     */
    public Integer getElectronCount() {
        return this.electronCount;
    }

    /**
     * Sets the number of electrons in this electron container.
     *
     * @param   electronCount The number of electrons in this electron container.
     *
     * @see     #getElectronCount
     */
    public void setElectronCount(Integer electronCount)
    {
        this.electronCount = electronCount;
    }

}


