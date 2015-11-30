package jay.miller.cs335.hw4.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Abstract class for an importance function.
 * Either gain, or gain ratio.
 * The function getAttribute is used by the decision tree
 * algorithm to determine which attribute to split the decision
 * tree on. Returns -1 if the gain or ratio is zero.
 * @author jaymiller
 *
 */
public abstract class ImportanceFunction {
	
	/**
	 * Get the most important attribute given partitions of data
	 * based on the attributes. 
	 * Return -1 if importance is 0
	 * @param partition
	 * @return integer representing index of best attribute, or -1
	 */
	public abstract int getAttribute(Map<Integer, Map<Integer, List<Integer[]>>> partitions);
	
	/**
	 * Get the entropy of a Bernoulli random variable, given probability
	 * @param n numerator of probability 
	 * @param d denominator of probability
	 * @return the entropy
	 */
	protected double getEntropy(int n, int d) {
		double prob = (double) n / (double) (d);
		double term1 = (prob > 0) ? prob*log2(prob) : 0d;
		term1 *= -1d;
		double term2 = ((1d-prob) > 0) ? (1d-prob)*log2((1d-prob)) : 0d;
		term2 *= -1d;
		return term1+term2;
	}
	
	/**
	 * Calculate log base 2
	 * @param a input
	 * @return log base 2
	 */
	protected double log2(double a) {
		return Math.log(a)/Math.log(2d);
	}
	
	/**
	 * Calculate the information gain of a given set partition
	 * @param set The partitioned set
	 * @return information
	 */
	protected double getInformationGain(Map<Integer, List<Integer[]>> set) {
		int totalPos = 0;
		int total = 0;
		List<Integer[]> infoPerSubset = new ArrayList<>();
		for(Map.Entry<Integer, List<Integer[]>> entry : set.entrySet()) {
			Integer[] subsetInfo = new Integer[]{0, 0};
			for(Integer[] data : entry.getValue()) {
				totalPos += data[0];
				subsetInfo[0] += data[0];
				total++;
				subsetInfo[1]++;
			}
			infoPerSubset.add(subsetInfo);
		}		
		double goalEntropy = getEntropy(totalPos, total);
		double remainder = 0;
		for(Integer[] info : infoPerSubset) {
			double entropy = getEntropy(info[0], info[1]);			
			remainder += ((double) (info[1])/ (double) total)*entropy;
		}
		return goalEntropy - remainder;
	}
	
	/**
	 * Calculate the intrinsic value of a given set partition
	 * @param set The partitioned set
	 * @return intrinsic value
	 */
	protected double getIntrinsicValue(Map<Integer, List<Integer[]>> set) {
		int total = 0;
		List<Integer> totalPerAttribute = new ArrayList<>();
		for(Map.Entry<Integer, List<Integer[]>> entry : set.entrySet()) {
			total += entry.getValue().size();
			int attTotal = 0;
			for(Integer[] data : entry.getValue()) {
				attTotal += data[0];
			}
			totalPerAttribute.add(attTotal);
		}
		double sum = 0;
		for(Integer x : totalPerAttribute) {
			double term1 = (double) x / (double) total;
			double term2 = (term1 > 0) ? log2(term1) : 0;
			sum += term1*term2;
		}
		return sum*-1d;
	}
}
