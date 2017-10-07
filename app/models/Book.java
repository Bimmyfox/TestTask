package models;

import play.data.validation.*;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Класс-модель книг
 * <p>
 * <p>
 * Содердит поля:
 * <p>
 * author - автор книги
 * title - название книги
 * publisher - название издательства книги
 * yearOfIssue - код выпуска книги из издательства
 * <p>
 * Связанна с моделью LogBook один-ко-многим
 */
@Entity
public class Book extends Model {

    @Required(message = "Введите автора книги")
    @MaxSize(value = 100, message = "не больше 100 символов")
    @Match(value = "^[А-Яа-яЁёa-zA-Z\\s-.,]+$",
            message = "Имя автора может содержать русские и латинские буквы, пробел, точку, запятую и дефис")
    public String author;


    @Required(message = "Введите название книги")
    @MaxSize(value = 300, message = "не больше 300 символов")
    @Match(value = "^[А-Яа-яЁёa-zA-Z\\s\\d-.,]+$",
            message = "Название может содержать русские и латинские буквы, цифры, пробел, точку, запятую и дефис")
    public String title;


    @Required(message = "Введите издателя книги")
    @MaxSize(value = 200, message = "не больше 200 символов")
    @Match(value = "^[А-Яа-яЁёa-zA-Z\\s-.,]+$",
            message = "Название издателя может содержать русские и латинские буквы, пробел, точку, запятую и дефис")
    public String publisher;


    @Required(message = "Введите год издания")
    @Range(min = 1800, max = 2018, message = "От 1800 до 2018 года")
    public int yearOfIssue;


    public Book(String author, String title, String publisher, int yearOfIssue) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.yearOfIssue = yearOfIssue;
    }


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    public List<LogBook> logBooks;


    public Book(String author, String title, String publisher, int yearOfIssue, List<LogBook> logBooks) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.yearOfIssue = yearOfIssue;
        this.logBooks = logBooks;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public List<LogBook> getLogBooks() {
        return logBooks;
    }

    public void setLogBooks(List<LogBook> logBooks) {
        this.logBooks = logBooks;
    }
}
