package friendsbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import friendsbook.configuration.FriendbookUserDetailService;
import friendsbook.jwt.helper.JwtUtil;
import friendsbook.jwt.model.JwtRequest;
import friendsbook.jwt.model.JwtResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class JwtController {
	@Autowired
	private AuthenticationManager authenticationmanager;
	@Autowired
	private FriendbookUserDetailService friendbookservice;
	@Autowired
	private JwtUtil jwtutil;
	
	@RequestMapping(value = "/token",method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtrequest) throws Exception{
		try {
			System.out.println(jwtrequest.getUsername());
			System.out.println(jwtrequest.getPassword());
	this.authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(jwtrequest.getUsername(), jwtrequest.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("badcredential");
		}
		UserDetails userdetail= this.friendbookservice.loadUserByUsername(jwtrequest.getUsername());
		String token=this.jwtutil.generateToken(userdetail);
		
		
		return ResponseEntity.ok(new JwtResponse(token));
		
	}

}
