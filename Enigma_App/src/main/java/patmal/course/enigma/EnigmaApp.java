package patmal.course.enigma;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan({"engine", "validator", "controller","dto","configuration", "repository", "patmal.course.enigma"})
public class EnigmaApp {
    public static void main(String[] args) {

        SpringApplication.run(EnigmaApp.class, args);
    }
}
