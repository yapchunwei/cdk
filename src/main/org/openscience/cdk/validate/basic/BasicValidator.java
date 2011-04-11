/* Copyright (C) 2008-2011  Egon Willighagen <egonw@users.sf.net>
 *                    2009  Rajarshi Guha <rajarshi.guha@gmail.com>
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
package org.openscience.cdk.validate.basic;

import java.util.Iterator;

import org.openscience.cdk.config.AtomTypeFactory;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IPseudoAtom;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.tools.ILoggingTool;
import org.openscience.cdk.tools.LoggingToolFactory;
import org.openscience.cdk.tools.manipulator.BondManipulator;
import org.openscience.cdk.validate.AbstractValidator;
import org.openscience.cdk.validate.ValidationReport;
import org.openscience.cdk.validate.ValidationTest;
import org.openscience.cdk.validate.ValidationTestType;

/**
 * Validator which tests a number of basic chemical semantics.
 *
 * @author   Egon Willighagen
 * @cdk.githash
 * @cdk.created  2003-08-22
 * @cdk.module   valid
 */ 
public class BasicValidator extends AbstractValidator {

    private final static ValidationTestType EMPTY_MOLECULE =
        new ValidationTestType("Molecule does not contain any atom") {};
    private final static ValidationTestType CONTAINS_PSEUDOATOMS =
        new ValidationTestType(
            "Molecule contains PseudoAtom's. Won't be able to calculate " +
            "some properties, like molecular mass."
    	) {};
    private final static ValidationTestType UNLIKE_CHARGE =
        new ValidationTestType(
            "Atom has an unlikely large positive or negative charge"
        ) {};
    private final static ValidationTestType NEGATIVE_HCOUNT =
        new ValidationTestType(
        	"An Atom cannot have a negative number of hydrogens attached."
        ) {};
    private final static ValidationTestType ATOM_NOT_ELEMENT =
        new ValidationTestType(
            "Non-element atom must be of class PseudoAtom."
        ) {};
    private final static ValidationTestType BOND_BASED_STEREO =
        new ValidationTestType(
            "Defining stereochemistry on bonds is not safe."
        ) {};
    private final static ValidationTestType TOOHIGH_BOND_ORDER =
        new ValidationTestType(
            "Bond order exceeds the maximum for one of its atoms."
        ) {};
    private final static ValidationTestType TOOHIGH_BOND_ORDER_SUM =
        new ValidationTestType(
            "The atom's total bond order is too high."
        ) {};
    private final static ValidationTestType UNKNOWN_MASSNUMBER =
        new ValidationTestType(
            "Isotope with this mass number is not known for this element."
        ) {};
    private final static ValidationTestType ATOM_COUNT_PRESERVED =
    	new ValidationTestType(
            "Atom count mismatch for reaction: the product side has a " +
            "different atom count than the reactant side."
    	) {};
    private final static ValidationTestType CHARGE_PRESERVED =
        new ValidationTestType(
            "Total formal charge is not preserved during the reaction"
        ) {};
	
    	

    private static ILoggingTool logger =
        LoggingToolFactory.createLoggingTool(BasicValidator.class);
    
    public BasicValidator() {
    }
    
