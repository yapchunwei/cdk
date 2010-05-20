/* Copyright (C) 2007  Miguel Rojasch <miguelrojasch@users.sf.net>
 *               2010  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk.nonotify;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.interfaces.IReactionScheme;

/** 
 * @cdk.module nonotify
 */
public class NNReactionScheme extends NNReactionSet implements IReactionScheme {

	private static final long serialVersionUID = 2715958118776363563L;

	public IChemObjectBuilder getBuilder() {
		return NoNotificationChemObjectBuilder.getInstance();
	}

    /** A List of reaction schemes*/
    private List<IReactionScheme> reactionScheme;
    
    /**  Constructs an empty ReactionScheme.
     */
    public NNReactionScheme() {
        reactionScheme = new ArrayList<IReactionScheme>();
    }
    
    /**
     * Add a Scheme of Reactions.
     * 
     * @param scheme The IReactionScheme to include
     */
    @TestMethod("testAdd_IReactionScheme")
    public void add(IReactionScheme scheme) {
        reactionScheme.add(scheme);
    }
    
    /**
     *  Returns an Iterable for looping over all IMolecularScheme
     *   in this ReactionScheme.
     *
     * @return    An Iterable with the IMolecularScheme in this ReactionScheme
     */
    @TestMethod("testReactionSchemes")
    public Iterable<IReactionScheme> reactionSchemes() {
        return reactionScheme;
    }
    
    /**
     * Returns the number of ReactionScheme in this Scheme.
     *
     * @return     The number of ReactionScheme in this Scheme
     */
    @TestMethod("testGetReactionSchemeCount")
    public int getReactionSchemeCount(){
        return reactionScheme.size();
    }
    
    /**
     * Removes all IReactionScheme from this chemObject.
     */
    @TestMethod("testRemoveAllReactionSchemes")
    public void removeAllReactionSchemes() {
        reactionScheme.clear();
    }

    /**
     * Removes an IReactionScheme from this chemObject.
     *
     * @param  scheme  The IReactionScheme to be removed from this chemObject
     */
    @TestMethod("testRemoveReactionScheme_IReactionScheme")
    public void removeReactionScheme(IReactionScheme scheme) {
        reactionScheme.remove(scheme);
    }
    /**
     * Clones this ReactionScheme object and its content.
     *
     * @return    The cloned object
     */
    @TestMethod("testClone")
    public Object clone() throws CloneNotSupportedException {
        
        IReactionScheme clone = (NNReactionScheme)super.clone();
        for (IReactionScheme scheme : this.reactionSchemes()){
            clone.add((IReactionScheme) scheme.clone());
        }
        
        for (IReaction reaction : reactions()) {
            clone.addReaction((IReaction)reaction.clone());
        }
        
        return clone;
    }
}
