package engine;

import dto.*;
import bte.component.jaxb.BTEEnigma;
import dto.ProcessRecord;
import engine.statistic.StatisticsManager;
import enigma.machine.component.keyboard.KeyBoard;
import enigma.machine.component.keyboard.KeyBoardImpl;
import enigma.machine.component.machine.EnigmaMachineImpl;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.setting.Setting;
import enigma.machine.component.setting.SettingImpl;
import repository.Repository;
import validator.InputValidator;
import validator.XmlFileValidator;
import enigma.machine.component.machine.EnigmaMachine;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class EngineImpl implements Engine {
    private final int NUMBER_OF_ROTORS = 3;
    private EnigmaMachine machine;
    private StatisticsManager statisticsManager;
    private Repository repository;
    private static int messageCount = 0;

    public EngineImpl() {
        statisticsManager = new StatisticsManager();
    }

    @Override
    public void loadXml(String path) {
        XmlFileValidator validator = new XmlFileValidator();
        validator.ValidateFilePath(path);
        BTEEnigma bteMachine = LoadManager.loadXmlToObject(path);
        validator.ValidateAll(bteMachine);
        repository = new Repository(bteMachine.getABC(), bteMachine);
        messageCount = 0;
        statisticsManager.resetStatistics();
    }

    @Override
    public DtoMachineSpecification showMachineDetails(boolean isCodeSet) {

        if(!isCodeSet) {
            DtoMachineSpecification dtoMachineSpecification = new DtoMachineSpecification(repository.getRotorCount(),
                    repository.getReflectorCount(),
                    messageCount,
                    "", "");
            return dtoMachineSpecification;
        }
        StringBuilder originalSbString = new StringBuilder();
        Setting code = machine.getSetting();

        BuildOrinigalCodeString(code, originalSbString);

        StringBuilder currentSbString = new StringBuilder();

        BuildCurrentCodeString(code, currentSbString);


        DtoMachineSpecification dtoMachineSpecification = new DtoMachineSpecification(repository.getRotorCount(),
                repository.getReflectorCount(),
                messageCount,
                originalSbString.toString(), currentSbString.toString());

        return dtoMachineSpecification;
    }

    private void BuildCurrentCodeString(Setting machineOrinialCode, StringBuilder currentSbString) {
        BuildRotorsIdString(machineOrinialCode, currentSbString);
        BuildCurrentCode(currentSbString, machineOrinialCode);
        BuildReflectorIdSring(currentSbString, machineOrinialCode);
    }

    private void BuildCurrentCode(StringBuilder currentSbString, Setting machineOrinialCode) {

        currentSbString.append('<');
        List<Setting.RotorPosition> activeRotors = machineOrinialCode.getActiveRotors();
        for (int i = 0; i < activeRotors.size(); i++) {
            int currentPosition = activeRotors.get(i).getRotor().getCurrentPosition();
            currentSbString.append(repository.getRotor(activeRotors.get(i).getRotor().getRotorId()).getRightMapping().get(currentPosition));
            currentSbString.append('(');
            int abcLength = repository.getAbc().length();
            int DistanceFromNotch = (activeRotors.get(i).getRotor().getNotchIndex() - currentPosition + abcLength) % abcLength;
            currentSbString.append(DistanceFromNotch);
            currentSbString.append(')');
            if (i != activeRotors.size() - 1) {
                currentSbString.append(',');
            }
        }
        currentSbString.append('>');
    }

    private void BuildOrinigalCodeString(Setting machineOrinialCode, StringBuilder sb) {
        BuildRotorsIdString(machineOrinialCode, sb);
        BuildOrignialCode(machineOrinialCode, sb);
        BuildReflectorIdSring(sb, machineOrinialCode);
    }

    private static void BuildReflectorIdSring(StringBuilder sb, Setting machineOrinialCode) {
        sb.append('<');
        sb.append(machineOrinialCode.getReflector().getReflectorId());
        sb.append('>');
    }

    private void BuildOrignialCode(Setting machineOrinialCode, StringBuilder sb) {
        sb.append('<');
        for (int i = 0; i < machineOrinialCode.getActiveRotors().size(); i++) {
            int orinigalPosition = machineOrinialCode.getActiveRotors().get(i).getRotor().getOriginalPosition();
            sb.append(repository.getRotor(machineOrinialCode.getActiveRotors().get(i).getRotor().getRotorId()).getRightMapping().get(orinigalPosition));
            sb.append('(');
            int DistanceFromNotch = (machineOrinialCode.getActiveRotors().get(i).getRotor().getNotchIndex() - orinigalPosition + repository.getAbc().length()) % repository.getAbc().length();
            sb.append(DistanceFromNotch);
            sb.append(')');
            if (i != machineOrinialCode.getActiveRotors().size() - 1) {
                sb.append(',');
            }
        }
        sb.append('>');

    }

    private static void BuildRotorsIdString(Setting machineOrinialCode, StringBuilder sb) {
        sb.append('<');
        for (int i = 0; i < machineOrinialCode.getActiveRotors().size(); i++) {
            sb.append(machineOrinialCode.getActiveRotors().get(i).getRotor().getRotorId());
            if (i != machineOrinialCode.getActiveRotors().size() - 1) {
                sb.append(',');
            }
        }
        sb.append('>');

    }

    @Override
    public String processMessage(String message) {

        InputValidator.validateMessageInput(message, repository.getAbc());
        long startTime = System.nanoTime();
        messageCount++;
        char[] result = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            result[i] = machine.processLatter(ch);
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        updateStatistic(message, result, totalTime);

        return new String(result);
    }

    private void updateStatistic(String message, char[] result, long totalTime) {
        ProcessRecord processRecord = new ProcessRecord(message, new String(result), totalTime);
        statisticsManager.addStatistic(processRecord);
    }

    @Override
    public void codeManual(String line, String initialRotorsPositions, int reflectorId) {
        InputValidator inputValidator = new InputValidator();
        inputValidator.validateRotorIds(line);
        List<Integer> rotorIds = Arrays.stream(line.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        inputValidator.validateAllManualCode(rotorIds, repository.getRotorCount(), initialRotorsPositions, repository.getAbc(), reflectorId);
        String reflectorIdStr = intToRoman(reflectorId);
        setMachineSetting(rotorIds, initialRotorsPositions, reflectorIdStr);

        createCodeForStatistic();


    }

    private void createCodeForStatistic() {

        StringBuilder originalCode = new StringBuilder();
        BuildOrinigalCodeString(machine.getSetting(), originalCode);
        statisticsManager.setCurrentCode(originalCode.toString());
    }

    private void setMachineSetting(List<Integer> rotorIds, String initialRotorsPositions, String reflectorId) {
        List<Setting.RotorPosition> activeRotors = new ArrayList<>();
        int size = rotorIds.size();
        for (int i = size - 1; i >= 0; i--) {
            Rotor rotor = repository.getRotor(rotorIds.get(i));
            int position = rotor.getRightMapping().indexOf(initialRotorsPositions.charAt(i));
            Setting.RotorPosition rotorPosition = new Setting.RotorPosition(rotor, position);
            activeRotors.add(rotorPosition);
        }
        Reflector reflector = repository.getReflecton(reflectorId);
        Setting setting = new SettingImpl(reflector, activeRotors);
        KeyBoard keyBoard = new KeyBoardImpl(repository.getAbc());
        machine = new EnigmaMachineImpl(keyBoard, setting);
    }

    @Override
    public String codeAuto() {
        Random rand = new Random();
        String initialRotorsPositions = "";
        int numberOfReflectors = repository.getReflectorCount();
        List<Integer> rotorIds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ROTORS; i++) {
            int minId = 1;
            int maxId = repository.getRotorCount();
            initialRandomRotorId(rotorIds, rand, maxId, minId);
            initialRotorsPositions = initialRandomRotorPosition(initialRotorsPositions, rand);
        }
        int ReflectorId = rand.nextInt((numberOfReflectors)) + 1;
        String id = intToRoman(ReflectorId);
        setMachineSetting(rotorIds, initialRotorsPositions, id);

        StringBuilder originalCode = new StringBuilder();
        BuildOrinigalCodeString(machine.getSetting(), originalCode);
        createCodeForStatistic();
        return originalCode.toString();
    }

    private String initialRandomRotorPosition(String initialRotorsPositions, Random rand) {
        while (initialRotorsPositions.length() < NUMBER_OF_ROTORS) {
            char randomChar = repository.getAbc().charAt(rand.nextInt(repository.getAbc().length()));
            initialRotorsPositions += randomChar;
        }
        return initialRotorsPositions;
    }

    private void initialRandomRotorId(List<Integer> rotorIds, Random rand, int maxId, int minId) {
        while (rotorIds.size() < NUMBER_OF_ROTORS) {
            int randomId = rand.nextInt((maxId - minId) + 1) + minId;
            if (!rotorIds.contains(randomId)) {
                rotorIds.add(randomId);
            }
        }
    }

    @Override
    public void resetCode() {
        machine.resetMachine();
    }

    @Override
    public DtoStatistic statistics() {
        DtoStatistic dtoStatistic = new DtoStatistic(statisticsManager.getStatisticsData());
        return dtoStatistic;
    }

    private String intToRoman(int num) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[num / 1000] +
                hundreds[(num % 1000) / 100] +
                tens[(num % 100) / 10] +
                units[num % 10];
    }

    @Override
    public void saveMachineStateToFile(String path) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(machine);
            out.writeObject(statisticsManager);
            out.writeObject(repository);
        }
    }

    @Override
    public void loadMachineStateFromFile(String path) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            this.machine = (EnigmaMachine) in.readObject();
            this.statisticsManager = (StatisticsManager) in.readObject();
            this.repository = (Repository) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
