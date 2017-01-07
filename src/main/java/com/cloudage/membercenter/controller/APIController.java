package com.cloudage.membercenter.controller;

import java.io.File;
import java.util.ArrayList;
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
import com.cloudage.membercenter.entity.Money;
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
 * 闂佺鐭囬崘銊у幀缂備緡鍋傜槐顔炬濠靛鍋ㄩ柕濞垮�楅懝楣冩倵閸︻厼浠ф鐐叉喘瀹曘儵宕熼褎缍掗梺鍝勫�介～澶屾兜閿燂拷
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
		//闁兼儳鍢茬欢杈亹閹惧啿顤呴柣鈧妽閸╋拷
		User currentuser=getCurrentUser(request);
		//闁瑰灚鍎抽崺宀冦亹閹惧啿顤呭☉鏃撴嫹
		Book book=bookService.findOne(book_id);
		if (isAddBookToBus) {
			//鍔犲叆璐墿杞�
			bookBusService.addBookbus(currentuser, book);
		}
		else {
			//缂佸顭峰▍搴ｆ嫻椤撶姴鈷栭弶鐑囨嫹
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
			@RequestParam(name = "num") String num,//闁荤姵鍔х粻鎴ｃ亹閿燂拷
			@RequestParam(name = "password") String password, //闁诲酣娼уΛ娑㈡偉閿燂拷
			@RequestParam(name = "email") String email,//闂備緡鍙庨崰姘额敊閿燂拷
			@RequestParam(name = "name") String name,//闂佸搫瀚弫姝屸叿
			@RequestParam(name = "phoneNumb") String phoneNumb,//闂佹椿婢�缁插鎯佹禒瀣煑濡炲娴烽崹锟�
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
	 * 闂佽皫鍡╁殭缂傚秴绉归獮娆忣吋閸曨厾鈻�
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

	//闂婎偄娲﹂…鍫ヮ敊閸モ晪鎷烽棃娑欘棤闁绘牗绮撻弫宥呯暆閸曨偅顏熼柣鐘辩劍閸庢娊鎯侀幋锔藉剺闁跨噦鎷�
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
	 * 闂佸吋鍎抽崲鑼鏉堫偁浜归柟鎯у暱椤ゅ懘鏌ｉ埀顒�濡介柛鈺嬫嫹
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
	 * 闂侇偓鎷烽柛鎴ｆ濞呫儴銇愰弴顏嗙闁告绮敮锟絪ession
	 */
	@RequestMapping(value="/exit")
	public void exit(HttpServletRequest request){
		User me=getCurrentUser(request);
		if(me!=null){
			request.getSession(true).removeAttribute("user");
		}
	}

	/*
	 * 闂佸吋鍎抽崲鑼闁秵鐒芥い鏃傜帛椤愶拷
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
		//闂佸吋鍎抽崲鑼鏉堫偁浜归柟鎯у暱椤ゅ懘鏌ｉ埀顒�濡介柛鈺嬫嫹
		User user=getCurrentUser(request);
		return commentService.findAllCommentofAuthor(user.getId(), 0);
	}

	/**
	 * get CurrentUser闁炽儲螢 all add to bookbus'book
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
	 * 闁诲孩绋掗敋闁告瑥妫濆畷鍫曞矗閵夈儱顫犳繛锝呮礌閸撴繃瀵奸敓锟�
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
			MultipartFile bookavatar,//闁诲孩绋掕摫闁哄倷绶氬畷鍫曞礈瑜嶉。锟�
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
				book.setBookavatar("upload/books/"+title+".png");           //闁诲孩绋掕摫闁哄倷绶氬畷鍫曞礈瑜嶉。濠氭煟閵娿儱顏柣顓熷劤椤曘儵鏁撻敓锟�

			} catch (Exception e) {
			}
		}
		bookService.updateSubscribeB(currentUser.getId());

		return bookService.save(book);
	}

	//闂佸吋鍎抽崲鑼躲亹閸ヮ剙绀勯柛婵嗗閺嗘盯鏌涢妷銉ョ稏闁告梻鍠栧畷姘旈崟鈺嬫嫹閿燂拷
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
	//闁瑰吋绮庨崒銊╂儍閸曨偄顫ｉ弶鐐跺Г濞叉寧寰勯敓锟�
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

	//闁哄秷顫夊畵渚�宕堕崣銉ュ闁哄秴娲ㄩ鐑藉椽鐏炶棄褰犻梺娆惧枛閻⊙囧箹濠婂懎鍋嶉柛銉ュ綖閸旓拷
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
	 * 闂佽浜介崕鏌ュ极瑜版帒鐭楅柟杈捐閹烽攱鎷呴崷顓夈儵鏌熼褍鐏ｆ繛鍫熷灴楠炴帡濡烽妷銉х▔
	 * @param String private:缂備礁顦鎺撶┍婵犲洦鍎嶉柛鏇ㄥ亜瀵版柨霉閿濆棙灏柛鐐差嚟閿熺晫娅㈤幏锟�
	 * @param String receiverAccount :缂備礁顦鎺撶┍婵犲洤绠抽柕澶涢檮閺嗩亪鏌ら弶鎸庡櫧婵炲牊鍨归弫顕�骞囬锟芥繛锟�
	 * 
	 */
	@RequestMapping(value = "/privateMessage",method = RequestMethod.POST)
	public PrivateMessage savePrivateMessage(@RequestParam String privateText,
			@RequestParam String receiverAccount,
			HttpServletRequest request
			){

		User user = getCurrentUser(request);//闂佸吋鍎抽崲鑼躲亹閸ヮ亗浜归柟鎯у暱椤ゅ懘鏌ｉ埀顒�濡介柛鈺嬫嫹

		/*//濠电偞娼欓鍫ユ儊閿燂拷
			User user = userService.findNum("gg");*/
		User receiver = userService.findNum(receiverAccount);//闂佺懓鐏氶崕鎶藉春瀹�锟界划鏃�鎷呴摎鍌滅畾闂佽浜介崕鏌ュ极瑜版帗鍤�闁跨噦鎷�
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setPrivateMessageSender(user);
		privateMessage.setPrivateMessageReceiver(receiver);
		privateMessage.setPrivateText(privateText);
		return privateMessageService.save(privateMessage);

	}

	/**
	 * 2016-12-22 19:06:02
	 * 闂佸搫琚崕鍙夌珶濡偐鐭撳ù锝堟腹缁诲棝鏌ｉ妸銉ヮ仼闁哥偛顕敓鐣屾閹凤拷
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
		//User user = userService.findNum("gg"); //濠电偞娼欓鍫ユ儊椤栫偛鏋侀柣妤�鐗嗙粊锟�
		User user = getCurrentUser(request);//

		return privateMessageService.findPrivateMessagesByReveiverId(receiverId,user.getId(), page);
	}

	/**
	 * 2016-12-23 18:28:39
	 * 闁哄被鍎叉竟妯肩矓娴ｈ渹绻嗛柣銊ュ閸亞鎮伴敓锟�
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
	//	濞磋偐濮村畷鐘碉拷鐟板濞堟吔d闁挎稑鏈ˉ鍛村蓟閵夛箑鐏夐柡鍕靛灠閹胶鎷嬮姀銈嗩潐閻犲洢鍎卞畷鐘碉拷鐧告嫹
	@RequestMapping("/saler/{saler_id}/issubscribe")
	public boolean checkSubscribe(@PathVariable int saler_id,HttpServletRequest request){
		User me = getCurrentUser(request);
		return subscribeService.checkSubscribe(me.getId(), saler_id);
	}
	//	濞磋偐濮烽弫銈夊箣閸戙倗绀夐弶鈺傛煥濞叉牠鎮介妸锕�鐓曢悹浣靛灲濡插嫰鎯冮崟顐㈢閻庣櫢鎷�
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

		Money money=new Money();
		saveMoneyList(recharge, request);
		return userService.save(user);
	}

	@RequestMapping(value="/me/money",method=RequestMethod.GET)
	public float getCurrentMoney(HttpServletRequest request) {
		User user = getCurrentUser(request);
		return user.getSumMoney();

	}

	@RequestMapping(value="/{user_id}/money/list")
	public Page<Money> getMoneyList(
			HttpServletRequest request){
		User user = getCurrentUser(request);
		//int user_id = user.getId();
		String cuser=user.getAccount();
		return moneyService.getLists(cuser,0);
	}


	@RequestMapping(value="/me/recharge/list",method = RequestMethod.POST)
	public Money saveMoneyList(
			/*@RequestParam String currentUser,*/
			@RequestParam float recharge,
			HttpServletRequest request){
		User user = getCurrentUser(request);
		Money money=new Money();
		/*float sumMoney=money.getSumMoney();
		user.setSumMoney(sumMoney);
		userService.save(user);*/
		//user.setSumMoney(user.getSumMoney()+recharge);


		String currenUser=user.getAccount();

		money.setCurrentUser(currenUser);
		money.setRecharge(recharge);
		money.setSumMoney(user.getSumMoney());

		return moneyService.save(money);
	}
	//鐠併垹宕�
	@RequestMapping(value = "/books/{book_id}/orders", method = RequestMethod.POST)
	public OrderLists addToOrderList(
			@PathVariable int book_id,
			@RequestParam int payway,
			@RequestParam int finish,
			@RequestParam String orderId,
			//			@RequestParam int booksAdded,
			@RequestParam String payMoney,
			HttpServletRequest request) {

		User currentuser=getCurrentUser(request);
		Book book=bookService.findOne(book_id);

		OrderLists orderList = new OrderLists();
		orderList.setBook(book);
		orderList.setUser(currentuser);
		//----------------------
		orderList.setPayway(payway);
		orderList.setFinish(finish);
		orderList.setOrderId(orderId);
		orderList.setPayMoney(payMoney);

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
	@RequestMapping(value="/deleteOrder" ,method=RequestMethod.POST)
	public boolean  deleteOrder(@RequestParam Integer orderId){
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

	//---------浣欓
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

	//改变订单状态------------
	//交易状态:0为已提交未付款；1为已付款；2为处理中；3为已发货；4为订单已取消；5为订单已完成
	@RequestMapping(value="/order/changeState",method = RequestMethod.POST)
	public void changeState(
			@RequestParam String orderId,
			@RequestParam int finish,
			HttpServletRequest request){
		List<OrderLists> orderlist = (List<OrderLists>) orderListService.findOrdersByOrderNumblist(orderId);
		OrderLists order = new OrderLists();
		for(int i =0;i<orderlist.size();i++){
			order = orderlist.get(i);
			order.setFinish(finish);
			orderListService.save(order);
			System.out.println(order.toString());
		}
		

	}


}

