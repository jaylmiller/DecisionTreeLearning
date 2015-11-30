package jay.miller.cs335.hw4.algorithms;

import java.util.List;
import java.util.Map;

/**
 * Information gain importance function.
 * @author jaymiller
 *
 */
public class InformationGain extends ImportanceFunction {

	@Override
	public int getAttribute(Map<Integer, Map<Integer, List<Integer[]>>> partitions) {
		double maxGain = 0;
		int bestAttribute = -1;
		for(Map.Entry<Integer, Map<Integer, List<Integer[]>>> partition : partitions.entrySet()) {
			double infoGain = getInformationGain(partition.getValue());
			if(infoGain > maxGain) {
				maxGain = infoGain;
				bestAttribute = partition.getKey();
			}
		}
		return bestAttribute;
	}
}
