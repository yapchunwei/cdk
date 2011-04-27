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

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.interfaces.IChemObject;

/**
 * Error type during validation of a {@link IChemObject}.
 *
 * @author       Egon Willighagen
 * @cdk.created  2011-04-11
 *
 * @cdk.githash
 * @cdk.module   valid
 */
@TestClass("org.openscience.cdk.validate.AbstractValidationTestTypeTest")
public abstract class AbstractValidationTestType implements IValidationTestType {
    
    /** String representation of the found error. */
    private String error;

    @TestMethod("testConstructor")
    public AbstractValidationTestType(String error) {
        this.error = error;
    }

    /* (non-Javadoc)
     * @see org.openscience.cdk.validate.IValidationTestType#getError()
     */
    @Override
    @TestMethod("testGetError")
    public String getError() {
        return this.error;
    }
}
