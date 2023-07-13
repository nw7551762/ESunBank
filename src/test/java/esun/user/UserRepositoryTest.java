package esun.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	

	@Test	
	void checkIfUsernameExists() {
		User user = new User();
		user.setUsername("username1");
		user.setPassword("pasword");
		userRepository.save(user);
		Boolean exist =  userRepository.existsByUsername("username1");
		
		assertThat(exist).isTrue();
	}
	
	@Test	
	void checkIfUsernameNotExists() {
		Boolean exist =  userRepository.existsByUsername("username1");
		assertThat(exist).isFalse();
	}
	
	
}
