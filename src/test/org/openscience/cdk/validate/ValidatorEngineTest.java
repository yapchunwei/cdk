/* Copyright (C) 2011  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.validate;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.Atom;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.AtomType;
import org.openscience.cdk.Bond;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.ChemModel;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.ChemSequence;
import org.openscience.cdk.Crystal;
import org.openscience.cdk.ElectronContainer;
import org.openscience.cdk.Element;
import org.openscience.cdk.Isotope;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.Reaction;
import org.openscience.cdk.ReactionSet;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemSequence;
import org.openscience.cdk.interfaces.ICrystal;
import org.openscience.cdk.interfaces.IElectronContainer;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.interfaces.IReactionSet;

/**
 * @cdk.module test-valid
 */
public class ValidatorEngineTest {

    private static ValidatorEngine validator;

    @BeforeClass
    public static void setValidator() {
        ValidatorEngineTest.validator = new ValidatorEngine();
    }

    @Test
    public void testConstructor() {
        ValidatorEngine engine = new ValidatorEngine();
        Assert.assertNotNull(engine);
    }

    @Test
    public void testValidateChemObject() {
        ValidationReport report = validator.validateChemObject(new ChemObject());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateAtom() {
        ValidationReport report = validator.validateChemObject(new Atom());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateAtomContainer() {
        ValidationReport report = validator.validateChemObject(new AtomContainer());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateAtomType() {
        ValidationReport report = validator.validateChemObject(new AtomType(new Element("C")));
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateBond() {
        ValidationReport report = validator.validateChemObject(new Bond());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateChemFile() {
        ValidationReport report = validator.validateChemObject(new ChemFile());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateChemModel() {
        ValidationReport report = validator.validateChemObject(new ChemModel());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateChemSequence() {
        ValidationReport report = validator.validateChemObject(new ChemSequence());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateCrystal() {
        ValidationReport report = validator.validateChemObject(new Crystal());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateElectronContainer() {
        ValidationReport report = validator.validateChemObject(new ElectronContainer());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateElement() {
        ValidationReport report = validator.validateChemObject(new Element("C"));
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateIsotope() {
        ValidationReport report = validator.validateChemObject(new Isotope("C"));
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateMolecule() {
        ValidationReport report = validator.validateChemObject(new Molecule());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateReaction() {
        ValidationReport report = validator.validateChemObject(new Reaction());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateMoleculeSet() {
        ValidationReport report = validator.validateChemObject(new MoleculeSet());
        Assert.assertNotNull(report);
    }

    @Test
    public void testValidateReactionSet() {
        ValidationReport report = validator.validateChemObject(new ReactionSet());
        Assert.assertNotNull(report);
    }
	
    @Test
    public void testAddValidator() {
        ValidatorEngine engine = new ValidatorEngine();
        TestValidator validator = new TestValidator();
        engine.addValidator(validator);
        Assert.assertEquals(0, validator.getCount());
        ValidationReport report = engine.validateChemObject(new ReactionSet());
        Assert.assertEquals(1, validator.getCount());
        Assert.assertNotNull(report);
    }

    @Test
    public void testRemoveValidator() {
        ValidatorEngine engine = new ValidatorEngine();
        TestValidator validator = new TestValidator();
        engine.addValidator(validator);
        Assert.assertEquals(0, validator.getCount());
        engine.removeValidator(validator);
        ValidationReport report = engine.validateChemObject(new ReactionSet());
        Assert.assertEquals(0, validator.getCount());
        Assert.assertNotNull(report);
    }

    class TestValidator implements IValidator {
        
        private int count = 0;

        @Override
        public ValidationReport validateReactionSet(IReactionSet subject) {
            return new ValidationReport();
        }
        
        public int getCount() {
            return this.count ;
        }

        @Override
        public ValidationReport validateReaction(IReaction subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateMoleculeSet(IMoleculeSet subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateMolecule(IMolecule subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateIsotope(IIsotope subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateElement(IElement subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateElectronContainer(IElectronContainer subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateCrystal(ICrystal subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateChemSequence(IChemSequence subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateChemObject(IChemObject object) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateChemModel(IChemModel subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateChemFile(IChemFile subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateBond(IBond subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateAtomType(IAtomType subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateAtomContainer(IAtomContainer subject) {
            count++;
            return new ValidationReport();
        }
        
        @Override
        public ValidationReport validateAtom(IAtom subject) {
            count++;
            return new ValidationReport();
        }
    }
}


