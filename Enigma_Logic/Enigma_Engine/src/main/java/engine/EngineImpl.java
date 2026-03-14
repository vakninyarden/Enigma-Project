package engine;

import dto.DtoMachineSpecification;
import dto.config.details.MachineSnapshot;
import dto.config.details.RotorSnapshot;
import dto.history.ProcessRecord;
import enigma.machine.component.keyboard.KeyBoard;
import enigma.machine.component.keyboard.KeyBoardImpl;
import enigma.machine.component.machine.EnigmaMachine;
import enigma.machine.component.machine.EnigmaMachineImpl;
import enigma.machine.component.plugboard.PlugBoard;
import enigma.machine.component.plugboard.PlugBoardImpl;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.setting.Setting;
import enigma.machine.component.setting.SettingImpl;
import repository.Repository;
import validator.InputValidator;
import validator.OrderOperationValidator;

import java.util.*;
import java.util.stream.Collectors;


public class EngineImpl implements Engine {
    private final InputValidator inputValidator;
    boolean isCodeSet = false;
    private final OrderOperationValidator orderOperationValidator;

    private int messageCount = 0;
    private EnigmaMachine machine;
    private Repository repository;


    public EngineImpl(Repository repository) {
        this.repository = repository;
        this.inputValidator = new InputValidator();
        this.orderOperationValidator = new OrderOperationValidator();

    }