    public ValidationReport validateAtom(IAtom subject) {
        ValidationReport report = new ValidationReport();
        report.addReport(validateCharge(subject));
        report.addReport(validateHydrogenCount(subject));
        report.addReport(validatePseudoAtom(subject));
        return report;
    }
    public ValidationReport validateBond(IBond subject) {
        ValidationReport report = new ValidationReport();
        report.addReport(validateStereoChemistry(subject));
        report.addReport(validateMaxBondOrder(subject));
        return report;
    }
    public ValidationReport validateIsotope(IIsotope subject) {
        return validateIsotopeExistence(subject);
    }
    public ValidationReport validateMolecule(IMolecule subject) {
        ValidationReport report = new ValidationReport();
        ValidationTest emptyMolecule = new ValidationTest(EMPTY_MOLECULE, subject);
        
        if (subject.getAtomCount() == 0) {
            report.addError(emptyMolecule);
        } else {
            report.addOK(emptyMolecule);
            ValidationTest massCalcProblem = new ValidationTest(
                CONTAINS_PSEUDOATOMS, subject
            );
            boolean foundMassCalcProblem = false;
            for (int i=0; i<subject.getAtomCount(); i++) {
                if (subject.getAtom(i) instanceof IPseudoAtom) {
                    foundMassCalcProblem = true;
                } else {
                    report.addReport(validateBondOrderSum(subject.getAtom(i), subject));
                }
            }
            if (foundMassCalcProblem) {
                report.addWarning(massCalcProblem);
            } else {
                report.addOK(massCalcProblem);
            }
        }
        return report;
    }
    public ValidationReport validateReaction(IReaction subject) {
        ValidationReport report = new ValidationReport();
        IAtomContainer container1 = subject.getBuilder().newInstance(IAtomContainer.class);
        IMoleculeSet reactants = subject.getReactants();
        for (int i=0; i<reactants.getAtomContainerCount(); i++) {
            container1.add(reactants.getMolecule(i));
        }
        IAtomContainer container2 = subject.getBuilder().newInstance(IAtomContainer.class);
        IMoleculeSet products = subject.getProducts();
        for (int i=0; i<products.getAtomContainerCount(); i++) {
            container2.add(products.getMolecule(i));
        }
        report.addReport(validateAtomCountConservation(subject, container1, container2));
        report.addReport(validateChargeConservation(subject, container1, container2));
        return report;
    }
    
    // the Atom tests
    
    private ValidationReport validateCharge(IAtom atom) {
        ValidationReport report = new ValidationReport();
        ValidationTest tooCharged = new ValidationTest(UNLIKE_CHARGE, atom);
        if (atom.getSymbol().equals("O") || atom.getSymbol().equals("N") ||
            atom.getSymbol().equals("C") || atom.getSymbol().equals("H")) {
            if (atom.getFormalCharge() == 0) {
                report.addOK(tooCharged);
            } else {
                tooCharged.setDetails("Atom " + atom.getSymbol() + " has charge " + atom.getFormalCharge() );
                if (atom.getFormalCharge() < -3) {
                    report.addError(tooCharged);
                } else if (atom.getFormalCharge() < -1) {
                    report.addWarning(tooCharged);
                } else if (atom.getFormalCharge() > 3) {
                    report.addError(tooCharged);
                } else if (atom.getFormalCharge() > 1) {
                    report.addWarning(tooCharged);
                }
            }
        } else {
            if (atom.getFormalCharge() == 0) {
                report.addOK(tooCharged);
            } else {
                tooCharged.setDetails("Atom " + atom.getSymbol() + " has charge " + atom.getFormalCharge() );
                if (atom.getFormalCharge() < -4) {
                    report.addError(tooCharged);
                } else if (atom.getFormalCharge() < -3) {
                    report.addWarning(tooCharged);
                } else if (atom.getFormalCharge() > 4) {
                    report.addError(tooCharged);
                } else if (atom.getFormalCharge() > 3) {
                    report.addWarning(tooCharged);
                }
            }
        }
        return report;
    }

    private ValidationReport validateHydrogenCount(IAtom atom) {
        ValidationReport report = new ValidationReport();
        ValidationTest negativeHydrogenCount = new ValidationTest(NEGATIVE_HCOUNT, atom);
        if (atom.getImplicitHydrogenCount() != null &&
        	atom.getImplicitHydrogenCount() < 0 ) {
            negativeHydrogenCount.setDetails(
                "Atom has " + atom.getImplicitHydrogenCount() + " hydrogens."
            );
            report.addError(negativeHydrogenCount);
        } else {
            report.addOK(negativeHydrogenCount);
        }
        return report;
    }

