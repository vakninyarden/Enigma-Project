package dto;

public class DtoMachineSpecification {
    int numOfRotors;
    int numOfReflectors;
    int numOfMessages;
    String currentCode;
    String originalCode;

    public DtoMachineSpecification(int numOfRotors, int numOfReflectors, int numOfMessages, String originalCode, String currentCode) {
        this.numOfRotors = numOfRotors;
        this.numOfReflectors = numOfReflectors;
        this.numOfMessages = numOfMessages;
        this.currentCode = currentCode;
        this.originalCode = originalCode;

    }


    public String getOriginalCode() {
        return originalCode;
    }

}
