package engine;

import bte.component.jaxb.BTEEnigma;
import dto.LoadMachineCommand;
import dto.LoadMachineResult;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repository.Repository;
import repository.XMLRepository;
import validator.XmlFileValidator;


// -------------------- TO THINK -------------
// recomanded : change the functions that they get inputstream instead of file name (string)
// to let the controller to pass th file content diractly to the load
// IN SPRING BOOT WE PREPFRE WORK WITH NONE STATIC FUNCTIONS AND AUTOWIRE THE LOAD MANAGER TO THE ENGINE IMPL
@Service
public class LoadManager {
    @Autowired
    private XmlFileValidator xmlValidator;


    public  BTEEnigma loadXmlToObject(String xmlNameFile) {
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


    // THIS FUCNTION ALLOWS TO THE WEB SERVER TO LOAD FILES FROM THE BROWSER
    // מתודה חדשה וקריטית למטלה 3 (תשמש את ה-Controller)
    public BTEEnigma loadXmlFromStream(InputStream inputStream) {
        try {
            BTEEnigma machine = deserializeFrom(inputStream);
            cleanMachine(machine);
            return machine;
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse XML", e);
        }
    }

    private  BTEEnigma deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(BTEEnigma.class);
        Unmarshaller u = jc.createUnmarshaller();
        return (BTEEnigma) u.unmarshal(in);
    }

    private  void cleanMachine(BTEEnigma machine) {
        if (machine == null) {
            return;
        }

        String abc = machine.getABC();
        if (abc != null) {
            machine.setABC(abc.trim());

        }
    }

    public LoadMachineResult loadXml(LoadMachineCommand command) {
        InputStream inputStream=command.getInputStream();
        BTEEnigma bteMachine = loadXmlFromStream(inputStream);

        xmlValidator.ValidateAll(bteMachine);
       int numberofRotors = bteMachine.getRotorsCount().intValue();
       // NUMBER_OF_ROTORS = bteMachine.getRotorsCount().intValue();
        Repository repository = new Repository(bteMachine.getABC(), bteMachine, numberofRotors);
       // this.messageCount = 0;
        // statisticsManager.resetStatistics();

        String machineName = bteMachine.getName();
        XMLRepository.addRepository(machineName, repository);
       // isMachineLoaded = true;
        return new LoadMachineResult(machineName);
    }

};
