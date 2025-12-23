package console;
import dto.DtoMachineSpecification;
import dto.DtoStatistic;
import engine.Engine;
import dto.ProcessRecord;
import exception.fileexception.FileValidationException;
import exception.inputexception.InputValidationException;
import exception.inputexception.InvalidEnigmaCharacterException;

import java.io.IOException;
import java.util.*;

public class ConsoleUI {
    private final Engine engine;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isMachineLoaded = false;
    private boolean isCodeSet = false;

    public ConsoleUI(Engine engine) {
        this.engine = engine;
    }

    void showMenu() {

        boolean exit = false;
        int choice = -1;
        while (!exit) {
            printMainMenu();
            try {
                choice = scanner.nextInt();
                if ((choice > 1 && choice <= 8) && !isMachineLoaded) {
                    throw new InputValidationException("Machine not loaded. Please load a machine from XML first.");
                }
                if (( choice == 5 || choice == 6 || choice == 8) && !isCodeSet) {
                    throw new InputValidationException("Code not set. Please set the code first.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid choice, please enter a number between 1-10.");
                continue;
            } catch (InputValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }


            switch (choice) {
                case 1:

                    handleLoadMachineFromXml();
                    break;
                case 2:
                    handleShowCurrentMachineStatus();

                    break;
                case 3:

                    handleManualCodeSelection();
                    break;
                case 4:
                    handleAutomaticCodeSetup();
                    break;
                case 5:
                    handleProcessInput();
                    break;
                case 6:
                    handleResetToOriginalCode();
                    break;
                case 7:
                    handleShowMachineHistory();
                    break;
                case 8:
                    handleSaveMachineStateToFile();
                    break;
                case 9:
                    handleLoadMachineStateFromFile();
                    break;
                case 10:
                    exit = true;
                    System.out.println("Exiting, goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice, please enter a number between 1-10.");
            }
        }

    }

    private void printMainMenu() {
        System.out.println("===== Enigma Menu =====");
        System.out.println("1. Load machine from XML");
        System.out.println("2. Get current machine status");
        System.out.println("3. Manual code selection");
        System.out.println("4. Automatic code setup");
        System.out.println("5. Process input");
        System.out.println("6. Reset to original code");
        System.out.println("7. Get machine history");
        System.out.println("8. Save machine state to file");
        System.out.println("9. Load machine state from file");
        System.out.println("10. Exit");
        System.out.print("Choose option (1-10): ");
        System.out.println();
    }

    private void handleLoadMachineFromXml() {
        String path = readNonEmptyLine("Please enter the XML file full path:");
        try {
            engine.loadXml(path);
            System.out.println("Machine loaded successfully from: " + path);
            isMachineLoaded = true;
            isCodeSet = false;
        } catch (FileValidationException e) {
            System.out.println("Error loading machine: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleShowCurrentMachineStatus() {
        DtoMachineSpecification spec = engine.showMachineDetails(isCodeSet);
        System.out.println("Current machine status: ");
        System.out.println("Amount of rotors: ");
        System.out.println(spec.getNumOfRotors());
        System.out.println("Amount of reflectors: ");
        System.out.println(spec.getNumOfReflectors());
        System.out.println("Number of processed messages: ");
        System.out.println(spec.getNumOfMessages());
        if (isCodeSet) {
            System.out.println("Original Setting Code:");
            System.out.println(spec.getOriginalCode());
            System.out.println("Current Setting Code");
            System.out.println(spec.getCurrentCode());
        }

    }

    private void handleManualCodeSelection() {
        System.out.println("Please enter the ids of the rotor you want to use " + engine.getNumberOfRotors() + " rotors expected");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        System.out.println("Please enter the initial positions of the rotors (from the ABC):");
        String positionsLine = scanner.nextLine();
        System.out.println("Please enter the ids of the reflector you want to use:");
        System.out.println("1 = I, 2 = II, 3 = III, 4 = IV, 5 = V");

        int reflectorId;
/*        try {*/
            reflectorId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
      /*  } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid choice, the reflector id must be a number between 1-5.");
            return;
        }*/

        System.out.println("Please enter the plugboard settings (e.g. ABCDEF), or leave empty for no plugboard:");
        String plugboardLine = scanner.nextLine();

        try {
            engine.codeManual(line, positionsLine, reflectorId, plugboardLine);
        } catch (InputValidationException e) {
            System.out.println("An error occurred while setting the manual code: " + e.getMessage());
            return;
        }
        isCodeSet = true;
    }

    private void handleAutomaticCodeSetup() {
        String code = engine.codeAuto();
        System.out.println("Automatic code setup successfully: " + code);
        isCodeSet = true;
    }

    private void handleResetToOriginalCode() {
        engine.resetCode();
    }

    private void handleProcessInput() {
        String input = readNonEmptyLine("Please enter the text to process:");

        input = input.toUpperCase();
        try {
            String output = engine.processMessage(input);
            System.out.println(output.toUpperCase());
        } catch (InvalidEnigmaCharacterException e) {
            System.out.println("An error occurred while processing the message: " + e.getMessage());
            return;
        }
    }

    private void handleShowMachineHistory() {
        DtoStatistic statisticsData = engine.statistics();
        printStatistics(statisticsData.getStatisticsData());
    }

    public void printStatistics(Map<String, List<ProcessRecord>> statisticsData) {
        if (statisticsData.isEmpty()) {
            System.out.println("No statistics available.");
            return;
        }
        for (Map.Entry<String, List<ProcessRecord>> entry : statisticsData.entrySet()) {
            String code = entry.getKey();
            List<ProcessRecord> messages = entry.getValue();
            System.out.println("Code: " + code);

            int counter = 1;
            for (ProcessRecord message : messages) {
                System.out.print(counter + ". <" + message.getSorceMessage() + "> --> <" +
                        message.getProcessedMessage()
                        + " (" + message.getTimeInNanos() + " nano-seconds)\n");
                counter++;
            }
        }
    }

    private String readNonEmptyLine(String msg) {
        while (true) {

            if (scanner.hasNextLine()) {  // Clear any leftover newline characters
                String leftover = scanner.nextLine();
                if (!leftover.isEmpty()) {
                    return leftover.trim();
                }
            }

            System.out.println(msg);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Please enter a non-empty line");
            System.out.println(msg);

        }
    }

    private void handleSaveMachineStateToFile() {
        String fullPath = readNonEmptyLine("Please enter the full path of the file you want to save the machine to (without postfix):");
        fullPath += ".bin";
        try {
            engine.saveMachineStateToFile(fullPath);
            System.out.println("Machine state saved successfully to: " + fullPath);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the machine state: " + e.getMessage());
        }
    }

    private void handleLoadMachineStateFromFile() {
        String fullPath = readNonEmptyLine("Please enter the full path of the file you want to load the machine from: (without postfix)");
        fullPath += ".bin";
        try {
            engine.loadMachineStateFromFile(fullPath);
            System.out.println("Machine state loaded successfully from: " + fullPath);
            isMachineLoaded = true;
            isCodeSet = true;
        } catch (IOException e) {
            System.out.println("An error occurred while loading the machine state: " + e.getMessage());
        }


    }
}