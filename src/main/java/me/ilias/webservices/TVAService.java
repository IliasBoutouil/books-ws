package me.ilias.webservices;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import me.ilias.domains.Book;
import me.ilias.repositories.BookRepository;

import java.time.LocalDate;
@WebService(serviceName = "TvaWs")
public class TVAService {

    BookRepository bookRepository;

    public TVAService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @WebMethod
    public double getTva(@WebParam(name = "title") String title)
    {
        Book book = bookRepository.findByBookTitle(title);
        LocalDate availableDate = book.getAvailableDate();
        if(availableDate.isBefore(LocalDate.of(2020,1,1)))
        {
            return book.getDefaultTVA()*2;
        }
        else
            return book.getDefaultTVA();
    }
}
