package controllers;

import models.Book;
import models.LogBook;
import models.Reader;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Главный контроллер
 * <p>
 * Содержит функции:
 * <p>
 * вывода журнала взятых и возвращённых книг,
 * записи книг на читателя,
 * возврата книги в библиотеку,
 * отображения списка читателей книги,
 * отображения списка книг читателя,
 * поиска читателей по книге,
 * поиска книг по читателю
 */

public class Application extends Controller {

    /**
     * Функция отображает журнал взятых и возвращённых книг
     */
    public static void index() {
        List<LogBook> logBooks = LogBook.find("order by reader, book").fetch();
        render(logBooks);
    }


    /**
     * Функция записи книг на читателя
     * <p>
     * Если книга найдена и она в библиотеке, а также найден читатель,
     * То производится запись книги на читателя в журнал(таблица LogBook)
     * иначе выводится соответствующее сообщение об ошибке
     *
     * @param readerSurname String фамилии читателей
     * @param readerName    String имена читателей
     * @param bookTitle     String названия книг
     * @param bookAuthor    String авторы книг
     */
    public static void takeTheBooks(@Required String readerSurname, @Required String readerName,
                                    @Required String bookTitle, @Required String bookAuthor) {
        if (!Validation.hasErrors()) {
            //сегодняшняя дата
            Date dateToday = Calendar.getInstance().getTime();

            //дата, когда книга должна быть возвращена
            DateTime dateMustReturned = new DateTime(dateToday).plusMonths(1);

            //парсинг строк в массивы
            String[] surnamesArr = readerSurname.split(",");
            String[] namesArr = readerName.split(",");
            String[] titlesArr = bookTitle.split(",");
            String[] authorsArr = bookAuthor.split(",");

            //читатель, на которого будут записаны книги
            Reader reader = ReadersController.searchByNameAndSurname(namesArr[0], surnamesArr[0]);

            //запись книг на читателя
            for (int i = 0; i < namesArr.length; i++) {

                Book book = BooksController.searchBookByTitleAndAuthor(titlesArr[i], authorsArr[i]);

                //если есть читатель, найдена книга и она в библиотеке - записываем
                if (reader != null && book != null && BooksController.checkReturnedBook(book)) {
                    LogBook logBook = new LogBook(reader, book, dateToday, dateMustReturned.toDate(), false);
                    logBook.save();
                }

                //если книга на руках, читатель или книга не найдены - то выводим соответствующее сообщение
                //(надо больше ифов)

                if (reader == null && book == null) {
                    error("Читатель " + namesArr[i] + " " + surnamesArr[i] +
                            " и книга \"" + titlesArr[i] + "\", " + authorsArr[i] + " не найдены");
                }

                if (reader == null) {
                    error("Читатель " + namesArr[i] + " " + surnamesArr[i] + " не найден");
                }

                if (book == null) {
                    error("Книга \"" + titlesArr[i] + "\", " + authorsArr[i] + " не найдена");
                }

                if (BooksController.checkReturnedBook(book)) {
                    error("Книги \"" + titlesArr[i] + "\", " + authorsArr[i] + " нет в наличии");
                }
            }
        } else index();
    }


    /**
     * Возврат книги
     * <p>
     * Изменяет поле returned в таблице LogBook на true
     *
     * @param id Long номер книги, которую надо вернуть
     */
    public static void returnTheBook(Long id) {
        if (id != null) {
            LogBook logBook = LogBook.findById(id);
            logBook.setReturned(true);
            logBook.save();
        }
        index();
    }
}
