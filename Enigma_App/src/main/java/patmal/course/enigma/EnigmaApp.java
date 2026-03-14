package patmal.course.enigma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = {"patmal.course.enigma"})
@SpringBootApplication
@EntityScan(basePackages = {"patmal.course.enigma"})
@ComponentScan({"engine", "validator", "controller", "dto", "configuration", "repository", "patmal.course.enigma"})
public class EnigmaApp {
    public static void main(String[] args) {

        SpringApplication.run(EnigmaApp.class, args);
    }
}
