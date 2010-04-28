/* Copyright (C) 1997-2007  Christoph Steinbeck <steinbeck@users.sf.net>
 *                    2010  Egon Willighagen <egonw@users.sf.net>
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
 */
package org.openscience.cdk;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.event.ChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;

/**
 * Helper class for {@link IChemObjectChangeNotifier} implementations. It provides
 * functionality to keep track of {@link IChemObjectListener}s.
 *
 * @cdk.githash
 * @cdk.module    data
 */
public class ChemObjectNotifier implements IChemObjectChangeNotifier {

    private IChemObject chemObject;
    
    public ChemObjectNotifier(IChemObject chemObject) {
        this.chemObject = chemObject;
    }

    /** {@inheritDoc} */
	private List<IChemObjectListener> chemObjectListeners;

    /** {@inheritDoc} */
	private List<IChemObjectListener> lazyChemObjectListeners() {
		if (chemObjectListeners == null) {
			chemObjectListeners = new ArrayList<IChemObjectListener>();
		}
		return chemObjectListeners;
	}

    /** {@inheritDoc} */
	public void addListener(IChemObjectListener col) {
		List<IChemObjectListener> listeners = lazyChemObjectListeners();

		if (!listeners.contains(col)) {
			listeners.add(col);
		}
	}

    /** {@inheritDoc} */
	public int getListenerCount() {
		if (chemObjectListeners == null) {
			return 0;
		}
		return lazyChemObjectListeners().size();
	}

    /** {@inheritDoc} */
	public void removeListener(IChemObjectListener col) {
        if (chemObjectListeners == null) {
			return;
		}
        
        List<IChemObjectListener> listeners = lazyChemObjectListeners();
		if (listeners.contains(col)) {
			listeners.remove(col);
		}
	}

    /** {@inheritDoc} */
	public void notifyChanged() {
        if (getListenerCount() > 0) {
            List<IChemObjectListener> listeners = lazyChemObjectListeners();
            for (IChemObjectListener listener : listeners) {
                listener.stateChanged(
                    new ChemObjectChangeEvent(this.chemObject)
                );
            }
        }
	}

    /** {@inheritDoc} */
	public void notifyChanged(IChemObjectChangeEvent evt) {
        if (getListenerCount() > 0) {
            List<IChemObjectListener> listeners = lazyChemObjectListeners();
            for (IChemObjectListener listener : listeners) {
                listener.stateChanged(evt);
            }
        }
	}

}


