package com.example.tokens.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<String> sayHelloPublic() {
        return ResponseEntity.ok("Hello from public endpoint");
    }

    @GetMapping("/user")
    public ResponseEntity<String> sayHelloUser() {
        return ResponseEntity.ok("Hello from user endpoint");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> sayHelloAdmin() {
        return ResponseEntity.ok("Hello from admin endpoint");
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> sayHelloAuthenticated() {
        return ResponseEntity.ok("Hello from authenticated endpoint");
    }
}
