package com.cloudage.membercenter.controller;

import java.io.File;
import java.util.List;
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
import com.cloudage.membercenter.entity.PrivateMessage;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.IBookService;
import com.cloudage.membercenter.service.ICommentService;
import com.cloudage.membercenter.service.IPrivateMessageService;
import com.cloudage.membercenter.service.ISubscribeService;
import com.cloudage.membercenter.service.IUserService;

/*
 * 鎺у埗绫伙紝鐢ㄤ簬瀹炵幇鍚勭鏂规硶
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

	@Autowired
	ISubscribeService subscribeService;
	
	@Autowired
	IPrivateMessageService privateMessageService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "HELLO WORLD";
	}



	/*
	 * 娉ㄥ唽鎿嶄綔
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(
			@RequestParam(name = "num") String num,//璐﹀彿
			@RequestParam(name = "password") String password, //瀵嗙爜
			@RequestParam(name = "email") String email,//閭
			@RequestParam(name = "name") String name,//鏄电О
			@RequestParam(name = "phoneNumb") String phoneNumb,//鐢佃瘽鍙风爜
			@RequestParam(name = "qq") String qq,//QQ
			MultipartFile avatar,
			HttpServletRequest request) {
		User user = new User();
		user.setAccount(num);
		user.setPasswordHash(password);
		user.setName(name);
		user.setEmail(email);
		user.setPhoneNumb(phoneNumb);
		user.setQq(qq);

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
	 * 鐧诲綍鎿嶄綔
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

	//蹇樿瀵嗙爜锛岄噸璁惧瘑鐮�
	@RequestMapping(value="/passwordrecover",method=RequestMethod.POST)
	public boolean resetPassword(
			@RequestParam String email,
			@RequestParam String passwordHash){
		User user=userService.findEmail(email);
		if(user==null){
			return false;
		}else{
			user.setPasswordHash(passwordHash);
			userService.save(user);
			return true;
		}
	}



	/*
	 * 鑾峰緱褰撳墠鐢ㄦ埛
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
	
	/**
	 * 退出登录，去掉session
	 */
	@RequestMapping(value="/exit")
	public void exit(HttpServletRequest request){
		User me=getCurrentUser(request);
		if(me!=null){
			request.getSession(true).removeAttribute("user");
		}
	}

	/*
	 * 鑾峰緱閭欢
	 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public boolean Email(
			@RequestParam String email,
			//@RequestParam String password,
			HttpServletRequest request) {

		User user =userService.findEmail(email);           //
		//
		if (user==null) {
			return false;
		}else {
			//
			//user.setPasswordHash(password);
			//
			userService.save(user);
			return true;
		}

	}

	/**
	 * @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id蹇呴』璺�
	 * @PathVariable(value = "book_id") int book_id涓紝int鐨勫彉閲忎竴鏍凤紝濡傛灉鎯充笉涓�鏍凤紝鍒�
	 * 鍦ㄥ叾鍓嶉潰鍔爒alue = "book_id"锛屼絾杩欎釜蹇呴』璺�
	 *  @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id涓�鏍�
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
	 * @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id蹇呴』璺�
	 * @PathVariable(value = "book_id") int book_id涓紝int鐨勫彉閲忎竴鏍凤紝濡傛灉鎯充笉涓�鏍凤紝鍒�
	 * 鍦ㄥ叾鍓嶉潰鍔爒alue = "book_id"锛屼絾杩欎釜蹇呴』璺�
	 *  @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id涓�鏍�
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value= "/book/{book_id}/comment/{page}")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id,
			@PathVariable int page) {

		return commentService.findCommentNumofBook(book_id, page);
	}

	/*
	 * @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id蹇呴』璺�
	 * @PathVariable(value = "book_id") int book_id涓紝int鐨勫彉閲忎竴鏍凤紝濡傛灉鎯充笉涓�鏍凤紝鍒�
	 * 鍦ㄥ叾鍓嶉潰鍔爒alue = "book_id"锛屼絾杩欎釜蹇呴』璺�
	 *  @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id涓�鏍�
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/book/{book_id}/comment")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id) {

		return commentService.findCommentNumofBook(book_id, 0);
	}

	/**
	 * @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id蹇呴』璺�
	 * @PathVariable(value = "book_id") int book_id涓紝int鐨勫彉閲忎竴鏍凤紝濡傛灉鎯充笉涓�鏍凤紝鍒�
	 * 鍦ㄥ叾鍓嶉潰鍔爒alue = "book_id"锛屼絾杩欎釜蹇呴』璺�
	 *  @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id涓�鏍�
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/comments")
	public Page<Comment> getAllCommentsOfAuthor(HttpServletRequest request)
	{
		//鑾峰緱褰撳墠鐢ㄦ埛
		User user=getCurrentUser(request);
		return commentService.findAllCommentofAuthor(user.getId(), 0);
	}

	/***
	 * 
	 * 瀛樺叆鍥句功淇℃伅
	 * 
	 * */
	@RequestMapping(value="/sellbooks",method=RequestMethod.POST)
	public Book addBook(
			@RequestParam String title,
			@RequestParam String author,
			@RequestParam float price,
			@RequestParam String text,
			@RequestParam String publisher,
			@RequestParam String book_isbn,
			@RequestParam String tag,
			@RequestParam String summary,
			@RequestParam int booknumber, 
			MultipartFile bookavatar,//瀛樻斁鍥剧墖
			HttpServletRequest request){
		User currentUser = getCurrentUser(request);
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setPrice(price);
		book.setText(text);
		book.setPublisher(publisher);
		book.setIsbn(book_isbn);
		book.setTag(tag);
		book.setSummary(summary);
		book.setBooknumber(booknumber);
		book.setUser(currentUser);
		if (bookavatar!=null) {
			try {
				String realPath=request.getSession().getServletContext().getRealPath("/WEB-INF/upload/books");
				FileUtils.copyInputStreamToFile(bookavatar.getInputStream(), new File(realPath,title+".png"));
				book.setBookavatar("upload/books/"+title+".png");           //瀛樻斁鍥剧墖鐨勮矾寰�

			} catch (Exception e) {
			}
		}
		
		return bookService.save(book);
	}

	//鑾峰彇鍑哄敭鍥句功鍒楄〃
	@RequestMapping("/books/{page}")
	public Page<Book> getFeeds(@PathVariable int page){
		return bookService.getBooks(page);
	}
	@RequestMapping("/books")
	public Page<Book> getFeeds(){
		return getFeeds(0);
	}

	//鎼滅储鍥句功--------(鏍规嵁 鍥句功鍚嶇О|鍥句功浣滆�厊ISBN|鍗栧 鎼滅储)
	@RequestMapping(value="/book/s/{keyword}")
	public Page<Book> fingTextByKeyword(
			@PathVariable String keyword,
			@RequestParam(defaultValue="0") int page){
		return bookService.findTextByKeyword(keyword, page);
	}

	/**
	 * 2016-12-22 19:01:20
	 * 鎺ユ敹鍙戦�佹秷鎭殑鎺ュ彛
	 * @param String private:绉佷俊鐨勫叿浣撳唴瀹�
	 * @param String receiverAccount :绉佷俊鎺ユ敹鑰呯殑甯愬彿
	 * 
	 */
		@RequestMapping(value = "/privateMessage",method = RequestMethod.POST)

		public PrivateMessage savePrivateMessage(@RequestParam String privateText,
				@RequestParam String receiverAccount,
				HttpServletRequest request
				){
			
			User user = getCurrentUser(request);//鑾峰彇褰撳墠鐢ㄦ埛
			
			/*//娴嬭瘯
			User user = userService.findNum("gg");*/
			User receiver = userService.findNum(receiverAccount);//鎵惧埌绉佷俊鎺ユ敹鑰�
			PrivateMessage privateMessage = new PrivateMessage();
			privateMessage.setPrivateMessageSender(user);
			privateMessage.setPrivateMessageReceiver(receiver);
			privateMessage.setPrivateText(privateText);
			return privateMessageService.save(privateMessage);

			}
		
		/**
		 * 2016-12-22 19:06:02
		 * 鏌ユ壘绉佷俊鐨勫唴瀹�
		 * @param senderId
		 * @param page
		 * @param request
		 * @return  Page<PrivateMessage>
		 */
		@RequestMapping(value= "/findPrivateMessage/{receiverId}")
		public Page<PrivateMessage> findPrivateMessageByReceiverId( @PathVariable int receiverId,
				@RequestParam(defaultValue="0") int page,
				HttpServletRequest request
			
				){
			//User user = userService.findNum("gg"); //娴嬭瘯鏁版嵁
			User user = getCurrentUser(request);//
		
	    return privateMessageService.findPrivateMessagesByReveiverId(receiverId,user.getId(), page);
		}
