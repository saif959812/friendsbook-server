package friendsbook.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import friendsbook.repository.UserRepository;
@Service
public class FriendbookUserDetailService implements UserDetailsService {
 @Autowired
 UserRepository userepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
friendsbook.model.User user =this.userepo.getUserByUserName(username);
friendsbook.model.User user1 = this.userepo.getUserByUserName(username);

if (user1==null) {
	throw new UsernameNotFoundException("user not found!!");
	
}
FreindbookUserDetails  friendbookuserdetails=new FreindbookUserDetails(user1);

return friendbookuserdetails;
	




		
		
		//		if(username.equals("saif")) {
//			return new User("saif","hesoyam", new ArrayList<>());
//		}
//		else 
//			throw  new UsernameNotFoundException("user not found");
//		
//	}

}}
