package repository;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class XMLRepository {
    private static Map<String, Repository> repositoryMap = new ConcurrentHashMap<>();


    public static void addRepository(String name, Repository repository) {
        if(repositoryMap.containsKey(name)) {
            throw new IllegalArgumentException("XML with name '" + name + "' already exists.");
        }
        repositoryMap.put(name, repository);
    }

    public static Repository getRepository(String name) {
        if(!repositoryMap.containsKey(name)) {
            throw new IllegalArgumentException("XML with name '" + name + "' not found.");
        }
        return repositoryMap.get(name);
    }
}




