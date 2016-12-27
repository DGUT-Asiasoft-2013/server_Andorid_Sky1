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
import com.cloudage.membercenter.entity.Bookbus;
import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.Money;
import com.cloudage.membercenter.entity.PrivateMessage;
import com.cloudage.membercenter.entity.Subscribe;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.IBookBusService;
import com.cloudage.membercenter.service.IBookService;
import com.cloudage.membercenter.service.ICommentService;
import com.cloudage.membercenter.service.IMoneyService;
import com.cloudage.membercenter.service.IPrivateMessageService;
import com.cloudage.membercenter.service.ISubscribeService;
import com.cloudage.membercenter.service.IUserService;

/*
 * 閹貉冨煑缁紮绱濋悽銊ょ艾鐎圭偟骞囬崥鍕潚閺傝纭�
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
	
	@Autowired
	IBookBusService bookBusService;
	
	@Autowired
	IMoneyService moneyService;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "HELLO WORLD";
	}

	
	/**
	 * 涓嬮潰涓哄姞鍏ヨ喘鐗╄溅
	 * @PathVariable int book_id蹇呴』涓�
	 * @RequestMapping(value = "/book/{book_id}/bookbus", method = RequestMethod.POST)
	 * 閲岄潰鐨刡ook_id鐩稿悓锛屽惁鍒欏氨蹇呴』鍔燖PathVariable(value = "book_id") int book_id锛岄噷闈㈢殑value蹇呴』涓嶡RequestMapping閲岄潰鐨勭浉鍚�
	 * @return
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="article_id") ,its content is client not need to add
	 */
	@RequestMapping(value = "/book/{book_id}/bookbus", method = RequestMethod.POST)
	public int addToBookbus(
			@PathVariable int book_id,
			@RequestParam boolean isAddBookToBus,
			HttpServletRequest request) {
		//鑾峰緱褰撳墠鐢ㄦ埛
		User currentuser=getCurrentUser(request);
		//鎵惧埌褰撳墠涔�
		Book book=bookService.findOne(book_id);
		
		if (isAddBookToBus) {
			
			//鍔犲叆璐墿杞�
			bookBusService.addBookbus(currentuser, book);
		}
		else {
			//绉婚櫎璐墿杞�
			bookBusService.removeBookFromBus(currentuser, book);
		}
		return bookBusService.CountBook(book_id);          //return add to bookbus'number
		
	}


	/*
	 * 濞夈劌鍞介幙宥勭稊
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="article_id") ,its content is client not need to add
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(
			@RequestParam(name = "num") String num,//鐠愶箑褰�
			@RequestParam(name = "password") String password, //鐎靛棛鐖�
			@RequestParam(name = "email") String email,//闁喚顔�
			@RequestParam(name = "name") String name,//閺勭數袨
			@RequestParam(name = "phoneNumb") String phoneNumb,//閻絻鐦介崣椋庣垳
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
	 * 閻ц缍嶉幙宥勭稊
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

	//韫囨顔囩�靛棛鐖滈敍宀勫櫢鐠佹儳鐦戦惍锟�
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
	
	/**
	 * change name
	 * @param name
	 * @param request
	 * @return
	 */

	@RequestMapping(value="/change/name",method=RequestMethod.POST)
	public boolean resetName(
			@RequestParam String name,
			HttpServletRequest request){
		User user=getCurrentUser(request);
		
		user.setName(name);
		userService.save(user);
		return true;
	}
	
	/**
	 * change email
	 * @param email
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="/change/email",method=RequestMethod.POST)
	public boolean resetEmail(
			@RequestParam String email,
			HttpServletRequest request){
		User user=getCurrentUser(request);
		
		user.setEmail(email);
		userService.save(user);
		return true;
	}
	
	/**
	 * change phone
	 * @param phone
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="/change/phone",method=RequestMethod.POST)
	public boolean resetPhone(
			@RequestParam String phone,
			HttpServletRequest request){
		User user=getCurrentUser(request);
		
		user.setPhoneNumb(phone);
		userService.save(user);
		return true;
	}
	
	/**
	 * change qq
	 * @param qq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/change/qq",method=RequestMethod.POST)
	public boolean resetQq(
			@RequestParam String qq,
			HttpServletRequest request){
		User user=getCurrentUser(request);
		
		user.setQq(qq);
		userService.save(user);
		return true;
	}
	
	/**
	 * change qq
	 * @param qq
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/change/avatar",method=RequestMethod.POST)
	public User resetAvatar(
			MultipartFile avatar,
			HttpServletRequest request){
		User user=getCurrentUser(request);
		try {
			String realPath=request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
			FileUtils.copyInputStreamToFile(avatar.getInputStream(), new File(realPath,user.getAccount()+".png"));
			user.setAvatar("upload/"+user.getAccount()+".png");           //

		} catch (Exception e) {
			// TODO: handle exception
		}

		return  userService.save(user);
		
	}

	/*
	 * 閼惧嘲绶辫ぐ鎾冲閻€劍鍩�
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
	 * 閫�鍑虹櫥褰曪紝鍘绘帀session
	 */
	@RequestMapping(value="/exit")
	public void exit(HttpServletRequest request){
		User me=getCurrentUser(request);
		if(me!=null){
			request.getSession(true).removeAttribute("user");
		}
	}

	/*
	 * 閼惧嘲绶遍柇顔绘
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
	 * @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id韫囧懘銆忕捄锟�
	 * @PathVariable(value = "book_id") int book_id娑擃叏绱漣nt閻ㄥ嫬褰夐柌蹇庣閺嶅嚖绱濇俊鍌涚亯閹厖绗夋稉锟介弽鍑ょ礉閸掞拷
	 * 閸︺劌鍙鹃崜宥夋桨閸旂垝alue = "book_id"閿涘奔绲炬潻娆庨嚋韫囧懘銆忕捄锟�
	 *  @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id娑擄拷閺嶏拷
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
	 * @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id韫囧懘銆忕捄锟�
	 * @PathVariable(value = "book_id") int book_id娑擃叏绱漣nt閻ㄥ嫬褰夐柌蹇庣閺嶅嚖绱濇俊鍌涚亯閹厖绗夋稉锟介弽鍑ょ礉閸掞拷
	 * 閸︺劌鍙鹃崜宥夋桨閸旂垝alue = "book_id"閿涘奔绲炬潻娆庨嚋韫囧懘銆忕捄锟�
	 *  @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id娑擄拷閺嶏拷
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
	 * @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id韫囧懘銆忕捄锟�
	 * @PathVariable(value = "book_id") int book_id娑擃叏绱漣nt閻ㄥ嫬褰夐柌蹇庣閺嶅嚖绱濇俊鍌涚亯閹厖绗夋稉锟介弽鍑ょ礉閸掞拷
	 * 閸︺劌鍙鹃崜宥夋桨閸旂垝alue = "book_id"閿涘奔绲炬潻娆庨嚋韫囧懘銆忕捄锟�
	 *  @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id娑擄拷閺嶏拷
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
	 * @RequestMapping(value = "/book/{book_id}/comment")閲岄潰鐨刡ook_id蹇呴』涓�
	 * @PathVariable(value = "book_id") int book_id閲岄潰鐨刬nt绫诲瀷鐨勭浉鍚岋紝濡傛灉瑕佷笉鍚�
	 * 鍚﹀垯灏辫缃畍alue = "book_id"涓�/{book_id}鐩稿悓
	 *  @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id娑擄拷閺嶏拷
	 * @param content
	 * @param book_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/comments")
	public Page<Comment> getAllCommentsOfAuthor(HttpServletRequest request)
	{
		//閼惧嘲绶辫ぐ鎾冲閻€劍鍩�
		User user=getCurrentUser(request);
		return commentService.findAllCommentofAuthor(user.getId(), 0);
	}

	/**
	 * get CurrentUser鈥榮 all add to bookbus'book
	 */
	@RequestMapping(value="/bookbus")
	public Page<Bookbus> getAllbookAddtoBookBus(HttpServletRequest request)
	{
		//get currentuser
		User user=getCurrentUser(request);
		return bookBusService.findCurrentUserAllBookAddtoBookBus(user.getId(), 0);
	}
	/***
	 * 
	 * 鐎涙ê鍙嗛崶鍙ュ姛娣団剝浼�
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
			MultipartFile bookavatar,//鐎涙ɑ鏂侀崶鍓у
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
				book.setBookavatar("upload/books/"+title+".png");           //鐎涙ɑ鏂侀崶鍓у閻ㄥ嫯鐭惧锟�

			} catch (Exception e) {
			}
		}

		return bookService.save(book);
	}

	//閼惧嘲褰囬崙鍝勬暛閸ュ彞鍔熼崚妤勩��
	@RequestMapping("/books/{page}")
	public Page<Book> getFeeds(@PathVariable int page){
		return bookService.getBooks(page);
	}
	@RequestMapping("/books")
	public Page<Book> getFeeds(){
		return getFeeds(0);
	}
	
	//閹兼粎鍌ㄩ崶鍙ュ姛--------(閺嶈宓� 閸ュ彞鍔熼崥宥囆瀨閸ュ彞鍔熸担婊嗭拷鍘奍SBN|閸楁牕顔� 閹兼粎鍌�)
	@RequestMapping(value="/book/s/{keyword}")
	public Page<Book> fingTextByKeyword(
			@PathVariable String keyword,
			@RequestParam(defaultValue="0") int page){
		return findTextByKeyword(keyword, page);
	}
	//鎼滅储鐨勫姞杞芥洿澶�
	@RequestMapping(value="/book/s/{keyword}/{page}")
	public Page<Book> findTextByKeyword(
			@PathVariable String keyword,
			@PathVariable int page){
		return bookService.findTextByKeyword(keyword, page);
	}
	
	//鏍规嵁鍥句功鏍囩鎼滅储鍥句功(鍥句功鍒嗙被)
	@RequestMapping("/books/{tag}/class/{page}")
	public Page<Book> findBooksByType(
			@PathVariable String tag,
			@PathVariable int page){
		return bookService.getBooksByType(tag,page);
	}
	@RequestMapping("/books/{tag}/class")
	public Page<Book> getBooksByType(@PathVariable String tag){
		return findBooksByType(tag,0);
	}
	
	//鏍规嵁鍥句功鏍囩鍜屽叧閿瓧鎼滅储鍥句功
	@RequestMapping("/books/{keyword}/and/{tag}/class/{page}")
	public Page<Book> findBooksByKeywordAndType(
			@PathVariable String keyword,
			@PathVariable String tag,
			@PathVariable int page){
		return bookService.getBooksByKeywordAndType(keyword,tag,page);
	}
	@RequestMapping("/books/{keyword}/and/{tag}/class")
	public Page<Book> getBooksByKeywordAndType(
			@PathVariable String keyword,
			@PathVariable String tag){
		return findBooksByKeywordAndType(keyword,tag,0);
	}

	/**
	 * 2016-12-22 19:01:20
	 * 閹恒儲鏁归崣鎴︼拷浣圭Х閹垳娈戦幒銉ュ經
	 * @param String private:缁変椒淇婇惃鍕徔娴ｆ挸鍞寸�癸拷
	 * @param String receiverAccount :缁変椒淇婇幒銉︽暪閼板懐娈戠敮鎰娇
	 * 
	 */
		@RequestMapping(value = "/privateMessage",method = RequestMethod.POST)
		public PrivateMessage savePrivateMessage(@RequestParam String privateText,
				@RequestParam String receiverAccount,
				HttpServletRequest request
				){
			
			User user = getCurrentUser(request);//閼惧嘲褰囪ぐ鎾冲閻€劍鍩�
			
			/*//濞村鐦�
			User user = userService.findNum("gg");*/
			User receiver = userService.findNum(receiverAccount);//閹垫儳鍩岀粔浣蜂繆閹恒儲鏁归懓锟�
			PrivateMessage privateMessage = new PrivateMessage();
			privateMessage.setPrivateMessageSender(user);
			privateMessage.setPrivateMessageReceiver(receiver);
			privateMessage.setPrivateText(privateText);
			return privateMessageService.save(privateMessage);

			}
		
		/**
		 * 2016-12-22 19:06:02
		 * 閺屻儲澹樼粔浣蜂繆閻ㄥ嫬鍞寸�癸拷
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
			//User user = userService.findNum("gg"); //濞村鐦弫鐗堝祦
			User user = getCurrentUser(request);//
		
	    return privateMessageService.findPrivateMessagesByReveiverId(receiverId,user.getId(), page);
		}

		/**
		 * 2016-12-23 18:28:39
		 * 鏌ユ壘绉佷俊鐨勫垪琛�
		 * @param request
		 * @return
		 */
	/*	@RequestMapping(value = "/getPrivateMessageList")
		public Page<PrivateMessage> getPrivateMessageList(@RequestParam(defaultValue="1") int a,
				@RequestParam(defaultValue="0") int page,
				HttpServletRequest request){
			User user = getCurrentUser(request);
			
			return privateMessageService.getPrivateMessageList(a,page);
		}*/
		
		@RequestMapping(value = "/getPrivateMessageList")
		public Page<User> getPrivateMessageList(@RequestParam(defaultValue="0") int page,
				HttpServletRequest request){
			User user = getCurrentUser(request);
			
			return privateMessageService.findAllOtherUsersByNum(user.getAccount(),page);
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
	@RequestMapping(value="/{user_id}/subscribe")
	public List<Subscribe> getSalerByUserName(@PathVariable int user_id){
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
//		User saler =  subscribeService.findAllByUser(saler_id).get(0).getId().getSaler();
		User saler = userService.findOne(saler_id);
		if(subscribe)
			subscribeService.addSubscribe(me, saler);
		else
			subscribeService.removeSubscribe(me, saler);

		return subscribeService.countSubscribe(saler_id);
	}
	
	@RequestMapping(value="/me/recharge",method = RequestMethod.POST)
	public User saveMoney(
			/*@RequestParam String currentUser,*/
			@RequestParam float recharge,
			HttpServletRequest request){
		User user = getCurrentUser(request);
		user.setSumMoney(user.getSumMoney()+recharge);

		return userService.save(user);
	}
	
	@RequestMapping(value="/me/money",method=RequestMethod.GET)
	public float getCurrentMoney(HttpServletRequest request) {
		User user = getCurrentUser(request);
		return user.getSumMoney();
		
	}

}

