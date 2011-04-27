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
package org.openscience.cdk.validate;

import junit.framework.Assert;

import org.junit.Test;
import org.openscience.cdk.Atom;

/**
 * @cdk.module test-valid
 */
public class ValidationReportTest {

    @Test
    public void testConstructor() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report);
        Assert.assertEquals(0, report.getCDKErrorCount());
        Assert.assertEquals(0, report.getCount());
        Assert.assertEquals(0, report.getErrorCount());
        Assert.assertEquals(0, report.getWarningCount());
    }

    @Test
    public void testAddOK() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report);
        Assert.assertEquals(0, report.getCount());
        report.addOK(new ValidationTest(new AbstractValidationTestType(
            "A Test Helper Class"
        ) {}, new Atom()));
        Assert.assertEquals(1, report.getCount());
        Assert.assertEquals(0, report.getCDKErrorCount());
        Assert.assertEquals(0, report.getErrorCount());
        Assert.assertEquals(0, report.getWarningCount());
    }

    @Test
    public void testAddWarning() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report);
        Assert.assertEquals(0, report.getCount());
        report.addWarning(new ValidationTest(new AbstractValidationTestType(
            "A Test Helper Class"
        ) {}, new Atom()));
        Assert.assertEquals(1, report.getCount());
        Assert.assertEquals(0, report.getCDKErrorCount());
        Assert.assertEquals(0, report.getErrorCount());
        Assert.assertEquals(1, report.getWarningCount());
    }

    @Test
    public void testAddError() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report);
        Assert.assertEquals(0, report.getCount());
        report.addError(new ValidationTest(new AbstractValidationTestType(
            "A Test Helper Class"
        ) {}, new Atom()));
        Assert.assertEquals(1, report.getCount());
        Assert.assertEquals(0, report.getCDKErrorCount());
        Assert.assertEquals(1, report.getErrorCount());
        Assert.assertEquals(0, report.getWarningCount());
    }

    @Test
    public void testAddCDKError() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report);
        Assert.assertEquals(0, report.getCount());
        report.addCDKError(new ValidationTest(new AbstractValidationTestType(
            "A Test Helper Class"
        ) {}, new Atom()));
        Assert.assertEquals(1, report.getCount());
        Assert.assertEquals(1, report.getCDKErrorCount());
        Assert.assertEquals(0, report.getErrorCount());
        Assert.assertEquals(0, report.getWarningCount());
    }

    @Test
    public void testAddReport() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report);
        Assert.assertEquals(0, report.getCount());
        report.addCDKError(new ValidationTest(new AbstractValidationTestType(
            "A Test Helper Class"
        ) {}, new Atom()));
        Assert.assertEquals(1, report.getCount());
        Assert.assertEquals(1, report.getCDKErrorCount());
        Assert.assertEquals(0, report.getErrorCount());
        Assert.assertEquals(0, report.getWarningCount());

        ValidationReport reportTwo = new ValidationReport();
        Assert.assertNotNull(reportTwo);
        reportTwo.addReport(report);
        Assert.assertEquals(1, reportTwo.getCount());
        Assert.assertEquals(1, reportTwo.getCDKErrorCount());
        Assert.assertEquals(0, reportTwo.getErrorCount());
        Assert.assertEquals(0, reportTwo.getWarningCount());
    }

    @Test
    public void testGetErrors() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report.getErrors());
        Assert.assertEquals(0, report.getErrors().size());
    }

    @Test
    public void testGetCDKErrors() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report.getCDKErrors());
        Assert.assertEquals(0, report.getCDKErrors().size());
    }

    @Test
    public void testGetOKs() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report.getOKs());
        Assert.assertEquals(0, report.getOKs().size());
    }

    @Test
    public void testGetWarnings() {
        ValidationReport report = new ValidationReport();
        Assert.assertNotNull(report.getWarnings());
        Assert.assertEquals(0, report.getWarnings().size());
    }
}


