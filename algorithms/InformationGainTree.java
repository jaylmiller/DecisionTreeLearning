package jay.miller.cs335.hw4.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Traditional decision tree using information gain as importance function.
 * @author jaymiller
 *
 */
public class InformationGainTree extends DecisionTree {
	
	/**
	 * Constructor.
	 * @param data Training data for the tree to learn from
	 */
	public InformationGainTree(List<Integer[]> data) {
		int vectorLength = data.get(0).length;
		ArrayList<Integer> attributes = new ArrayList<>();
		for(int i = 1; i < vectorLength; i++) {
			attributes.add(i);
		}
		this.importanceFunction = new InformationGain();
		this.treeHead = learnTree(data, new ArrayList<Integer[]>(), attributes, -1);
	}
}
