package esun.user;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import esun.exception.UsernameAlreadyExistsException;
import esun.util.JwtTokenUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	// userRepository已經測驗過OK
	// 模擬userRepository注入userService
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordService passwordService;
	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	private UserService userService;
	
	@BeforeEach
	void setup() {
		userService = new UserService(  userRepository, passwordService, jwtTokenUtil);
	}
	
	@Test
	void checkExistsByUsername() {
		userService.existsByUsername("username1");
		verify(userRepository).existsByUsername("username1"); 
	}
	
	@Test
	void checkIfCanRegistUser() {
		User user = new User();
		user.setUsername("username1");
		user.setPassword("pasword");
		userService.registerUser(user);
		
		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(argumentCaptor.capture());
		
		User capturedUser = argumentCaptor.getValue();
		
		assertThat(user).isEqualTo(capturedUser);
		
	}
	
	@Test
	void checkIfTThrowWheRegistExistUsername() {
		User user = new User();
		user.setUsername("username1");
		user.setPassword("pasword");
		
		given( userRepository.existsByUsername( anyString() ))
			.willReturn(true);
				
		assertThatThrownBy( ()-> userService.registerUser(user) )
			.isInstanceOf(  UsernameAlreadyExistsException.class )
			.hasMessageContaining("Username already exists");
		
		verify(userRepository, never()).save(any());
		
		
	}
	
}
