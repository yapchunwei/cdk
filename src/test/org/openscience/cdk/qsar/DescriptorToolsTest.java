/* Copyright (C) 2012  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.qsar;

import org.junit.Assert;
import org.junit.Test;
import org.openscience.cdk.CDKTestCase;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.qsar.descriptors.atomic.AtomDegreeDescriptor;
import org.openscience.cdk.qsar.descriptors.bond.AtomicNumberDifferenceDescriptor;

/**
 * TestSuite that runs all tests for the {@link DescriptorTools}.
 *
 * @cdk.module test-qsar
 */
public class DescriptorToolsTest extends CDKTestCase {

    @Test
    public void testIsDescriptorFor() {
    	Assert.assertTrue(DescriptorTools.isDescriptorFor(new AtomDegreeDescriptor(), IAtom.class));
    	Assert.assertFalse(DescriptorTools.isDescriptorFor(new AtomDegreeDescriptor(), IBond.class));

    	Assert.assertFalse(DescriptorTools.isDescriptorFor(new AtomicNumberDifferenceDescriptor(), IAtom.class));
    	Assert.assertTrue(DescriptorTools.isDescriptorFor(new AtomicNumberDifferenceDescriptor(), IBond.class));
    }

}

