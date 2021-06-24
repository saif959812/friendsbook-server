package friendsbook.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import friendsbook.model.Friend;
import friendsbook.model.User;
import friendsbook.repository.FriendsRepository;
import friendsbook.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class HomeController {
	@Autowired
	UserRepository userepo;
	@Autowired
	FriendsRepository friendrepo;
	String text = "this is a private space";

	@PostMapping("/signup")

	public ResponseEntity<?> Signup(@Valid @RequestBody() User user, BindingResult result) {
		System.out.println(user);
		if (result.hasErrors()) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("enter valid details and feild cannot be empty");

		} else {
			User user2 = null;
			User user1 = userepo.getUserByUserName(user.getEmail());
			Friend friend = null;
			Friend friend1 = friendrepo.findByEmailAndUserId(user.getEmail(), null);
			if (user1 == null) {
				user.setRegisterdate(LocalDateTime.now() + "");
				user.setRole("ROLE_USER");

				if (friend1 == null) {
					friend = new Friend(user);
					friendrepo.save(friend);
				}
				userepo.save(user);
				user2 = userepo.getUserByUserName(user.getEmail());

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user with email id already present");
			}
			return ResponseEntity.ok(user2);
		}
	}

}
