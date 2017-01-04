package com.cloudage.membercenter.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
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
import com.cloudage.membercenter.entity.OrderLists;
import com.cloudage.membercenter.entity.PrivateMessage;
import com.cloudage.membercenter.entity.Subscribe;
import com.cloudage.membercenter.entity.User;
import com.cloudage.membercenter.service.IBookBusService;
import com.cloudage.membercenter.service.IBookService;
import com.cloudage.membercenter.service.ICommentService;
import com.cloudage.membercenter.service.IMoneyService;
import com.cloudage.membercenter.service.IOrderListService;
import com.cloudage.membercenter.service.IPrivateMessageService;
import com.cloudage.membercenter.service.ISubscribeService;
import com.cloudage.membercenter.service.IUserService;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

/*
 * 闁硅矇鍐ㄧ厬缂侇偂绱槐婵嬫偨閵娿倗鑹鹃悗鍦仧楠炲洭宕ラ崟顓ф綒闁哄倽顫夌涵锟�
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

	@Autowired
	IOrderListService orderListService;


	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "HELLO WORLD";
	}


	/**
	 * @PathVariable int book_idmust the same as
	 * @RequestMapping(value = "/book/{book_id}/bookbus", method = RequestMethod.POST)
	 * if it dont't want to the same as,or PathVariable(value = "book_id") int book_id;the (value = "book_id")must the same as
	 * @RequestMapping(value = "/book/{book_id}/bookbus", method = RequestMethod.POST)
	 * @return
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="article_id") ,its content is client not need to add
	 */
	@RequestMapping(value = "/book/{book_id}/bookbus", method = RequestMethod.POST)
	public int addToBookbus(
			@PathVariable int book_id,
			@RequestParam boolean isAddBookToBus,
			HttpServletRequest request) {
		//閼惧嘲绶辫ぐ鎾冲閻€劍鍩�
		User currentuser=getCurrentUser(request);
		//閹垫儳鍩岃ぐ鎾冲娑旓拷
		Book book=bookService.findOne(book_id);
		if (isAddBookToBus) {
			//加入购物车
			bookBusService.addBookbus(currentuser, book);
		}
		else {
			//缁夊娅庣拹顓犲⒖鏉烇拷
			bookBusService.removeBookFromBus(currentuser, book);
		}
		return bookBusService.CountBook(book_id);          //return add to bookbus'number

	}
	
	/**
	 * remove from bookbus
	 * @param isRemoveBookFromBus
	 * @return
	 */
	@RequestMapping(value = "/book/{book_id}/removefrombookbus", method = RequestMethod.POST)
	public void RemoveFromBookBus(
			@PathVariable int book_id,
			@RequestParam boolean isRemoveBookFromBus,
			HttpServletRequest request) 
	{
		Bookbus bookbus=new Bookbus();
		//get current user
		User currentuser=getCurrentUser(request);
		Book book=bookService.findOne(book_id);
		if (isRemoveBookFromBus) {
			
			bookBusService.removeBookFromBus(currentuser, book);
		}
	}

	/*
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="article_id") ,its content is client not need to add
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(
			@RequestParam(name = "num") String num,//閻犳劧绠戣ぐ锟�
			@RequestParam(name = "password") String password, //閻庨潧妫涢悥锟�
			@RequestParam(name = "email") String email,//闂侇収鍠氶锟�
			@RequestParam(name = "name") String name,//闁哄嫮鏁歌ⅷ
			@RequestParam(name = "phoneNumb") String phoneNumb,//闁活澀绲婚惁浠嬪矗妞嬪海鍨�
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
	 * 闁谎嗩嚙缂嶅秹骞欏鍕▕
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

	//闊洦顭堥鍥╋拷闈涙閻栨粓鏁嶅畝鍕閻犱焦鍎抽惁鎴︽儘閿燂拷
	@RequestMapping(value="/passwordrecover",method=RequestMethod.POST)
	public boolean resetPassword(
			@RequestParam String phone,
			@RequestParam String passwordHash){
		User user=userService.findPhone(phone);
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
	 * 闁兼儳鍢茬欢杈亹閹惧啿顤呴柣鈧妽閸╋拷
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
	 * 闁拷閸戣櫣娅ヨぐ鏇礉閸樼粯甯�session
	 */
	@RequestMapping(value="/exit")
	public void exit(HttpServletRequest request){
		User me=getCurrentUser(request);
		if(me!=null){
			request.getSession(true).removeAttribute("user");
		}
	}

	/*
	 * 闁兼儳鍢茬欢閬嶆焽椤旂粯顐�
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


	@RequestMapping(value = "/phone", method = RequestMethod.POST)
	public boolean Phone(
			@RequestParam String phone,
			//@RequestParam String password,
			HttpServletRequest request) {

		User user =userService.findPhone(phone);           //
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
	 * @PathVariable int book_idmust the same as
	 * @RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	 * if it dont't want to the same as,or PathVariable(value = "book_id") int book_id;the (value = "book_id")must the same as
	 * @RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	 * @return
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="book_id") ,its content is client not need to add
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
	  @PathVariable int book_idmust the same as
	 * @RequestMapping(value = "/book/{book_id}/comment/{page}", method = RequestMethod.POST)
	 * if it dont't want to the same as,or PathVariable(value = "book_id") int book_id;the (value = "book_id")must the same as
	 * @RequestMapping(value = "/book/{book_id}/comment/{page}", method = RequestMethod.POST)
	 * @return
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="book_id") ,its content is client not need to addn
	 */
	@RequestMapping(value= "/book/{book_id}/comment/{page}")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id,
			@PathVariable int page) {

		return commentService.findCommentNumofBook(book_id, page);
	}

	/*
	 * @PathVariable int book_idmust the same as
	 * @RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	 * if it dont't want to the same as,or PathVariable(value = "book_id") int book_id;the (value = "book_id")must the same as
	 * @RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	 * @return
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="book_id") ,its content is client not need to addn
	 */
	@RequestMapping(value="/book/{book_id}/comment")
	public Page<Comment> getCommentofArtical(
			@PathVariable(value="book_id") int book_id) {

		return commentService.findCommentNumofBook(book_id, 0);
	}

	/**
	 * @PathVariable int book_idmust the same as
	 * @RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	 * if it dont't want to the same as,or PathVariable(value = "book_id") int book_id;the (value = "book_id")must the same as
	 * @RequestMapping(value = "/book/{book_id}/comment", method = RequestMethod.POST)
	 * @return
	 * @RequestParam(name="content") String content,its content is client need to add
	 * @PathVariable(value="book_id") ,its content is client not need to addn
	 * @return
	 */
	@RequestMapping(value="/comments")
	public Page<Comment> getAllCommentsOfAuthor(HttpServletRequest request)
	{
		//闁兼儳鍢茬欢杈亹閹惧啿顤呴柣鈧妽閸╋拷
		User user=getCurrentUser(request);
		return commentService.findAllCommentofAuthor(user.getId(), 0);
	}

	/**
	 * get CurrentUser閳ユΞ all add to bookbus'book
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
	 * 閻庢稒锚閸欏棝宕堕崣銉ュ濞ｅ洠鍓濇导锟�
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
			MultipartFile bookavatar,//閻庢稒蓱閺備線宕堕崜褍顣�
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
				book.setBookavatar("upload/books/"+title+".png");           //閻庢稒蓱閺備線宕堕崜褍顣婚柣銊ュ閻儳顕ラ敓锟�

			} catch (Exception e) {
			}
		}
		bookService.updateSubscribeB(currentUser.getId());

		return bookService.save(book);
	}

	//闁兼儳鍢茶ぐ鍥礄閸濆嫭鏆涢柛銉ュ綖閸旂喖宕氬Δ鍕╋拷锟�
	@RequestMapping("/books/{page}")
	public Page<Book> getFeeds(@PathVariable int page){
		return bookService.getBooks(page);
	}
	@RequestMapping("/books")
	public Page<Book> getFeeds(){
		return getFeeds(0);
	}

	@RequestMapping(value="/book/s/{keyword}")
	public Page<Book> fingTextByKeyword(
			@PathVariable String keyword,
			@RequestParam(defaultValue="0") int page){
		return findTextByKeyword(keyword, page);
	}
	//閹兼粎鍌ㄩ惃鍕鏉炶姤娲挎径锟�
	@RequestMapping(value="/book/s/{keyword}/{page}")
	public Page<Book> findTextByKeyword(
			@PathVariable String keyword,
			@PathVariable int page){
		return bookService.findTextByKeyword(keyword, page);
	}

	//根据图书标签搜索图书(图书分类)
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

	//閺嶈宓侀崶鍙ュ姛閺嶅洨顒烽崪灞藉彠闁款喖鐡ч幖婊呭偍閸ュ彞鍔�
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
	 * 闁规亽鍎查弫褰掑矗閹达讣鎷锋担鍦ラ柟顓у灣濞堟垿骞掗妷銉ョ稉
	 * @param String private:缂佸妞掓穱濠囨儍閸曨偄寰斿ù锝嗘尭閸炲锟界櫢鎷�
	 * @param String receiverAccount :缂佸妞掓穱濠囧箳閵夛附鏆柤鏉挎噽濞堟垹鏁幇顒�濞�
	 * 
	 */
	@RequestMapping(value = "/privateMessage",method = RequestMethod.POST)
	public PrivateMessage savePrivateMessage(@RequestParam String privateText,
			@RequestParam String receiverAccount,
			HttpServletRequest request
			){

		User user = getCurrentUser(request);//闁兼儳鍢茶ぐ鍥亹閹惧啿顤呴柣鈧妽閸╋拷

		/*//婵炴潙顑堥惁锟�
			User user = userService.findNum("gg");*/
		User receiver = userService.findNum(receiverAccount);//闁瑰灚鍎抽崺宀�绮旀担铚傜箚闁规亽鍎查弫褰掓嚀閿燂拷
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setPrivateMessageSender(user);
		privateMessage.setPrivateMessageReceiver(receiver);
		privateMessage.setPrivateText(privateText);
		return privateMessageService.save(privateMessage);

	}

	/**
	 * 2016-12-22 19:06:02
	 * 闁哄被鍎叉竟妯肩矓娴ｈ渹绻嗛柣銊ュ閸炲锟界櫢鎷�
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
		//User user = userService.findNum("gg"); //婵炴潙顑堥惁顖炲极閻楀牆绁�
		User user = getCurrentUser(request);//

		return privateMessageService.findPrivateMessagesByReveiverId(receiverId,user.getId(), page);
	}

	/**
	 * 2016-12-23 18:28:39
	 * 閺屻儲澹樼粔浣蜂繆閻ㄥ嫬鍨悰锟�
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
	//	传卖家的id，返回卖家的订阅数
	@RequestMapping("/saler/{saler_id}/subscribe")
	public int countSubscribe(@PathVariable int saler_id){
		return subscribeService.countSubscribe(saler_id);
	}
	//	娴肩姴宕犵�瑰墎娈慽d閿涘本顥呴弻銉﹀灉閺勵垰鎯佺拋銏ゆ鐠囥儱宕犵�癸拷
	@RequestMapping("/saler/{saler_id}/issubscribe")
	public boolean checkSubscribe(@PathVariable int saler_id,HttpServletRequest request){
		User me = getCurrentUser(request);
		return subscribeService.checkSubscribe(me.getId(), saler_id);
	}
	//	娴肩姷鏁ら幋鍑ょ礉鏉╂柨娲栭悽銊﹀煕鐠併垽妲勯惃鍕礌鐎癸拷
	@RequestMapping(value="/{user_id}/subscribe")
	public List<Subscribe> getSalerByUserName(@PathVariable int user_id){
		return subscribeService.findAllByUser(user_id);
	}
	@RequestMapping(value="/subscribe/b",method=RequestMethod.POST)
	public void changeB(@RequestParam int user_id,
			@RequestParam int saler_id,
			@RequestParam boolean b){
		subscribeService.changeBoolean(user_id,saler_id,b);
	}
	//传一个boolean，为真，添加订阅关系，为假，取消订阅关系，并返回卖家的被订阅数
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
	@RequestMapping(value="/subscribe/{user_id}/count")
	public int getCount(@PathVariable int user_id){
		if(subscribeService.isExistence(user_id)>0)
			return subscribeService.getUserCount(user_id);
		else
			return 0;

	}
	@RequestMapping(value = "/subscribe/{user_id}/{saler_id}")
	public void setCountZero(@PathVariable int user_id,
			@PathVariable int saler_id){
		subscribeService.setCountZero(user_id,saler_id);
	}
	@RequestMapping(value="/subscribe/{user_id}/{saler_id}/count")
	public int getCountWithSalerId(@PathVariable int user_id,@PathVariable int saler_id){
		return subscribeService.getCountWithSalerId(user_id,saler_id);
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

	//璁㈠崟
	@RequestMapping(value = "/books/{book_id}/orders", method = RequestMethod.POST)
	public OrderLists addToOrderList(
			@PathVariable int book_id,
			@RequestParam String payway,
			@RequestParam String finish,
			@RequestParam String orderId,//璁㈠崟鍙�
			//			@RequestParam int booksAdded,
			@RequestParam String payMoney,
			HttpServletRequest request) {

		User currentuser=getCurrentUser(request);
		Book book=bookService.findOne(book_id);

		//		OrderLists.orders_Key key=new orders_Key();               //获得对象
		//		key.setBook(book);
		//		key.setUser(currentuser);

		OrderLists orderList = new OrderLists();
		//		orderList.setId(key);
		orderList.setBook(book);
		orderList.setUser(currentuser);
		//----------------------
		orderList.setPayway(payway);
		orderList.setFinish(finish);
		//		orderList.setBooksAdded(booksAdded);
		orderList.setOrderId(orderId);
		orderList.setPayMoney(payMoney);
		//删除购物车
		bookBusService.removeBookFromBus(currentuser, book);

		return orderListService.save(orderList);

	}

	@RequestMapping(value="/orders/{tag}/{page}")
	public Page<OrderLists> getOrders(
			@PathVariable String tag,
			@RequestParam(defaultValue="0") int page,
			HttpServletRequest request){
		if(tag.equals("BUY")){//我买到的书
			User user = getCurrentUser(request);
			int userId = user.getId();
			return orderListService.getListsBuy(userId,page);
		}
		else//我卖出的书
		{
			User user = getCurrentUser(request);
			int userId = user.getId();
			return orderListService.getListsSale(userId,page);
		}
	}

	/*@RequestMapping(value="/orders")
	public Page<OrderLists> getOrders(
			HttpServletRequest request){
		User user = getCurrentUser(request);
		int userId = user.getId();
		return orderListService.getLists(userId,0);
	}*/
	
	//获取我买到的书
	@RequestMapping(value="/orders/{tag}")
	public Page<OrderLists> getOrderss(
			@PathVariable String tag,
			HttpServletRequest request){
		if(tag.equals("BUY")){//我买到的书
			User user = getCurrentUser(request);
			int userId = user.getId();
			return orderListService.getListsBuy(userId,0);
		}
		else//我卖出的书
		{
			User user = getCurrentUser(request);
			int userId = user.getId();
			return orderListService.getListsSale(userId,0);
		}
		
	}
	
	
	//删除指定的订单
	@RequestMapping(value="/deleteOrder/{orderId}" ,method=RequestMethod.POST)
	public boolean  deleteOrder(@PathVariable int orderId){
		return orderListService.deleteOrderById(orderId);
	}


	//
	//-get orders------
	@RequestMapping("/orders/get/{order_numb}")
	public OrderLists getOrders(@PathVariable String order_numb){
		return orderListService.findOrdersByOrderNumb(order_numb);
	}

	//get count of comment about book
	@RequestMapping("/count/{comment_id}/comments")
	public int countComments(@PathVariable int comment_id){
		return commentService.CountCommentsNumber(comment_id);
	}

	//---------余额
	@RequestMapping(value="/me/recharge/use",method = RequestMethod.POST)
	public User saveMoneyUse(
			/*@RequestParam String currentUser,*/
			@RequestParam float useMoney,
//			@RequestParam String orderId,
			HttpServletRequest request){
		User user = getCurrentUser(request);
		user.setSumMoney(user.getSumMoney()-useMoney);
		

		return userService.save(user);
	}


}

