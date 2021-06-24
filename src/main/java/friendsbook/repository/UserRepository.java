package friendsbook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;



import friendsbook.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);

	public List<User> findAll();
	
	
	public Page<User> findByFirstnameContaining(String keyword,Pageable pageable);
	  
	
	
	

}
