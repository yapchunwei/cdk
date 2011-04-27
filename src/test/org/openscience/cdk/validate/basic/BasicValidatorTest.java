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
package org.openscience.cdk.validate.basic;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.validate.AbstractValidatorTest;
import org.openscience.cdk.validate.IValidator;

/**
 * @cdk.module test-valid
 */
public class BasicValidatorTest extends AbstractValidatorTest {

    @BeforeClass
    public static void setup() {
        setValidator(new BasicValidator());
    }

    @Test
    public void testConstructor() {
        IValidator validator = new BasicValidator();
        Assert.assertNotNull(validator);
    }
    
}

