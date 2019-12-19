package com.example.quoteservice;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class QuoteServiceApplication {
    // @Bean
    // CommandLineRunner demoData(QuoteRepository quoteRepository) {
    // return strings -> {
    // quoteRepository.save(new Quote("These go to eleven.", "This Is Spinal Tap,
    // 1984"));
    // quoteRepository
    // .save(new Quote("All you need to start an asylum is an empty room and the
    // right kind of people.",
    // "My Man Godfrey, 1936"));
    // quoteRepository.save(new Quote("The Dude abides.", "The Big Lebowski,
    // 1998"));
    // quoteRepository.save(new Quote("Hasta la vista, baby.", "Terminator 2:
    // Judgment Day, 1991"));
    // quoteRepository.save(new Quote("What we've got here is a failure to
    // communicate.", "Cool Hand Luke, 1967"));
    // quoteRepository
    // .save(new Quote("Roads? Where we're going we don't need roads.", "Back to the
    // Future, 1985"));
    // quoteRepository.save(new Quote("Houston, we have a problem.", "Apollo 13,
    // 1995"));
    // quoteRepository.save(new Quote("To infinity and beyond!", "Toy Story,
    // 1995"));
    // quoteRepository.save(new Quote("Hello. My name is Inigo Montoya. You killed
    // my father. Prepare to die.",
    // "The Princess Bride, 1987"));

    // quoteRepository.findAll().forEach(System.out::println);
    // };
    // }

    public static void main(String[] args) {
        SpringApplication.run(QuoteServiceApplication.class, args);
    }
}

@RepositoryRestResource()
interface QuoteRepository extends CrudRepository<Quote, Long> {
    @Query("select q from Quote q order by RAND()")
    List<Quote> getQuotesRandomOrder();
}

@RestController
class QuoteController {
    private final QuoteRepository quoteRepository;

    Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Value("${applicationVersion.releaseType}")
    private String currentReleaseType;

    QuoteController(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/random")
    public Quote getRandomQuote() {
        this.logger.info("A random Quote is being requested");
        return this.quoteRepository.getQuotesRandomOrder().get(0);
    }

    @PutMapping("quotes/collection")
    public Quote[] addMany(@RequestBody Quote[] newQuotes) {
        this.logger.info("A bunch of quotes are being added");
        List<Quote> savedQuotes = new ArrayList<Quote>();

        for (Quote quote : newQuotes) {
            savedQuotes.add(this.quoteRepository.save(quote));
        }

        Quote[] returnItems = savedQuotes.toArray(new Quote[0]);
        this.logger.info(returnItems.length + " quotes were added");
        return returnItems;
    }

    @GetMapping("/kill")
    public void killService() {
        System.exit(1);
    }

    @GetMapping("/version")
    public VersionInfo getVersion()
    {
        return new VersionInfo(this.currentReleaseType);
    }
}

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
class Quote {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String text, source;
}

class VersionInfo {
    public VersionInfo(String newReleaseType) {
        this.releaseType = newReleaseType;
    }

    public String releaseType;
}