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
package org.openscience.cdk.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.openscience.cdk.Atom;
import org.openscience.cdk.Bond;
import org.openscience.cdk.LonePair;
import org.openscience.cdk.SingleElectron;
import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.ILonePair;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.ISingleElectron;
import org.openscience.cdk.io.formats.CDKBinaryFormat;
import org.openscience.cdk.io.formats.IResourceFormat;
import org.openscience.cdk.nonotify.NNAtom;
import org.openscience.cdk.nonotify.NNBond;
import org.openscience.cdk.nonotify.NNLonePair;
import org.openscience.cdk.nonotify.NNMolecule;
import org.openscience.cdk.nonotify.NNSingleElectron;
import org.openscience.cdk.tools.ILoggingTool;
import org.openscience.cdk.tools.LoggingToolFactory;

import com.esotericsoftware.kryo.Kryo;

/**
 * Writer for CDK objects that uses a binary format created with Kryo for
 * serialization.
 *
 * @cdk.module io
 * @cdk.githash
 */
@TestClass("org.openscience.cdk.io.CDKBinaryWriterTest")
public class CDKBinaryWriter extends DefaultChemObjectWriter {

    private static ILoggingTool logger =
        LoggingToolFactory.createLoggingTool(CDKBinaryWriter.class);

    /**
     * Constructs a new CrystClustWriter class. Output will be stored in the Writer
     * class given as parameter.
     *
     * @param out Writer to redirect the output to.
     */
    public CDKBinaryWriter(OutputStream output) {
    }
    
    public CDKBinaryWriter() {
    }
    
    @TestMethod("testGetFormat")
    public IResourceFormat getFormat() {
        return CDKBinaryFormat.getInstance();
    }
    
    public void setWriter(Writer out) throws CDKException {
    }

    public void setWriter(OutputStream output) throws CDKException {
    	setWriter(new OutputStreamWriter(output));
    }
    
	@TestMethod("testAccepts")
    public boolean accepts(Class classObject) {
		Class[] interfaces = classObject.getInterfaces();
        for (Class anInterface : interfaces) {
            if (IAtomContainer.class.equals(anInterface)) return true;
            if (IMolecule.class.equals(anInterface)) return true;
        }
        Class superClass = classObject.getSuperclass();
        if (superClass != null) return this.accepts(superClass);
		return false;
	}

    /**
     * Serializes the IChemObject to CrystClust format and redirects it to the output Writer.
     *
     * @param object A Molecule of MoleculeSet object
     */
    public void write(IChemObject object) throws CDKException {
        if (object instanceof IMolecule) {
            Kryo kryo = new Kryo();
            kryo.register(IMolecule.class);
            kryo.register(NNMolecule.class);
            kryo.register(NNAtom.class);
            kryo.register(Atom.class);
            kryo.register(Atom[].class);
            kryo.register(IAtom.class);
            kryo.register(IAtom[].class);
            kryo.register(boolean[].class);
            kryo.register(IAtomType.Hybridization.class);
            kryo.register(NNBond.class);
            kryo.register(Bond.class);
            kryo.register(Bond[].class);
            kryo.register(IBond.class);
            kryo.register(IBond[].class);
            kryo.register(IBond.Order.class);
            kryo.register(IBond.Stereo.class);
            kryo.register(ILonePair.class);
            kryo.register(ILonePair[].class);
            kryo.register(LonePair.class);
            kryo.register(NNLonePair.class);
            kryo.register(ISingleElectron.class);
            kryo.register(ISingleElectron[].class);
            kryo.register(SingleElectron.class);
            kryo.register(NNSingleElectron.class);
            kryo.register(ArrayList.class);
            kryo.register(LinkedHashMap.class);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            kryo.writeClassAndObject(buffer, object);
            buffer.flip();
            try {
                FileOutputStream fout = new FileOutputStream( "/tmp/foo.cdk" );
                FileChannel fc = fout.getChannel();
                fc.write(buffer);
                fout.close();
            } catch (Exception exception) {
                throw new CDKException("Error while writing.", exception);
            }
        }
    }

    /**
     * Flushes the output and closes this object
     */
    @TestMethod("testClose")
    public void close() throws IOException {
    }

}
