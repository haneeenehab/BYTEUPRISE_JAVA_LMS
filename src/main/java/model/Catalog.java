package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class Catalog implements Search {
    private Date creationDate;
    private int totalBooks;
    private Map<String, LinkedList> bookTitles;
    private Map<String, LinkedList> bookAuthors;
    private Map<String, LinkedList> bookSubjects;
    private Map<Date, LinkedList> bookPublicationDates;
}
