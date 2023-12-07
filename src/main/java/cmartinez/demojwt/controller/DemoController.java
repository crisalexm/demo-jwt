package cmartinez.demojwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {

    @RequestMapping("/demo")
    public ResponseEntity<?> demo() {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Haz sido autenticado correctamente. Bienvenido!");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
