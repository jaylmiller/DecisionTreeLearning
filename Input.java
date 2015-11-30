package jay.miller.cs335.hw4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jay.miller.cs335.hw4.algorithms.GainRatioTree;
import jay.miller.cs335.hw4.algorithms.InformationGainTree;
import jay.miller.cs335.hw4.algorithms.DecisionTree;


/**
 * Runs the main method.
 * Command line input format should be:
 * 1st argument: training data
 * 2nd argument: test data
 * 3rd argument: integer specifying which algorithm to use
 * 		0 = information gain
 * 		1 = gain ratio
 * 		2 = genetic algorithm
 * @author jaymiller
 *
 */

public class Input {
	private static List<Integer[]> trainingData;
	private static List<Integer[]> testData;
	/**
	* Gather input, run methods
	*/
	public static void main(String[] args) {
		String workingDirectory = System.getProperty("user.dir");
		//workingDirectory = workingDirectory.substring(0, workingDirectory.length()-3);
		workingDirectory = workingDirectory.substring(0, workingDirectory.length());
		List<String> trainingLines = null;
		List<String> testLines = null;
		int algoDecider = -1;
		try {
			String trainingSetFile = args[0];
			String testSetFile = args[1];
			algoDecider = Integer.parseInt(args[2]);
			trainingLines = Files.readAllLines(Paths.get(workingDirectory+"/data/"+trainingSetFile));
			testLines = Files.readAllLines(Paths.get(workingDirectory+"/data/"+testSetFile));			
			trainingData = stringListToData(trainingLines);
			testData = stringListToData(testLines);
		} catch(Exception e) {
			System.err.println("Invalid input");
			System.exit(0);
		}
		//make sure the length of the rows in the matrices are equal
		int rowLength = trainingData.get(0).length;
		for(Integer[] row : trainingData) {			
			if(row.length != rowLength) {
				System.err.println("Invalid data set");
				System.exit(0);
			}
		}
		for(Integer[] row : testData) {
			if(row.length != rowLength) {
				System.err.println("Invalid data set");
				System.exit(0);
			}
		}
		DecisionTree tree = null;
		if(algoDecider == 0) {
			tree = new InformationGainTree(trainingData);
		} else if(algoDecider == 1) {
			tree = new GainRatioTree(trainingData);
		} else if(algoDecider == 2) {
			//genetic tree
		}
		TreeTester tester = new TreeTester(trainingData, testData, tree);
		tester.trainDataStats();
		tester.testDataStats();
	}
	
	/**
	 * Takes a list of strings and returns a matrix of integers representing the data of the string
	 * Note: data must be of correct format (all integers, separated by commas)
	 * @param stringList list of strings to get data from
	 * @return integer matrix of data
	 */
	private static List<Integer[]> stringListToData(List<String> stringList) {
		int rows = stringList.size();
		List<Integer[]> dataSet = new ArrayList<>();
		for(int i = 0; i < rows; i++) {
			String[] dataString = stringList.get(i).split(",");	
			Integer[] data = new Integer[dataString.length];
			try {
				for(int j = 0; j < data.length; j++) {
					data[j] = Integer.parseInt(dataString[j]);
				}
				dataSet.add(data);
			} catch(NumberFormatException e) {
				System.err.println("Invalid input");				
			}
		}
		return dataSet;
	}

}
