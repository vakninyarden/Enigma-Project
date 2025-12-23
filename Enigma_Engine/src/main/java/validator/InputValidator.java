package validator;

import exception.inputexception.*;

import java.util.List;

public class InputValidator {
    public static void validateMessageInput(String massege, String abc) {
        for (char c : massege.toCharArray()) {
            if (!abc.contains(String.valueOf(c))) {
                throw new InvalidEnigmaCharacterException(c);
            }
        }
    }

    private void validateDuplicateRotorIds(List<Integer> rotorIds) {
        java.util.Set<Integer> seen = new java.util.HashSet<>();
        for (Integer id : rotorIds) {
            if (id == null) continue;
            if (!seen.add(id)) {
                throw new DuplicateRotorException(id.toString());
            }
        }
    }

    private void validateReflectorSelecttion(int reflectorId) {
        if (reflectorId <= 0 || reflectorId > 5) throw new InvalidReflectorSelectionException("" + reflectorId);
    }

    public void validateRotorIds(String input) {

        if (input == null || input.trim().isEmpty()) {
            throw new TooFewRotorsSelectedException("0");
        }

        String[] parts = input.split(",");

        for (String part : parts) {
            String trimmed = part.trim();

            if (trimmed.isEmpty()) {
                throw new NonNumericRotorIdException(part);
            }

            try {
                Integer.parseInt(trimmed);
            } catch (NumberFormatException e) {
                throw new NonNumericRotorIdException(part);
            }
        }

        if (parts.length != 3) {
            if (parts.length > 3) throw new TooManyRotorsSelectedException("you selected" + parts.length + " rotors");
            else throw new TooFewRotorsSelectedException("you selected" + parts.length + " rotors");
        }

    }

    private void validateRotorExistence(List<Integer> rotorIds, int sizeOfRotors) {
        for (Integer id : rotorIds) {
            if (id <= 0 || id > sizeOfRotors) {
                throw new RotorNotFoundException(id.toString());
            }
        }
    }

    private void validatePositionsLength(String positionsInput, int expectedLength, String abc) {
        if (positionsInput.length() != expectedLength) {
            throw new PositionLengthMismatchException("expected length: " + expectedLength + ", but got: " + positionsInput.length());
        }
        for (Character character : positionsInput.toCharArray()) {
            if (!abc.contains(String.valueOf(character))) {
                throw new InvalidEnigmaCharacterException(character);
            }
        }
    }

    public void validateAllManualCode(List<Integer> rotorIds, int sizeOfRotors, String positionsInput, String abc, int reflectorId) {
        validateRotorExistence(rotorIds, sizeOfRotors);
        validateDuplicateRotorIds(rotorIds);
        validatePositionsLength(positionsInput, rotorIds.size(), abc);
        validateReflectorSelecttion(reflectorId);
    }


}
