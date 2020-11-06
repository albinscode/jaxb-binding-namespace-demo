package fr.albinscode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import albinscode.fr._1.main_with_include.Library;

public class UnmarshallIncludeTest extends AbstractTestUtils {

    
    
    /**
     * Unmarshall a valid file with jaxb classes built with "include".
     * Books are properly unmarshalled because no namespace is needed for commons inclusion.
     * @throws JAXBException 
     */
    @Test
    public void testUnmarshallBooksWorking() throws JAXBException {
        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/with_include/books-without-namespace.xml"));

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

        Library library = (Library) this.unmarshall(Library.class, new File("src/test/resources/with_include/authors-only-without-namespace.xml"));

        assertFalse(this.hasValidationErrors);

        assertNotNull(library);
        assertNotNull(library.getAuthors());
        assertEquals(1, library.getAuthors().getAuthor().size());
        assertNotNull(library.getAuthors().getAuthor().get(0));

        System.out.println(library.getAuthors().getAuthor().get(0));
    }
    
    
}