    private ValidationReport validatePseudoAtom(IAtom atom) {
        ValidationReport report = new ValidationReport();
        ValidationTest isElementOrPseudo = new ValidationTest(ATOM_NOT_ELEMENT, atom);
        if (atom instanceof IPseudoAtom) {
            // that's fine
            report.addOK(isElementOrPseudo);
        } else {
            // check whether atom is really an element
            try {
                IsotopeFactory isotopeFactory = IsotopeFactory.getInstance(atom.getBuilder());
                IElement element = isotopeFactory.getElement(atom.getSymbol());
                if (element == null) {
                    isElementOrPseudo.setDetails(
                        "Element " + atom.getSymbol() + " does not exist."
                    );
                    report.addError(isElementOrPseudo);
                } else {
                    report.addOK(isElementOrPseudo);
                }
            } catch (Exception exception) {
                // well... don't throw an error then.
                isElementOrPseudo.setDetails(exception.toString());
                report.addCDKError(isElementOrPseudo);
            }
        }
        return report;
    }
    
    // the Bond tests
    
    private ValidationReport validateStereoChemistry(IBond bond) {
        ValidationReport report = new ValidationReport();
        ValidationTest bondStereo = new ValidationTest(BOND_BASED_STEREO, bond);
        if (bond.getStereo() != IBond.Stereo.NONE) {
            report.addWarning(bondStereo);
        } else {
            report.addOK(bondStereo);
        }
        return report;
    }
    
    private ValidationReport validateMaxBondOrder(IBond bond) {
        ValidationReport report = new ValidationReport();
        ValidationTest maxBO = new ValidationTest(TOOHIGH_BOND_ORDER, bond);
        try {
            AtomTypeFactory structgenATF = AtomTypeFactory.getInstance(
                "org/openscience/cdk/dict/data/cdk-atom-types.owl", 
                bond.getBuilder()
            );
            for (int i=0; i<bond.getAtomCount(); i++) {
                IAtom atom = bond.getAtom(i);
                if (atom instanceof IPseudoAtom) {
                    // ok, all is fine; we don't know the properties of pseudo atoms
                    break;
                }
                IAtomType[] atomTypes = structgenATF.getAtomTypes(atom.getSymbol());
                IAtomType failedOn = null;
                boolean foundMatchingAtomType = false;
                for (int j=0; j<atomTypes.length; j++) {
                    if (!BondManipulator.isHigherOrder(bond.getOrder(), atomTypes[j].getMaxBondOrder())) {
                        foundMatchingAtomType = true;
                    } else {
                        failedOn = atomTypes[j];
                    }
                }
                if (foundMatchingAtomType) {
                    report.addOK(maxBO);
                } else {
                    if (failedOn != null) {
                        maxBO.setDetails(
                            "Bond order exceeds the one allowed for atom " +
                            atom.getSymbol() + " for which the maximum bond order is " +
                            failedOn.getMaxBondOrder()
                        );
                    }
                    report.addError(maxBO);
                }
            }
        } catch (Exception exception) {
            logger.error("Error while performing atom bos validation");
            logger.debug(exception);
            maxBO.setDetails("Error while performing atom bos validation: " +
              exception.toString());
            report.addCDKError(maxBO);
        }
        return report;
    }
    
    // the Isotope tests
    
    public ValidationReport validateIsotopeExistence(IIsotope isotope) {
        ValidationReport report = new ValidationReport();
        ValidationTest isotopeExists = new ValidationTest(UNKNOWN_MASSNUMBER, isotope);
        try {
            IsotopeFactory isotopeFac = IsotopeFactory.getInstance(isotope.getBuilder());
            IIsotope[] isotopes = isotopeFac.getIsotopes(isotope.getSymbol());
            if (isotope.getMassNumber() != 0) {
                boolean foundKnownIsotope = false;
                for (int i=0; i<isotopes.length; i++) {
                    if (isotopes[i].getMassNumber() == isotope.getMassNumber()) {
                        foundKnownIsotope = true;
                    }
                }
                if (!foundKnownIsotope) {
                    report.addError(isotopeExists);
                } else {
                    report.addOK(isotopeExists);
                }
            } else {
                // isotopic number is not set
                report.addOK(isotopeExists);
            }
        } catch (Exception exception) {
            // too bad...
        }
        return report;
    }
    
