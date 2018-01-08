package cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import tsp.algorithm.Algorithm;
import tsp.algorithm.Algorithm.AlgorithmBuilder;
import tsp.algorithm.crossover.PMXCrossoverOperator;
import tsp.algorithm.mutation.InvertMutationOperator;
import tsp.algorithm.mutation.MutationOperator;
import tsp.algorithm.mutation.SwapMutationOperator;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.algorithm.thread.BestDistanceSampler;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class CommandLineInterface {

	private boolean terminated = false;

	private Scanner scannerSysIn = new Scanner(System.in);

	private AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();

	private BestDistanceSampler bestDistanceSampler = new BestDistanceSampler();

	private AlgorithmBuilder algorithmBuilder = new Algorithm.AlgorithmBuilder();

	private Instance loadedInstance = null;

	private List<MutationOperator> availableMutationOperators = new ArrayList<>();

	public CommandLineInterface() {
		availableMutationOperators.add(new SwapMutationOperator());
		availableMutationOperators.add(new InvertMutationOperator());
	}

	public void enter() {

		algorithmBuilder.crossoverOperator(new PMXCrossoverOperator());
		algorithmBuilder.mutationOperator(new InvertMutationOperator());

		while (!terminated) {
			clearScreen();

			showCurrentConfiguration();
			showMainMenuOptions();
		}
	}

	private void showMainMenuOptions() {
		System.out.println("============================");
		System.out.println("Opcje: ");

		System.out.println("1. Wczytaj instancje z pliku");
		System.out.println("2. Wprowadź kryterium stopu");
		System.out.println("3. Wprowadź wielkosć populacji początkowej");
		System.out.println("4. Wprowadź wielkosć turnieju");
		System.out.println("5. Wprowadź współczynnik mutacji");
		System.out.println("6. Wprowadź współczynnik krzyżowania");
		System.out.println("7. Wybierz metodę mutacji");
		System.out.println("8. Uruchom algorytm");
		System.out.println("9. Wyjdź");

		System.out.println("Twój wybór: ");

		int pickedOne = -1;
		do {
			try {
				pickedOne = Integer.parseInt(scannerSysIn.nextLine());
			} catch (Exception e) {
				pickedOne = 0;
			}
		} while (pickedOne < 1 || pickedOne > 9);

		if (pickedOne == 1) {
			processInstanceLoad();
		} else if (pickedOne == 2) {
			processStopCriteriumConfig();
		} else if (pickedOne == 3) {
			processPopulationSizeConfig();
		} else if (pickedOne == 4) {
			processTournamentSizeConfig();
		} else if (pickedOne == 5) {
			processMutationRateConfig();
		} else if (pickedOne == 6) {
			processCrossoverRateConfig();
		} else if (pickedOne == 7) {
			processMutationOperatorConfig();
		} else if (pickedOne == 8) {
			processAlgorithmRun();
		} else if (pickedOne == 9) {
			terminated = true;
		}

	}

	private void processTournamentSizeConfig() {
		clearScreen();

		System.out.println("Wprowadź wielkość turnieju: ");

		int enteredSize = -1;
		do {
			try {
				enteredSize = Integer.parseInt(scannerSysIn.nextLine());
			} catch (Exception e) {
				enteredSize = 0;
			}
		} while (enteredSize <= 0);

		algorithmBuilder.tournamentSize(enteredSize);
	}

	private void processMutationOperatorConfig() {
		clearScreen();
		for (int i = 0; i < availableMutationOperators.size(); i++) {
			System.out.println(i + ") " + availableMutationOperators.get(i));
		}

		System.out.println("Twój wybór: ");

		int pickedOne = -1;
		do {
			pickedOne = scannerSysIn.nextInt();
		} while (pickedOne < 0 || pickedOne >= availableMutationOperators.size());

		algorithmBuilder.mutationOperator(availableMutationOperators.get(pickedOne));
	}

	private void processCrossoverRateConfig() {
		clearScreen();

		System.out.println("Wprowadź współczynnik krzyżowania: ");

		double enteredCrossoverRate = -1;
		do {
			try {
				enteredCrossoverRate = Double.parseDouble(scannerSysIn.nextLine());
			} catch (Exception e) {
				enteredCrossoverRate = 0;
			}
		} while (enteredCrossoverRate <= 0);

		algorithmBuilder.crossoverRate(enteredCrossoverRate);
	}

	private void processMutationRateConfig() {
		clearScreen();

		System.out.println("Wprowadź współczynnik mutacji: ");

		double enteredMutationRate = -1;
		do {
			try {
				enteredMutationRate = Double.parseDouble(scannerSysIn.nextLine());
			} catch (Exception e) {
				enteredMutationRate = 0;
			}
		} while (enteredMutationRate <= 0);

		algorithmBuilder.mutationRate(enteredMutationRate);
	}

	private void processPopulationSizeConfig() {
		clearScreen();

		System.out.println("Wprowadź wielkość populacji: ");

		int enteredSize = -1;
		do {
			try {
				enteredSize = Integer.parseInt(scannerSysIn.nextLine());
			} catch (Exception e) {
				enteredSize = 0;
			}
		} while (enteredSize <= 0);

		algorithmBuilder.populationSize(enteredSize);
	}

	private void processAlgorithmRun() {
		if (loadedInstance != null) {
			
			setupChart();

			Algorithm algorithm = algorithmBuilder.algorithmTerminator(algorithmTerminator)
					.bestDistanceSampler(bestDistanceSampler).build();
			algorithm.execute(loadedInstance);

			processPressToContinue();
		} else {
			System.out.println("Najpierw wczytaj instancję.");
			processPressToContinue();
		}
	}
	
	private void setupChart() {
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Random");
		xySeriesCollection.addSeries(series);

		JFreeChart jfreechart = ChartFactory.createXYLineChart("Długość najlepszej znalezionej trasy od czasu",
				"Czas[s]", "Długość", (XYDataset) xySeriesCollection, PlotOrientation.VERTICAL, true, true, false);

		ChartFrame cf = new ChartFrame("D³ugoœæ najlepszej znalezionej trasy po czasie", jfreechart);
		cf.setSize(800, 600);
		cf.show();
		bestDistanceSampler.setPlotSerie(series);

	}

	private void processPressToContinue() {
		System.out.println("Wciśnij enter aby kontynuować...");
		scannerSysIn.nextLine();
	}

	private void processInstanceLoad() {
		clearScreen();
		System.out.println("Nazwa pliku (ścieżka względna): ");

		String enteredFilename = scannerSysIn.nextLine();

		InstanceFileReader instanceFileReader = new InstanceFileReader();
		try {
			loadedInstance = instanceFileReader.read(enteredFilename);
		} catch (Exception e) {
			System.out.println("Instancja nie mogła zostać wczytana.");
			processPressToContinue();
			return;
		}

		System.out.println("Instancja wczytana pomyślnie");
		processPressToContinue();
	}

	private void processStopCriteriumConfig() {
		clearScreen();

		System.out.println("Wprowadź limit czasowy wykonywania algorytmu (w sekundach): ");

		long enteredTimeSeconds = -1;
		do {
			try {
				enteredTimeSeconds = Long.parseLong(scannerSysIn.nextLine());
			} catch (Exception e) {
				enteredTimeSeconds = 0;
			}
		} while (enteredTimeSeconds <= 0);

		algorithmTerminator.setTimeLimitMs(enteredTimeSeconds * 1000);
	}

	private void showCurrentConfiguration() {
		System.out.println("Instancja:" + ((loadedInstance == null) ? "Brak" : loadedInstance.getName()));
		System.out.println("");
		System.out.println("Zatrzymaj po: " + algorithmTerminator.getTimeLimitMs() / 1000 + "s");
		System.out.println(algorithmBuilder);
	}

	private void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}
}
