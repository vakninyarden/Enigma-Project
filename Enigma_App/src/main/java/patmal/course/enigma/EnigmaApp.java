package patmal.course.enigma;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan({"engine", "validator", "controller"})
public class EnigmaApp {
    public static void main(String[] args) {

        SpringApplication.run(EnigmaApp.class, args);
    }
}
