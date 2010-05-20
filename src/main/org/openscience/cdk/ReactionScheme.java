/*  Copyright (C) 2006-2007  Miguel Rojas <miguelrojasch@yahoo.es>
 *                     2010  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk;

import java.util.Map;

import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.interfaces.IReactionScheme;
import org.openscience.cdk.nonotify.NNReactionScheme;


/**
 * Classes that extends the definition of reaction to a scheme. 
 * This is designed to contain a set of reactions which are linked in 
 * some way but without hard coded semantics.
 *
 * @author      miguelrojasch <miguelrojasch@yahoo.es>
 * @cdk.module  data
 * @cdk.keyword reaction
 */
public class ReactionScheme extends NNReactionScheme
implements IReactionScheme, IChemObjectListener, IChemObjectChangeNotifier {

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -3676327644698347260L;
	
	/**  Constructs an empty ReactionScheme.
	 */
	public ReactionScheme() {
		super();
	}
	
	/** {@inheritDoc} */
	@TestMethod("testAdd_IReactionScheme")
	public void add(IReactionScheme scheme) {
		super.add(scheme);
		notifyChanged();
	}

    /** {@inheritDoc} */
    @TestMethod("testRemoveAllReactionSchemes")
    public void removeAllReactionSchemes() {
    	super.removeAllReactions();
    	notifyChanged();
    }

    /** {@inheritDoc} */
    @TestMethod("testRemoveReactionScheme_IReactionScheme")
    public void removeReactionScheme(IReactionScheme scheme) {
    	super.removeReactionScheme(scheme);
    	notifyChanged();
    }
    /**
	 * Clones this ReactionScheme object and its content.
	 *
	 * @return    The cloned object
	 */
    @TestMethod("testClone")
	public Object clone() throws CloneNotSupportedException {
		
    	IReactionScheme clone = new ReactionScheme();
		for (IReactionScheme scheme : this.reactionSchemes()){
			clone.add((IReactionScheme) scheme.clone());
		}
		
		for (IReaction reaction : reactions()) {
			clone.addReaction((IReaction)reaction.clone());
		}
		
		return clone;
	}

    /** {@inheritDoc} */
    public void addReaction(IReaction reaction) {
        super.addReaction(reaction);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeReaction(int pos) {
        super.removeReaction(pos);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void removeAllReactions() {
        super.removeAllReactions();
        notifyChanged();
    }

    public void stateChanged(IChemObjectChangeEvent event) {
        notifyChanged(event);
    }

    /** {@inheritDoc} */
    public void removeReaction(IReaction relevantReaction) {
        super.removeReaction(relevantReaction);
        notifyChanged();
    }

    private ChemObjectNotifier notifier = null;

    /** {@inheritDoc} */
    public void addListener(IChemObjectListener col) {
        if (notifier == null) notifier = new ChemObjectNotifier(this);
        notifier.addListener(col);
    }

    /** {@inheritDoc} */
    public int getListenerCount() {
        if (notifier == null) return 0;
        return notifier.getListenerCount();
    }

    /** {@inheritDoc} */
    public void removeListener(IChemObjectListener col) {
        if (notifier == null) return;
        notifier.removeListener(col);
    }

    /** {@inheritDoc} */
    public void notifyChanged() {
        if (notifier == null) return;
        notifier.notifyChanged();
    }

    /** {@inheritDoc} */
    public void notifyChanged(IChemObjectChangeEvent evt) {
        if (notifier == null) return;
        notifier.notifyChanged(evt);
    }

    /** {@inheritDoc} */
    public void setProperty(Object description, Object property) {
        super.setProperty(description, property);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setID(String identifier) {
        super.setID(identifier);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setFlag(int flag_type, boolean flag_value) {
        super.setFlag(flag_type, flag_value);
        notifyChanged();
    }

    /** {@inheritDoc} */
    public void setProperties(Map<Object,Object> properties) {
        super.setProperties(properties);
        notifyChanged();
    }
  
    /** {@inheritDoc} */
    public void setFlags(boolean[] flagsNew){
        super.setFlags(flagsNew);
        notifyChanged();
    }
}
