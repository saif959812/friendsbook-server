package friendsbook.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import friendsbook.configuration.FriendbookUserDetailService;
import friendsbook.jwt.helper.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter  {
 @Autowired
 JwtUtil jwtutil;
 @Autowired
 FriendbookUserDetailService friendbookservice;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestTokenHeader=request.getHeader("Authorization");
	
		String username=null;
		String jwttoken=null;
		if(requestTokenHeader!=null&&requestTokenHeader.startsWith("Bearer ")) {
			jwttoken=requestTokenHeader.substring(7);
			System.out.println(jwttoken);
			try {
			username=	this.jwtutil.extractUsername(jwttoken);
			System.out.println(username);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		UserDetails userdetail = friendbookservice.loadUserByUsername(username);
			if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userdetail, null,userdetail.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("token is not validted");
			}
			
		}
		filterChain.doFilter(request, response);
	}
	

}
