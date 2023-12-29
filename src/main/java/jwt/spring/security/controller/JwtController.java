package jwt.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jwt.spring.security.entity.AuthRequest;
import jwt.spring.security.entity.User;
import jwt.spring.security.service.JwtService;
import jwt.spring.security.service.UserService;

@RestController
@RequestMapping("api")
public class JwtController {
	@Autowired
	private UserService userService;
	
    @Autowired
    private JwtService jwtService; 
  
    @Autowired
    private AuthenticationManager authenticationManager;

	@GetMapping("welcome")
	public String welcome() {
		return "Welcome_World";
	}
	
	@PostMapping("addNewUser") 
    public String addNewUser(@RequestBody User user) { 
        return userService.addUser(user); 
    } 
	
	 @GetMapping("user/userProfile") 
	    @PreAuthorize("hasAuthority('ROLE_USER')") 
	    public String userProfile() { 
	        return "Welcome to User Profile"; 
	    }
	 
	 @GetMapping("admin/adminProfile") 
	    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
	    public String adminProfile() { 
	        return "Welcome to Admin Profile"; 
	    }
	 
	 @PostMapping("generateToken") 
	    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
	        if (authentication.isAuthenticated()) { 
	            return jwtService.generateToken(authRequest.getUsername()); 
	        } else { 
	        	return "invalid user request";
	           // throw new UsernameNotFoundException("invalid user request !"); 
	        } 
	    } 
}
