package com.cloudage.membercenter.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudage.membercenter.entity.Book;
import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.IBookService;
import com.cloudage.membercenter.service.ICommentService;
import com.cloudage.membercenter.service.IUserService;

/*
 * 创建一个表就要重新写那4个东西
 */
@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	IUserService userService;

	@Autowired
	IBookService bookService;

	@Autowired
	ICommentService commentService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "HELLO WORLD";
	}

	/*
	 * 增加评论， 检查点赞,RequestMapping("/book/{book_id}/comment"){}里面的book_id
	 * 必须与@PathVariable int book_id里面的定义的一样，也可以不一样
	 * 就是@PathVariable(value="book_id")此中必须一样
	 */

	@RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	public Comment addComment(@RequestParam(name = "content") String content,
			@PathVariable(value = "book_id") int book_id, HttpServletRequest request) {
		// 获得当前用户
		User user = getCurrentUser(request);
		Book book = bookService.findOne(book_id);
		// 获取评论对象
		Comment comment = new Comment();
		comment.setCommentor(user);
		comment.setContent(content);
		comment.setBook(book);
		return commentService.save(comment);

	}
	
	/*
	 * 查询某一页的评论，
	 * 检查点赞,RequestMapping("/book/{book_id}/comment/{page}"){}里面的book_id
	 * 必须与@PathVariable int book_id里面的定义的一样，也可以不一样
	 * 就是@PathVariable(value="book_id")此中必须一样
	 */
	
	@RequestMapping("/book/{book_id}/comment/{page}")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id,
			@PathVariable int page) {
		
		return commentService.findCommentNumofBook(book_id, page);
	}
	
	/*
	 * 查询全部的评论
	 */
	@RequestMapping("/article/{book_id}/comment")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id) {
		
		return commentService.findCommentNumofBook(book_id, 0);
	}
	
	//找某个人的所有评论
	@RequestMapping("/comments")
	public Page<Comment> getAllCommentsOfAuthor(HttpServletRequest request)
	{
		//获得当前用户
		User user=getCurrentUser(request);
		return commentService.findAllCommentofAuthor(user.getId(), 0);
	}
	
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(@RequestParam(name = "num") String num, @RequestParam(name = "password") String password,
			@RequestParam(name = "email") String email, @RequestParam(name = "name") String name, MultipartFile avatar,
			HttpServletRequest request) {
		User user = new User();
		user.setAccount(num);
		user.setPasswordHash(password);
		user.setName(name);
		user.setEmail(email);

		if (avatar != null) {
			try {
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(realPath, num + ".png"));
				user.setAvatar("upload/" + num + ".png"); // 保存头像

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return userService.save(user);
	}

	/*
	 * 下面为登录操作的方法
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User Login(@RequestParam String num, @RequestParam String password, HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();

		User user = userService.findNum(num); // 找到账户
		// 判断用户是否为空并且密码是否正确
		if (user != null && user.getPasswordHash().equals(password)) {
			request.getSession().setAttribute("user", user);
			return user;
		} else {

			return null;
		}

	}

	/*
	 * 返回当前用户
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		Object object = request.getSession().getAttribute("user");
		if (object instanceof User) {
			// 判断object是否为User的实例,如果是则返回true，否则返回false
			return (User) object;
		} else {
			return null;
		}

	}

	/*
	 * 下面为返回email方法
	 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public boolean Email(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {

		User user = userService.findEmail(email); // 获得email
		// 判断用户是否为空
		if (user == null) {
			return false;
		} else {
			// 重新设置密码
			user.setPasswordHash(password);
			// 保存密码
			userService.save(user);
			return true;
		}

	}

}
