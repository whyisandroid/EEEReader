package com.ereader.client.service.impl;


import com.ereader.client.EReaderApplication;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.entities.Login;
import com.ereader.client.entities.PageRq;
import com.ereader.client.entities.json.AddBuyResp;
import com.ereader.client.entities.json.ArticleDetailResp;
import com.ereader.client.entities.json.ArticleResp;
import com.ereader.client.entities.json.BaseResp;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.entities.json.BookResp;
import com.ereader.client.entities.json.BookSearchResp;
import com.ereader.client.entities.json.BookShowResp;
import com.ereader.client.entities.json.CardInfoResp;
import com.ereader.client.entities.json.CategoryResp;
import com.ereader.client.entities.json.CommentResp;
import com.ereader.client.entities.json.DisCategoryResp;
import com.ereader.client.entities.json.FriendsResp;
import com.ereader.client.entities.json.GiftResp;
import com.ereader.client.entities.json.LoginResp;
import com.ereader.client.entities.json.MessageResp;
import com.ereader.client.entities.json.MessageSystemResp;
import com.ereader.client.entities.json.NoticeDetailResp;
import com.ereader.client.entities.json.NoticeResp;
import com.ereader.client.entities.json.OrderListResp;
import com.ereader.client.entities.json.OrderRechargeResp;
import com.ereader.client.entities.json.OrderResp;
import com.ereader.client.entities.json.PointResp;
import com.ereader.client.entities.json.RecommendResp;
import com.ereader.client.entities.json.SPResp;
import com.ereader.client.entities.json.SubCategoryResp;
import com.ereader.client.entities.json.WalletResp;
import com.ereader.client.service.AppContext;
import com.ereader.client.service.AppService;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.exception.ErrorMessage;
import com.ereader.common.net.Request;
import com.ereader.common.net.XUtilsSocketImpl;
import com.ereader.common.util.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.util.LogUtil;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class AppServiceImpl implements AppService {
	private AppContext context;


	public AppServiceImpl(AppContext context) {
		this.context = context;
	}

	@Override
	public void login() throws BusinessException {
		String account = (String) context.getBusinessData("user.account");
		String password = (String) context.getBusinessData("user.password");
		Request<LoginResp> request = new Request<LoginResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("loginname", account));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_LOGIN);
		request.setR_calzz(LoginResp.class);
		LoginResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			EReaderApplication.getInstance().setLogin(true);
			resp.getData().setToken(resp.get_token_());
			EReaderApplication.getInstance().saveLogin(resp.getData());
			EReaderApplication.getInstance().saveLocalInfoByKeyValue("LoginAccount",account);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void register() throws BusinessException {
		String phone = context.getStringData("regisrerPhone");
		String pwd = context.getStringData("regisrerPwd");
		String email = context.getStringData("regisrerEmail");
		String code = context.getStringData("code");
		String regisrerName = context.getStringData("regisrerName");
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("phone", phone));
		nameValuePairs.add(new BasicNameValuePair("password", pwd));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("nickname", regisrerName));
		nameValuePairs.add(new BasicNameValuePair("vcode", code));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_REGISTER);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}


	@Override
	public void getCode(String phone, String type) throws BusinessException {
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("phone", phone));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_CODE);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void verifyCode(String phone, String code, String type) throws BusinessException {
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("phone", phone));
		nameValuePairs.add(new BasicNameValuePair("vcode", code));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_CODE_VERIFY);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void findCode() throws BusinessException {
		String pwd = context.getStringData("pwdCode");
		String phone = context.getStringData("phone");
		String vcode = context.getStringData("vcode");
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("password", pwd));
		nameValuePairs.add(new BasicNameValuePair("phone", phone));
		nameValuePairs.add(new BasicNameValuePair("vcode", vcode));
		nameValuePairs.add(new BasicNameValuePair("email", ""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_FIND_PWD);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void featuredList(PageRq pageRq) throws BusinessException {
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("page", pageRq.getPage()+""));
		nameValuePairs.add(new BasicNameValuePair("per_page", pageRq.getPer_page()+""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_FEATURED);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookFeaturedResp", resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public void recommend(PageRq pageRq) throws BusinessException {
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("page", pageRq.getPage()+""));
		nameValuePairs.add(new BasicNameValuePair("per_page", pageRq.getPer_page()+""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_RECOMMEND);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookFeaturedResp", resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public void latest() throws BusinessException {
		Request<CategoryResp> request = new Request<CategoryResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_LATEST_CATE);
		request.setR_calzz(CategoryResp.class);
		CategoryResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("CategoryResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void latest(String cate_id,PageRq pageRq) throws BusinessException {

		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("category_id", cate_id));
		nameValuePairs.add(new BasicNameValuePair("page", pageRq.getPage()+""));
		nameValuePairs.add(new BasicNameValuePair("per_page", pageRq.getPer_page()+""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_LATEST);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookFeaturedResp"+cate_id, resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void discount() throws BusinessException {
		Request<DisCategoryResp> request = new Request<DisCategoryResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_DISCOUNT_CATE);
		request.setR_calzz(DisCategoryResp.class);
		DisCategoryResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("DisCategoryResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void discountBook(DisCategory mDisCate,PageRq mPageRq) throws BusinessException {
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("min", mDisCate.getMin()));
		nameValuePairs.add(new BasicNameValuePair("max", mDisCate.getMax()));
		nameValuePairs.add(new BasicNameValuePair("page", mPageRq.getPage()+""));
		nameValuePairs.add(new BasicNameValuePair("per_page", mPageRq.getPer_page()+""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_DISCOUNT);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookFeaturedResp"+mDisCate.getName(), resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getCollection() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_COLLECTION);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("CollectionResp", resp.getData().getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void deleteCollection(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("product_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_DELETE_COLLECTION);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {

		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}


	public void getCategory() throws BusinessException {
		Request<SubCategoryResp> request = new Request<SubCategoryResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_CATEGORY);
		request.setR_calzz(SubCategoryResp.class);
		SubCategoryResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			EReaderApplication.getInstance().saveCategroy(resp);
			context.addBusinessData("SubCategoryResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void categroyItem(String id,PageRq pageRq) throws BusinessException {
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("category_id", id));
		nameValuePairs.add(new BasicNameValuePair("page", pageRq.getPage()+""));
		nameValuePairs.add(new BasicNameValuePair("per_page", pageRq.getPer_page()+""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_SEARCH);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookFeaturedResp"+id, resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void search(String value) throws BusinessException {
		Request<BookSearchResp> request = new Request<BookSearchResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("keyword", value));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_SEARCH);
		request.setR_calzz(BookSearchResp.class);
		BookSearchResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("SearchBookResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}


	@Override
	public void addCollection(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("product_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_ADD_COLLECTION);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {

		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getSP() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<SPResp> request = new Request<SPResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "30"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_SP);
		request.setR_calzz(SPResp.class);
		SPResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("SPResp", resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void buyCar() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BookOnlyResp> request = new Request<BookOnlyResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BUY_CAR);
		request.setR_calzz(BookOnlyResp.class);
		BookOnlyResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			for (int i = 0; i < resp.getData().size(); i++) {
				resp.getData().get(i).setSelect(true);
			}
			EReaderApplication.getInstance().saveBuyCar(resp);
			context.addBusinessData("BuyCarResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void addBuyCar(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<AddBuyResp> request = new Request<AddBuyResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("product_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BUY_CAR_ADD);
		request.setR_calzz(AddBuyResp.class);
		AddBuyResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("AddBuyResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void deleteBuyCar(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("product_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BUY_CAR_DELETE);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getComment(String id) throws BusinessException {
		Request<CommentResp> request = new Request<CommentResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("product_id", id));
		nameValuePairs.add(new BasicNameValuePair("rank", "ALL"));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "10"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_COMMENT);
		request.setR_calzz(CommentResp.class);
		CommentResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("CommentResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getFriends() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<FriendsResp> request = new Request<FriendsResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_FRIENDS);
		request.setR_calzz(FriendsResp.class);
		FriendsResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("FriendsResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void myRecommend() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<RecommendResp> request = new Request<RecommendResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "50"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_RECOMMEND);
		request.setR_calzz(RecommendResp.class);
		RecommendResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("RecommendResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	 public void addFriends(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("friend_search", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_FRIENDS_ADD);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void agreeFriends(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("friend_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_FRIENDS_ADD);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public void disagreeFriends(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("friend_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_FRIENDS_DIS);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void loginExit() throws BusinessException {

	}

	@Override
	public void user() throws BusinessException {

	}

	@Override
	public void wallet() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<WalletResp> request = new Request<WalletResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_USER_WALLET);
		request.setR_calzz(WalletResp.class);
		WalletResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("WalletResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void bill() throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPointList(String balance,String type) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<PointResp> request = new Request<PointResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("balance", balance));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "50"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_USER_POINT_LIST);
		request.setR_calzz(PointResp.class);
		PointResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("PointResp"+balance+type, resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void orderList(String type) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<OrderListResp> request = new Request<OrderListResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("pay_status", type));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "50"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_USER_ORDER_LIST);
		request.setR_calzz(OrderListResp.class);
		OrderListResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("OrderListResp"+type, resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void cancelOrder(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("order_id", id));
		nameValuePairs.add(new BasicNameValuePair("per_page", "30"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_USER_ORDER_CANENL);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void gift(String type) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<GiftResp> request = new Request<GiftResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("status", type));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "30"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_USER_GIFT);
		request.setR_calzz(GiftResp.class);
		GiftResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("GiftResp"+type, resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void useCard(String card,String type) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("code", card));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_GIFT_USE);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {

		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public void getRechCard(String card) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<CardInfoResp> request = new Request<CardInfoResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("code", card));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_GIFT_USE);
		request.setR_calzz(CardInfoResp.class);
		CardInfoResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("CardInfoResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void addComment(float rating, String order_id,String id, String title, String comment) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("order_id", order_id));
		nameValuePairs.add(new BasicNameValuePair("score", rating+""));
		nameValuePairs.add(new BasicNameValuePair("product_id", id));
		nameValuePairs.add(new BasicNameValuePair("title", title));
		nameValuePairs.add(new BasicNameValuePair("content", comment));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_ADD_COMMENT);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {

		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void createOrder(String orderMessage) throws BusinessException {
			String token = EReaderApplication.getInstance().getLogin().getToken();
			Request<OrderResp> request = new Request<OrderResp>();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("_token_", token));
			nameValuePairs.add(new BasicNameValuePair("jsondata", orderMessage));
			request.addParameter(Request.AJAXPARAMS, nameValuePairs);
			request.setUrl(Config.HTTP_PAY_OREDER);
			request.setR_calzz(OrderResp.class);
			OrderResp resp = EReaderApplication.getAppSocket().shortConnect(request);
			if (BaseResp.SUCCESS.equals(resp.getStatus())) {
				context.addBusinessData("OrderResp",resp.getData());
			} else {
				throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
			}
	}
	@Override
	public void getRechargeOrder(String money) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<OrderRechargeResp> request = new Request<OrderRechargeResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("money", money));
		nameValuePairs.add(new BasicNameValuePair("pay_type", "alipay"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_PAY_OREDER_RECHARGE);
		request.setR_calzz(OrderRechargeResp.class);
		OrderRechargeResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("OrderRechargeResp",resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public void payType() throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void pay(String orderId,String money,String point,String frinedName) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("out_trade_no", orderId));
		nameValuePairs.add(new BasicNameValuePair("total_fee", money));
		nameValuePairs.add(new BasicNameValuePair("total_point", point));
		nameValuePairs.add(new BasicNameValuePair("to_user_id", frinedName));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_PAY);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		//	context.addBusinessData("ArticleResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void helpType() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<ArticleResp> request = new Request<ArticleResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MORE_HELP);
		request.setR_calzz(ArticleResp.class);
		ArticleResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("ArticleResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	public void helpDetail(String id) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<ArticleDetailResp> request = new Request<ArticleDetailResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("article_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MORE_HELP_DETAIL);
		request.setR_calzz(ArticleDetailResp.class);
		ArticleDetailResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("ArticleDetailResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void notice(PageRq pageRq) throws BusinessException {
		Request<NoticeResp> request = new Request<NoticeResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("page", pageRq.getPage()+""));
		nameValuePairs.add(new BasicNameValuePair("per_page", pageRq.getPer_page()+""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MORE_HELP_NOTICE);
		request.setR_calzz(NoticeResp.class);
		NoticeResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("NoticeResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}


	@Override
	public void noticeDetail(String notice_id) throws BusinessException {
		Request<ArticleDetailResp> request = new Request<ArticleDetailResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("notice_id", notice_id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MORE_HELP_NOTICEDETAIL);
		request.setR_calzz(ArticleDetailResp.class);
		ArticleDetailResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("ArticleDetailResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getMessage(String type) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<MessageResp> request = new Request<MessageResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "30"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_MESSAGE);
		request.setR_calzz(MessageResp.class);
		MessageResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("MessageResp"+type, resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getFriendsApply() throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<MessageResp> request = new Request<MessageResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "10"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_MESSAGE_APPLY);
		request.setR_calzz(MessageResp.class);
		MessageResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("MessageApplyResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getFriendsMessage() throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<MessageResp> request = new Request<MessageResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "10"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_MESSAGE_FRIENDS);
		request.setR_calzz(MessageResp.class);
		MessageResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("MessageFriendsResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public void deleteMessage(int type) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("type", type+""));
		nameValuePairs.add(new BasicNameValuePair("message_id", ""));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_MESSAGE_DETELETE);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void getSystemMessage() throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<MessageSystemResp> request = new Request<MessageSystemResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "10"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_MESSAGE_SYSTEM);
		request.setR_calzz(MessageSystemResp.class);
		MessageSystemResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("MessageSystemResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void tellToFriend(String friendId) throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		String bookSendId = context.getStringData("bookSendId");
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("friend_id", friendId));
		nameValuePairs.add(new BasicNameValuePair("product_id", bookSendId));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_TO_FRIEND);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void updatePwd() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		String mPwd = context.getStringData("mPwd");
		String mNewPwd = context.getStringData("mNewPwd");
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("old_password", mPwd));
		nameValuePairs.add(new BasicNameValuePair("new_password", mNewPwd));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_PWD);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void updatePhone() throws BusinessException {
			String token = EReaderApplication.getInstance().getLogin().getToken();
			String mNewPhone = context.getStringData("mNewPhone");
			String mPhoneCode = context.getStringData("mPhoneCode");
			Request<BaseResp> request = new Request<BaseResp>();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("_token_", token));
			nameValuePairs.add(new BasicNameValuePair("phone", mNewPhone));
			nameValuePairs.add(new BasicNameValuePair("vcode", mPhoneCode));
			request.addParameter(Request.AJAXPARAMS, nameValuePairs);
			request.setUrl(Config.HTTP_MY_PHONE);
			request.setR_calzz(BaseResp.class);
			BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
			if (BaseResp.SUCCESS.equals(resp.getStatus())) {
				Login login = EReaderApplication.getInstance().getLogin();
				login.setPhone(mNewPhone);
				EReaderApplication.getInstance().saveLogin(login);
			} else {
				throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
			}
	}

	@Override
		 public void updateName(String name) throws BusinessException {
		{
			String token = EReaderApplication.getInstance().getLogin().getToken();
			Request<BaseResp> request = new Request<BaseResp>();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("_token_", token));
			nameValuePairs.add(new BasicNameValuePair("nickname", name));
			request.addParameter(Request.AJAXPARAMS, nameValuePairs);
			request.setUrl(Config.HTTP_MY_NAME);
			request.setR_calzz(BaseResp.class);
			BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
			if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			} else {
				throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
			}
		}
	}
	@Override
	public void updateEmail(String email,String pwd) throws BusinessException {
		{
			String token = EReaderApplication.getInstance().getLogin().getToken();
			Request<BaseResp> request = new Request<BaseResp>();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("_token_", token));
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("password", pwd));
			request.addParameter(Request.AJAXPARAMS, nameValuePairs);
			request.setUrl(Config.HTTP_MY_EMAIL);
			request.setR_calzz(BaseResp.class);
			BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
			if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			} else {
				throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
			}
		}
	}
	/*
	* 已经购买的图书
	* */
	@Override
	public void shelfBuyBooks() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BookShowResp> request = new Request<BookShowResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
//		nameValuePairs.add(new BasicNameValuePair("per_page", "20"));
//		nameValuePairs.add(new BasicNameValuePair("page", "20"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_SHELF_BOOKS);
		request.setR_calzz(BookShowResp.class);
		BookShowResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookShowResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}

	@Override
	public void shelfDelBuyBooks() throws BusinessException {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		String book_id = (String) context.getBusinessData("delete.book_id");
		Request<BookShowResp> request = new Request<BookShowResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("book_id", book_id));
//		nameValuePairs.add(new BasicNameValuePair("page", "20"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_SHELF_BOOKS_DELETE);
		request.setR_calzz(BookShowResp.class);
		BookShowResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookShowResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public String getDownUrl(){

		String result="";
		String token = EReaderApplication.getInstance().getLogin().getToken();
		String book_id = (String) context.getBusinessData("download.book_id");
		Request request = new Request();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("book_id", book_id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_SHELF_BOOKS_DOWNLOAD);
		try {
			result= XUtilsSocketImpl.getDownURL(request);
		} catch (Exception e) {
			LogUtil.LogError("getDownUrlerror",e.toString());
			result= "";
		}
		return result;
	}
}

