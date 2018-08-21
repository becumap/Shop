package shop.dev.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import shop.dev.common.Result;
import shop.dev.constant.CodeConstant;
import shop.dev.repository.RoleEntity;
import shop.dev.repository.RoleRepository;

@Service
public class RoleServiceImpl {

	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/list")
	public Result getList() {
		
		Result result = new Result();
		
		List<RoleEntity> rawData = new ArrayList<>();
		try {
			rawData = roleRepository.findAll();
			result.setCode(CodeConstant.CODESUCCESS);
			result.setData(rawData);
			result.setMessage(CodeConstant.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			result.setCode(CodeConstant.CODEFAIL);
			result.setData(rawData);
			result.setMessage(CodeConstant.FAIL);
		}
		return result;
	}
}
