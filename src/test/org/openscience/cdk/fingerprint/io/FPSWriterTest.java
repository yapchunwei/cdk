/* Copyright (C) 2011  Egon Willighagen <egonw@users.sf.net>
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All I ask is that proper credit is given for my work, which includes
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
package org.openscience.cdk.fingerprint.io;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

import org.junit.Assert;
import org.junit.Test;
import org.openscience.cdk.CDK;
import org.openscience.cdk.CDKTestCase;
import org.openscience.cdk.fingerprint.Fingerprinter;

/**
 * @cdk.module test-fingerprint
 */
public class FPSWriterTest extends CDKTestCase {

    @Test
    public void testFPSFormatCheck() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        new FPSWriter(output, new Fingerprinter());
        String fpsStr = new String(output.toByteArray());
        Assert.assertTrue(fpsStr.contains("#FPS1")); // format check
    }

    @Test
    public void testFPSSoftwareCheck() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        new FPSWriter(output, new Fingerprinter());
        String fpsStr = new String(output.toByteArray());
        Assert.assertTrue(fpsStr.contains("#software=CDK")); // format check
    }

    @Test
    public void testFPSCDKVersionCheck() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        new FPSWriter(output, new Fingerprinter());
        String fpsStr = new String(output.toByteArray());
        Assert.assertTrue(fpsStr.contains(CDK.getVersion())); // format check
        Assert.assertFalse(fpsStr.contains("ERROR"));
    }

    @Test
    public void testWrite() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FPSWriter writer = new FPSWriter(output, new Fingerprinter());
        BitSet bitset = new BitSet();
        bitset.set(43);
        writer.write(bitset);
        String fpsStr = new String(output.toByteArray());
        Assert.assertTrue(fpsStr.contains(CDK.getVersion())); // format check
        Assert.assertTrue(fpsStr.contains("000000000008000000000000000000000000000000"));
    }

}

