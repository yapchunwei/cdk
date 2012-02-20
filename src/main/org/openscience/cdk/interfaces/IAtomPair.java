/* Copyright (C) 2006-2012  Egon Willighagen <egonw@users.sf.net>
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
 * Implements the concept of an atom pair. Unlike a two-atom bond, an atom
 * pair does not have to be connected.
 *
 * @cdk.module  interfaces
 * @cdk.githash
 * @cdk.keyword atom
 */
public interface IAtomPair extends IElectronContainer {

	/**
	 * Returns the {@link Iterable} to atoms making up this pair.
	 *
	 * @return    An {@link Iterable} to atoms participating in this pair
	 * @see       #setAtoms
	 */
	public Iterable<IAtom> atoms();

	/**
	 * Sets the array of atoms making up this pair.
	 *
	 * @param  atoms  An array of atoms that forms this pair
	 * @see           #atoms
	 */
	public void setAtoms(IAtom[] atoms);

	/**
	 * Returns an {@link IAtom} from this pair.
	 *
	 * @param  position  The position in this pair where the atom is
	 * @return           The atom at the specified position
	 * @see              #setAtom
	 */
	public IAtom getAtom(int position);

	/**
	 * Returns the atom connected to the given atom.
	 *
	 * @param  atom  The atom the pair partner is searched of
	 * @return       the connected atom or null if the given atom is not part of the pair
	 */
	public IAtom getConnectedAtom(IAtom atom);

    /**
	 * Returns true if the given atom participates in this pair.
	 *
	 * @param  atom  The atom to be tested if it participates in this pair
	 * @return       true if the atom participates in this pair
	 */
	public boolean contains(IAtom atom);

	/**
	 * Sets an {@link IAtom} in this pair.
	 *
	 * @param  atom      The atom to be set
	 * @param  position  The position in this pair where the atom is to be inserted
	 * @see              #getAtom
	 */
	public void setAtom(IAtom atom, int position);

}

