package esun.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistRequest {
	private String memberId;
	private String name;
	private String password;
	private String email;
	private String gender;

}
