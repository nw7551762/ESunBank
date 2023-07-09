package esun.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import esun.user.UserService;
import esun.util.JwtTokenUtil;

@RestController
public class LoginController {
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	@Autowired
    private UserService userService;
	
	@PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        boolean isValidCredentials = userService.validateCredentials(username, password);
        if (!isValidCredentials) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String jwtToken = jwtTokenUtil.generateToken(username);

        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwtToken);
        response.put("username", username);

        return ResponseEntity.ok(response);
    }
	
}
