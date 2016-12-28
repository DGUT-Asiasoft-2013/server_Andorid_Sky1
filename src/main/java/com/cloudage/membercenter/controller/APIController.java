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
	 * 娑撳娼版稉鍝勫閸忋儴鍠橀悧鈺勬簠
	 * @PathVariable int book_id韫囧懘銆忔稉锟�
	 * @RequestMapping(value = "/book/{book_id}/bookbus", method = RequestMethod.POST)
	 * 闁插矂娼伴惃鍒ok_id閻╃鎮撻敍灞芥儊閸掓瑥姘ㄨ箛鍛淬�忛崝鐕朠athVariable(value = "book_id") int book_id閿涘矂鍣烽棃銏㈡畱value韫囧懘銆忔稉宥equestMapping闁插矂娼伴惃鍕祲閸氾拷
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

			//閸旂姴鍙嗙拹顓犲⒖鏉烇拷
			bookBusService.addBookbus(currentuser, book);
		}
		else {
			//缁夊娅庣拹顓犲⒖鏉烇拷
			bookBusService.removeBookFromBus(currentuser, book);
		}
		return bookBusService.CountBook(book_id);          //return add to bookbus'number

	}


	/*
	 * 婵炲鍔岄崬浠嬪箼瀹ュ嫮绋�
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
	 * @RequestMapping(value = "/book/{book_id}/comment")闂佹彃鐭傚浼存儍閸掝摰ok_id闊洤鎳橀妴蹇曟崉閿燂拷
	 * @PathVariable(value = "book_id") int book_id濞戞搩鍙忕槐婕t闁汇劌瀚ぐ澶愭煂韫囧海顏遍柡宥呭殩缁辨繃淇婇崒娑氫函闁诡垰鍘栫粭澶嬬▔閿熶粙寮介崙銈囩闁告帪鎷�
	 * 闁革负鍔岄崣楣冨礈瀹ュ妗ㄩ柛鏃傚灊alue = "book_id"闁挎稑濂旂徊鐐交濞嗗酣鍤嬮煫鍥ф嚇閵嗗繒鎹勯敓锟�
	 *  @RequestMapping(value = "/book/{book_id}/comment")闂佹彃鐭傚浼存儍閸掝摰ok_id濞戞搫鎷烽柡宥忔嫹
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
	 * @RequestMapping(value = "/book/{book_id}/comment")闂佹彃鐭傚浼存儍閸掝摰ok_id闊洤鎳橀妴蹇曟崉閿燂拷
	 * @PathVariable(value = "book_id") int book_id濞戞搩鍙忕槐婕t闁汇劌瀚ぐ澶愭煂韫囧海顏遍柡宥呭殩缁辨繃淇婇崒娑氫函闁诡垰鍘栫粭澶嬬▔閿熶粙寮介崙銈囩闁告帪鎷�
	 * 闁革负鍔岄崣楣冨礈瀹ュ妗ㄩ柛鏃傚灊alue = "book_id"闁挎稑濂旂徊鐐交濞嗗酣鍤嬮煫鍥ф嚇閵嗗繒鎹勯敓锟�
	 *  @RequestMapping(value = "/book/{book_id}/comment")闂佹彃鐭傚浼存儍閸掝摰ok_id濞戞搫鎷烽柡宥忔嫹
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
	 * @RequestMapping(value = "/book/{book_id}/comment")闂佹彃鐭傚浼存儍閸掝摰ok_id闊洤鎳橀妴蹇曟崉閿燂拷
	 * @PathVariable(value = "book_id") int book_id濞戞搩鍙忕槐婕t闁汇劌瀚ぐ澶愭煂韫囧海顏遍柡宥呭殩缁辨繃淇婇崒娑氫函闁诡垰鍘栫粭澶嬬▔閿熶粙寮介崙銈囩闁告帪鎷�
	 * 闁革负鍔岄崣楣冨礈瀹ュ妗ㄩ柛鏃傚灊alue = "book_id"闁挎稑濂旂徊鐐交濞嗗酣鍤嬮煫鍥ф嚇閵嗗繒鎹勯敓锟�
	 *  @RequestMapping(value = "/book/{book_id}/comment")闂佹彃鐭傚浼存儍閸掝摰ok_id濞戞搫鎷烽柡宥忔嫹
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
	 * @RequestMapping(value = "/book/{book_id}/comment")闁插矂娼伴惃鍒ok_id韫囧懘銆忔稉锟�
	 * @PathVariable(value = "book_id") int book_id闁插矂娼伴惃鍒琻t缁鐎烽惃鍕祲閸氬矉绱濇俊鍌涚亯鐟曚椒绗夐崥锟�
	 * 閸氾箑鍨亸杈啎缂冪晬alue = "book_id"娑擄拷/{book_id}閻╃鎮�
	 *  @RequestMapping(value = "/book/{book_id}/comment")闂佹彃鐭傚浼存儍閸掝摰ok_id濞戞搫鎷烽柡宥忔嫹
	 * @param content
	 * @param book_id
	 * @param request
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

	//闁瑰吋绮庨崒銊╁炊閸欍儱濮�--------(闁哄秷顫夊畵锟� 闁搞儱褰為崝鐔煎触瀹ュ泦鐎ㄩ柛銉ュ綖閸旂喐鎷呭鍡嫹閸樺SBN|闁告鐗曢锟� 闁瑰吋绮庨崒锟�)
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

	//閺嶈宓侀崶鍙ュ姛閺嶅洨顒烽幖婊呭偍閸ュ彞鍔�(閸ュ彞鍔熼崚鍡欒)
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
	//	娴肩姴宕犵�瑰墎娈慽d閿涘矁绻戦崶鐐插礌鐎瑰墎娈戠拋銏ゆ閺侊拷
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
	//娴肩姳绔存稉鐚檕olean閿涘奔璐熼惇鐕傜礉濞ｈ濮炵拋銏ゆ閸忓磭閮撮敍灞艰礋閸嬪浄绱濋崣鏍ㄧХ鐠併垽妲勯崗宕囬兇閿涘苯鑻熸潻鏂挎礀閸楁牕顔嶉惃鍕潶鐠併垽妲勯弫锟�
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

	//璁㈠崟
	@RequestMapping(value = "/books/{book_id}/orders", method = RequestMethod.POST)
	public OrderLists addToOrderList(
			@PathVariable int book_id,
			@RequestParam String payway,
			@RequestParam String finish,
			@RequestParam String orderId,//璁㈠崟鍙�
			@RequestParam int booksAdded,
			@RequestParam float payMoney,
			HttpServletRequest request) {
		
		User currentuser=getCurrentUser(request);
		Book book=bookService.findOne(book_id);
		orderListService.addOrders(currentuser, book);
		
		OrderLists orderList = new OrderLists();
		orderList.setPayway(payway);
		orderList.setFinish(finish);
		orderList.setBooksAdded(booksAdded);
		orderList.setOrderId(orderId);
		orderList.setPayMoney(payMoney);
		
		return orderListService.save(orderList);

	}

	@RequestMapping("/orders/{page}")
	public Page<OrderLists> getOrders(
			@PathVariable() int page,
			HttpServletRequest request){
		User user = getCurrentUser(request);
		int userId = user.getId();
		return orderListService.getLists(userId,page);
	}

	@RequestMapping("/orders")
	public Page<OrderLists> getOrders(
			HttpServletRequest request){
		User user = getCurrentUser(request);
		int userId = user.getId();
		return orderListService.getLists(userId,0);
	}
	
	
}

