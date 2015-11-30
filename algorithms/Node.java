package jay.miller.cs335.hw4.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	/**
	 * Data contained by the node. For testing/debugging purposes.
	 * Ideally, the node doesn't have to actually hold the data, which
	 * will save memory.
	 */
	public List<Integer[]> data;
	/**
	 * Which attribute does does this node represent
	 * If this is a leaf node then this is meaningless
	 * so let it be -1
	 */
	private int attribute = -1;
	
	/**
	 * Which value of the parent node's attribute does
	 * this node represent.
	 * If this node is the tree head then this number is meaningless
	 * so we let it be -1
	 */
	private int value;
	
	/**
	 * classification this node represents
	 * only applicable if leaf node
	 */
	private int classification;
	
	/**
	 * Children of this node
	 */
	private List<Node> children;
	
	/**
	 * Constructor
	 * @param data Example data for this node
	 */
	public Node(int value) {		
		this.value = value;
		this.children = new ArrayList<Node>();
	}
	
	/**
	 * Constructor that takes value and data set
	 * primarily for debugging/testing purposes
	 * @param value
	 * @param data
	 */
	public Node(int value, List<Integer[]> data) {
		this.value = value;
		this.data = data;
	}
	
	/**
	 * Set this nodes attribute
	 * @param a The attribute
	 */
	public void setAttribute(int a) {
		this.attribute = a;
	}
	
	/**
	 * Get the attribute for this node
	 * @return The attribute
	 */
	public int getAttribute() {
		return this.attribute;
	}
	
	/**
	 * Get this nodes value
	 * 
	 * @return the value
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Set the classification for this node
	 * @param classification
	 */
	public void setClassification(int classification) {
		this.classification = classification;
	}
	
	/**
	 * Get the classification this node represents (if leaf node)
	 * If not a leaf node, -1 will be returned
	 * @return the classification, or -1 if not a leaf node
	 */
	public int getClassification() {
		return this.classification;
	}
	
	/**
	 * Add a child node
	 * @param child child to add
	 */
	public void addChild(Node child) {
		if(child == null) return;
		else this.children.add(child);
	}
	
	/**
	 * Get the children of the current node
	 * @return List of children
	 */
	public List<Node> getChildren() {
		return this.children;
	}
	
	/**
	 * Is this a leaf node?
	 * @return true if leaf node
	 */
	public boolean leaf() {
		return this.children.isEmpty();
	}
	
	/**
	 * Prints data. For testing/debugging purposes
	 */
	public void printData() {
		System.out.println("Attribute: " + this.attribute);
		for(Integer[] vector : data) {
			for(Integer i : vector) {
				System.out.print(i + ", ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
}
