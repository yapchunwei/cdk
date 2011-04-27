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

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.validate.AbstractValidationTestType;
import org.openscience.cdk.validate.AbstractValidator;
import org.openscience.cdk.validate.IValidationTestType;
import org.openscience.cdk.validate.ValidationReport;
import org.openscience.cdk.validate.ValidationTest;

/**
 * Validator which tests a number of basic chemical semantics.
 *
 * @cdk.githash
 * @cdk.module   valid
 */
@TestClass("org.openscience.cdk.validate.basic.WedgeBondStereoValidatorTest")
public class WedgeBondStereoValidator extends AbstractValidator {

    private final static IValidationTestType BOND_BASED_STEREO =
        new AbstractValidationTestType(
            "Defining stereochemistry on bonds is not safe."
        ) {};

    @TestMethod("testConstructor")
    public WedgeBondStereoValidator() {}

    @TestMethod("testValidateBond")
    public ValidationReport validateBond(IBond subject) {
        ValidationReport report = new ValidationReport();
        report.addReport(validateStereoChemistry(subject));
        return report;
    }

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

}
