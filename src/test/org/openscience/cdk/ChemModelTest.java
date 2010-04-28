/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 1997-2007  The Chemistry Development Kit (CDK) project
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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.interfaces.AbstractChemModelTest;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.ICrystal;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.interfaces.IReactionSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.interfaces.ITestObjectBuilder;

/**
 * Checks the functionality of the ChemModel class.
 *
 * @cdk.module test-data
 *
 * @see org.openscience.cdk.ChemModel
 */
public class ChemModelTest extends AbstractChemModelTest {

    @BeforeClass public static void setUp() {
        setTestObjectBuilder(new ITestObjectBuilder() {
            public IChemObject newTestObject() {
                return new ChemModel();
            }
        });
    }

    @Test public void testChemModel() {
	    IChemModel chemModel = new ChemModel();
	    Assert.assertNotNull(chemModel);
    }

    @Test public void testStateChanged_IChemObjectChangeEvent() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);
        
        chemObject.setMoleculeSet(chemObject.getBuilder().newInstance(IMoleculeSet.class));
        Assert.assertTrue(listener.changed);
        
        listener.reset();
        Assert.assertFalse(listener.changed);
        chemObject.setReactionSet(chemObject.getBuilder().newInstance(IReactionSet.class));
        Assert.assertTrue(listener.changed);
        
        listener.reset();
        Assert.assertFalse(listener.changed);
        chemObject.setCrystal(chemObject.getBuilder().newInstance(ICrystal.class));
        Assert.assertTrue(listener.changed);
        
        listener.reset();
        Assert.assertFalse(listener.changed);
        chemObject.setRingSet(chemObject.getBuilder().newInstance(IRingSet.class));
        Assert.assertTrue(listener.changed);
    }

    @Test public void testStateChanged_EventPropagation_Crystal() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        ICrystal crystal = chemObject.getBuilder().newInstance(ICrystal.class);
        chemObject.setCrystal(crystal);
        Assert.assertTrue(listener.changed);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set should trigger a change event in the IChemModel
        crystal.add(chemObject.getBuilder().newInstance(IMolecule.class));
        Assert.assertTrue(listener.changed);
    }

    @Test public void testStateChanged_EventPropagation_MoleculeSet() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        IMoleculeSet molSet = chemObject.getBuilder().newInstance(IMoleculeSet.class);
        chemObject.setMoleculeSet(molSet);
        Assert.assertTrue(listener.changed);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set should trigger a change event in the IChemModel
        molSet.addAtomContainer(chemObject.getBuilder().newInstance(IMolecule.class));
        Assert.assertTrue(listener.changed);
    }

    @Test public void testStateChanged_EventPropagation_ReactionSet() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        IReactionSet reactionSet = chemObject.getBuilder().newInstance(IReactionSet.class);
        chemObject.setReactionSet(reactionSet);
        Assert.assertTrue(listener.changed);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set should trigger a change event in the IChemModel
        reactionSet.addReaction(chemObject.getBuilder().newInstance(IReaction.class));
        Assert.assertTrue(listener.changed);
    }

    @Test public void testStateChanged_EventPropagation_RingSet() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        IRingSet ringSet = chemObject.getBuilder().newInstance(IRingSet.class);
        chemObject.setRingSet(ringSet);
        Assert.assertTrue(listener.changed);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set should trigger a change event in the IChemModel
        ringSet.addAtomContainer(chemObject.getBuilder().newInstance(IRing.class));
        Assert.assertTrue(listener.changed);
    }

    @Test public void testStateChanged_ButNotAfterRemoval_Crystal() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        ICrystal crystal = chemObject.getBuilder().newInstance(ICrystal.class);
        chemObject.setCrystal(crystal);
        Assert.assertTrue(listener.changed);
        // remove the set from the IChemModel
        chemObject.setCrystal(null);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set must *not* trigger a change event in the IChemModel
        crystal.add(chemObject.getBuilder().newInstance(IMolecule.class));
        Assert.assertFalse(listener.changed);
    }

    @Test public void testStateChanged_ButNotAfterRemoval_MoleculeSet() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        IMoleculeSet molSet = chemObject.getBuilder().newInstance(IMoleculeSet.class);
        chemObject.setMoleculeSet(molSet);
        Assert.assertTrue(listener.changed);
        // remove the set from the IChemModel
        chemObject.setMoleculeSet(null);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set must *not* trigger a change event in the IChemModel
        molSet.addAtomContainer(chemObject.getBuilder().newInstance(IMolecule.class));
        Assert.assertFalse(listener.changed);
    }

    @Test public void testStateChanged_ButNotAfterRemoval_ReactionSet() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        IReactionSet reactionSet = chemObject.getBuilder().newInstance(IReactionSet.class);
        chemObject.setReactionSet(reactionSet);
        Assert.assertTrue(listener.changed);
        // remove the set from the IChemModel
        chemObject.setReactionSet(null);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set must *not* trigger a change event in the IChemModel
        reactionSet.addReaction(chemObject.getBuilder().newInstance(IReaction.class));
        Assert.assertFalse(listener.changed);
    }

    @Test public void testStateChanged_ButNotAfterRemoval_RingSet() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemModel chemObject = (IChemModel)newChemObject();
        Assert.assertTrue(chemObject instanceof IChemObjectChangeNotifier);
        ((IChemObjectChangeNotifier)chemObject).addListener(listener);

        IRingSet ringSet = chemObject.getBuilder().newInstance(IRingSet.class);
        chemObject.setRingSet(ringSet);
        Assert.assertTrue(listener.changed);
        // remove the set from the IChemModel
        chemObject.setRingSet(null);
        // reset the listener
        listener.reset(); Assert.assertFalse(listener.changed);
        // changing the set must *not* trigger a change event in the IChemModel
        ringSet.addAtomContainer(chemObject.getBuilder().newInstance(IRing.class));
        Assert.assertFalse(listener.changed);
    }

    private class ChemObjectListenerImpl implements IChemObjectListener {
        private boolean changed;
        
        private ChemObjectListenerImpl() {
            changed = false;
        }
        
        @Test public void stateChanged(IChemObjectChangeEvent e) {
            changed = true;
        }
        
        @Test public void reset() {
            changed = false;
        }
    }
}
