package com.ereader.common.util;

import java.io.File;

import android.os.Environment;

/**
 * **************************************** 类描述： 配置信息类 类名称：Config
 * 
 * @version: 1.0
 * @author: why
 * @time: 2014-2-14 下午3:32:29
 ***************************************** 
 */
public class Config {

	/** 是否调试. */
	public final static boolean DEBUG = true;


	// 正式环境
	// public final static String MY_SERVICE = "http://10.106.4.56:5050";
	// 测试环境
	public final static String MY_SERVICE = "http://bookshop.0ett.com";

	/** 临时文件保存路径. */
	// 下载包保存路径
	public static final String PATH_SDCARD = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "CreditWealth";
	// 更新APK
	public static final String PATH_UPDATE_APK = PATH_SDCARD + File.separator
			+ "Update";
	

	public final static String HTTP_LOGIN = MY_SERVICE+ "/api/index/login"; // 登录接口
	public final static String HTTP_REGISTER = MY_SERVICE+ "/api/index/reg"; //注册接口
	public final static String HTTP_QUIT = MY_SERVICE+ "/api/index/logout"; //退出接口
	public final static String HTTP_CODE = MY_SERVICE+ "/api/index/sendCodeToPhone"; //注册接口
	public final static String HTTP_BOOK_FEATURED= MY_SERVICE+ "/api/product/featured"; // 经典热销
	public final static String HTTP_BOOK_LATEST_CATE= MY_SERVICE+ "/Api/product/latest_category"; // 最新上架类别
	public final static String HTTP_BOOK_LATEST= MY_SERVICE+ "/Api/product/latest"; // 最新上架
	public final static String HTTP_BOOK_DISCOUNT_CATE= MY_SERVICE+ "/Api/product/discount_category"; // 特价专区类别
	public final static String HTTP_BOOK_DISCOUNT= MY_SERVICE+ "/Api/product/discount"; // 特价专区
	public final static String HTTP_BOOK_ADD_COLLECTION= MY_SERVICE+ "/api/product/addToFavourite"; //添加到收藏夹
	public final static String HTTP_BOOK_COLLECTION= MY_SERVICE+ "/api/product/favourite"; // 获取收藏夹
	public final static String HTTP_BOOK_DELETE_COLLECTION= MY_SERVICE+ "/api/product/delFromFavourite"; // 删除收藏夹
	public final static String HTTP_BOOK_CATEGORY= MY_SERVICE+ "/Api/product/category"; //  商品分类
	public final static String HTTP_BOOK_SEARCH = MY_SERVICE+ "/api/product/search"; //  搜索列表
	public final static String HTTP_BUY_CAR = MY_SERVICE+ "/api/product/cart"; //  我的购物车
	public final static String HTTP_BUY_CAR_DELETE = MY_SERVICE+ "/api/product/delFromCart"; //  删除购物车
	public final static String HTTP_BUY_CAR_ADD = MY_SERVICE+ "/api/product/addToCart"; //  添加购物车
	public final static String HTTP_BOOK_COMMENT = MY_SERVICE+ "/api/product/comment"; // 商品书评

	
	public final static String HTTP_MY_SP = MY_SERVICE+ "/api/user/comment"; //  我的评论
	public final static String HTTP_MY_FRIENDS = MY_SERVICE+ "/api/user/friend"; //  我的好友
	public final static String HTTP_MY_FRIENDS_ADD = MY_SERVICE+ "/api/user/addFriend"; //  添加好友
	
	//TODO  my
	public final static String HTTP_LOGIN_EXIT = MY_SERVICE+ "/api/index/logout"; //  退出
	public final static String HTTP_USER = MY_SERVICE+ "/api/user/user"; //  用户基本信息
	public final static String HTTP_USER_WALLET = MY_SERVICE+ "/api/user/wallet"; //  我的钱包
	public final static String HTTP_USER_BILL = MY_SERVICE+ "/api/user/bill"; //  我的账单列表
	public final static String HTTP_USER_ORDER = MY_SERVICE+ "/api/user/order"; //  我的订单列表
	public final static String HTTP_USER_GIFT= MY_SERVICE+ "/api/user/giftcard"; //  我的充值劵
	public final static String HTTP_LOGIN_GIFT_USE = MY_SERVICE+ "/api/user/inpourFromGiftcard"; //  2.1.10.	使用充值券
	
	
	//TODO  book
	public final static String HTTP_BOOK_COMMENT_COUNT = MY_SERVICE+ "/api/product/commentCount"; //  商品评论数据
	public final static String HTTP_BOOK_ADD_COMMENT = MY_SERVICE+ "/api/product/addToComment"; // 添加商品的评分评论
	
	//TODO pay
	public final static String HTTP_PAY_OREDER = MY_SERVICE+ "/api/order/create"; //  创建订单
	public final static String HTTP_PAY_TYPE = MY_SERVICE+ "/api/order/pay_type"; //  设置订单支付方式
	public final static String HTTP_PAY = MY_SERVICE+ "/api/order/pay"; //  使用电子币支付
	
	
	// TODO　reader
	public final static String HTTP_MORE_HELP = MY_SERVICE+ "/api/article/lists"; //  帮助中心
	public final static String HTTP_MORE_HELP_DETAIL = MY_SERVICE+ "/api/article/detail"; //  帮助中心内容详情
	
	
}
