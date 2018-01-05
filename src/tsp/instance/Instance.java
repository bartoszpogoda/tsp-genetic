package tsp.instance;

import tsp.algorithm.individual.PathIndividual;

/***
 * Class responsible for holding TS Problem instance data
 * 
 * @author Student225988
 */
public class Instance {
	private double adjacencyMatrix[][];
	private int numberOfCities;
	
	private String name;

	public Instance(int numberOfCities) {
		this.numberOfCities = numberOfCities;

		adjacencyMatrix = new double[numberOfCities][numberOfCities];
	}

	public int getNumberOfCities() {
		return numberOfCities;
	}

	public void setDistance(int startCity, int endCity, double distance) {
		adjacencyMatrix[startCity][endCity] = distance;
	}

	public double getDistance(int startCity, int endCity) {
		return adjacencyMatrix[startCity][endCity];
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public double calculateTotalDistance(PathIndividual path) {
		double totalDistance = 0;
		
		for(int i=0 ; i < path.getLength() - 1 ; i++) {
			totalDistance += getDistance(path.getCity(i), path.getCity(i+1));
		}
		
		return totalDistance;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0 ; i < numberOfCities ; i++) {
			for(int j = 0 ; j < numberOfCities ; j++) {
				stringBuilder.append(String.format("%8.2f", getDistance(i, j)));
			}
			stringBuilder.append("\n");
		}
		
		return stringBuilder.toString();
	}
}
