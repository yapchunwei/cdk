/* Copyright (C) 2003-2007  The Chemistry Development Kit (CDK) project
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
package org.openscience.cdk.validate;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
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
 * Abstract validator that does nothing but provide all the methods that the
 * ValidatorInterface requires.
 *
 * @cdk.module valid
 * @cdk.githash
 *
 * @author   Egon Willighagen
 * @cdk.created  2004-03-27
 */
@TestClass("org.openscience.cdk.validate.AbstractValidatorTest")
public abstract class AbstractValidator implements IValidator {

    @TestMethod("testAbstractConstructor")
    public AbstractValidator() {}

    @TestMethod("testValidateChemObject")
    public ValidationReport validateChemObject(IChemObject subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateAtom")
    public ValidationReport validateAtom(IAtom subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateAtomContainer")
    public ValidationReport validateAtomContainer(IAtomContainer subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateAtomType")
    public ValidationReport validateAtomType(IAtomType subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateBond")
    public ValidationReport validateBond(IBond subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateChemFile")
    public ValidationReport validateChemFile(IChemFile subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateChemModel")
    public ValidationReport validateChemModel(IChemModel subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateChemSequence")
    public ValidationReport validateChemSequence(IChemSequence subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateCrystal")
    public ValidationReport validateCrystal(ICrystal subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateElectronContainer")
    public ValidationReport validateElectronContainer(IElectronContainer subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateElement")
    public ValidationReport validateElement(IElement subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateIsotope")
    public ValidationReport validateIsotope(IIsotope subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateMolecule")
    public ValidationReport validateMolecule(IMolecule subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateReaction")
    public ValidationReport validateReaction(IReaction subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateMoleculeSet")
    public ValidationReport validateMoleculeSet(IMoleculeSet subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }

    @TestMethod("testValidateReactionSet")
    public ValidationReport validateReactionSet(IReactionSet subject) {
        ValidationReport report = new ValidationReport();
        return report;
    }
    
}
