/* Copyright (C) 2001-2008,2010  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.protein.data;

import org.openscience.cdk.ChemObjectNotifier;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.ILonePair;
import org.openscience.cdk.interfaces.IMonomer;
import org.openscience.cdk.interfaces.IPDBAtom;
import org.openscience.cdk.interfaces.IPDBPolymer;
import org.openscience.cdk.interfaces.IPDBStructure;
import org.openscience.cdk.interfaces.ISingleElectron;
import org.openscience.cdk.interfaces.IStrand;
import org.openscience.cdk.nonotify.NNPDBPolymer;

/**
 * An entry in the PDB database. It is not just a regular protein, but the
 * regular PDB mix of protein or protein complexes, ligands, water molecules
 * and other species.
 *
 * @cdk.module  data
 * @cdk.githash
 *
 * @author      Egon Willighagen
 * @cdk.created 2006-04-19
 * @cdk.keyword polymer
 */
public class PDBPolymer extends NNPDBPolymer
implements Cloneable, IPDBPolymer, IChemObjectChangeNotifier, IChemObjectListener { 

	private static final long serialVersionUID = 4173552834313952358L;

	/**
	 * Constructs a new Polymer to store the {@link IMonomer}s.
	 */	
	public PDBPolymer() {
		super();
	}

    /** {@inheritDoc} */
	public void addStructure(IPDBStructure structure) {
		super.addStructure(structure);
		notifyChanged();
	}

    /** {@inheritDoc} */
	public void addAtom(IPDBAtom oAtom){
		super.addAtom(oAtom);
		notifyChanged();
	}

    /** {@inheritDoc} */
	public void addAtom(IPDBAtom oAtom, IMonomer oMonomer) {
		super.addAtom(oAtom, oMonomer);
		notifyChanged();
	}

    /** {@inheritDoc} */
	public void addAtom(IPDBAtom oAtom, IMonomer oMonomer, IStrand oStrand) {
		super.addAtom(oAtom, oMonomer, oStrand);
		notifyChanged();
	}

    /** {@inheritDoc} */
    public void addAtom(IAtom oAtom, IStrand oStrand) {
        super.addAtom(oAtom, oStrand);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void addAtom(IAtom oAtom, IMonomer oMonomer, IStrand oStrand)    {
        super.addAtom(oAtom, oMonomer, oStrand);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void removeStrand(String name)   {
        super.removeStrand(name);
        notifyChanged();
    }

    public Object clone() throws CloneNotSupportedException {
        PDBPolymer clone = (PDBPolymer)super.clone();
        return clone;
    }

    /** {@inheritDoc} */
    public void addAtom(IAtom oAtom, IMonomer oMonomer) {
        super.addAtom(oAtom, oMonomer);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeMonomer(String name)  {
        super.removeMonomer(name);
        notifyChanged();
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

    /** {@inheritDoc} */
    public void stateChanged(IChemObjectChangeEvent event) {
        notifyChanged(event);
    }

    /** {@inheritDoc} */
    public void setAtoms(IAtom[] atoms) {
        super.setAtoms(atoms);
        for (IAtom atom : atoms) {
            if (atom instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)atom).addListener(this);
        }
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setBonds(IBond[] bonds) {
        super.setBonds(bonds);
        for (IBond bond : bonds) {
            if (bond instanceof IChemObjectChangeNotifier)
                ((IChemObjectChangeNotifier)bond).addListener(this);
        }
    }

    /** {@inheritDoc} */
    public void setAtom(int number, IAtom atom) {
        super.setAtom(number, atom);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void add(IAtomContainer atomContainer) {
        super.add(atomContainer);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addAtom(IAtom atom) {
        super.addAtom(atom);
        if (atom instanceof IChemObjectChangeNotifier)
            ((IChemObjectChangeNotifier)atom).addListener(this);
        notifyChanged();
    }


    /** {@inheritDoc} */
    public void addBond(IBond bond) {
        super.addBond(bond);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void addLonePair(ILonePair lonePair) {
        super.addLonePair(lonePair);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void addSingleElectron(ISingleElectron singleElectron) {
        super.addSingleElectron(singleElectron);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public void removeAtom(int position) {
        super.removeAtom(position);
        notifyChanged();
    }
    
    /** {@inheritDoc} */
    public IBond removeBond(int position) {
        IBond bond = super.removeBond(position);
        notifyChanged();
        return bond;
    }
    
    /** {@inheritDoc} */
    public ILonePair removeLonePair(int position) {
        ILonePair lp = super.removeLonePair(position);
        notifyChanged();
        return lp;
    }
    
    /** {@inheritDoc} */
    public ISingleElectron removeSingleElectron(int position) {
        ISingleElectron se = super.removeSingleElectron(position);
        notifyChanged();
        return se;
    }
    
    /** {@inheritDoc} */
    public void removeAtomAndConnectedElectronContainers(IAtom atom) {
        super.removeAtomAndConnectedElectronContainers(atom);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllElements() {
        super.removeAllElements();
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllElectronContainers() {
        super.removeAllElectronContainers();
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllBonds() {
        super.removeAllBonds();
        notifyChanged();
    }
}
