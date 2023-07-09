package esun.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test {
    @Autowired
    private UserService userService;
    
    public Test() {
	}

	public void executeRegistration() {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("password");

        // 執行註冊操作
        try {
            userService.save(user);
            System.out.println(" save user ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

