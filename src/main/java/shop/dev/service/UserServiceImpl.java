package shop.dev.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import shop.dev.common.Result;
import shop.dev.dto.UserDTO;
import shop.dev.repository.Role;
import shop.dev.repository.User;
import shop.dev.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@SuppressWarnings("null")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		UserBuilder builder = null;
		if (user == null) {
			throw new UsernameNotFoundException("user name not found!");
		}

		try {
			List<String> roles = new ArrayList<>();
			for(Role role: user.getRoles()) {
				roles.add(role.getRoleName());
			}
			builder.username(user.getUserName());
			builder.password(user.getPassword());
			builder.roles((String[]) roles.toArray());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return builder.build();
	}

	@Transactional
	public Result registerNewUser(UserDTO userDTO) throws Exception {
		Result result = new Result();
		User user = new User();
		try {
			user.setUserName(userDTO.getUserName());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setEmail(userDTO.getEmail());
			userRepository.save(user);
			result.setData(user);
			result.setCode("000");
			result.setMessage("SUCCESS");
		}catch (Exception e) {
			result.setCode("000");
			result.setMessage("FAIL!");
			e.printStackTrace();
		}
	
		return result;
	}

	public Result getAll() {
		Result result = new Result();
		List<User> rawData =  userRepository.findAll();
		
		result.setData(rawData);
		return result;
		
	}

	@Transactional
	public Result updateUser(UserDTO userDTO) throws Exception{
		Result result = new Result();
		User user = userRepository.findOne(userDTO.getId());
		
		try {
			user.setUserName(userDTO.getUserName());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setEmail(userDTO.getEmail());
			userRepository.save(user);
			result.setData(user);
			result.setCode("000");
			result.setMessage("SUCCESS");
		}catch (Exception e) {
			result.setCode("000");
			result.setMessage("FAIL!");
			e.printStackTrace();
		}
	
		return result;
	}
	
}
