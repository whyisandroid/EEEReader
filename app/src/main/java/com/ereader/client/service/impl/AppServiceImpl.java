package com.ereader.client.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ereader.client.EReaderApplication;
import com.ereader.client.entities.DisCategory;
import com.ereader.client.entities.json.ArticleDetailResp;
import com.ereader.client.entities.json.ArticleResp;
import com.ereader.client.entities.json.BaseResp;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.entities.json.BookResp;
import com.ereader.client.entities.json.CategoryResp;
import com.ereader.client.entities.json.CommentResp;
import com.ereader.client.entities.json.DisCategoryResp;
import com.ereader.client.entities.json.LoginResp;
import com.ereader.client.entities.json.SPResp;
import com.ereader.client.entities.json.SubCategoryResp;
import com.ereader.client.service.AppContext;
import com.ereader.client.service.AppService;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.exception.ErrorMessage;
import com.ereader.common.net.Request;
import com.ereader.common.util.Config;

public class AppServiceImpl implements AppService {
	private AppContext context;


	public AppServiceImpl(AppContext context) {
		this.context = context;
	}

	@Override
	public void login() throws Exception {
		String account = (String)context.getBusinessData("user.account");
		String password = (String)context.getBusinessData("user.password");
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
			
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	
	@Override
	public void register() throws Exception {
		String phone = context.getStringData("regisrerPhone");
		String pwd = context.getStringData("regisrerPwd");
		String email = context.getStringData("regisrerEmail");
		String code = context.getStringData("code");
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("phone", phone));
		nameValuePairs.add(new BasicNameValuePair("password", pwd));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("nickname", phone ==null?email:phone));
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
	public void getCode() throws Exception {
		String phone = context.getStringData("regisrerPhone");
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("phone", phone));
		nameValuePairs.add(new BasicNameValuePair("type", "reg"));
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
	public void featuredList() throws Exception {
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "10"));
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
	public void latest() throws Exception {
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
	public void latest(String cate_id) throws Exception {

		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("category_id", cate_id));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "10"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_LATEST);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookFeaturedResp", resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	@Override
	public void discount() throws Exception {

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
	public void discountBook(DisCategory mDisCate) throws Exception {
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("min", mDisCate.getMin()));
		nameValuePairs.add(new BasicNameValuePair("max", mDisCate.getMax()));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "10"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_DISCOUNT);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("BookFeaturedResp", resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	
	@Override
	public void getCollection() throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BookOnlyResp> request = new Request<BookOnlyResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_COLLECTION);
		request.setR_calzz(BookOnlyResp.class);
		BookOnlyResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("CollectionResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	
	@Override
	public void deleteCollection(String id) throws Exception {
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
		
			
	public void getCategory() throws Exception {
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
	public void search(String value) throws Exception {
		Request<BookResp> request = new Request<BookResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("keyword", value));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BOOK_SEARCH);
		request.setR_calzz(BookResp.class);
		BookResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			context.addBusinessData("SearchBookResp", resp.getData());
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	
	
	@Override
	public void addCollection(String id) throws Exception {
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
	public void getSP() throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<SPResp> request = new Request<SPResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
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
	public void buyCar() throws Exception {
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
	public void addBuyCar(String id) throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("product_id", id));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_BUY_CAR_ADD);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	
	@Override
	public void deleteBuyCar(String id) throws Exception {
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
	public void getComment(String id) throws Exception {
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
			context.addBusinessData("CommentResp", resp);
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	
	@Override
	public void getFriends() throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<BaseResp> request = new Request<BaseResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_MY_FRIENDS);
		request.setR_calzz(BaseResp.class);
		BaseResp resp = EReaderApplication.getAppSocket().shortConnect(request);
		if (BaseResp.SUCCESS.equals(resp.getStatus())) {
			
		} else {
			throw new BusinessException(new ErrorMessage(resp.getStatus(), resp.getMessage()));
		}
	}
	
	@Override
	public void addFriends(String id) throws Exception {

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
	public void loginExit() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void user() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void wallet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bill() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void order() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gift() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giftUse() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commentCount() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createOrder() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void payType() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pay() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void helpType(String type) throws Exception {
		String token = EReaderApplication.getInstance().getLogin().getToken();
		Request<ArticleResp> request = new Request<ArticleResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("_token_", token));
		nameValuePairs.add(new BasicNameValuePair("parent_id", type));
		nameValuePairs.add(new BasicNameValuePair("page", "1"));
		nameValuePairs.add(new BasicNameValuePair("per_page", "30"));
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
	
	@Override
	public void helpDetail(String id) throws Exception {

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
}
