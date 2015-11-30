package jay.miller.cs335.hw4;

import java.util.List;

import jay.miller.cs335.hw4.algorithms.DecisionTree;

public class TreeTester {
	
	private List<Integer[]> trainData, testData;
	private DecisionTree tree;
	
	public TreeTester(List<Integer[]> trainData, List<Integer[]> testData, DecisionTree tree) {
		this.trainData = trainData;
		this.testData = testData;
		this.tree = tree;
	}
	
	public void trainDataStats() {
		System.out.println("Training data stats:");
		printStats(trainData);
	}
	
	public void testDataStats() {
		System.out.println("Test data stats:");
		printStats(testData);
	}
	
	private void printStats(List<Integer[]> data) {
		int totalPos = 0; 
		int truePos = 0; 
		int falsePos = 0; 
		int trueNeg = 0;
		int size = data.size();
		for(Integer[] dataVector : data) {
			totalPos += dataVector[0];
			int prediction = tree.getPrediction(dataVector);
			if(prediction == 1) {
				if(dataVector[0] == 1) truePos++;
				else falsePos++;
			} else if(dataVector[0] == 0) {
				trueNeg++;
			}
		}
		double recall = (double) truePos / (double) totalPos;
		double precision = (double) truePos / (double) (truePos+falsePos);
		double accuracy = (double) (truePos+trueNeg) / (double) size;
		System.out.println("Accuracy: " + accuracy);
		System.out.println("Precision: " + precision);
		System.out.println("Recall: " + recall);
	}
}
