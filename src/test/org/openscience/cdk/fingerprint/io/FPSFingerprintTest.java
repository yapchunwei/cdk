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

import java.util.BitSet;

import org.junit.Assert;
import org.junit.Test;
import org.openscience.cdk.CDKTestCase;

/**
 * Test cases from <a href="http://code.google.com/p/chem-fingerprints/wiki/BitCheck">BitCheck</a>.
 *
 * @cdk.module test-fingerprint
 */
public class FPSFingerprintTest extends CDKTestCase {

    @Test
    public void testConstructorSet() {
        BitSet set = new BitSet(166);
        set.set(5);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertFalse(fps.isSet(1));
        Assert.assertFalse(fps.isSet(2));
        Assert.assertFalse(fps.isSet(3));
        Assert.assertFalse(fps.isSet(4));
        Assert.assertTrue(fps.isSet(5));
        Assert.assertFalse(fps.isSet(6));
        Assert.assertFalse(fps.isSet(7));
        Assert.assertFalse(fps.isSet(8));
    }

    @Test
    public void testSet() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertFalse(fps.isSet(5));
        fps.set(5);
        Assert.assertTrue(fps.isSet(5));
    }
    
    @Test
    public void testSetWithZero() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertFalse(fps.isSet(0));
        Assert.assertFalse(fps.isSet(5));
        fps.set(0);
        fps.set(5);
        Assert.assertTrue(fps.isSet(0));
        Assert.assertTrue(fps.isSet(5));
    }
    
    @Test
    public void testSetWithSeven() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertFalse(fps.isSet(0));
        Assert.assertFalse(fps.isSet(7));
        fps.set(0);
        fps.set(7);
        Assert.assertTrue(fps.isSet(0));
        Assert.assertTrue(fps.isSet(7));
    }
    
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testSetOutOfRange() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        fps.set(-1);
    }
    
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testSetOutOfRange2() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        fps.set(166);
    }
    
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testSetOutOfRangeUnset() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        fps.unset(-1);
    }
    
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testSetOutOfRange2IsSet() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        fps.isSet(166);
    }
    
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testSetOutOfRangeIsSet() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        fps.isSet(-1);
    }
    
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testSetOutOfRange2Unset() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        fps.unset(166);
    }
    
    @Test
    public void testUnSet() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertFalse(fps.isSet(5));
        fps.set(5);
        Assert.assertTrue(fps.isSet(5));
        fps.unset(5);
        Assert.assertFalse(fps.isSet(5));
    }
    
    @Test
    public void testSet2() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertFalse(fps.isSet(5));
        fps.set(1);
        fps.set(5);
        Assert.assertTrue(fps.isSet(1));
        Assert.assertTrue(fps.isSet(5));
        fps.set(5);
        Assert.assertTrue(fps.isSet(5));
    }
    
    @Test
    public void testSetTwoByte() {
        BitSet set = new BitSet(166);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertFalse(fps.isSet(5));
        fps.set(1);
        fps.set(12);
        Assert.assertTrue(fps.isSet(1));
        Assert.assertTrue(fps.isSet(12));
    }
    
    // tests from the BitCheck page at http://code.google.com/p/chem-fingerprints/wiki/BitCheck
    
    @Test
    public void testBitCheckFake0() {
        BitSet set = new BitSet(166);
        set.set(0);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertEquals("010000000000000000000000000000000000000000", fps.toString());
    }

    @Test
    public void testBitCheckFake43() {
        BitSet set = new BitSet(166);
        set.set(43);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertEquals("000000000008000000000000000000000000000000", fps.toString());
    }

    @Test
    public void testBitCheckSmilesOn1cncccc1() {
        BitSet set = new BitSet(166);
        int[] bits = {
            12,18,23,44,68,70,74,76,77,93,94,98,101,118,119,120,121,123,126,
            136,138,141,142,147,157,160,163,164
        };
        for (int i=0; i<bits.length; i++) set.set(bits[i]);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertEquals("0010840000100000503400602400c04b0065082019", fps.toString());
    }

    @Test
    public void testBitCheckSmilesO1CCCCC1() {
        BitSet set = new BitSet(166);
        int[] bits = {
            56,85,97,108,117,127,128,131,136,137,146,152,156,162,163,164
        };
        for (int i=0; i<bits.length; i++) set.set(bits[i]);
        FPSFingerprint fps = new FPSFingerprint(set, 166);
        Assert.assertEquals("00000000000000010000200002102080090304111c", fps.toString());
    }
}

