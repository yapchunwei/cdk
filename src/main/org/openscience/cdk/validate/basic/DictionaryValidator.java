/* Copyright (C) 2003-2007  The Chemistry Development Kit (CDK) project
 *                    2011  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.validate.basic;

import java.util.Iterator;
import java.util.Map;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.dict.DictionaryDatabase;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.tools.ILoggingTool;
import org.openscience.cdk.tools.LoggingToolFactory;
import org.openscience.cdk.validate.AbstractValidator;
import org.openscience.cdk.validate.ValidationReport;
import org.openscience.cdk.validate.ValidationTest;
import org.openscience.cdk.validate.AbstractValidationTestType;

/**
 * Validates the existence of references to dictionaries.
 *
 * @author   Egon Willighagen
 * @cdk.githash
 * @cdk.created  2003-03-28
 * @cdk.module   valid
 */
@TestClass("org.openscience.cdk.validate.basic.DictionaryValidatorTest")
public class DictionaryValidator extends AbstractValidator {

    private final static AbstractValidationTestType NO_NAMESPACE =
        new AbstractValidationTestType(
            "Dictionary Reference lacks a namespace indicating the dictionary."
        ) {};
    private final static AbstractValidationTestType NO_DICTIONARY =
        new AbstractValidationTestType(
            "The referenced dictionary does not exist."
        ) {};
    private final static AbstractValidationTestType NO_ENTRY =
        new AbstractValidationTestType(
            "The referenced entry does not exist in the dictionary."
        ) {};

    private static ILoggingTool logger =
        LoggingToolFactory.createLoggingTool(DictionaryValidator.class);
    
    private DictionaryDatabase db;
    
    @TestMethod("testConstructor")
    public DictionaryValidator(DictionaryDatabase db) {
        this.db = db;
    }

    @TestMethod("testValidateChemObject")
    public ValidationReport validateChemObject(IChemObject subject) {
        ValidationReport report = new ValidationReport();
        Map<Object,Object> properties = subject.getProperties();
        Iterator<Object> iter = properties.keySet().iterator();
        ValidationTest noNamespace = new ValidationTest(NO_NAMESPACE, subject);
        ValidationTest noDict = new ValidationTest(NO_DICTIONARY, subject);
        ValidationTest noEntry = new ValidationTest(NO_ENTRY, subject);
        while (iter.hasNext()) {
            Object key = iter.next();
            if (key instanceof String) {
                String keyName = (String)key;
                if (keyName.startsWith(DictionaryDatabase.DICTREFPROPERTYNAME)) {
                    String dictRef = (String)properties.get(keyName);
                    String details = "Dictref being anaylyzed: " + dictRef + ". ";
                    noNamespace.setDetails(details);
                    noDict.setDetails(details);
                    noEntry.setDetails(details);
                    int index = dictRef.indexOf(':');
                    if (index != -1) {
                        report.addOK(noNamespace);
                        String dict = dictRef.substring(0,index);
                        logger.debug("Looking for dictionary:" + dict);
                        if (db.hasDictionary(dict)) {
                            report.addOK(noDict);
                            if (dictRef.length() > index+1) {
                                String entry = dictRef.substring(index+1);
                                logger.debug("Looking for entry:" + entry);
                                if (db.hasEntry(dict, entry)) {
                                    report.addOK(noEntry);
                                } else {
                                    report.addError(noEntry);
                                }
                            } else {
                                report.addError(noEntry);
                            }
                        } else {
                            details += "The dictionary searched: " + dict + ".";
                            noDict.setDetails(details);
                            report.addError(noDict);
                            report.addError(noEntry);
                        }
                    } else {
                        // The dictRef has no namespace
                        details += "There is not a namespace given.";
                        noNamespace.setDetails(details);
                        report.addError(noNamespace);
                        report.addError(noDict);
                        report.addError(noEntry);
                    }
                } else {
                    // not a dictref
                }
            } else {
                // not a dictref
            }
        }
        return report;
    }
    
}
