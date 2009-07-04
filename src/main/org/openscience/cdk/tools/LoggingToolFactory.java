/* Copyright (C) 2009  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;

/**
 * Factory used to instantiate a {@link ILoggingTool}. To get an instance, run:
 * <pre>
 * public class SomeClass {
 *   private static ILoggingTool logger;
 *
 *   static {
 *     logger = LoggingToolFactory.createLoggingTool(SomeClass.class);
 *   }
 * }
 * </pre>
 * 
 * @cdk.module core
 */
@TestClass("org.openscience.cdk.tools.LoggingToolFactoryTest")
public class LoggingToolFactory {

    /** Default logging tool. Currently, the log4j based one. */
    public final static String DEFAULT_LOGGING_TOOL_CLASS =
        "org.openscience.cdk.tools.LoggingTool";
    /** Back-up logging tool. Currently, a tool that outputs to System.out. */

    private static Class<? extends ILoggingTool> loggingTool;

    /**
     * Sets the {@link ILoggingTool} implementation to be used.
     *
     * @param loggingTool The new {@link ILoggingTool}.
     * @see   #getLoggingToolClass()
     */
    @TestMethod("testSetGetLoggingToolClass")
    public static void setLoggingToolClass(
            Class<? extends ILoggingTool> loggingTool) {
        LoggingToolFactory.loggingTool = loggingTool;
    }

    /**
     * Gets the currently used {@link ILoggingTool} implementation.
     *
     * @return The currently used {@link ILoggingTool}.
     * @see    #setLoggingToolClass(Class)
     */
    @TestMethod("testSetGetLoggingToolClass")
    public static Class<? extends ILoggingTool> getLoggingToolClass() {
        return LoggingToolFactory.loggingTool;
    }

    /**
     * Dynamically create a {@link ILoggingTool} for the given
     * <code>sourceClass</code>.
     * 
     * @param  sourceClass Class for which the {@link ILoggingTool} should be
     *                     constructed.
     * @return             An {@link ILoggingTool} implementation.
     */
    @TestMethod("testCreateLoggingTool")
    public static ILoggingTool createLoggingTool(Class<?> sourceClass) {
        try {
            Class<?> possibleLoggingToolClass = sourceClass.getClassLoader()
               .loadClass(DEFAULT_LOGGING_TOOL_CLASS);
            if (ILoggingTool.class.isAssignableFrom(possibleLoggingToolClass)) {
                Method createMethod = possibleLoggingToolClass.getMethod(
                    "create", Class.class
                );
                Object createdLoggingTool = createMethod.invoke(
                    null, sourceClass
                );
                if (createdLoggingTool instanceof ILoggingTool) {
                    return (ILoggingTool)createdLoggingTool;
                } else {
                    System.out.println("Expected ILoggingTool, but found a:"
                        + createdLoggingTool.getClass().getName());
                }
            }
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException e) {
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        }
        return null;
    }

}