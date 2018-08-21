package shop.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.dev.common.Result;
import shop.dev.service.RoleServiceImpl;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	RoleServiceImpl roleService;
	
	@GetMapping
	public Result getList() {
		Result result = roleService.getList();
		return result;
	}
}
