/* Copyright (C) 2003-2008  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.validate;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;

/**
 * A report on validation of chemical semantics.
 *
 * @cdk.module valid
 */
@TestClass("org.openscience.cdk.validate.ValidationReportTest")
public class ValidationReport {

    private List<ValidationTest> errors;
    private List<ValidationTest> warnings;
    private List<ValidationTest> oks;
    private List<ValidationTest> cdkErrors;

    /**
     * Constructs a new empty ValidationReport.
     */
    @TestMethod("testConstructor")
    public ValidationReport() {
        errors = new ArrayList<ValidationTest>();
        warnings = new ArrayList<ValidationTest>();
        oks = new ArrayList<ValidationTest>();
        cdkErrors = new ArrayList<ValidationTest>();
    }

    /**
     * Merges the tests with the tests in this ValidationReport.
     */
    @TestMethod("testAddReport")
    public void addReport(ValidationReport report) {
        errors.addAll(report.errors);
        warnings.addAll(report.warnings);
        oks.addAll(report.oks);
        cdkErrors.addAll(report.cdkErrors);
    }
    
    /**
     * Adds a validation test which gives serious errors.
     */
    @TestMethod("testAddError")
    public void addError(ValidationTest test) {
        errors.add(test);
    }
    
    /**
     * Adds a validation test which indicate a possible problem.
     */
    @TestMethod("testAddWarning")
    public void addWarning(ValidationTest test) {
        warnings.add(test);
    }
    
    /**
     * Adds a validation test which did not find a problem.
     */
    @TestMethod("testAddOK")
    public void addOK(ValidationTest test) {
        oks.add(test);
    }
    
    /**
     * Adds a CDK problem.
     */
    @TestMethod("testAddCDKError")
    public void addCDKError(ValidationTest test) {
        cdkErrors.add(test);
    }
    
    /**
     * Returns the number of failed tests.
     */
    @TestMethod("testConstructor")
    public int getErrorCount() {
        return errors.size();
    }
    
    /**
     * Returns the number of tests which gave warnings.
     */
    @TestMethod("testConstructor")
    public int getWarningCount() {
        return warnings.size();
    }
    
    /**
     * Returns the number of tests without errors.
     */
    @TestMethod("testConstructor")
    public int getOKCount() {
        return oks.size();
    }
    
    /**
     * Returns the number of CDK errors.
     */
    @TestMethod("testConstructor")
    public int getCDKErrorCount() {
        return cdkErrors.size();
    }
    
    /**
     * Returns the number of CDK errors.
     */
    @TestMethod("testConstructor")
    public int getCount() {
        return cdkErrors.size() + errors.size() + warnings.size() + oks.size();
    }
    
    /**
     * Returns an array of ValidationTest errors.
     */
    @TestMethod("testGetErrors")
    public List<ValidationTest> getErrors() {
        return errors;
    }

    /**
     * Returns an array of ValidationTest warnings.
     */
    @TestMethod("testGetWarnings")
    public List<ValidationTest> getWarnings() {
        return warnings;
    }

    /**
     * Returns an array of ValidationTest which did not find problems.
     */
    @TestMethod("testGetOKs")
    public List<ValidationTest> getOKs() {
        return oks;
    }

    /**
     * Returns an array of ValidationTest indicating CDK problems.
     */
    @TestMethod("testGetCDKErrors")
    public List<ValidationTest> getCDKErrors() {
        return cdkErrors;
    }

}
