package esun.user;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esun.auth.AuthenticationResponse;
import esun.exception.UsernameAlreadyExistsException;
import esun.jwt.JwtService;
import esun.util.JwtTokenUtil;
import lombok.var;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

    public UserService() {
    }
    
    public UserService(UserRepository userRepository, PasswordService passwordService, JwtTokenUtil jwtTokenUtil ) {
    	this.userRepository = userRepository ;
    	this.passwordService = passwordService ;
    	this.jwtTokenUtil = jwtTokenUtil ;
	}

	private String generateSalt() {
    	// 生成安全的隨機數字鹽值
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        // 將二進制鹽值轉換為 Base64 字串表示
        return Base64.getEncoder().encodeToString(salt);
    }
    
    private User setSaltedPassword( User user ) {
    	// 生成隨機的鹽值
        String salt = generateSalt();
        String encodedPassword = passwordService.encodePassword(user.getPassword(), salt);
        user.setPassword(encodedPassword);
        user.setSalt(salt);
        
        return user;
    }
    
    public AuthenticationResponse registerUser( User user ) {
    	
    	if ( userRepository.existsByUsername(user.getUsername() )) {
	        throw new UsernameAlreadyExistsException("Username already exists");
	    }
    	
    	user = setSaltedPassword(user);
        userRepository.save(user);
        var jwtToken = jwtTokenUtil.generateToken( user.getUsername());
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
    }

	public User save(User user) {
		userRepository.save(user);
		return user;
	}
	
	public User findByUser(User user) {
		userRepository.findByUsername( user.getUsername() );
		return user;
	}

	public Optional<User> findByUsername(String userName) {
		User user = userRepository.findByUsername( userName );
		return Optional.of(user);
	}

	public boolean validateCredentials(String username, String password) {
		User user = userRepository.findByUsername(username);
		if ( user==null  ) {
			return false;
		}
		if ( user.getUsername().equals(username) &&  passwordService.matchPassword(password, user.getPassword(), user.getSalt()) ) {
			return true;
		}
		return false;
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
    
    
}
