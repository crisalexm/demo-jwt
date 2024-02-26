package cmartinez.demojwt.controller;
import cmartinez.demojwt.service.AuthService;
import cmartinez.demojwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "false") boolean includeInactive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Map<String, Object> response = userService.getUsers(includeInactive, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> getUser(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserById(userId));
    }

    @DeleteMapping("/users/{userId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deactivateUser(@PathVariable UUID userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User deactivated");
    }
}
