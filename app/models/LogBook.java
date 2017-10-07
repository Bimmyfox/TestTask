package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Класс-модель журнал
 * <p>
 * Содержит поля:
 * <p>
 * reader - объект класса Reader, связь многие-к-одному
 * book - объект класса Book, связь многие-к-одному
 * dateOfBookWasTaken - дата, когда книга была взята читателем в библиотке
 * dateOfBookWasReturned - дата, когда книгу необходимо вернуть в библиотеку
 */
@Entity
@Table(name = "log_book")
public class LogBook extends Model {

    @Required(message = "Поле 'Читатель' не должно быть пустым")
    @ManyToOne
    private Reader reader;

    @Required(message = "Поле 'Книга' не должно быть пустым")
    @ManyToOne
    private Book book;

    @Required(message = "Поле 'Дата взятия книги' не должно быть пустым")
    @Temporal(TemporalType.DATE)
    private Date dateOfBookWasTaken;

    @Required(message = "Поле 'Дата возрата книги' не должно быть пустым")
    @Temporal(TemporalType.DATE)
    private Date dateOfBookWasReturned;

    @Required(message = "Поле 'Возврат книги' не должно быть пустым")
    private boolean returned;


    public LogBook(Reader reader, Book book, Date dateOfBookWasTaken, Date dateOfBookWasReturned, boolean returned) {
        this.reader = reader;
        this.book = book;
        this.dateOfBookWasTaken = dateOfBookWasTaken;
        this.dateOfBookWasReturned = dateOfBookWasReturned;
        this.returned = returned;
    }


    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateOfBookWasTaken() {
        return dateOfBookWasTaken;
    }

    public void setDateOfBookWasTaken(Date dateOfBookWasTaken) {
        this.dateOfBookWasTaken = dateOfBookWasTaken;
    }

    public Date getDateOfBookWasReturned() {
        return dateOfBookWasReturned;
    }

    public void setDateOfBookWasReturned(Date dateOfBookWasReturned) {
        this.dateOfBookWasReturned = dateOfBookWasReturned;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}

