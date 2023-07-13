package esun.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import esun.exception.InvalidEmailException;
import esun.exception.InvalidUsernameException;
import esun.exception.UsernameAlreadyExistsException;
import esun.user.User;
import esun.user.UserService;

@RestController
@RequestMapping("/api/demo")
public class registController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/user")
    public void findUser( @ModelAttribute UserRegistrationForm form )  {
           System.out.println(" find user ");
    }
	
	
	@PostMapping("/register")
	public ModelAndView registerUser(@ModelAttribute UserRegistrationForm form) {
	    User user = registrationFormToUser(form);

	    // 驗證使用者名稱是否為手機號碼格式
	    String username = user.getUsername();
	    if (!isValidPhoneNumber(username)) {
	        throw new InvalidUsernameException("Invalid username format");
	    }

	    // 驗證電子郵件格式
	    String email = user.getEmail();
	    if (!isValidEmail(email)) {
	        throw new InvalidEmailException("Invalid email format");
	    }

	    // 執行註冊操作
	    try {
	        userService.registerUser(user);
	        System.out.println("save user");
	        return new ModelAndView("registSuccess");
	    } catch (UsernameAlreadyExistsException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return new ModelAndView("registerPage");

	}

	private boolean isValidPhoneNumber(String phoneNumber) {
	    // 使用正則表達式檢查手機號碼格式
	    String regex = "^09\\d{8}$";
	    return phoneNumber.matches(regex);
	}

	private boolean isValidEmail(String email) {
	    // 使用正則表達式檢查電子郵件格式
	    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    return email.matches(regex);
	}




	private User registrationFormToUser(UserRegistrationForm form) {
		Blob userImg = null;
		String userBiography = null;
		
		Optional<Blob> imgblob = formImgToBlob(form.getCoverImage());
		if ( imgblob.isPresent() ) {
			userImg = imgblob.get() ;
		}
		
		if (form.getBiography()!=null) {
			userBiography = form.getBiography();
		}
		
		User user = new User(form.getUserName(), form.getEmail(), form.getPassword(), userImg , userBiography );
		return user;
	}


	private Optional<Blob> formImgToBlob( MultipartFile coverImage ) {
		byte[] imgBytes = null;
		Blob imgblob = null;
		try {
			// 判断Optional是否包含值
			if ( coverImage!=null ) {
				imgBytes = coverImage.getBytes();
				imgblob = new SerialBlob(imgBytes);
				return Optional.of(imgblob);
			}
		} catch (IOException | SQLException e) {
			System.out.println(" formImgToBlob error ");
			e.printStackTrace();
		}
		return Optional.empty();
		
	}

}
