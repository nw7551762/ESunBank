package esun.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private PasswordEncoder passwordEncoder;

    public PasswordService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String password, String salt) {
        String saltedPassword = password + salt;
        return passwordEncoder.encode(saltedPassword);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword, String salt) {
        String saltedPassword = rawPassword + salt;
        return passwordEncoder.matches(saltedPassword, encodedPassword);
    }
}