//	浼犲崠瀹剁殑id锛岃繑鍥炲崠瀹剁殑璁㈤槄鏁�
	@RequestMapping("/saler/{saler_id}/subscribe")
	public int countSubscribe(@PathVariable int saler_id){
		return subscribeService.countSubscribe(saler_id);
	}
//	浼犲崠瀹剁殑id锛屾鏌ユ垜鏄惁璁㈤槄璇ュ崠瀹�
	@RequestMapping("/saler/{saler_id}/issubscribe")
	public boolean checkSubscribe(@PathVariable int saler_id,HttpServletRequest request){
		User me = getCurrentUser(request);
		return subscribeService.checkSubscribe(me.getId(), saler_id);
	}
//	浼犵敤鎴凤紝杩斿洖鐢ㄦ埛璁㈤槄鐨勫崠瀹�
	@RequestMapping(value="/user_id/subscribe")
	public List<User> getBookByUserName(@PathVariable int user_id){
		return subscribeService.findAllByUser(user_id);
	}
//浼犱竴涓猙oolean锛屼负鐪燂紝娣诲姞璁㈤槄鍏崇郴锛屼负鍋囷紝鍙栨秷璁㈤槄鍏崇郴锛屽苟杩斿洖鍗栧鐨勮璁㈤槄鏁�
	@RequestMapping(value="/saler/{saler_id}/subscribe",method = RequestMethod.POST)
	public int changeSubscribe(
			@PathVariable int saler_id,
			@RequestParam boolean subscribe,
			HttpServletRequest request
			){
		User me = getCurrentUser(request);
		User saler = userService.findOne(saler_id);

		if(subscribe)
			subscribeService.addSubscribe(me, saler);
		else
			subscribeService.removeSubscribe(me, saler);
		
		return subscribeService.countSubscribe(saler_id);
	}
}

