/* Copyright (C) 2011  Egon Willighagen <egonw@users.sf.net>
 *
 * Contact: cdk-devel@lists.sourceforge.net
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
package org.openscience.cdk.fingerprint.io;

import java.util.BitSet;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;

/**
 * A FPS-style fingerprint. It internally uses an array of bytes.
 * 
 * @cdk.module fingerprint
 */
@TestClass("org.openscience.cdk.fingerprint.io.FPSFingerprintTest")
public class FPSFingerprint {

    private byte[] print;
    private int byteLength;
    private int size;
    
    /**
     * Creates a FPS-style fingerprint for the given input.
     *
     * @param fingerprint {@link BitSet} representation of a fingerprint.
     */
    @TestMethod("testConstructorSet")
    public FPSFingerprint(BitSet fingerprint, int size) {
        this.size = size;
        this.byteLength = this.size/8;
        // use the last byte for whatever bits we have left
        if (this.byteLength*8 < size) byteLength++;

        this.print = new byte[byteLength];
        for (int i=0; i<fingerprint.size(); i++) {
            if (fingerprint.get(i)) set(i);
        }
    }

    /**
     * Sets the bit at the given position.
     *
     * @param position position of the bit, from 0 to size-1.
     */
    @TestMethod("testSet,testSetWithZero,testSetWithSeven,testSetOutOfRange")
    public void set(int position) {
        isInRange(position);
        int bytePos = getBytePosition(position);
        int bitPos = getBitPosition(position);
        print[bytePos] = (byte) (print[bytePos] | (byte)(0x01 << bitPos));
    }

    /**
     * Unsets the bit at the given position.
     *
     * @param position position of the bit, from 0 to size-1.
     */
    @TestMethod("testUnSet,testSetOutOfRangeUnset")
    public void unset(int position) {
        isInRange(position);
        if (isSet(position)) {
            int bytePos = getBytePosition(position);
            int bitPos = getBitPosition(position);
            print[bytePos] = (byte) (print[bytePos] ^ (byte)(0x01 << bitPos));
        }
    }

    /**
     * Returns the hexadecimal FPS bit set representation.
     * 
     * @return The FPS string.
     */
    @TestMethod("testBitCheckFake0,testBitCheckFake43")
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i=0; i<byteLength; i++) {
            String toAppend = Integer.toHexString(0xff & print[i]);
            if (toAppend.length() == 1) buffer.append('0');
            buffer.append(toAppend);
        }
        return buffer.toString();
    }

    private static int getBytePosition(int position) {
        return position / 8;
    }

    private static int getBitPosition(int position) {
        return position % 8;
    }

    /**
     * Returns true if the bit at the given position is set.
     *
     * @param position position of the bit, from 0 to size-1.
     * @return true if the bit is set and false otherwise.
     */
    @TestMethod("testSet") // and many more
    public boolean isSet(int position) {
        isInRange(position);
        int bytePos = getBytePosition(position);
        int bitPos = getBitPosition(position);
        return (print[bytePos] >> bitPos & 0x01) == 1;
    }

    /**
     * Checks is the position is in [0, size>.
     */
    private void isInRange(int position) {
        if (position >= size || position < 0)
            throw new ArrayIndexOutOfBoundsException(
                "Position is outside bit set range"
            );
    }
}
