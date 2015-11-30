package jay.miller.cs335.hw4.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for a decision tree.
 * @author jaymiller
 *
 */
public abstract class DecisionTree {
	/**
	 * The head of the tree
	 */
	protected Node treeHead;
	
	/**
	 * function that evaluates importance of a node
	 * either information gain, or gain ratio
	 */
	protected ImportanceFunction importanceFunction;
	
	/**
	 * Recursive algorithm that creates the tree from data
	 * @param currentData data being read by the current node
	 * @param parentData data at the parent node
	 * @param attributes attributes remaining
	 * @param value attribute value the current node represents
	 * @return a node of the tree
	 */
	protected Node learnTree(List<Integer[]> currentData, List<Integer[]> parentData, List<Integer> attributes, int value) {
		Node currentNode = new Node(value);
		currentNode.data = currentData;
		if(currentData.isEmpty()) {
			currentNode.setClassification(getPlurality(parentData));
			return currentNode;
		} else if(allSameClass(currentData)) {
			currentNode.setClassification(currentData.get(0)[0]);
			return currentNode;
		} else if(attributes.isEmpty()) {
			currentNode.setClassification(getPlurality(currentData));
			return new Node(getPlurality(currentData), currentData);
		}
		
		//partition current data by every remaining attribute
		Map<Integer, Map<Integer, List<Integer[]>>> partitions = new HashMap<>();	
		for(Integer attribute : attributes) {
			partitions.put(attribute, getPartition(currentData, attribute));
		}
		//look for which of the partitions gives highest information gain
		int bestAttribute = importanceFunction.getAttribute(partitions);
		//bestAttribute will be -1 if there is no importance
		//i.e. no attribute worth splitting on, so create a leaf
		if(bestAttribute == -1) {
			currentNode.setClassification(getPlurality(currentData));
			return currentNode;
		}
		Map<Integer, List<Integer[]>> bestPartition = partitions.get(bestAttribute);
		currentNode.setAttribute(bestAttribute);
		
		//add children
		for(Map.Entry<Integer, List<Integer[]>> set : bestPartition.entrySet()) {
			int newValue = set.getKey();
			List<Integer[]> newData = set.getValue();
			List<Integer> newAttributes = new ArrayList<Integer>(attributes);
			newAttributes.remove((Integer) bestAttribute);
			currentNode.addChild(learnTree(newData, currentData, newAttributes, newValue));
		}
		return currentNode;
	}
	/**
	 * Return the decision tree's prediction given a vector of data
	 * @param data The input data
	 * @return integer representing the class
	 */
	public int getPrediction(Integer[] data) {
		Node currentNode = this.treeHead;
		while(!currentNode.leaf()) {			
			int val = data[currentNode.getAttribute()];
			boolean noValue = true;
			for(Node child : currentNode.getChildren()) {
				if(child.getValue() == val) {
					noValue = false;
					currentNode = child;
					continue;
				}
			}
			//if no leaves with the current attribute value
			//go to the child with the most examples
			if(noValue && !currentNode.leaf()) {
				int maxExamples = 0;
				int maxIndex = 0;
				for(int i = 0; i < currentNode.getChildren().size(); i++) {
					if(currentNode.getChildren().get(i).data.size() > maxExamples) {
						maxExamples = currentNode.getChildren().get(i).data.size();
						maxIndex = i;
					}
				}
				currentNode = currentNode.getChildren().get(maxIndex);
			}
		}
		return currentNode.getClassification();
	}
	
	/**
	 * Checks if everything in a data set is of the same class
	 * @param data Data set to check
	 * @return True if everything in same class, false otherwise
	 */
	private boolean allSameClass(List<Integer[]> data) {
		int c = data.get(0)[0];
		for(Integer[] vector : data) {
			if(vector[0] != c) return false;
		}
		return true;
	}
	
	/**
	 * Partitions a data set based on a given attribute.
	 * @param data The set to partition
	 * @param attribute The attribute to partition based on
	 * @return A map which maps attribute value to the list of data vectors
	 * 		whose attribute has that value
	 */
	private Map<Integer, List<Integer[]>> getPartition(List<Integer[]> data, int attribute) {
		Map<Integer, List<Integer[]>> map = new HashMap<>();
		for(Integer[] dataVector : data) {
			Integer value = dataVector[attribute];
			if(map.containsKey(value)) {
				List<Integer[]> partition = map.get(value);
				partition.add(dataVector);
			} else {
				ArrayList<Integer[]> newList = new ArrayList<>();
				newList.add(dataVector);
				map.put(value, newList);
			}
		}
		return map;
	}
	
	
	/**
	 * Get most common classification from data
	 * @param data
	 * @return 0 or 1 based on classification
	 */
	private int getPlurality(List<Integer[]> data) {
		int num0s = 0;
		int num1s = 0;
		for(Integer[] dataVector : data) {
			if(dataVector[0] == 0) num0s++;
			else num1s++;
		}
		if(num0s == num1s) {
			return (Math.random() < .5) ? 0 : 1;
		}
		return (Math.max(num0s, num1s) == num0s) ? 0 : 1;
	}

	
}