    // the Molecule tests

    private ValidationReport validateBondOrderSum(IAtom atom, IMolecule molecule) {
        ValidationReport report = new ValidationReport();
        ValidationTest checkBondSum = new ValidationTest(TOOHIGH_BOND_ORDER_SUM, atom);
        try {
            AtomTypeFactory structgenATF = AtomTypeFactory.getInstance(
                "org/openscience/cdk/dict/data/cdk-atom-types.owl", 
                atom.getBuilder()
            );
            int bos = (int)molecule.getBondOrderSum(atom);
            org.openscience.cdk.interfaces.IAtomType[] atomTypes = structgenATF.getAtomTypes(atom.getSymbol());
            if (atomTypes.length == 0) {
                checkBondSum.setDetails(
                    "Cannot validate bond order sum for atom not in valency atom type list: " +
                    atom.getSymbol()
                );
                report.addWarning(checkBondSum);
            } else {
                IAtomType failedOn = null;
                boolean foundMatchingAtomType = false;
                for (int j=0; j<atomTypes.length; j++) {
                    IAtomType type = atomTypes[j];
                    if (atom.getFormalCharge() == type.getFormalCharge()) {
                        foundMatchingAtomType = true;
                        if (bos == type.getBondOrderSum()) {
                            // skip this atom type
                        } else {
                            failedOn = atomTypes[j];
                        }
                    }
                }
                if (foundMatchingAtomType) {
                    report.addOK(checkBondSum);
                } else {
                    if (failedOn != null) {
                        checkBondSum.setDetails(
                            "Bond order exceeds the one allowed for atom " +
                            atom.getSymbol() + " for which the total bond order is " +
                            failedOn.getBondOrderSum()
                        );
                    }
                    report.addError(checkBondSum);
                }
            }
        } catch (Exception exception) {
            logger.error("Error while performing atom bos validation: ", exception.getMessage());
            logger.debug(exception);
        }
        return report;
    }

    private ValidationReport validateAtomCountConservation(IReaction reaction,
                                                        IAtomContainer reactants,
                                                        IAtomContainer products) {
        ValidationReport report = new ValidationReport();
        ValidationTest atomCount = new ValidationTest(ATOM_COUNT_PRESERVED, reaction);
        if (reactants.getAtomCount() != products.getAtomCount()) {
            report.addError(atomCount);
        } else {
            report.addOK(atomCount);
        }
        return report;
    }

    private ValidationReport validateChargeConservation(IReaction reaction,
                                                     IAtomContainer reactants,
                                                     IAtomContainer products) {
        ValidationReport report = new ValidationReport();
        ValidationTest chargeConservation = new ValidationTest(CHARGE_PRESERVED, reaction);
        Iterator<IAtom> atoms1 = reactants.atoms().iterator();
        int totalCharge1 = 0;
        while (atoms1.hasNext()) {
            totalCharge1 =+ ((IAtom)atoms1.next()).getFormalCharge();
        }
        Iterator<IAtom> atoms2 = products.atoms().iterator();
        int totalCharge2 = 0;
        while (atoms2.hasNext()) {
            totalCharge2 =+ ((IAtom)atoms2.next()).getFormalCharge();
        }
        if (totalCharge1 != totalCharge2) {
            report.addError(chargeConservation);
        } else {
            report.addOK(chargeConservation);
        }
        return report;
    }
    
}
