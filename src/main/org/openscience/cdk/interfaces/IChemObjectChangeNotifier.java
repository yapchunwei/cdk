/* Copyright (C) 2006-2007,2010  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.interfaces;

/**
 * This interface provides methods for adding listeners and for their notification
 * of events.
 *
 * @cdk.githash
 * @cdk.module    interfaces
 */
public interface IChemObjectChangeNotifier {

	/**
	 * Use this to add yourself to this IChemObject as a listener. In order to do
	 * so, you must implement the ChemObjectListener Interface.
	 *
	 * @param  col  the ChemObjectListener
	 * @see         #removeListener
	 */
	public void addListener(IChemObjectListener col);

	/**
	 * Returns the number of ChemObjectListeners registered with this object.
	 *
	 * @return    the number of registered listeners.
	 */
	public int getListenerCount();

	/**
	 * Use this to remove a ChemObjectListener from the ListenerList of this
	 * IChemObject. It will then not be notified of change in this object anymore.
	 *
	 * @param  col  The ChemObjectListener to be removed
	 * @see         #addListener
	 */
	public void removeListener(IChemObjectListener col);

	/**
	 * This should be triggered by an method that changes the content of an object
	 * to that the registered listeners can react to it.
	 */
	public void notifyChanged();

	/**
	 * This should be triggered by an method that changes the content of an object
	 * to that the registered listeners can react to it. This is a version of
	 * notifyChanged() which allows to propagate a change event while preserving
	 * the original origin.
	 *
	 * @param  evt  A ChemObjectChangeEvent pointing to the source of where
	 *		        the change happened
	 */
	public void notifyChanged(IChemObjectChangeEvent evt);

}