    @Override
    public DtoMachineSpecification showMachineDetails() {
        if (!isCodeSet) {
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
        BuildPlugBoardString(machineOrinialCode, currentSbString);

    }

    //this function build the current position of the rotors in the format of <Letter(DistanceFromNotch),Letter(DistanceFromNotch),...>
    private void BuildCurrentCode(StringBuilder currentSbString, Setting machineOrinialCode) {

        currentSbString.append('<');
        List<Setting.RotorPosition> activeRotors = machineOrinialCode.getActiveRotors();
        int abcLength = repository.getAbc().length();
        for (int i = 0; i < activeRotors.size(); i++) {
            Rotor rotor = activeRotors.get(i).getRotor();
            int currentPosition = rotor.getCurrentPosition();
            currentSbString.append(rotor.getRightMapping().get(currentPosition));
            currentSbString.append('(');
            int DistanceFromNotch = (rotor.getNotchIndex() - currentPosition + abcLength) % abcLength;
            currentSbString.append(DistanceFromNotch);
            currentSbString.append(')');
            if (i != activeRotors.size() - 1) {
                currentSbString.append(',');
            }
        }
        currentSbString.append('>');
    }

    private void BuildOrinigalCodeString(Setting machineOrinialCode, StringBuilder originalSbCode) {
        BuildRotorsIdString(machineOrinialCode, originalSbCode);
        BuildOrignialCode(machineOrinialCode, originalSbCode);
        BuildReflectorIdSring(originalSbCode, machineOrinialCode);
        BuildPlugBoardString(machineOrinialCode, originalSbCode);

    }

    private static void BuildReflectorIdSring(StringBuilder sb, Setting machineOrinialCode) {
        sb.append('<');
        sb.append(machineOrinialCode.getReflector().getReflectorId());
        sb.append('>');
    }

    //this function build the current position of the rotors in the format of <Letter(DistanceFromNotch),Letter(DistanceFromNotch),...>
    private void BuildOrignialCode(Setting machineOrinialCode, StringBuilder sb) {
        sb.append('<');
        List<Setting.RotorPosition> activeRotors = machineOrinialCode.getActiveRotors();
        int abcLength = repository.getAbc().length();

        for (int i = 0; i < activeRotors.size(); i++) {
            Rotor rotor = activeRotors.get(i).getRotor();
            int orinigalPosition = rotor.getOriginalPosition();
            sb.append(rotor.getRightMapping().get(orinigalPosition));
            sb.append('(');
            int DistanceFromNotch = (rotor.getNotchIndex() - orinigalPosition + abcLength) % abcLength;
            sb.append(DistanceFromNotch);
            sb.append(')');
            if (i != activeRotors.size() - 1) {
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
    public ProcessRecord processMessage(String message) {
        orderOperationValidator.validateCodeSet(isCodeSet);
        StringBuilder currentCode = new StringBuilder();
        Setting code = machine.getSetting();
        BuildCurrentCodeString(code, currentCode);

        message = message.toUpperCase();
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
        // updateStatistic(message, result, totalTime);
        String machineName = repository.getMachineName();


        return new ProcessRecord(message, new String(result), totalTime, currentCode.toString(), machineName);
        // return new String(result);
    }


    @Override
    public void codeManual(String line, String initialRotorsPositions, int reflectorId, String plugboardInput) {
        initialRotorsPositions = initialRotorsPositions.toUpperCase();
        plugboardInput = plugboardInput.toUpperCase();
        //InputValidator inputValidator = new InputValidator();
        inputValidator.validateRotorIds(line, repository.getNumberOfRotors());
        List<Integer> rotorIds = Arrays.stream(line.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        inputValidator.validateAllManualCode(rotorIds, repository.getRotorCount(), initialRotorsPositions, repository.getAbc(), reflectorId, plugboardInput);
        String reflectorIdStr = intToRoman(reflectorId);

        Map<Character, Character> plugboardMap = buildPlugboardMap(plugboardInput);
        setMachineSetting(rotorIds, initialRotorsPositions, reflectorIdStr, plugboardMap);

        isCodeSet = true;
    }


    private void setMachineSetting(List<Integer> rotorIds, String initialRotorsPositions, String reflectorId, Map<Character, Character> plugboardMap) {
        List<Setting.RotorPosition> activeRotors = new ArrayList<>();
        int size = rotorIds.size();
        for (int i = size - 1; i >= 0; i--) {
            Rotor rotor = repository.getRotor(rotorIds.get(i));
            int position = rotor.getRightMapping().indexOf(initialRotorsPositions.charAt(i));
            Setting.RotorPosition rotorPosition = new Setting.RotorPosition(rotor, position);
            activeRotors.add(rotorPosition);
        }

        Reflector reflector = repository.getReflecton(reflectorId);
        PlugBoard plugBoard = new PlugBoardImpl(plugboardMap);
        Setting setting = new SettingImpl(reflector, activeRotors, plugBoard);
        KeyBoard keyBoard = new KeyBoardImpl(repository.getAbc());
        machine = new EnigmaMachineImpl(keyBoard, setting);
    }


    @Override
    public String codeAuto() {
        Random rand = new Random();
        String initialRotorsPositions = "";
        int numberOfReflectors = repository.getReflectorCount();
        List<Integer> rotorIds = new ArrayList<>();
        for (int i = 0; i < repository.getNumberOfRotors(); i++) {
            int minId = 1;
            int maxId = repository.getRotorCount();
            initialRandomRotorId(rotorIds, rand, maxId, minId);
            initialRotorsPositions = initialRandomRotorPosition(initialRotorsPositions, rand);
        }
        int ReflectorId = rand.nextInt((numberOfReflectors)) + 1;
        String id = intToRoman(ReflectorId);

        Map<Character, Character> plugboardMap = BuildAutoPlugBoard();
        setMachineSetting(rotorIds, initialRotorsPositions, id, plugboardMap);

        StringBuilder originalCode = new StringBuilder();
        BuildOrinigalCodeString(machine.getSetting(), originalCode);

        isCodeSet = true;
        return originalCode.toString();
    }

    private String initialRandomRotorPosition(String initialRotorsPositions, Random rand) {
        while (initialRotorsPositions.length() < repository.getNumberOfRotors()) {
            char randomChar = repository.getAbc().charAt(rand.nextInt(repository.getAbc().length()));
            initialRotorsPositions += randomChar;
        }
        return initialRotorsPositions;
    }

    private void initialRandomRotorId(List<Integer> rotorIds, Random rand, int maxId, int minId) {
        while (rotorIds.size() < repository.getNumberOfRotors()) {
            int randomId = rand.nextInt((maxId - minId) + 1) + minId;
            if (!rotorIds.contains(randomId)) {
                rotorIds.add(randomId);
            }
        }
    }

    @Override
    public void resetCode() {
        orderOperationValidator.validateCodeSet(isCodeSet);
        machine.resetMachine();
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


    public Map<Character, Character> buildPlugboardMap(String input) {
        Map<Character, Character> plugboard = new HashMap<>();

        if (input == null || input.isEmpty()) {
            return plugboard;
        }

        for (int i = 0; i < input.length(); i += 2) {
            char a = input.charAt(i);
            char b = input.charAt(i + 1);

            plugboard.put(a, b);
            plugboard.put(b, a);
        }

        return plugboard;

    }


    Map<Character, Character> BuildAutoPlugBoard() {
        Map<Character, Character> plugboardMap = new HashMap<>();
        Random rand = new Random();
        int numberOfPairs = rand.nextInt((repository.getAbc().length() / 2) + 1);
        Set<Character> usedChars = new HashSet<>();

        while (plugboardMap.size() / 2 < numberOfPairs) {
            char char1 = repository.getAbc().charAt(rand.nextInt(repository.getAbc().length()));
            char char2 = repository.getAbc().charAt(rand.nextInt(repository.getAbc().length()));

            if (char1 != char2 && !usedChars.contains(char1) && !usedChars.contains(char2)) {
                plugboardMap.put(char1, char2);
                plugboardMap.put(char2, char1);
                usedChars.add(char1);
                usedChars.add(char2);
            }
        }
        return plugboardMap;
    }

    private void BuildPlugBoardString(Setting machineOrinialCode, StringBuilder stringBuilder) {
        if (machineOrinialCode.getPlugboard().getPlugboardMap().isEmpty()) {
            return;
        }
        Set<Character> SeemCharsInPlugBoard = new HashSet<>();
        stringBuilder.append('<');
        Map<Character, Character> plugboardMap = new HashMap<>();
        for (Map.Entry<Character, Character> entry : machineOrinialCode.getPlugboard().getPlugboardMap().entrySet()) {
            char key = entry.getKey();
            char value = entry.getValue();
            if (!SeemCharsInPlugBoard.contains(key)) {
                SeemCharsInPlugBoard.add(value);
                stringBuilder.append(key);
                stringBuilder.append("|");
                stringBuilder.append(value);
                stringBuilder.append(",");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append('>');
    }


    public String getCurrentRotorPositions() {
        StringBuilder currentSbString = new StringBuilder();
        BuildCurrentCodeString(machine.getSetting(), currentSbString);
        return currentSbString.toString();
    }

    @Override
    public MachineSnapshot getMachineSnapshot() {
        if (!isCodeSet) {
            MachineSnapshot snapshot = MachineSnapshot.builder()
                    .totalRotors(repository.getRotorCount())
                    .totalReflectors(repository.getReflectorCount())
                    .totalProcessedMessages(messageCount)
                    .build();
            return snapshot;
        }


        Setting setting = machine.getSetting();
        List<RotorSnapshot> rotorSnapshots = new ArrayList<>();
        for (Setting.RotorPosition rotorPosition : setting.getActiveRotors()) {
            Rotor rotor = rotorPosition.getRotor();

            int originalPos = rotor.getOriginalPosition();
            int currentPos = rotor.getCurrentPosition();

            char originalLetter =
                    repository.getRotor(rotor.getRotorId())
                            .getRightMapping()
                            .get(originalPos);

            char currentLetter = rotor.getRightMapping().get(currentPos);

            rotorSnapshots.add(
                    RotorSnapshot.builder()
                            .rotorId(rotor.getRotorId())
                            .originalPosition(rotor.getOriginalPosition())
                            .currentPosition(rotor.getCurrentPosition())
                            .originalLetter(originalLetter)
                            .currentLetter(currentLetter)
                            .notchIndex(rotor.getNotchIndex())
                            .alphabetSize(rotor.getRightMapping().size())
                            .build()
            );
        }
        StringBuilder originalCode = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        BuildOrinigalCodeString(setting, originalCode);
        BuildCurrentCodeString(setting, currentCode);

        Map<Character, Character> plugboardMap = setting.getPlugboard().getPlugboardMap();


        return MachineSnapshot.builder()
                .totalRotors(repository.getRotorCount())
                .totalReflectors(repository.getReflectorCount())
                .totalProcessedMessages(messageCount)
                .originalCodeCompact(originalCode.toString())
                .currentCodeCompact(currentCode.toString())
                .rotors(rotorSnapshots)
                .reflectorId(setting.getReflector().getReflectorId())
                .plugboard(plugboardMap)
                .build();
    }


}
