package com.ereader.client.service;


import com.ereader.client.entities.DisCategory;
import com.ereader.client.entities.PageRq;
import com.ereader.common.exception.BusinessException;

public interface AppService {

	/**
	 * 登陆
	 * @throws BusinessException
	 */
	public void login() throws BusinessException;

	/**经典热销**/
	public void featuredList(PageRq pageRq)throws BusinessException;
	/**推荐阅读**/
	public void recommend(PageRq pageRq)throws BusinessException ;
	/**注册**/
	public void register()throws BusinessException ;
	/**最新上架类别**/
	public void latest()throws BusinessException ;
	/**=获取书**/
	public void latest(String cate_id,PageRq pageRq)throws BusinessException;
	/**特价专区类别**/
	public void discount()throws BusinessException;
	/**特价专区类书**/
	public void discountBook(DisCategory mDisCate,PageRq mPageRq)throws BusinessException;
	/**获取收藏夹**/
	public void getCollection()throws BusinessException;
	/**删除收藏夹**/
	public void deleteCollection(String id)throws BusinessException;
	/**2.2.1.	商品分类**/
	public void getCategory()throws BusinessException;
	/**2.2.1.	商品分类 books**/
	public void categroyItem(String id,PageRq pageRq)throws BusinessException;
	/**2.2.1搜索列表**/
	public void search(String value)throws BusinessException;
	/**获取验证码**/
	public void getCode(String phone, String type)throws BusinessException;
	/**验证验证码**/
	public void verifyCode(String phone, String code, String type)throws BusinessException;
	/**添加到收藏夹**/
	public void addCollection(String id)throws BusinessException;
	/**添我的书评**/
	public void getSP()throws BusinessException;
	/**我的购物车**/
	public void buyCar()throws BusinessException;
	/**添加购物车**/
	public void addBuyCar(String id)throws BusinessException;
	/**删除购物车**/
	public void deleteBuyCar(String id)throws BusinessException;
	/**商品书评**/
	public void getComment(String id)throws BusinessException;
	/**我的好友**/
	public void getFriends()throws BusinessException;
	/**我的推荐**/
	public void myRecommend()throws BusinessException;
	/**添加好友**/
	public void addFriends(String id)throws BusinessException;
	/**同意好友**/
	public void agreeFriends(String id)throws BusinessException;
	/**同意好友**/
	public void disagreeFriends(String id)throws BusinessException;

	
	// TODO
	/***用户登出*/
	public void loginExit() throws BusinessException;
	/***用户基本信息*/
	public void user() throws BusinessException;
	/*** 我的钱包*/
	public void wallet() throws BusinessException;
	/***我的账单列表*/
	public void bill() throws BusinessException;
	/***我的积分列表*/
	public void getPointList(String balance, String type) throws BusinessException;
	/***我的订单列表*/
	public void orderList(String type) throws BusinessException;
	/***取消订单*/
	public void cancelOrder(String id) throws BusinessException;
	/***我的充值劵*/
	public void gift(String type) throws BusinessException;
	/***使用充值券*/
	public void useCard(String card,String type) throws BusinessException;

	/***获取充值券信息*/
	public void getRechCard(String card) throws BusinessException;
	/***商品评论数据*/
	public void addComment(float rating, String orderId, String id, String title, String comment) throws BusinessException;

	/***创建支付订单*/
	public void createOrder(String value) throws BusinessException;
	/***创建充值订单*/
	public void getRechargeOrder(String money) throws BusinessException;

	/***2.4.2.	设置订单支付方式*/
	public void payType() throws BusinessException;
	/***使用电子币支付*/
	public void pay(String orderId, String money, String point, String frinedName) throws BusinessException;
	
	//other
	/**帮助中心***/
	public void helpType()throws BusinessException;
	/**帮助中心详情***/
	public void helpDetail(String type)throws BusinessException;
	/**公告列表***/
	public void notice(PageRq pageRq)throws BusinessException;
	/**公告详情***/
	public void noticeDetail(String notice_id)throws BusinessException;
	/**我的消息***/
	public void getMessage(String type)throws Exception;
	/**好友申请消息***/
	public void getFriendsApply()throws Exception;
	/**好友推荐消息**/
	public void getFriendsMessage()throws Exception;
	/**删除消息**/
	public void deleteMessage(int type)throws BusinessException;
	/**系统消息***/
	public void getSystemMessage()throws Exception;
	/**向好友推荐商品***/
	public void tellToFriend(String friendID)throws BusinessException;

	/**找回密码***/
	public void findCode()throws BusinessException;


	/**已经购买的图书***/
	public void shelfBuyBooks()throws BusinessException;

	/**删除已经购买的图书***/
	public void shelfDelBuyBooks()throws BusinessException;
	/**修改我的密码***/
	public void updatePwd()throws BusinessException;
	/**修改我的手机号***/
	public void updatePhone()throws BusinessException;
	/**修改姓名***/
	public void updateName(String name)throws BusinessException;
	/**修改邮箱***/
	public void updateEmail(String emali, String pwd)throws BusinessException;

	public String getDownUrl();

}
