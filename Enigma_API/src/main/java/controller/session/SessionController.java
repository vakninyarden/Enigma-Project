package controller.session;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patmal.course.enigma.api.model.CreateSessionRequest;
import patmal.course.enigma.session.service.SessionManager;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enigma/session")
public class SessionController {
        private final SessionManager sessionManager;

        @PostMapping
        public ResponseEntity<String> createSession(@RequestBody CreateSessionRequest request) {
           try {
               String sessionId = sessionManager.createSession(request.getMachine());
                return ResponseEntity.ok(sessionId);
           }
           catch (Exception e) {
               return ResponseEntity.badRequest().body(e.getMessage());
           }
        }

        @DeleteMapping
        public ResponseEntity<String> deleteSession(@RequestParam("sessionID") String sessionID) {
            try {
                sessionManager.deleteSession(sessionID);
                return ResponseEntity.ok("Session deleted successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
}
