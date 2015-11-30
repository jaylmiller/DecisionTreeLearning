package jay.miller.cs335.hw4.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Traditional decision tree using gain ratio as importance function.
 * @author jaymiller
 *
 */
public class GainRatioTree extends DecisionTree {
	/**
	 * Constructor.
	 * @param data Training data for the tree to learn from
	 */
	public GainRatioTree(List<Integer[]> data) {
		int vectorLength = data.get(0).length;
		ArrayList<Integer> attributes = new ArrayList<>();
		for(int i = 1; i < vectorLength; i++) {
			attributes.add(i);
		}
		this.importanceFunction = new GainRatio();
		this.treeHead = learnTree(data, new ArrayList<Integer[]>(), attributes, -1);
	}	
}
