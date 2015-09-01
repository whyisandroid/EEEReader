package com.ereader.client.service;

import android.os.Handler;

import com.ereader.client.entities.DisCategory;

public interface AppService {

	/**
	 * 登陆
	 * @throws Exception
	 */
	public void login() throws Exception ;

	/**经典热销**/
	public void featuredList()throws Exception ;
	/**注册**/
	public void register()throws Exception ;
	/**最新上架类别**/
	public void latest()throws Exception ;
	/**=获取书**/
	public void latest(String cate_id)throws Exception;
	/**特价专区类别**/
	public void discount()throws Exception;
	/**特价专区类书**/
	public void discountBook(DisCategory mDisCate)throws Exception;
	/**获取收藏夹**/
	public void getCollection()throws Exception;
	/**删除收藏夹**/
	public void deleteCollection(String id)throws Exception;
	/**2.2.1.	商品分类**/
	public void getCategory()throws Exception;
	/**2.2.1搜索列表**/
	public void search(String value)throws Exception;
	/**获取验证码**/
	public void getCode()throws Exception;
	/**添加到收藏夹**/
	public void addCollection(String id)throws Exception;
	/**添我的书评**/
	public void getSP()throws Exception;
	/**我的购物车**/
	public void buyCar()throws Exception;
	/**添加购物车**/
	public void addBuyCar(String id)throws Exception;
	/**删除购物车**/
	public void deleteBuyCar(String id)throws Exception;
	/**商品书评**/
	public void getComment(String id)throws Exception;
	/**我的好友**/
	public void getFriends()throws Exception;
	/**添加好友**/
	public void addFriends(String id)throws Exception;
	
	
	// TODO
	/***用户登出*/
	public void loginExit() throws Exception;
	/***用户基本信息*/
	public void user() throws Exception;
	/*** 我的钱包*/
	public void wallet() throws Exception;
	/***我的账单列表*/
	public void bill() throws Exception;
	/***我的订单列表*/
	public void order() throws Exception;
	/***我的充值劵*/
	public void gift() throws Exception;
	/***使用充值券*/
	public void giftUse() throws Exception;
	/***商品评论数据*/
	public void commentCount() throws Exception;
	
	/***创建订单*/
	public void createOrder() throws Exception;
	/***2.4.2.	设置订单支付方式*/
	public void payType() throws Exception;
	/***使用电子币支付*/
	public void pay() throws Exception;
	
	//other
	/**帮助中心***/
	public void helpType(String type)throws Exception;
	/**帮助中心***/
	public void helpDetail(String id)throws Exception;
	
}
