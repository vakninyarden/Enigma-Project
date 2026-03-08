package patmal.course.enigma.reflector;

import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.reflector.ReflectorImpl;

import java.util.HashMap;
import java.util.Map;

public class ReflectorEntityToReflectorConverter {
    public static Reflector convert(ReflectorEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("ReflectorEntity cannot be null");
        }

        Map<Integer, Integer> mapping = buildMapping(
                entity.getInput(),
                entity.getOutput()
        );

        return new ReflectorImpl(
                entity.getReflectorId(),
                mapping
        );
    }
    private static Map<Integer, Integer> buildMapping(String input, String output) {

        Map<Integer, Integer> mapping = new HashMap<>();

        String[] inputParts = input.split(",");
        String[] outputParts = output.split(",");

        if (inputParts.length != outputParts.length) {
            throw new IllegalArgumentException("Input and Output must be the same length");
        }

        for (int i = 0; i < inputParts.length; i++) {

            int in = Integer.parseInt(inputParts[i].trim());
            int out = Integer.parseInt(outputParts[i].trim());

            mapping.put(in, out);
        }

        return mapping;
    }
}



