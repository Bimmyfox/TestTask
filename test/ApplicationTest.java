import org.junit.*;
import play.cache.Cache;
import play.test.*;
import play.mvc.Http.*;


public class ApplicationTest extends FunctionalTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
        Cache.clear();
    }


    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }


    //--------logBook-----------

    @Test
    public void testReturnBookResponse() {
        Response response = GET("/returnTheBook");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/", response);
    }


    //---------readers----------

    @Test
    public void testThatReadersPageWorks() {
        Response response = GET("/readers");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }



    //--------books-----------
    @Test
    public void testThatBookPageWorks() {
        Response response = GET("/books");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

}