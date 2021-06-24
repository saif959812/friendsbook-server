package friendsbook.controller;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import friendsbook.model.Friend;
import friendsbook.model.User;
import friendsbook.repository.FriendsRepository;
import friendsbook.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200/",allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserRepository userepo;
	@Autowired
	private FriendsRepository friendrepo;
	private static final String text = "this is a private space";

	@GetMapping("/welcome")
	public User Home(Principal p) {
		User user = userepo.getUserByUserName(p.getName());

		return user;
	}

	@GetMapping("/addfriend")
	public User Addfriend(@RequestHeader("email") String email, Principal principal) throws Exception {
		String Secretkey = new Random().nextInt(99) * 100 + "";
		
		try {
			User friend = userepo.getUserByUserName(email);
			
			User user = userepo.getUserByUserName(principal.getName());
			Friend userfriend=this.friendrepo.findByEmailAndUserId(email, user.getId());
			if (friend != null && userfriend==null) {
				Friend friend1 = new Friend(friend);
				friend1.getFriendSkey().add(Secretkey);
				friend1.setUser(user);
				user.getFriendSkey().add(Secretkey);
				user.getFriends().add(friend1);
				userepo.save(user);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		User user1 = userepo.getUserByUserName(principal.getName());
		return user1;

	}

	@GetMapping("/getallfriends")
	public List<Friend> getALLFriends(Principal p) {
		User user = userepo.getUserByUserName(p.getName());

		return user.getFriends();
	}

	@GetMapping("/getallappuser")
	public Page<User> getALLAppUser(@RequestParam("keyword") String keyword,@RequestParam("page") Integer page ,Principal p ) {
		User user=this.userepo.getUserByUserName(p.getName());
		Pageable pageable=PageRequest.of(page,5);
		Page<User> users = userepo.findByFirstnameContaining(keyword ,pageable);
		
		
	

		return users;
	}

	@PostMapping("/getfriend")
	public Friend GetFriend(@RequestHeader("email") String email, Principal p) {
		User user = userepo.getUserByUserName(p.getName());
		user.getId();
		Friend friend = this.friendrepo.findByEmailAndUserId(email, user.getId());

		return friend;
	}

	@GetMapping("/deletefriend")
	public List<Friend> getALLFriends(@RequestHeader("email") String email, Principal p) {
		User user = userepo.getUserByUserName(p.getName());
		user.getId();
		Friend friend = this.friendrepo.findByEmailAndUserId(email, user.getId());
		friendrepo.delete(friend);
		return user.getFriends();
	}

	@PostMapping("/uploadprofile")
	public ResponseEntity<?> uploadprofile(@RequestParam("profilepicture") MultipartFile multipartfile,
			Principal principal) {
		User user = userepo.getUserByUserName(principal.getName());
		List<Friend> friend = friendrepo.findByEmail(user.getEmail());
		String imagename = null;
		if (multipartfile.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no image found");
		} else {
			try {
				String directory = System.getProperty("user.dir") + "/src/main/resources/static/img";
				imagename = user.getEmail() + ".jpg";
				Path path = Paths.get(directory, imagename);
				Files.copy(multipartfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				user.setImageurl(ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/").path(imagename)
						.toUriString());
				friend.forEach((n) -> n.setImageurl(ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/")
						.path(user.getEmail() + ".jpg").toUriString()));
				userepo.save(user);
				friendrepo.saveAll(friend);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return ResponseEntity.ok(
					ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/").path(imagename).toUriString());
		}
	}

	@GetMapping("/getuser")
	public User GetUser(@RequestHeader("email") String email) {
		User user = userepo.getUserByUserName(email);
		System.out.println(user);

		return user;
	}

}
