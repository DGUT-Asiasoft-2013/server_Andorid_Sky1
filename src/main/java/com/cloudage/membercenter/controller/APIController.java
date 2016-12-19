
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
 * 控制类，用于实现各种方法
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
	 * 注册操作
	 */
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
				user.setAvatar("upload/"+num+".png");           //

			} catch (Exception e) {
				// TODO: handle exception
			}


		}
		return userService.save(user);
	}

	/*
	 * 登录操作
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User Login(
			@RequestParam String num,
			@RequestParam String password,
			HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();

		User user =userService.findNum(num);           //
		//
		if (user!=null && user.getPasswordHash().equals(password)) {
			request.getSession().setAttribute("user", user);
			return user;
		}else {

			return null;
		}

	}

	/*
	 * 获得当前用户
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public User getCurrentUser(HttpServletRequest request) {
		Object object=request.getSession().getAttribute("user");
		if (object instanceof User) {
			//
			return (User) object;
		}
		else {
			return null;
		}

	}

	/*
	 * 获得邮件
	 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public boolean Email(
			@RequestParam String email,
			@RequestParam String password,
			HttpServletRequest request) {

		User user =userService.findEmail(email);           //
		//
		if (user==null) {
			return false;
		}else {
			//
			user.setPasswordHash(password);
			//
			userService.save(user);
			return true;
		}

	}

	/**
	 * @RequestMapping(value = "/book/{book_id}/comment")里面的book_id必须跟
	 * @PathVariable(value = "book_id") int book_id中，int的变量一样，如果想不一样，则
	 * 在其前面加value = "book_id"，但这个必须跟
	 *  @RequestMapping(value = "/book/{book_id}/comment")里面的book_id一样
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	public Comment addComment(@RequestParam(name = "content") String content,
			@PathVariable(value = "book_id") int book_id, HttpServletRequest request) {
		// 
		User user = getCurrentUser(request);
		Book book = bookService.findOne(book_id);
		// 
		Comment comment = new Comment();
		comment.setCommentor(user);
		comment.setContent(content);
		comment.setBook(book);
		return commentService.save(comment);

	}

	/**
	 * @RequestMapping(value = "/book/{book_id}/comment")里面的book_id必须跟
	 * @PathVariable(value = "book_id") int book_id中，int的变量一样，如果想不一样，则
	 * 在其前面加value = "book_id"，但这个必须跟
	 *  @RequestMapping(value = "/book/{book_id}/comment")里面的book_id一样
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping("/book/{book_id}/comment/{page}")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id,
			@PathVariable int page) {

		return commentService.findCommentNumofBook(book_id, page);
	}

	/*
	 * @RequestMapping(value = "/book/{book_id}/comment")里面的book_id必须跟
	 * @PathVariable(value = "book_id") int book_id中，int的变量一样，如果想不一样，则
	 * 在其前面加value = "book_id"，但这个必须跟
	 *  @RequestMapping(value = "/book/{book_id}/comment")里面的book_id一样
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping("/article/{book_id}/comment")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id) {

		return commentService.findCommentNumofBook(book_id, 0);
	}

	/**
	 * @RequestMapping(value = "/book/{book_id}/comment")里面的book_id必须跟
	 * @PathVariable(value = "book_id") int book_id中，int的变量一样，如果想不一样，则
	 * 在其前面加value = "book_id"，但这个必须跟
	 *  @RequestMapping(value = "/book/{book_id}/comment")里面的book_id一样
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping("/comments")
	public Page<Comment> getAllCommentsOfAuthor(HttpServletRequest request)
	{
		//获得当前用户
		User user=getCurrentUser(request);
		return commentService.findAllCommentofAuthor(user.getId(), 0);
	}
}
