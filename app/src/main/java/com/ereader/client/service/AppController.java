package com.ereader.client.service;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ereader.client.EReaderApplication;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.entities.Login;
import com.ereader.client.service.impl.AppServiceImpl;
import com.ereader.client.ui.bookshelf.SearchBuyActivity;
import com.ereader.client.ui.bookstore.BookActivity;
import com.ereader.client.ui.bookstore.BookTitleActivity;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.dialog.DialogUtil;
import com.ereader.client.ui.login.FindPwdActivity;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.login.RegisterActivity;
import com.ereader.client.ui.more.NoticeActivity;
import com.ereader.client.ui.more.NoticeDetailActivity;
import com.ereader.client.ui.my.MessageFriendsFragment;
import com.ereader.client.ui.my.OrderFragment;
import com.ereader.client.ui.pay.PayActivity;
import com.ereader.client.ui.pay.RechargeActivity;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.ToastUtil;

/***
 * 控制层 该类保存客户端控制器
 * 
 * @author why
 * @time 2011-9-22
 */
public class AppController {
	/**
	 * 客户端上下文，该对象用来缓存客户端业务数据和参数配置
	 */
	private AppContext context;
	/** 服务对象 **/
	private AppService service;
	
	
	private static final int HANDLER_DIALOG = 0; // 弹对话框;确认后关闭
	private static final int HANDLER_TOAST = 1; // 吐司 专用
	private static final int HANDLER_UPDATE = 2; // 更新
	private static final int HANDLER_UPDATE_ABOUT = 3; // 更新 错误信息由提示 about
	
	
	

	public AppContext getContext() {
		return context;
	}

	public void setContext(AppContext context) {
		this.context = context;
	}

	/***
	 * 控制器单例对象
	 */
	private static AppController controller = null;

	/***
	 * 当前android的活动对象
	 */
	private Activity currentActivity;

	private AppController(Activity act) {
		this.currentActivity = act;
		createContext();
		service = new AppServiceImpl(context);
	}

	/**
	 * 初始化客户端配置信息
	 */
	private void createContext() {
		context = new AppContext();
	}

	/**
	 * 得到单例的controller对象
	 * 
	 * @return
	 */
	public synchronized static AppController getController() {
		if (controller == null) {
			controller = new AppController(null);
		}
		return controller;
	}

