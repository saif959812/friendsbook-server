package friendsbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import friendsbook.model.Friend;


public interface FriendsRepository extends CrudRepository<Friend, Integer>{
	@Query("select u from Friend u where u.email=:email")
	public Friend getUserByUserName(@Param("email") String email);
	 
	public Friend findByEmailAndUserId(String email,Integer id);
	public List<Friend> findByEmail(String email);
}
