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

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.interfaces.IChemObject;

/**
 * @cdk.module test-valid
 */
public class ValidationTestTest {

    @Test
    public void testGetChemObject() {
        IChemObject object = new ChemObject();
        ValidationTest test = new ValidationTest(
            new AbstractValidationTestType("Some error") {
            }, object
        );
        Assert.assertNotNull(test.getChemObject());
        Assert.assertEquals(object, test.getChemObject());
    }

    @Test
    public void testGetType() {
        IChemObject object = new ChemObject();
        AbstractValidationTestType type = new AbstractValidationTestType("Some error") {
        }; 
        ValidationTest test = new ValidationTest(
            type, object
        );
        Assert.assertEquals(type, test.getType());
    }

    @Test
    public void testGetError() {
        IChemObject object = new ChemObject();
        ValidationTest test = new ValidationTest(
            new AbstractValidationTestType("Some error") {
            }, object
        );
        Assert.assertEquals("Some error", test.getError());
    }

    @Test
    public void testSetDetails() {
        IChemObject object = new ChemObject();
        ValidationTest test = new ValidationTest(
            new AbstractValidationTestType("Some error") {
            }, object, "This really sucks."
        );
        Assert.assertEquals("This really sucks.", test.getDetails());
        test.setDetails("But this does not.");
        Assert.assertEquals("But this does not.", test.getDetails());
    }

    @Test
    public void testGetDetails() {
        IChemObject object = new ChemObject();
        ValidationTest test = new ValidationTest(
            new AbstractValidationTestType("Some error") {
            }, object, "This really sucks."
        );
        Assert.assertEquals("This really sucks.", test.getDetails());
    }
}


