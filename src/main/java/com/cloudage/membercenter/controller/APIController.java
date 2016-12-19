package com.cloudage.membercenter.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.IUserService;

/*
 * ����һ�����Ҫ����д��4������
 */
@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	IUserService userService;
	
	@Autowired

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "HELLO WORLD";
	}
	
	
	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(
			@RequestParam(name = "num") String num,
			@RequestParam(name = "password") String password, 
			@RequestParam(name = "email") String email,
			@RequestParam(name = "name") String name,
			MultipartFile avatar,
			HttpServletRequest request) {
		User user = new User();
		user.setAccount(num);
		user.setPasswordHash(password);
		user.setName(name);
		user.setEmail(email);
		
		if (avatar!=null) {
			try {
				String realPath=request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(realPath,num+".png"));
				user.setAvatar("upload/"+num+".png");           //����ͷ��
				
			} catch (Exception e) {
				// TODO: handle exception
			}
					
			
		}
		return userService.save(user);
	}
	
	/*
	 * ����Ϊ��¼�����ķ���
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User Login(
			@RequestParam String num,
			@RequestParam String password,
			HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		
		User user =userService.findNum(num);           //�ҵ��˻�
		//�ж��û��Ƿ�Ϊ�ղ��������Ƿ���ȷ
		if (user!=null && user.getPasswordHash().equals(password)) {
			request.getSession().setAttribute("user", user);
			return user;
		}else {
			
			return null;
		}
		
	}
	
	/*
	 * ���ص�ǰ�û�
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		Object object=request.getSession().getAttribute("user");
		if (object instanceof User) {
			//�ж�object�Ƿ�ΪUser��ʵ��,������򷵻�true�����򷵻�false
			return (User) object;
		}
		else {
			return null;
		}
		
	}
	
	/*
	 * ����Ϊ����email����
	 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public boolean Email(
			@RequestParam String email,
			@RequestParam String password,
			HttpServletRequest request) {
		
		User user =userService.findEmail(email);           //���email
		//�ж��û��Ƿ�Ϊ��
		if (user==null) {
			return false;
		}else {
			//������������
			user.setPasswordHash(password);
			//��������
			userService.save(user);
			return true;
		}
		
	}
	

}
