package jay.miller.cs335.hw4.algorithms;

import java.util.List;
import java.util.Map;

/**
 * Gain ratio (gain/intrinsicvalue) importance function.
 * @author jaymiller
 *
 */
public class GainRatio extends ImportanceFunction {

	@Override
	public int getAttribute(Map<Integer, Map<Integer, List<Integer[]>>> partitions) {
		double maxRatio = 0;
		int bestAttribute = -1;
		for(Map.Entry<Integer, Map<Integer, List<Integer[]>>> partition : partitions.entrySet()) {
			double infoGain = getInformationGain(partition.getValue());			
			double intrinsicVal = getIntrinsicValue(partition.getValue());			
			double ratio = infoGain/intrinsicVal;			
			if(ratio > maxRatio) {
				maxRatio = ratio;
				bestAttribute = partition.getKey();
			}
		}
		return bestAttribute;
	}
}
