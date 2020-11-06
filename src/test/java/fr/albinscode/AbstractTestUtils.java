package fr.albinscode;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * A utility class to marshall/unmarshall objects/files.
 * @author avigier
 *
 */
public class AbstractTestUtils {
    
    protected boolean hasValidationErrors;

    /**
     * Utility to unmarshall a xml file
     * @param clazz
     * @param file
     * @throws JAXBException
     */
    protected Object unmarshall(@SuppressWarnings("rawtypes") Class clazz, File file) throws JAXBException {
        // we reinit the validation errors flag
        this.hasValidationErrors = false;

        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        // this handler is set to display validation errors like namespaces in our case
        jaxbUnmarshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent event) {
                hasValidationErrors = true;
                System.out.println(event.getMessage());
                // we try to step to the next elements
                return true;
            }
        });
        
        return jaxbUnmarshaller.unmarshal(file);
    }
    
}
