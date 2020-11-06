package fr.albinscode;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.junit.jupiter.api.Test;

import albinscode.fr._1.main.Library;

public class UnmarshallTest {

    
    /**
     * Unmarshall a valid file with jaxb classes built with "import".
     * Books are not properly unmarshalled because of commons.xsd namespace not specified
     * @throws JAXBException 
     */
    @Test
    public void testUnmarshalBooksImportNotWorking() throws JAXBException {
        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/books-without-namespace.xml"));

        assertNotNull(library);
        assertNotNull(library.getBooks());
        
        // books are unmarshalled but void
        assertEquals(1, library.getBooks().getBook().size());
        assertNull(library.getBooks().getBook().get(0).getTitle());
    }
    
    /**
     * Unmarshall a valid file with jaxb classes built with "import".
     * Books are properly unmarshalled because namespace is specified in xml file.
     * @throws JAXBException 
     */
    @Test
    public void testUnmarshalBooksImportWorking() throws JAXBException {
        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/books-with-namespace.xml"));

        assertNotNull(library);
        assertNotNull(library.getBooks());
        
        // books are unmarshalled but void
        assertEquals(1, library.getBooks().getBook().size());
        assertNotNull(library.getBooks().getBook().get(0).getTitle());

        library.getBooks().getBook().stream().forEach( book -> System.out.println(book.getTitle()));;
    }
    
    /**
     * Demonstrates basically that we can access simple types from other
     * namespaces without specifying namespace
     * @throws JAXBException
     */
    @Test
    public void testUnmarshallAuthors() throws JAXBException {

        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/authors-only-without-namespace.xml"));

        assertNotNull(library);
        assertNotNull(library.getAuthors());
        assertEquals(1, library.getAuthors().getAuthor().size());
        assertNotNull(library.getAuthors().getAuthor().get(0));
        
    }
    
    
    /**
     * Utility to unmarshall a xml file
     * @param clazz
     * @param file
     * @throws JAXBException
     */
    protected Object unmarshall(@SuppressWarnings("rawtypes") Class clazz, File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        // this handler is set to display validation errors like namespaces in our case
        jaxbUnmarshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent event) {
                System.out.println(event.getMessage());
                // we try to step to the next elements
                return true;
            }
        });
        
        return jaxbUnmarshaller.unmarshal(file);
    }
}
