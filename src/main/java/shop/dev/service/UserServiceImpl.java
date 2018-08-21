package shop.dev.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import shop.dev.common.Result;
import shop.dev.constant.CodeConstant;
import shop.dev.dto.UserDTO;
import shop.dev.repository.RoleEntity;
import shop.dev.repository.RoleRepository;
import shop.dev.repository.UserEntity;
import shop.dev.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(username);
		UserBuilder builder = null;
		if (user == null) {
			throw new UsernameNotFoundException("user name not found!");
		}

		try {
			List<Object[]> roleData = roleRepository.getRoleByUser(user.getId());
			List<String> roleList = new ArrayList<>();
			for(Object data : roleData) {
				BigInteger id = (BigInteger) data;
				RoleEntity role = roleRepository.findOne(id.longValue());
				roleList.add(role.getRoleName());
			}
			String[] roleArray = new String[roleList.size()];
			roleArray = roleList.toArray(roleArray);
			
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(user.getPassword());
			builder.roles(roleArray);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return builder.build();
	}

	@Transactional
	public Result registerNewUser(UserDTO userDTO) throws Exception {
		Result result = new Result();
		UserEntity user = new UserEntity();
				
		try {
			RoleEntity role = roleRepository.findOne(2L);
			List<RoleEntity> roles = new ArrayList<>();
			roles.add(role);
			
			user.setUserName(userDTO.getUserName());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setEmail(userDTO.getEmail());
			user.setRoles(roles);			
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
		
	//	Map<Object,Object> role = roleRepository.getRoleByRoleName("ADMIN");
		try {
			List<UserEntity> rawData  = new ArrayList<>();
			rawData =  userRepository.findAll();
			result.setData(rawData);
			result.setCode(CodeConstant.CODESUCCESS);
			result.setMessage(CodeConstant.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			result.setCode(CodeConstant.CODEFAIL);
			result.setMessage(CodeConstant.FAIL);
		}
			
		return result;
		
	}

	@Transactional
	public Result updateUser(UserDTO userDTO) throws Exception{
		Result result = new Result();
		UserEntity user = userRepository.findOne(userDTO.getId());
		
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
