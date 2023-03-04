// Aidan Wu : Assignment 4

package nlp.parser;

import java.util.ArrayList;

/**
 * A class representing a syntactic parse tree.  The parse tree is a recursive structure.
 * A parse tree consists of a label and, if it is not a terminal, children that are also
 * parse trees.
 * 
 * @author Dave Kauchak / Aidan Wu
 * @version 2/23/2023
 *
 */
public class ParseTree {
	
	private boolean terminal; // whether or not this is a terminal node in the tree
	private String label; // the label of this node of the tree, e.g. S, NP, VP, etc.
    private double weight; // cumulative weight of this node
	
	private ArrayList<ParseTree> children; // the sub parse trees of this node if it's a non-terminal
	
	/**
	 * Construct a new parse tree without any children (though it still may be a non-terminal
	 * with children yet unattached)
	 * 
	 * @param label the constituent label for this node (or the word if it's a terminal)
	 * @param terminal whether or not this is a terminal
	 */
	public ParseTree(String label, boolean terminal, double weight){
		this.label = label;
		this.terminal = terminal;
        this.weight = weight;

		if( !terminal ){
			children = new ArrayList<ParseTree>();
		}
	}
	
	/**
	 * Adds a child to this TreeNode from left to right
	 * 
	 * @param newChild the child to be added.
	 */
	public void addChild(ParseTree newChild){
		children.add(newChild);
	}
	
	/**
	 * Get the children of this parse tree.
	 * 
	 * @return an iterable object over the children parse trees
	 */
	public Iterable<ParseTree> getChildren(){
		return children;
	}
	
	/**
	 * Get the constituent labels of the children of this parse tree.
	 * 
	 * @return an ArrayList of the labels
	 */
	public ArrayList<String> getChildrenLabels(){
		ArrayList<String> labels = new ArrayList<String>(children.size());
		
		if( !terminal ){		
			for(ParseTree t: children){
				labels.add(t.getLabel());
			}
		}
		
		return labels;
	}
	
	/**
	 * Get the child at index of this parse tree
	 * 
	 * @param index the index of the child to obtain
	 * @return the child at index
	 */
	public ParseTree getChild(int index){
		return children.get(index);
	}
	
	/**
	 * Get the number of children/sub-trees for this parse tree
	 * 
	 * @return the number of children/sub-trees
	 */
	public int numChildren(){
		return children.size();
	}
	
	/**
	 * Checks if this parse tree is a terminal node or not
	 * 
	 * @return whether or not this parse tree is a terminal node
	 */
	public boolean isTerminal(){
		return terminal;
	}
	
	/**
	 * Get the constituent label for this parse tree.  If it is a terminal,
	 * this is the word.
	 * 
	 * @return the constituent label
	 */
	public String getLabel(){
		return label;
	}

    /**
	 * Get the cumulative weight for this node of the parse tree.
	 * 
	 * @return the cumulative weight
	 */
	public double getWeight(){
		return weight;
	}
	
	/**
	 * Get a string representation of this parse tree in parenthesized form
	 */
	public String toString(){
		if( terminal ){
			return label;
		}else{
			StringBuffer buffer = new StringBuffer();
		
			buffer.append("(");
			buffer.append(label);
			
			for(ParseTree child: children){
				buffer.append(" ");
				buffer.append(child.toString());
			}
			
			buffer.append(")");
			
			return buffer.toString();
		}
	}
}
