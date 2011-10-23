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

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.TimeZone;

import org.openscience.cdk.CDK;
import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.fingerprint.IFingerprinter;

/**
 * Writes one or more CDK fingerprints in the FSP format {@cdk.cite FPS}.
 * 
 * @cdk.module fingerprint
 */
@TestClass("org.openscience.cdk.fingerprint.io.FPSWriterTest")
public class FPSWriter {

    /** OutoutStream to write to. */
    private PrintStream output;
    /** {@link IFingerprinter} type */
    private IFingerprinter fingerprinter;
    /** Counter which is used as identifier. */
    private int counter;

    /**
     * Creates a new FPS writer for storing fingerprint of the given type.
     *
     * @param output {@link OutputStream} to be written too.
     * @param fpType {@link IFingerprinter} type of the fingerprints to be written
     */
    @TestMethod("testFPSFormatCheck")
    public FPSWriter(OutputStream output, IFingerprinter fpType) {
        if (output instanceof PrintStream) {
            this.output = (PrintStream)output;
        } else {
            this.output = new PrintStream(output);
        }
        this.fingerprinter = fpType;

        // write the header
        this.output.println("#FPS1");
        this.output.println("#num_bits=" + fpType.getSize());
        this.output.println("#software=CDK/" + CDK.getVersion());
        this.output.println("#type=" + fpType.getClass().getSimpleName());
        // date-time format: 2010-01-27T02:22:26
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.output.println("#date=" + formatter.format(new Date()));
        this.output.flush();
    }

    /**
     * Writes a calculated fingerprint BitSet to the file.
     *
     * @param fingerprint {@link BitSet} of the fingerprint to serialize.
     */
    @TestMethod("testWrite")
    public void write(BitSet fingerprint) {
        FPSFingerprint fpsFingerprint = new FPSFingerprint(
            fingerprint, this.fingerprinter.getSize()
        );
        this.output.print(fpsFingerprint.toString());
        this.output.print('\t');
        this.output.println(++counter);
        this.output.flush();
    }
}
