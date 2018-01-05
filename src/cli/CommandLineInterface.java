package cli;

import java.util.Scanner;
import tsp.algorithm.thread.AlgorithmTerminator;
import tsp.instance.Instance;
import tsp.instance.reader.InstanceFileReader;

public class CommandLineInterface {

	private boolean terminated = false;

	private Scanner scannerSysIn = new Scanner(System.in);

	private AlgorithmTerminator algorithmTerminator = new AlgorithmTerminator();

	private Instance loadedInstance = null;

	public CommandLineInterface() {
	}

	public void enter() {

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
		System.out.println("2. Wprowad� kryterium stopu");
		System.out.println("3. Wprowad� wielkos� populacji pocz�tkowej");
		System.out.println("4. Wprowad� wsp�czynnik mutacji");
		System.out.println("5. Wprowad� wsp�czynnik krzy�owania");
		System.out.println("6. Wybierz metod� mutacji");
		System.out.println("7. Uruchom algorytm");
		System.out.println("8. Wyjd�");

		System.out.println("Tw�j wyb�r: ");

		int pickedOne = -1;
		do {
			try {
				pickedOne = Integer.parseInt(scannerSysIn.nextLine());
			} catch (Exception e) {
				pickedOne = 0;
			}
		} while (pickedOne < 1 || pickedOne > 7);

		if (pickedOne == 1) {
			processInstanceLoad();
		} else if (pickedOne == 2) {
			processStopCriteriumConfig();
		} else if (pickedOne == 3) {

		} else if (pickedOne == 4) {

		} else if (pickedOne == 5) {

		} else if (pickedOne == 6) {

		} else if (pickedOne == 7) {
			processAlgorithmRun();
		} else if (pickedOne == 8) {
			terminated = true;
		}

	}

	private void processAlgorithmRun() {
		if (loadedInstance != null) {

			processPressToContinue();
		} else {
			System.out.println("Najpierw wczytaj instancj�.");
			processPressToContinue();
		}
	}

	private void processPressToContinue() {
		System.out.println("Wci�nij enter aby kontynuowa�...");
		scannerSysIn.nextLine();
	}

	private void processInstanceLoad() {
		clearScreen();
		System.out.println("Nazwa pliku (�cie�ka wzgl�dna): ");

		String enteredFilename = scannerSysIn.nextLine();

		InstanceFileReader instanceFileReader = new InstanceFileReader();
		try {
			loadedInstance = instanceFileReader.read(enteredFilename);
		} catch (Exception e) {
			System.out.println("Instancja nie mog�a zosta� wczytana.");
			processPressToContinue();
			return;
		}

		System.out.println("Instancja wczytana pomy�lnie");
		processPressToContinue();
	}

	private void processStopCriteriumConfig() {
		clearScreen();

		System.out.println("Wprowad� limit czasowy wykonywania algorytmu (w sekundach): ");

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
	}

	private void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}
}
