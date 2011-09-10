/* Copyright (C) 2011  Egon Willighagen <egonw@users.sourceforge.net>
 * 
 * Contact: cdk-devel@slists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
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
package org.openscience.cdk.io.rdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.SimpleChemObjectReaderTest;

/**
 * @cdk.module test-io
 */
public class CDKBinaryReaderTest extends SimpleChemObjectReaderTest {

    @BeforeClass public static void setup() {
        setSimpleChemObjectReader(new CDKBinaryReader(), "/tmp/foo.cdk");
    }

    @Before
    public void setInputStream() throws FileNotFoundException, CDKException {
        chemObjectIO.setReader(new FileInputStream(new File("/tmp/foo.cdk")));
    }

    @Test public void testAccepts() {
    	Assert.assertTrue(chemObjectIO.accepts(Molecule.class));
    }

    @Test
    public void testFile() throws FileNotFoundException, CDKException {
        CDKBinaryReader reader = new CDKBinaryReader(
            new FileInputStream(new File("/tmp/foo.cdk"))
        );
        IMolecule mol = reader.read(new Molecule());
        System.out.println("" + mol);
    }
}
