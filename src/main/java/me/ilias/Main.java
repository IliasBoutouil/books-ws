package me.ilias;


import jakarta.xml.ws.Endpoint;
import lombok.SneakyThrows;
import me.ilias.config.DbConfig;
import me.ilias.repositories.BookRepository;
import me.ilias.repositories.BookRepositoryImp;
import me.ilias.webservices.AvailabilityService;
import me.ilias.webservices.TVAService;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        String url = "http://0.0.0.0:8085/";
         DbConfig.initDb();
        BookRepository bookRepository = new BookRepositoryImp();
        AvailabilityService availabilityService = new AvailabilityService(bookRepository);
        TVAService tvaService= new TVAService(bookRepository);
        Endpoint.publish(url+"tva/",tvaService);
        Endpoint.publish(url+"availability/",availabilityService);
        System.out.println("Webservice started at "+url);
    }


}