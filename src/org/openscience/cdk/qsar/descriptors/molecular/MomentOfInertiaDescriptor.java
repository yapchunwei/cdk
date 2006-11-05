/*
 *  Copyright (C) 2004-2006  The Chemistry Development Kit (CDK) project
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.qsar.descriptors.molecular;

import javax.vecmath.Point3d;

import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.geometry.GeometryToolsInternalCoordinates;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.IMolecularDescriptor;
import org.openscience.cdk.qsar.result.DoubleArrayResult;
import org.openscience.cdk.tools.LoggingTool;
import org.openscience.cdk.tools.MFAnalyser;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * A descriptor that calculates the moment of inertia and radius of gyration.
 * Moment of inertia (MI) values characterize the mass distribution of a molecule.
 * Related to the MI values, ratios of the MI values along the three principal axes
 * are also well know modeling variables. This descriptor calculates the MI values
 * along the X, Y and Z axes as well as the ratio's X/Y, X/Z and Y/Z. Finally it also
 * calculates the radius of gyration of the molecule.
 * <p>
 * The descriptor generates 7 values in the following order
 * <ul>
 * <li>MI along X axis
 * <li>MI along Y axis
 * <li>MI along Z axis
 * <li>X/Y
 * <li>X/Z
 * <li>Y/Z
 * <li>Radius of gyration
 * </ul>
 * One important aspect of the algorithm is that if the eigenvalues of the MI tensor
 * are below 1e-3, then the ratio's are set to a default of 1000.
 * 
 * <p>This descriptor uses these parameters:
 * <table border="1">
 *   <tr>
 *     <td>Name</td>
 *     <td>Default</td>
 *     <td>Description</td>
 *   </tr>
 *   <tr>
 *     <td></td>
 *     <td></td>
 *     <td>no parameters</td>
 *   </tr>
 * </table>
 * 
 * @author      Rajarshi Guha
 * @cdk.created     2005-02-07
 *
 * @cdk.builddepends Jama-1.0.1.jar
 * @cdk.depends Jama-1.0.1.jar
 *
 * @cdk.module  qsar
 * @cdk.set     qsar-descriptors
 * @cdk.dictref qsar-descriptors:momentOfInertia
 */
public class MomentOfInertiaDescriptor implements IMolecularDescriptor {
    
    private LoggingTool logger;
    
    public MomentOfInertiaDescriptor() {
        logger = new LoggingTool(this);
    }

	public DescriptorSpecification getSpecification() {
        return new DescriptorSpecification(
            "http://www.blueobelisk.org/ontologies/chemoinformatics-algorithms/#momentOfInertia",
		    this.getClass().getName(),
		    "$Id$",
            "The Chemistry Development Kit");
    };

    /**
     *  Sets the parameters attribute of the MomentOfInertiaDescriptor object.
     *
     *@param  params            The new parameters value
     *@exception  CDKException  Description of the Exception
     *@see #getParameters
     */
    public void setParameters(Object[] params) throws CDKException {
        // no parameters for this descriptor
    }

    /**
     *  Gets the parameters attribute of the MomentOfInertiaDescriptor object.
     *
     *@return    The parameters value
     *@see #setParameters
     */
    public Object[] getParameters() {
        // no parameters to return
        return(null);
    }
    /**
     *  Gets the parameterNames attribute of the MomentOfInertiaDescriptor object.
     *
     *@return    The parameterNames value
     */
    public String[] getParameterNames() {
        // no param names to return
        return(null);
    }


    /**
     *  Gets the parameterType attribute of the MomentOfInertiaDescriptor object.
     *
     *@param  name  Description of the Parameter
     *@return       The parameterType value
     */
    public Object getParameterType(String name) {
         return (null);
    }

    /**
     *  Calculates the 3 MI's, 3 ration and the R_gyr value.
     *
     *@param  container  Parameter is the atom container.
     *@return            An ArrayList containing 7 elements in the order described above
     *@throws CDKException if the supplied AtomContainer does not contain 3D coordinates
     */

    public DescriptorValue calculate(IAtomContainer container) throws CDKException {
        IsotopeFactory factory = null;
        try {
            factory = IsotopeFactory.getInstance(container.getBuilder());
        } catch (Exception e) {
            logger.debug(e);
        }
        factory.configureAtoms(container);

        DoubleArrayResult retval = new DoubleArrayResult(7);

        double ccf = 1.000138;
        double eps = 1e-5;

        double[][] imat = new double[3][3];
        Point3d centerOfMass = GeometryToolsInternalCoordinates.get3DCentreOfMass(container);

        double xdif;
        double ydif;
        double zdif;
        double xsq;
        double ysq;
        double zsq;
        for (int i = 0; i < container.getAtomCount(); i++) {
            org.openscience.cdk.interfaces.IAtom currentAtom = container.getAtom(i);
            if (currentAtom.getPoint3d() == null) {
                throw new CDKException("Atom "+i+" did not have any 3D coordinates. These are required");
            }

            double mass = factory.getMajorIsotope(currentAtom.getSymbol()).getExactMass();

            xdif = currentAtom.getPoint3d().x - centerOfMass.x;
            ydif = currentAtom.getPoint3d().y - centerOfMass.y;
            zdif = currentAtom.getPoint3d().z - centerOfMass.z;
            xsq = xdif * xdif;
            ysq = ydif * ydif;
            zsq = zdif * zdif;

            imat[0][0] += mass * (ysq + zsq);
            imat[1][1] += mass * (xsq + zsq);
            imat[2][2] += mass * (xsq + ysq);

            imat[1][0] += -1 * mass * ydif * xdif;
            imat[0][1] = imat[1][0];

            imat[2][0] += -1 * mass * xdif * zdif;
            imat[0][2] = imat[2][0];

            imat[2][1] += -1 * mass * ydif * zdif;
            imat[1][2] = imat[2][1];
        }

        // diagonalize the MI tensor
        Matrix tmp = new Matrix(imat);
        EigenvalueDecomposition eigenDecomp = tmp.eig();
        double[] eval = eigenDecomp.getRealEigenvalues();

        retval.add( eval[2] );
        retval.add( eval[1] );
        retval.add( eval[0] );

        double etmp = eval[0];
        eval[0] = eval[2];
        eval[2] = etmp;

        if (Math.abs(eval[1]) > 1e-3) retval.add( eval[0]/eval[1] );
        else retval.add( 1000 );

        if (Math.abs(eval[2]) > 1e-3) {
            retval.add( eval[0]/eval[2] );
            retval.add( eval[1]/eval[2] );
        }
        else {
            retval.add( 1000 );
            retval.add( 1000 );
        }
        
        // finally get the radius of gyration
        double pri = 0.0;
        MFAnalyser mfa = new MFAnalyser(container);
        if (Math.abs(eval[2]) > eps) pri = Math.pow( eval[0]*eval[1]*eval[2], 1.0/3.0 ) ;
        else pri = Math.sqrt( eval[0] * ccf / mfa.getMass() );
        retval.add( Math.sqrt(Math.PI * 2 * pri * ccf / mfa.getMass()) );
                
        return new DescriptorValue(getSpecification(), getParameterNames(), getParameters(), retval);
    }
}
    

