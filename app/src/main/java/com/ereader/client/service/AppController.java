package com.ereader.client.service;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ereader.client.EReaderApplication;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.entities.Login;
import com.ereader.client.entities.PageRq;
import com.ereader.client.service.impl.AppServiceImpl;
import com.ereader.client.ui.bookshelf.SearchBuyActivity;
import com.ereader.client.ui.bookstore.BookActivity;
import com.ereader.client.ui.bookstore.BookDetailActivity;
import com.ereader.client.ui.bookstore.BookTitleActivity;
import com.ereader.client.ui.buycar.AddCarSuccessActivity;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.dialog.DialogUtil;
import com.ereader.client.ui.fragment.BookshelfFragment;
import com.ereader.client.ui.login.FindPwdActivity;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.login.RegisterActivity;
import com.ereader.client.ui.more.HelpActivity;
import com.ereader.client.ui.more.Notice2Activity;
import com.ereader.client.ui.more.NoticeDetailActivity;
import com.ereader.client.ui.my.CollectionActivity;
import com.ereader.client.ui.my.CouponsFragment;
import com.ereader.client.ui.my.MessageFragment;
import com.ereader.client.ui.my.MessageFriendApplyFragment;
import com.ereader.client.ui.my.MessageSystemFragment;
import com.ereader.client.ui.my.MySPActivity;
import com.ereader.client.ui.my.OrderFragment;
import com.ereader.client.ui.my.PointsFragment;
import com.ereader.client.ui.my.RecommendActivity;
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
    /**
     * 服务对象
     **/
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


    private Handler appHandler = new Handler() {

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

                        if (msg.obj.toString().startsWith("用户未登录")) {
                            if(LoginActivity.isBack){
                                currentActivity.finish();
                            }else {
                                EReaderApplication.getInstance().setLogin(false);
                                IntentUtil.intent(currentActivity, LoginActivity.class);
                                ToastUtil.showToast(currentActivity, msg.obj.toString(), ToastUtil.LENGTH_LONG);
                            }
                        } else {
                            ToastUtil.showToast(currentActivity, msg.obj.toString(), ToastUtil.LENGTH_LONG);
                        }
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
            appHandler.obtainMessage(HANDLER_TOAST, "登录成功！").sendToTarget();
            currentActivity.finish();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void featuredList(Handler mHandler, PageRq pageRq) {
        try {
            service.featuredList(pageRq);
            mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void recommend(Handler mHandler, PageRq pageRq) {
        try {
            service.recommend(pageRq);
            mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void bestComment(Handler mHandler, PageRq pageRq) {
        try {
            service.bestCommend(pageRq);
            mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void sale(Handler mHandler, PageRq pageRq) {
        try {
            service.sale(pageRq);
            mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void categroyItem(Handler mHandler, String id, PageRq pageRq) {
        try {
            service.categroyItem(id, pageRq);
            mHandler.obtainMessage(BookActivity.BOOK_CATE).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void register() {

        try {
            service.register();
            appHandler.obtainMessage(HANDLER_TOAST, "注册成功").sendToTarget();
            currentActivity.finish();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }


    public void getCode(Handler mHandler, String phone) {
        try {
            service.getCode(phone, "reg");
            mHandler.obtainMessage(RegisterActivity.CODE_OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void latest(Bundle bundle) {
        try {
            service.latest();
            IntentUtil.intent(currentActivity, bundle, BookTitleActivity.class, false);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void bookList(Handler mHandler, String cate_id, PageRq pageRq) {
        try {
            service.latest(cate_id, pageRq);
            mHandler.obtainMessage(BookActivity.BOOK).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(BookActivity.REFRESH_ERROR).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void discount(Bundle bundle) {

        try {
            service.discount();
            IntentUtil.intent(currentActivity, bundle, BookTitleActivity.class, false);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }

    public void booDiskList(Handler mHandler, DisCategory mDisCate, PageRq mPageRq) {

        try {
            service.discountBook(mDisCate, mPageRq);
            mHandler.obtainMessage(BookActivity.BOOK_DIS).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(BookActivity.REFRESH_ERROR).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }

    public void addCollection(Handler mHandler, String id) {
        try {
            service.addCollection(id);
            mHandler.obtainMessage(0).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCollection(Handler mHandler, PageRq mPageRq) {
        try {
            service.getCollection(mPageRq);
            mHandler.obtainMessage(CollectionActivity.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(CollectionActivity.REFRESH_ERROR).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void deleteCollection(Handler mHandler, int position, String id) {
        try {
            service.deleteCollection(id);
            mHandler.obtainMessage(100, position).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getCategory(Handler mHandler) {
        try {
            service.getCategory();
            mHandler.obtainMessage(0).sendToTarget();
        } catch (BusinessException e) {
        } catch (Exception e) {
        }

    }

    public void search(PageRq mPageRq, String value, Handler mHandler) {
        try {
            service.search(mPageRq, value);
            mHandler.obtainMessage(0).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(3).sendToTarget();
        }

    }

    public void getSP(Handler mHandler, PageRq pageRq) {

        try {
            service.getSP(pageRq);
            mHandler.obtainMessage(MySPActivity.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(MySPActivity.REFRESH_ERROR).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }

    public void buyCar(Handler mHandler) {
        try {
            service.buyCar();
            mHandler.obtainMessage(0).sendToTarget();
        } catch (BusinessException e) {
            if ("1000".equals(e.getErrorMessage().getCode())) {
                mHandler.obtainMessage(-1).sendToTarget();
                return;
            }
            //appHandler.obtainMessage(HANDLER_TOAST,e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }

    public void deleteBuyCar(Handler mHandler, String id) {
        try {
            service.deleteBuyCar(id);
            mHandler.obtainMessage(3).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, "删除成功").sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }

    public void addBuyCar(Handler mHandler, Book mBook) {
        try {
            service.addBuyCar(mBook.getInfo().getProduct_id());
            mHandler.obtainMessage(1).sendToTarget();
            Bundle bundle = new Bundle();
            bundle.putString("name", mBook.getInfo().getName());
            bundle.putString("coust", mBook.getPrice());
            IntentUtil.intent(currentActivity, bundle, AddCarSuccessActivity.class, false);

        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void getComment(Handler mHandler, String id) {
        try {
            service.getComment(id);
            mHandler.obtainMessage(0).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }

    public void getFriends(Handler mHandler) {

        try {
            service.getFriends();
            mHandler.obtainMessage(0).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }


    }

    public void addFriends(String id) {
        try {
            service.addFriends(id);
            ToastUtil.showToast(currentActivity, "好友申请已发送！", ToastUtil.LENGTH_LONG);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void agreeFriends(Handler mHandler, String id, int position) {
        try {
            service.agreeFriends(id);
            mHandler.obtainMessage(MessageFriendApplyFragment.AGREE, position).sendToTarget();
            ToastUtil.showToast(currentActivity, "好友添加成功", ToastUtil.LENGTH_LONG);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void disagreeFriends(Handler mHandler, String id, int position) {
        try {
            service.disagreeFriends(id);
            mHandler.obtainMessage(MessageFriendApplyFragment.REFUSE, position).sendToTarget();
            ToastUtil.showToast(currentActivity, "好友拒绝成功", ToastUtil.LENGTH_LONG);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getArticle(Handler mHandler) {
        try {
            service.helpType();
            mHandler.obtainMessage(0).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getArticleDetail(String article_id) {
        try {
            service.helpDetail(article_id);
            IntentUtil.intent(currentActivity, NoticeDetailActivity.class);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getNotice(PageRq pageRq) {
        try {
            service.notice(pageRq);
            IntentUtil.intent(currentActivity, Notice2Activity.class);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getNotice(Handler mHandler, PageRq pageRq) {
        try {
            service.notice(pageRq);
            mHandler.obtainMessage(Notice2Activity.BOOK).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(BookActivity.REFRESH_ERROR).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getNoticeDetail(String article_id) {
        try {
            service.noticeDetail(article_id);
            IntentUtil.intent(currentActivity, NoticeDetailActivity.class);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }


    public void getFriendsMessage(Handler mhandler) {
        try {
            service.getFriendsMessage();
            mhandler.obtainMessage(MessageFragment.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void deleteMessage(Handler mhandler, int type) {
        try {
            service.deleteMessage(type);
            mhandler.obtainMessage(type).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }


    public void getFriendsApply(Handler mhandler) {
        try {
            service.getFriendsApply();
            mhandler.obtainMessage(MessageFriendApplyFragment.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getMessageSystem(Handler mhandler) {
        try {
            service.getSystemMessage();
            mhandler.obtainMessage(MessageSystemFragment.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void sendCode(Handler mHandler, String phone, String type) {
        try {
            service.getCode(phone, type);
            mHandler.obtainMessage(FindPwdActivity.CODE_OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void verifyCode(Handler mHandler, String phone, String code, String type) {
        try {
            service.verifyCode(phone, code, type);
            mHandler.obtainMessage(FindPwdActivity.VERIFY_OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void findCode() {
        try {
            service.findCode();
            ToastUtil.showToast(currentActivity, "密码修改成功", ToastUtil.LENGTH_LONG);
            IntentUtil.intent(currentActivity, LoginActivity.class);
            currentActivity.finish();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getCoupons(Handler mHandler, String type) {
        try {
            service.gift(type);
            mHandler.obtainMessage(CouponsFragment.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void updatePwd() {
        try {
            service.updatePwd();
            appHandler.obtainMessage(HANDLER_TOAST, "密码修改成功").sendToTarget();
            EReaderApplication.getInstance().setLogin(false);
            IntentUtil.intent(currentActivity, LoginActivity.class);
            currentActivity.finish();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void updatePhone() {
        try {
            service.updatePhone();
            appHandler.obtainMessage(HANDLER_TOAST, "手机号修改成功").sendToTarget();
            currentActivity.finish();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }


    public void updateName(String name) {
        try {
            service.updateName(name);
            Login login = EReaderApplication.getInstance().getLogin();
            login.setNickname(name);
            EReaderApplication.getInstance().saveLogin(login);
            appHandler.obtainMessage(HANDLER_TOAST, "姓名修改成功").sendToTarget();
            currentActivity.finish();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void updateEmail(String email, String pwd) {
        try {
            service.updateEmail(email, pwd);
            Login login = EReaderApplication.getInstance().getLogin();
            login.setEmail(email);
            EReaderApplication.getInstance().saveLogin(login);
            appHandler.obtainMessage(HANDLER_TOAST, "邮箱修改成功").sendToTarget();
            currentActivity.finish();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void wallet(Handler mHandler) {
        try {
            service.wallet();
            mHandler.obtainMessage(11).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void useCard(String card, Handler mHandler, int position, String type) {
        try {
            service.useCard(card, type);
            if (mHandler != null) {
                mHandler.obtainMessage(CouponsFragment.INPUT_OK, position).sendToTarget();
            }
            appHandler.obtainMessage(HANDLER_TOAST, "充值成功").sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void sendFriends(Handler mHandler, String s) {
        try {
            service.tellToFriend(s);
            currentActivity.finish();
            appHandler.obtainMessage(HANDLER_TOAST, "推荐成功").sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void getOrderId(Handler mHandler, String orderData, String point) {
        try {
            service.createOrder(orderData, point);
            mHandler.obtainMessage(BuyCarActivity.ORDER_SUCCESS).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void shelfBuyBooks(Handler mHandler, PageRq mPageRq, String earch) {

        try {
            service.shelfBuyBooks(mPageRq, earch);
            mHandler.obtainMessage(SearchBuyActivity._OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }

    public void shelfDelBuyBooks(Handler mHandler) {

        try {
            service.shelfDelBuyBooks();
            mHandler.obtainMessage(SearchBuyActivity._OK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }
    }


    public void getRechargeOrder(Handler mHandler, String money) {
        try {
            service.getRechargeOrder(money);
            mHandler.obtainMessage(RechargeActivity.ORDER_SUCCESS).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOrderList(PageRq mPageRq, Handler mHandler, String mOrderType) {
        try {
            service.orderList(mPageRq, mOrderType);
            mHandler.obtainMessage(OrderFragment.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(OrderFragment.REFRESH_ERROR).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void pay(Handler mHandler, String orderId, String need, String point, String frinedName) {
        try {
            service.pay(orderId, need, point, frinedName);
            mHandler.obtainMessage(PayActivity.SUCCESS).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void addComment(float rating, String orderId, String id, String title, String comment) {
        try {
            service.addComment(rating, orderId, id, title, comment);
            currentActivity.setResult(0, currentActivity.getIntent());
            AppManager.getAppManager().finishActivity(currentActivity);
            IntentUtil.popFromLeft(currentActivity);
            appHandler.obtainMessage(HANDLER_TOAST, "提交成功").sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void cancelOrder(Handler mHandler, String id, int position) {
        try {
            service.cancelOrder(id);
            IntentUtil.popFromLeft(currentActivity);
            mHandler.obtainMessage(OrderFragment.CANCEL, position).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public String getDownUrl() {
        return service.getDownUrl();
    }

    public void getPointList(Handler mHandler, String balance, String type, PageRq pageRq) {
        try {
            service.getPointList(balance, type, pageRq);
            mHandler.obtainMessage(OrderFragment.REFRESH_DOWN_OK).sendToTarget();
        } catch (BusinessException e) {
            mHandler.obtainMessage(PointsFragment.REFRESH_ERROR).sendToTarget();
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void myRecommend(Handler mHandler, PageRq pageRq) {
        try {
            service.myRecommend(pageRq);
            mHandler.obtainMessage(RecommendActivity.SUCCESS).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void getRechCard(Handler mHandler, String card) {
        try {
            service.getRechCard(card);
            mHandler.obtainMessage(RechargeActivity.SUCCESS).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void shelfRecommend(Handler mHandler, PageRq pageRq) {
        try {
            service.shelfRecommend(pageRq);
            mHandler.obtainMessage(BookshelfFragment.RECOMMEND_BOOK).sendToTarget();
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
            appHandler.obtainMessage(HANDLER_TOAST, "网络异常！").sendToTarget();
        }
    }

    public void getCollectionState(Handler mHandler, String product_id) {
        try {
            service.getCollectionState(product_id);
            mHandler.obtainMessage(BookDetailActivity.COLLECTION_OK).sendToTarget();
            mHandler.obtainMessage(BookshelfFragment.RECOMMEND_BOOK).sendToTarget();
        } catch (BusinessException e) {
        } catch (Exception e) {
        }
    }

    public void getBookDetail(String product_id) {
        try {
            service.getBookDetail(product_id);
            Book mBook = (Book) getContext().getBusinessData("BookDetailResp");
            if ("0".equals(mBook.getOnsale_status())) {
                appHandler.obtainMessage(HANDLER_TOAST, "该书已下架").sendToTarget();
            } else {
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailBook", mBook);
                IntentUtil.intent(currentActivity, bundle, BookDetailActivity.class, false);
            }

        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        } catch (Exception e) {
        }

    }

    public void tryRead(Handler mHandler, Book book) {
        try {
            String bookId = book.getExtra().getBook_id();
            String try_read_id = "";
            if (book.getExtra().getTry_read_pages() != null && book.getExtra().getTry_read_pages().size() != 0) {
                try_read_id = book.getExtra().getTry_read_pages().get(3);
            } else {
                appHandler.obtainMessage(HANDLER_TOAST, "没有试读章节").sendToTarget();
                return;
            }
            service.tryRead(try_read_id, bookId);
            mHandler.obtainMessage(BookDetailActivity.TRY_READ_OK).sendToTarget();
            //IntentUtil.intent(currentActivity,BookDetailActivity.class);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }

    public void searhByBook(Handler mHandler, String searh) {
        try {
            service.searhByBook(searh);
        } catch (BusinessException e) {
            appHandler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
        }
    }
}
