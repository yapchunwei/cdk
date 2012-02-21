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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;

/**
 * A class with helper methods to deal with {@link IDescriptor} implementations.
 * 
 * @cdk.module qsar
 * @cdk.githash
 */
@TestClass(value="org.openscience.cdk.qsar.DescriptorToolsTest")
public final class DescriptorTools {

	/** Determines if the descriptor is for the given class.
     * 
     * @param  descriptor {@link IMoleculePartDescriptor} to be tested
     * @param  forClass   {@link Class} for which the descriptor should be written
     * @return            true, if the descriptor is suitable for the given class
     */
	@TestMethod("testIsDescriptorFor")
    public static boolean isDescriptorFor(IMoleculePartDescriptor<?> descriptor, Class<?> forClass) {
    	Type[] interfaces = descriptor.getClass().getGenericInterfaces();
    	Type interfaze = interfaces[0];
    	ParameterizedType parameterizedType = (ParameterizedType)interfaze;
    	Type[] paramTypes = parameterizedType.getActualTypeArguments();
    	Type parameter = paramTypes[0];
    	return parameter.toString().equals("interface " + forClass.getName());
	}

}

