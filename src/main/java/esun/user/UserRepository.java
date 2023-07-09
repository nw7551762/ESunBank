package esun.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 可以自訂義其他的查詢方法
	public User findByUsername( String userName );

	public Boolean existsByUsername(  String userName );

	
	
}
