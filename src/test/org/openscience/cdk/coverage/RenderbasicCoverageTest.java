/* Copyright (C) 2008-2009  Egon Willighagen <egonw@users.sf.net>
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.coverage;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @cdk.module test-renderbasic
 */
public class RenderbasicCoverageTest extends CoverageAnnotationTest {

    private final static String CLASS_LIST = "renderbasic.javafiles";

    @BeforeClass public static void setUp() throws Exception {
    	// defined by interface with generics, but never implemented
    	List<String> ignored = new ArrayList<String>();
    	// in IGenerator<T extends IChemObject>
    	ignored.add("generate(IChemObject,RendererModel)");
    	// in IRenderer<T extends IChemObject>
    	ignored.add("paint(IChemObject,IDrawVisitor,Rectangle2D,boolean)");
    	ignored.add("paint(IChemObject,IDrawVisitor)");
    	ignored.add("setScale(IChemObject)");
    	ignored.add("setup(IChemObject,Rectangle)");
    	ignored.add("calculateDiagramBounds(IChemObject)");
    	// in AbstractRenderer<T extends IChemObject>
    	ignored.add("generateDiagram(IChemObject)");

        loadClassList(
        	CLASS_LIST, RenderbasicCoverageTest.class.getClassLoader(), ignored
        );
    }

    @Test public void testCoverage() {
        Assert.assertTrue(super.runCoverageTest());
    }

}