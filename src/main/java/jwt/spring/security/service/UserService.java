package jwt.spring.security.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jwt.spring.security.entity.User;
import jwt.spring.security.repository.UserRepository;
@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder encoder;

	@Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
	  
	        Optional<User> userDetail = userRepository.findByUsername(username); 
	  
	        // Converting userDetail to UserDetails 
	        return userDetail.map(UserInfoDetails::new) 
	                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	    }
	 
	 public String addUser(User user) { 
	        user.setPassword(encoder.encode(user.getPassword())); 
	        userRepository.save(user); 
	        return "User Added Successfully"; 
	    }
	 

}
