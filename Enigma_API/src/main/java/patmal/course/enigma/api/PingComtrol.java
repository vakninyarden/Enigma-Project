package patmal.course.enigma.api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enigma")
public class PingComtrol {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
