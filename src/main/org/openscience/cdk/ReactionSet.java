/* Copyright (C) 2003-2007,2010  Egon Willighagen <egonw@users.sf.net>
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
package org.openscience.cdk;

import java.io.Serializable;
import java.util.Map;

import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectChangeNotifier;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.interfaces.IReactionSet;
import org.openscience.cdk.nonotify.NNReactionSet;

/** 
 * A set of reactions, for example those taking part in a reaction.
 *
 * To retrieve the reactions from the set, there are two options:
 *
 * <pre>
 * Iterator reactions = reactionSet.reactions();
 * while (reactions.hasNext()) {
 *     IReaction reaction = (IReaction)reactions.next();
 * }
 * </pre>
 *
 * and
 *
 * <pre>
 * for (int i=0; i < reactionSet.getReactionCount(); i++) {
 *    IReaction reaction = reactionSet.getReaction(i);
 * }
 * </pre>
 *
 * @cdk.module data
 * @cdk.githash
 *
 * @cdk.keyword reaction
 */
public class ReactionSet extends NNReactionSet
implements Serializable, IReactionSet, IChemObjectListener, Cloneable, IChemObjectChangeNotifier
{

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is incompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 1555749911904585204L;

	/** {@inheritDoc} */
	public ReactionSet() {
		super();
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
	
	/**
	 * Clones this <code>ReactionSet</code> and the contained <code>Reaction</code>s
     * too.
	 *
	 * @return  The cloned ReactionSet
	 */
	public Object clone() throws CloneNotSupportedException {
		ReactionSet clone = (ReactionSet)super.clone();
		return clone;
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
