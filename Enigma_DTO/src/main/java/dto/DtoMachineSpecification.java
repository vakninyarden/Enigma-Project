package dto;

public class DtoMachineSpecification {
    int numOfRotors;
    int numOfReflectors;
    int numOfMessages;
    String currentCode;
    String originalCode;

    public DtoMachineSpecification(int numOfRotors, int numOfReflectors, int numOfMessages, String originalCode,String currentCode) {
        this.numOfRotors = numOfRotors;
        this.numOfReflectors = numOfReflectors;
        this.numOfMessages = numOfMessages;
        this.currentCode = currentCode;
        this.originalCode = originalCode;
    }


    public int getNumOfRotors() {
        return numOfRotors;
    }

    public int getNumOfReflectors() {
        return numOfReflectors;
    }

    public int getNumOfMessages() {
        return numOfMessages;
    }

    public String getCurrentCode() {
        return currentCode;
    }

    public String getOriginalCode() {
        return originalCode;
    }

}
