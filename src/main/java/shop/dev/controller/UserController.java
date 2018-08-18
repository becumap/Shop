package shop.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.dev.common.Result;
import shop.dev.dto.UserDTO;
import shop.dev.service.UserServiceImpl;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/getAllUSer")
	public Result getAll() {
		Result result  = userService.getAll();
		return result;
	}
	
	@PostMapping("/register")
	public Result registerNewUser(UserDTO userDTO) {
		
		Result result = null;
		try {
			result = userService.registerNewUser(userDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;		
	}

	@PutMapping("/updateUser")
	public Result updateUser(UserDTO userDTO) {
		Result result = null;
		try {
			result = userService.updateUser(userDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;	
	}
}
