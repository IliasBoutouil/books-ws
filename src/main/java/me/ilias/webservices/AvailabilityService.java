package me.ilias.webservices;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import me.ilias.domains.Book;
import me.ilias.repositories.BookRepository;

@WebService(serviceName = "AvailabilityWS")
public class AvailabilityService {
   private BookRepository bookRepository;

   public AvailabilityService(BookRepository bookRepository) {
      this.bookRepository = bookRepository;
   }

   @WebMethod
   public boolean isAvailable(@WebParam(name = "title") String bookTitle)
   {
       return bookRepository.isAvailable(bookTitle);
   }
   @WebMethod
   public Book toggleAvailability(@WebParam(name = "title") String bookTitle)
   {
       return bookRepository.toggleAvailable(bookTitle);
   }
}
