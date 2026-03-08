package patmal.course.enigma.reflector;

import enigma.machine.component.reflector.Reflector;

import java.util.Map;

public class ReflectorToReflectorPersistentEntityConverter {

    public static ReflectorEntity convert(Reflector reflector) {
        ReflectorEntity entity = new ReflectorEntity();
        entity.setReflectorId(reflector.getReflectorId());
        entity.setInput(mapToString(reflector.getMapping(), true));
        entity.setOutput(mapToString(reflector.getMapping(), false));
        return entity;
    }


    private static String mapToString(Map<Integer, Integer> map, boolean keys) {

        StringBuilder sb = new StringBuilder();

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    if (keys) {
                        sb.append(entry.getKey());
                        sb.append(',');
                    } else {
                        sb.append(entry.getValue());
                        sb.append(',');
                    }
                });

        return sb.toString();
    }
}


/*
private final String reflectorId;
private final Map<Integer, Integer> mapping;*/
