package engine;

import bte.component.jaxb.BTEEnigma;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


class LoadManager {

    public static BTEEnigma loadXmlToObject(String xmlNameFile) {
        try {
            InputStream inputStream = new FileInputStream(new File(xmlNameFile));
            BTEEnigma machine = deserializeFrom(inputStream);
            cleanMachine(machine);
            return machine;
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BTEEnigma deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(BTEEnigma.class);
        Unmarshaller u = jc.createUnmarshaller();
        return (BTEEnigma) u.unmarshal(in);
    }

    private static void cleanMachine(BTEEnigma machine) {
        if (machine == null) {
            return;
        }

        String abc = machine.getABC();
        if (abc != null) {
            machine.setABC(abc.trim());

        }
    }
};
