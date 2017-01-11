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
 * 闂備胶顢婇惌鍥礃閵娧冨箑缂傚倷绶￠崑鍌滄椤旂偓顫曟繝闈涱儐閸嬨劑鏌曟繛鍨拷妤呮嚌妤ｅ啯鍊甸柛锔诲幖娴犙勵殽閻愬弶鍠樼�规洏鍎靛畷鐔碱敇瑜庣紞鎺楁⒑閸濆嫬锟戒粙锝炴径灞惧厹闁跨噦鎷�
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
		//闂佸吋鍎抽崲鑼鏉堫偁浜归柟鎯у暱椤ゅ懘鏌ｉ埀顒�濡介柛鈺嬫嫹
		User currentuser=getCurrentUser(request);
		//闂佺懓鐏氶崕鎶藉春瀹�鍐︿汗闁规儳鍟块·鍛槈閺冩挻瀚�
		Book book=bookService.findOne(book_id);
		if (isAddBookToBus) {
			//閸旂姴鍙嗙拹顓犲⒖鏉烇拷
			bookBusService.addBookbus(currentuser, book);
		}
		else {
			//缂備礁顦…宄扳枍鎼达絾瀚绘い鎾跺Т閳锋牠寮堕悜鍥ㄥ
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
			@RequestParam(name = "num") String num,//闂佽崵濮甸崝褏绮婚幋锝冧汗闁跨噦鎷�
			@RequestParam(name = "password") String password, //闂佽閰ｅ褍螞濞戙垺鍋夐柨鐕傛嫹
			@RequestParam(name = "email") String email,//闂傚倷绶￠崣搴ㄥ窗濮橀鏁婇柨鐕傛嫹
			@RequestParam(name = "name") String name,//闂備礁鎼�氼噣寮灞稿徔
			@RequestParam(name = "phoneNumb") String phoneNumb,//闂備焦妞垮锟界紒鎻掝煼閹焦绂掔�ｎ亞鐓戞俊鐐差儏濞寸兘宕归敓锟�
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
	 * 闂備浇鐨崱鈺佹缂傚倸绉寸粔褰掔嵁濞嗗浚鍚嬮柛鏇ㄥ幘閳伙拷
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

	//闂傚鍋勫ú锕傗�﹂崼銉晩闁搞儮鏅幏鐑芥濞戞瑯妫ら梺缁樼墬缁捇寮鍛殕闁告洦鍋呴鐔兼煟閻樿京鍔嶉柛搴㈠▕閹線骞嬮敂钘夊壓闂佽法鍣﹂幏锟�
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
	 * 闂備礁鍚嬮崕鎶藉床閼碱剚顐介弶鍫亖娴滃綊鏌熼幆褍鏆辨い銈呮嚇閺岋綁鍩�椤掞拷婵′粙鏌涢埡瀣
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
	
	@RequestMapping(value = "/me/if", method = RequestMethod.GET)
	public boolean getPayFirst(HttpServletRequest request) {
		User me=getCurrentUser(request);
		
		String payPassword=me.getPayPassword();
		
		if (payPassword.isEmpty()) {
			//
			return false;
		}
		else {
			return true;
		}

	}
	
	@RequestMapping(value = "/set/pay", method = RequestMethod.POST)
	public boolean setPay(
				@RequestParam(name="payPassword") String payPassword,
				HttpServletRequest request) {
		User user=getCurrentUser(request);
		
		user.setPayPassword(payPassword);
		userService.save(user);
		return true;
	}

	
	@RequestMapping(value = "/pay/exit", method = RequestMethod.POST)
	public boolean Pay(
			@RequestParam String payPassword,
			HttpServletRequest request) {
		//Map<String, String[]> params = request.getParameterMap();
		User user=getCurrentUser(request);
		
		if (user!=null && user.getPayPassword().equals(payPassword)) {
			return true;
		}else {
			return false;
		}

	}

	/**
	 * 闂備緡鍋撻幏鐑芥煕閹达絾顏犳繛鍛劥閵囨劙寮撮鍡欘槷闂佸憡顭囩划顖滄暜閿熺氮ession
	 */
	@RequestMapping(value="/exit")
	public void exit(HttpServletRequest request){
		User me=getCurrentUser(request);
		if(me!=null){
			request.getSession(true).removeAttribute("user");
		}
	}

	/*
	 * 闂備礁鍚嬮崕鎶藉床閼碱剚顐介梺顒�绉甸悞鑺ャ亜閺冨倻甯涙い鎰舵嫹
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
		//闂備礁鍚嬮崕鎶藉床閼碱剚顐介弶鍫亖娴滃綊鏌熼幆褍鏆辨い銈呮嚇閺岋綁鍩�椤掞拷婵′粙鏌涢埡瀣
		User user=getCurrentUser(request);
		return commentService.findAllCommentofAuthor(user.getId(), 0);
	}

	/**
	 * get CurrentUser闂佺偨鍎茶灑 all add to bookbus'book
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
	 * 闂佽瀛╃粙鎺楁晪闂佸憡鐟ュΛ婵嗙暦閸洖鐭楅柕澶堝劚椤姵绻涢敐鍛闁告挻绻冪�靛ジ鏁撻敓锟�
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
			MultipartFile bookavatar,//闂佽瀛╃粙鎺曟懌闂佸搫鍊风欢姘暦閸洖绀堢憸宥夈�傞敓锟�
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
				book.setBookavatar("upload/books/"+title+".png");           //闂佽瀛╃粙鎺曟懌闂佸搫鍊风欢姘暦閸洖绀堢憸宥夈�傛繝姘厽闁靛鍎遍顒勬煟椤撶喎鍔ゆい鏇樺劦閺佹捇鏁撻敓锟�

			} catch (Exception e) {
			}
		}
		bookService.updateSubscribeB(currentUser.getId());

		return bookService.save(book);
	}

	//闂備礁鍚嬮崕鎶藉床閼艰翰浜归柛銉墮缁�鍕煕濠靛棗顏柡鍡樼洴閺屾盯濡烽妷銉х◤闂佸憡姊婚崰鏍х暦濮橆儵鏃堝礋閳哄瀚归柨鐕傛嫹
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
	//闂佺懓鍚嬬划搴ㄥ磼閵娾晜鍎嶉柛鏇ㄥ亜椤綁寮堕悙璺盒撴繛鍙夊瀵板嫰鏁撻敓锟�
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

	//闂佸搫绉烽～澶婄暤娓氾拷瀹曞爼宕ｉ妷銉ヮ潬闂佸搫绉村ú銊╊敆閻戣棄妞介悘鐐舵瑜扮娀姊哄▎鎯ф灈闁烩姍鍥х婵犲﹤鎳庨崑宥夋煕閵夈儱缍栭柛鏃撴嫹
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
	 * 闂備浇顫夋禍浠嬪磿閺屻儱鏋佺憸鐗堝笒閻鏌熸潏鎹愵吅闁圭兘鏀遍幏鍛村捶椤撳鍎甸弻鐔碱敇瑜嶉悘锝嗙箾閸喎鐏存鐐村浮婵＄兘濡烽妷褏鈻�
	 * @param String private:缂傚倷绀侀ˇ顔碱渻閹烘挾鈹嶅┑鐘叉处閸庡秹鏌涢弴銊ヤ簻鐎电増鏌ㄩ湁闁挎繂妫欑亸顓㈡煕閻愬樊鍤熼柨鐔烘櫕濞呫垽骞忛敓锟�
	 * @param String receiverAccount :缂傚倷绀侀ˇ顔碱渻閹烘挾鈹嶅┑鐘叉搐缁犳娊鏌曟径娑㈡闁哄棭浜弻銈夊级閹稿骸娅у┑鐐茬墛閸ㄥ綊寮锟介獮鍥敆閿熻姤绻涢敓锟�
	 * 
	 */
	@RequestMapping(value = "/privateMessage",method = RequestMethod.POST)
	public PrivateMessage savePrivateMessage(@RequestParam String privateText,
			@RequestParam String receiverAccount,
			HttpServletRequest request
			){

		User user = getCurrentUser(request);//闂備礁鍚嬮崕鎶藉床閼艰翰浜归柛銉簵娴滃綊鏌熼幆褍鏆辨い銈呮嚇閺岋綁鍩�椤掞拷婵′粙鏌涢埡瀣

		/*//婵犵數鍋炲娆擃敄閸儲鍎婇柨鐕傛嫹
			User user = userService.findNum("gg");*/
		User receiver = userService.findNum(receiverAccount);//闂備胶鎳撻悘姘跺磿閹惰棄鏄ョ�癸拷閿熺晫鍒掗弮锟介幏鍛存憥閸屾粎鐣鹃梻浣筋潐娴滀粙宕曢弻銉ユ瀬鐟滅増甯楅崵锟介梺璺ㄥ櫐閹凤拷
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setPrivateMessageSender(user);
		privateMessage.setPrivateMessageReceiver(receiver);
		privateMessage.setPrivateText(privateText);
		return privateMessageService.save(privateMessage);

	}

	/**
	 * 2016-12-22 19:06:02
	 * 闂備礁鎼悮顐﹀磿閸欏鐝舵俊顖濆亹閻挸霉閿濆牊鑵圭紒璇叉閺岋綁濡搁妷銉患闂佸摜鍋涢顓㈡晸閻ｅ本顏熼柟鍑ゆ嫹
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
		//User user = userService.findNum("gg"); //婵犵數鍋炲娆擃敄閸儲鍎婃い鏍仜閺嬩線鏌ｅΔ锟介悧鍡欑矈閿燂拷
		User user = getCurrentUser(request);//

		return privateMessageService.findPrivateMessagesByReveiverId(receiverId,user.getId(), page);
	}

	/**
	 * 2016-12-23 18:28:39
	 * 闂佸搫琚崕鍙夌珶濡偐鐭撳ù锝堟腹缁诲棝鏌ｉ妸銉ヮ仼闁割煈浜為幃浼存晸閿燂拷
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
	//	婵炵鍋愭慨鏉戠暦閻樼鎷烽悷鏉款暢婵炲牊鍚攄闂佹寧绋戦張顒勊夐崨鏉戣摕闁靛绠戦悘澶愭煛閸曢潧鐏犻柟顖欒兌閹峰濮�閵堝棭娼愰柣鐘叉储閸庡崬鐣烽悩纰夋嫹閻у憡瀚�
	@RequestMapping("/saler/{saler_id}/issubscribe")
	public boolean checkSubscribe(@PathVariable int saler_id,HttpServletRequest request){
		User me = getCurrentUser(request);
		return subscribeService.checkSubscribe(me.getId(), saler_id);
	}
	//	婵炵鍋愭慨鐑藉极閵堝绠ｉ柛鎴欏�楃粈澶愬级閳哄倹鐓ユ繛鍙夌墵閹粙濡搁敃锟介悡鏇㈡偣娴ｉ潧鐏叉俊鎻掑閹啴宕熼銏㈩槺闁诲海娅㈤幏锟�
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
	@RequestMapping(value="/saler/{saler_id}/subscribe/s",method = RequestMethod.POST)
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
	//閻犱降鍨瑰畷锟�
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
		if(tag.equals("BUY")){//鎴戜拱鍒扮殑涔�
			User user = getCurrentUser(request);
			int userId = user.getId();
			return orderListService.getListsBuy(userId,page);
		}
		else//鎴戝崠鍑虹殑涔�
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
		if(tag.equals("BUY")){//鎴戜拱鍒扮殑涔�
			User user = getCurrentUser(request);
			int userId = user.getId();
			return orderListService.getListsBuy(userId,0);
		}
		else//鎴戝崠鍑虹殑涔�
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

	//---------娴ｆ瑩顤�
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

