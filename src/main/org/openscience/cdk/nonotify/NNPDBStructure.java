/* Copyright (C) 2006-2007,2010  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.nonotify;

import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IPDBStructure;

/**
 * @cdk.module  nonotify
 * @cdk.githash
 */
public class NNPDBStructure extends NNChemObject implements IPDBStructure {

	private static final long serialVersionUID = -7423565527556262186L;

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    public final static String HELIX = "helix";
    public final static String SHEET = "sheet";
    public final static String TURN = "turn";
    
    private String structureType;
    private Character startChainID;
    private Integer startSequenceNumber;
    private Character startInsertionCode;
    private Character endChainID;
    private Integer endSequenceNumber;
    private Character endInsertionCode;
    
    /**
     * get the ending Chain identifier of this structure.
     * 
     * @return the ending Chain identifier of this structure
     */
    public Character getEndChainID() {
        return endChainID;
    }
    /**
     * set the ending Chain identifier of this structure.
     * 
     * @param endChainID  the ending Chain identifier of this structure
     */
    public void setEndChainID(Character endChainID) {
        this.endChainID = endChainID;
    }
    /**
     * get the ending Code for insertion of residues of this structure.
     * 
     * @return the ending Code for insertion of residues of this structure
     */
    public Character getEndInsertionCode() {
        return endInsertionCode;
    }
    /**
     * set the ending Code for insertion of residues of this structure.
     * 
     * @param endInsertionCode  the ending Code for insertion of residues of this structure
     */
    public void setEndInsertionCode(Character endInsertionCode) {
        this.endInsertionCode = endInsertionCode;
    }
    /**
     * get the ending sequence number of this structure.
     * 
     * @return the ending sequence number of this structure
     */
    public Integer getEndSequenceNumber() {
        return endSequenceNumber;
    }
    /**
     * set the ending sequence number of this structure.
     * 
     * @param endSequenceNumber  the ending sequence number of this structure
     */
    public void setEndSequenceNumber(Integer endSequenceNumber) {
        this.endSequenceNumber = endSequenceNumber;
    }
    /**
     * get start Chain identifier of this structure.
     * 
     * @return the start Chain identifier of this structure
     */
    public Character getStartChainID() {
        return startChainID;
    }
    /**
     * set the start Chain identifier of this structure.
     * 
     * @param startChainID  the start Chain identifier of this structure
     */
    public void setStartChainID(Character startChainID) {
        this.startChainID = startChainID;
    }
    /**
     * get start Code for insertion of residues of this structure.
     * 
     * @return the start Code for insertion of residues of this structure
     */
    public Character getStartInsertionCode() {
        return startInsertionCode;
    }
    /**
     * set the start Chain identifier of this structure.
     * 
     * @param startInsertionCode  the start Chain identifier of this structure
     */
    public void setStartInsertionCode(Character startInsertionCode) {
        this.startInsertionCode = startInsertionCode;
    }
    /**
     * get the start sequence number of this structure.
     * 
     * @return the start sequence number of this structure
     */
    public Integer getStartSequenceNumber() {
        return startSequenceNumber;
    }
    /**
     * set the start sequence number of this structure.
     * 
     * @param startSequenceNumber  the start sequence number of this structure
     */
    public void setStartSequenceNumber(Integer startSequenceNumber) {
        this.startSequenceNumber = startSequenceNumber;
    }
    /**
     * get Structure Type of this structure.
     * 
     * @return the Structure Type of this structure
     */
    public String getStructureType() {
        return structureType;
    }
    /**
     * set the Structure Type of this structure.
     * 
     * @param structureType  the Structure Type of this structure
     */
    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }
}