	/**
	 * 得到单例controller对象，并设置当前controller当前关联的Activity活动对象
	 */
	public synchronized static AppController getController(Activity act) {
		if (controller == null) {
			controller = new AppController(act);
		} else {
			controller.setCurrentActivity(act);
		}
		return controller;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}
	
	
	private Handler appHandler = new Handler(){
		
		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case HANDLER_DIALOG:
				if (null != msg.obj && !TextUtils.isEmpty(msg.obj.toString())) {
					DialogUtil.showError(currentActivity, msg.obj.toString());
				} else {
					LogUtil.LogError("error", "服务器-未知错误！");
				}
				break;
			case HANDLER_TOAST:
				if (null != msg.obj && !TextUtils.isEmpty(msg.obj.toString())) {
					ToastUtil.showToast(currentActivity, msg.obj.toString(),
							ToastUtil.LENGTH_LONG);
				} else {
					ToastUtil.showToast(currentActivity, "服务器未知错误！",
							ToastUtil.LENGTH_LONG);
					LogUtil.LogError("error", "服务器-未知错误！");
				}
				break;
			case HANDLER_UPDATE:
			
				break;
			case HANDLER_UPDATE_ABOUT:
				
				break;
			default:
				break;
			}
		}
	};

	

	public void login() {
		try {
			service.login();
			appHandler.obtainMessage(HANDLER_TOAST,"登录成功！").sendToTarget();
			currentActivity.finish();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void featuredList(Handler mHandler) {
		try {
			service.featuredList();
			mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void recommend(Handler mHandler) {
		try {
			service.recommend();
			mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void categroyItem(Handler mHandler, String id) {
		try {
			service.categroyItem(id);
			mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void register() {

		try {
			service.register();
			appHandler.obtainMessage(HANDLER_TOAST,"注册成功").sendToTarget();
			currentActivity.finish();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	
	}
	
	

	public void getCode(Handler mHandler,String phone) {
		try {
			service.getCode(phone, "reg");
			mHandler.obtainMessage(RegisterActivity.CODE_OK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void latest(Bundle bundle) {
		try {
			service.latest();
			IntentUtil.intent(currentActivity, bundle, BookTitleActivity.class, false);
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void bookList(Handler mHandler,String cate_id) {
		try {
			service.latest(cate_id);
			mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
		} catch (BusinessException e) {
			mHandler.obtainMessage(BookActivity.REFRESH_ERROR).sendToTarget();
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void discount(Bundle bundle) {

		try {
			service.discount();
			IntentUtil.intent(currentActivity, bundle, BookTitleActivity.class, false);
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	
	}

	public void booDiskList(Handler mHandler, DisCategory mDisCate) {

		try {
			service.discountBook(mDisCate);
			mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
		} catch (BusinessException e) {
			mHandler.obtainMessage(BookActivity.REFRESH_ERROR).sendToTarget();
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	
	}
	
	public void addCollection(Handler mHandler,String id) {
		try {
			service.addCollection(id);
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getCollection(Handler mHandler) {
		try {
			service.getCollection();
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCollection(Handler mHandler,int position,String id) {
		try {
			service.deleteCollection(id);
			mHandler.obtainMessage(100,position).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}
		
	public void getCategory(Handler mHandler) {
		try {
			service.getCategory();
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
		}catch (Exception e) {
		}
	
	}

	public void search(String value,Handler mHandler) {
		try {
			service.search(value);
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
		}catch (Exception e) {
		}
	
	}

	public void getSP(Handler mHandler) {

		try {
			service.getSP();
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
			
	}

	public void buyCar(Handler mHandler) {
		try {
			service.buyCar();
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
			if("1000".equals(e.getErrorMessage().getCode())){
				mHandler.obtainMessage(-1).sendToTarget();
				return;
			}
			//appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	
	}
	public void deleteBuyCar(Handler mHandler,String id) {
		try {
			service.deleteBuyCar(id);
			mHandler.obtainMessage(3).sendToTarget();
			appHandler.obtainMessage(HANDLER_TOAST,"删除成功").sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	
	}
	public void addBuyCar(Handler mHandler,String id) {
		try {
			service.addBuyCar(id);
			appHandler.obtainMessage(HANDLER_TOAST,"已加入购物车").sendToTarget();
			mHandler.obtainMessage(1).sendToTarget();
		} catch (BusinessException e) {
		}catch (Exception e) {
		}
	
	}

	public void getComment(Handler mHandler,String id) {
		try {
			service.getComment(id);
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
			
	}

	public void getFriends(Handler mHandler) {

		try {
			service.getFriends();
			mHandler.obtainMessage(0).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
			
			
	}

	public void addFriends(Handler mHandler,String id) {
		try {
			service.addFriends(id);
			mHandler.obtainMessage(1).sendToTarget();
			ToastUtil.showToast(currentActivity, "好友申请已发送！", ToastUtil.LENGTH_LONG);
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void getArticle(String id) {
		try {
			service.helpType(id);
			Bundle bundle = new Bundle();
			bundle.putString("id", id);
			IntentUtil.intent(currentActivity, bundle, NoticeActivity.class, false);
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void getArticleDetail(String article_id) {
		try {
			service.helpDetail(article_id);
			IntentUtil.intent(currentActivity, NoticeDetailActivity.class);
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void getMessage(Handler mhandler,String type) {
		try {
			service.getMessage(type);
			mhandler.obtainMessage(MessageFriendsFragment.REFRESH_DOWN_OK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void sendCode(Handler mHandler,String phone,String type) {
			try {
				service.getCode(phone, type);
				mHandler.obtainMessage(FindPwdActivity.CODE_OK).sendToTarget();
			} catch (BusinessException e) {
				appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
			}catch (Exception e) {
			}
	}

	public void findCode() {
		try {
			service.findCode();
			ToastUtil.showToast(currentActivity, "密码修改成功", ToastUtil.LENGTH_LONG);
			IntentUtil.intent(currentActivity, LoginActivity.class);
			currentActivity.finish();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void getCoupons(Handler mHandler,String type) {
		try {
			service.gift(type);
			mHandler.obtainMessage(FindPwdActivity.CODE_OK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void updatePwd() {
			try {
				service.updatePwd();
				appHandler.obtainMessage(HANDLER_TOAST,"密码修改成功").sendToTarget();
				currentActivity.finish();
			} catch (BusinessException e) {
				appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
			}catch (Exception e) {
			}
	}

	public void updatePhone() {
		try {
			service.updatePhone();
			appHandler.obtainMessage(HANDLER_TOAST,"手机号修改成功").sendToTarget();
			currentActivity.finish();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}


	public void updateName(String name) {
		try {
			service.updateName(name);
			Login login = EReaderApplication.getInstance().getLogin();
			login.setNickname(name);
			EReaderApplication.getInstance().saveLogin(login);
			appHandler.obtainMessage(HANDLER_TOAST,"姓名修改成功").sendToTarget();
			currentActivity.finish();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}
	public void updateEmail(String email,String pwd) {
		try {
			service.updateEmail(email, pwd);
			Login login = EReaderApplication.getInstance().getLogin();
			login.setEmail(email);
			EReaderApplication.getInstance().saveLogin(login);
			appHandler.obtainMessage(HANDLER_TOAST,"邮箱修改成功").sendToTarget();
			currentActivity.finish();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void wallet(Handler mHandler) {
		try {
			service.wallet();
			mHandler.obtainMessage(11).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void useCard(String card) {
		try {
			service.useCard(card);
			appHandler.obtainMessage(HANDLER_TOAST,"充值成功").sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void sendFriends(Handler mHandler, String s) {
		try {
			service.tellToFriend(s);
			currentActivity.finish();
			appHandler.obtainMessage(HANDLER_TOAST,"推荐成功").sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void getOrderId(Handler mHandler, String orderData) {
		try {
			service.createOrder(orderData);
			mHandler.obtainMessage(BuyCarActivity.ORDER_SUCCESS).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void shelfBuyBooks(Handler mHandler){

		try {
			service.shelfBuyBooks();
			mHandler.obtainMessage(SearchBuyActivity._OK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}

	public void shelfDelBuyBooks(Handler mHandler){

		try {
			service.shelfDelBuyBooks();
			mHandler.obtainMessage(SearchBuyActivity._OK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}catch (Exception e) {
		}
	}


	public void getRechargeOrder(Handler mHandler,String money) {
		try {
			service.getRechargeOrder(money);
			mHandler.obtainMessage(RechargeActivity.ORDER_SUCCESS).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getOrderList(Handler mHandler, String mOrderType) {
		try {
			service.orderList(mOrderType);
			mHandler.obtainMessage(OrderFragment.REFRESH_DOWN_OK).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}
	}

	public void pay(Handler mHandler,String orderId,String money,String point,String frinedName) {
		try {
			service.pay(orderId, money, point, frinedName);
			mHandler.obtainMessage(PayActivity.SUCCESS).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}
	}

	public void addComment(float rating, String id, String title, String comment) {
		try {
			service.addComment(rating, id, title, comment);
			currentActivity.finish();
			IntentUtil.popFromLeft(currentActivity);
			appHandler.obtainMessage(HANDLER_TOAST,"提交成功").sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}
	}

	public void cancelOrder(Handler mHandler,String id,int position) {
		try {
			service.cancelOrder(id);
			IntentUtil.popFromLeft(currentActivity);
			mHandler.obtainMessage(OrderFragment.CANCEL,position).sendToTarget();
		} catch (BusinessException e) {
			appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
		}
	}

	public String getDownUrl(){
		return service.getDownUrl();
	}
}
