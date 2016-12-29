package com.cloudage.membercenter.service;

import com.cloudage.membercenter.entity.User;

public interface IUserService {
	User create(String account, String passwordHash);
	
	User save(User user);              //閿熸枻鎷烽敓鏂ゆ嫹user閿熸枻鎷穝ave()閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂娇閿熸枻鎷锋敞閿熸枻鎷烽敓缁炴唻鎷疯櫘锛旈敓鏂ゆ嫹閿熸枻鎷烽敓锟�
	User findNum(String num);           //閿熸枻鎷烽敓鏂ゆ嫹user閿熸枻鎷穎indNum()閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂娇閿熺煫纰夋嫹褰曢敓鏂ゆ嫹鏃堕敓鏂ゆ嫹閿熸彮纰夋嫹涓�閿熸枻鎷锋敞閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
	User findEmail(String email);       //閿熸枻鎷烽敓鏂ゆ嫹user閿熸枻鎷穎indEmail()閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂娇閿熺煫纰夋嫹褰曢敓鏂ゆ嫹鏃堕敓鏂ゆ嫹閿熸彮纰夋嫹涓�閿熸枻鎷锋敞閿熸枻鎷烽敓绲渕ail
	
	User creat(User user);
	
	User findOne(int userId);//闁俺绻僫d鐎电粯澹榰ser
	void login(String account, String passwordHash);
	User getCurrentUser();
	boolean changePassword(String newPasswordHash);
	void logout();
	
	User findSum(float sumMoney);

	User findPhone(String phone);

	


}