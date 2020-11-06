package fr.albinscode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * A utility class to marshall/unmarshall objects/files.
 * 
 * @author avigier
 *
 */
public class AbstractTestUtils {

    protected boolean hasValidationErrors;

    /**
     * Utility to unmarshall a xml file
     * 
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

    /**
     * Allows to apply an xsl transformation on the input XML file.
     * The aim is to add missing namespaces due to XSD refactoring.
     * @param xmlSourceFile
     * @param xslFile
     * @param xmlDestFile
     * @throws TransformerException
     * @throws IOException 
     */
    protected void xslTransform(File xmlSourceFile, File xslFile, File xmlDestFile) throws TransformerException, IOException {
        StreamSource source = new StreamSource(xmlSourceFile);
        StreamSource stylesource = new StreamSource(xslFile);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(stylesource);

        StreamResult result = new StreamResult(xmlDestFile);
        transformer.transform(source, result);
        
        System.out.println(String.format("Xml file %s generated after xsl transformation", xmlDestFile.getAbsolutePath()));
        Files.readAllLines(xmlDestFile.toPath()).stream().forEach(line -> System.out.println(line));;
    }

}
