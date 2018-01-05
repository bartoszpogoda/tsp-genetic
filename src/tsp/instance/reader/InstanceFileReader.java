package tsp.instance.reader;

import java.io.File;
import java.io.IOException;

import org.jorlib.io.tspLibReader.TSPLibInstance;
import org.jorlib.io.tspLibReader.graph.DistanceTable;

import tsp.instance.Instance;

/***
 * An interface for jorlib library used for data reading
 * 
 * @author Student225988
 */
public class InstanceFileReader {

	public Instance read(String fileName) throws IOException {
		File instnaceFile = new File(fileName);
		TSPLibInstance readInstance = new TSPLibInstance(instnaceFile);

		return convert(readInstance.getDistanceTable(), fileName);
	}

	private Instance convert(DistanceTable distanceTable, String instanceName) {
		int instanceSize = distanceTable.listNodes().length;

		Instance instance = new Instance(instanceSize);
		instance.setName(instanceName);
		
		for (int i = 0; i < instanceSize; i++) {
			for (int j = 0; j < instanceSize; j++) {
				instance.setDistance(i, j, distanceTable.getDistanceBetween(i, j));
			}
		}

		return instance;
	}

}
