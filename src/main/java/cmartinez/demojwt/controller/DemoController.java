package cmartinez.demojwt.controller;

import cmartinez.demojwt.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {
    @Autowired
    DemoService demoService;

    @GetMapping("/users")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "false") boolean includeInactive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Map<String, Object> response = demoService.getUsers(includeInactive, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> getUser(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(demoService.getUserById(userId));
    }

    @DeleteMapping("/users/{userId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deactivateUser(@PathVariable UUID userId) {
        demoService.deactivateUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario desactivado.");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
