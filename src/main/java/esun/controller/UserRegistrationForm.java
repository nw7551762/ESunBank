package esun.controller;


import org.springframework.web.multipart.MultipartFile;

public class UserRegistrationForm {
    private String userName;
    private String email;
    private String password;
    private MultipartFile coverImage;
    private String biography;
    
    
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getCoverImage() {
		return coverImage;
	}
	public void setCoverImage(MultipartFile coverImage) {
		this.coverImage = coverImage;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}

    
}

