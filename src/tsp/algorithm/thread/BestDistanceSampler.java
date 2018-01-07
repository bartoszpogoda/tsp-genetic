package tsp.algorithm.thread;

import java.io.PrintWriter;

import org.jfree.data.xy.XYSeries;

import tsp.algorithm.Algorithm;

/***
 * Thread that samples current best distance and saves new probes to the
 * referenced BestDistanceHistory
 * 
 * @author Student225988
 *
 */
public class BestDistanceSampler extends Thread {

	private volatile boolean running = true;

	private int samplingIntervalMs = 1000;

	private XYSeries plotSerie;
	private Algorithm algorithm;
	
	private String filename = "sampling.txt";

	public BestDistanceSampler() {
		
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void setPlotSerie(XYSeries plotSerie) {
		this.plotSerie = plotSerie;
	}

	public void setSamplingInterval(int ms) {
		samplingIntervalMs = ms;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void terminate() {
		running = false;
	}

	@Override
	public void run() {

		PrintWriter writer = null;
		PrintWriter writerEvery30s = null;
		try {
			writer = new PrintWriter(filename + ".txt", "UTF-8");
			writerEvery30s = new PrintWriter(filename + "_tiny" + ".txt", "UTF-8");
		} catch (Exception e) {
			return;
		}

		int timeInMs = 0;
		while (running) {
			try {
				double currentBestDistance = algorithm.getCurrentBestDistance();
				writer.println(timeInMs + ";" + (int)currentBestDistance);
				System.out.println(timeInMs + ";" + (int)currentBestDistance);
				
				if(timeInMs % 30000 == 0) {
					writerEvery30s.println(timeInMs + ";" + (int)currentBestDistance);
				}
				
				if (plotSerie != null) {
					plotSerie.add(timeInMs/1000, currentBestDistance);
				}
				
				Thread.sleep(samplingIntervalMs);
				timeInMs += samplingIntervalMs;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		writer.close();
		writerEvery30s.close();
	}

	
}
