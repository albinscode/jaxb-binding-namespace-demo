package fr.albinscode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.junit.Test;

import albinscode.fr._1.main_with_import.Library;

public class UnmarshallImportTest extends AbstractTestUtils {

    
    /**
     * Unmarshall a valid file with jaxb classes built with "import".
     * Books are not properly unmarshalled because of commons.xsd namespace not specified for book elements.
     * @throws JAXBException 
     */
    @Test
    public void testUnmarshallBooksNotWorking() throws JAXBException {
        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/with_import/books-without-namespace.xml"));

        assertTrue(this.hasValidationErrors);

        assertNotNull(library);
        assertNotNull(library.getBooks());
        
        // books are unmarshalled but void
        assertEquals(1, library.getBooks().getBook().size());
        assertNull(library.getBooks().getBook().get(0).getTitle());
    }
    
    /**
     * Unmarshall a valid file with jaxb classes built with "import".
     * Books are properly unmarshalled because namespace is specified in xml file for book elements.
     * @throws JAXBException 
     */
    @Test
    public void testUnmarshallBooksWorking() throws JAXBException {
        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/with_import/books-with-namespace.xml"));

        assertFalse(this.hasValidationErrors);

        assertNotNull(library);
        assertNotNull(library.getBooks());
        
        // books are unmarshalled but void
        assertEquals(1, library.getBooks().getBook().size());
        assertNotNull(library.getBooks().getBook().get(0).getTitle());

        library.getBooks().getBook().stream().forEach( book -> System.out.println(book.getTitle()));;
    }
    
    /**
     * Demonstrates basically that we can access simple types from other
     * namespaces without specifying namespace.
     * @throws JAXBException
     */
    @Test
    public void testUnmarshallAuthors() throws JAXBException {

        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/with_import/authors-only-without-namespace.xml"));

        assertFalse(this.hasValidationErrors);

        assertNotNull(library);
        assertNotNull(library.getAuthors());
        assertEquals(1, library.getAuthors().getAuthor().size());
        assertNotNull(library.getAuthors().getAuthor().get(0));
        
    }
    
    /**
     * Processes a xml file that has books with no namespace prefix with XSL to add prefixes.
     * Then unmarshall it to check it has worked.
     * @throws JAXBException
     * @throws TransformerException 
     * @throws IOException 
     */
    @Test
    public void testUnmarshallBooksWorkingAfterTransformation() throws JAXBException, TransformerException, IOException {

        // transforms the file
        File xmlFile = new File("src/test/resources/with_import/books-without-namespace.xml");
        File transformedFile = File.createTempFile(xmlFile.getName(), "-xsl-transformed");
        this.xslTransform(xmlFile, new File("xsd/add-prefixes.xsl"), transformedFile);

        // unmarshall the file
        Library library = (Library) this.unmarshall(Library.class, transformedFile);

        assertFalse(this.hasValidationErrors);

        assertNotNull(library);
        assertNotNull(library.getBooks());
        
        // books are unmarshalled but void
        assertEquals(1, library.getBooks().getBook().size());
        assertNotNull(library.getBooks().getBook().get(0).getTitle());

        library.getBooks().getBook().stream().forEach( book -> System.out.println(book.getTitle()));;
    }
    
}
