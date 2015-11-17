package com.ereader.client.ui.bookstore;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.AddBuy;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.PayCar;
import com.ereader.client.entities.PayCarList;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseFragmentActivity;
import com.ereader.client.ui.adapter.BookDetailFragsAdapter;
import com.ereader.client.ui.adapter.BookDetailTabsAdapter;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.my.CollectionActivity;
import com.ereader.client.ui.my.FriendsActivity;
import com.ereader.client.ui.pay.PayActivity;
import com.ereader.client.ui.view.ScrollingTabsView;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.Json_U;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.RegExpUtil;
import com.ereader.common.util.ToastUtil;

public class BookDetailActivity extends BaseFragmentActivity implements OnClickListener {
	private ScrollingTabsView st_book_detail;
	private ViewPager vp_book_store;
	private AppController controller;
	private Button main_top_right;
	private Button bt_book_add_buy;
	private Button bt_book_add_friends;
	private Button book_detail_bt_buy;
	private List<String> mListTitle;
	private TextView tv_book_collection;
	private TextView tv_book_name;
	private TextView tv_book_author;
	private TextView tv_book_publish;
	private RatingBar rb_book_star;
	private TextView tv_book_price;
	private ImageView iv_book;
	private int buyNum = 0;

	private Book mBook;
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				tv_book_collection.setText("已收藏");
				ToastUtil.showToast(BookDetailActivity.this, "收藏成功", ToastUtil.LENGTH_LONG);
				break;
			case 1:
				//TODO  改变购物车的数量
				BookOnlyResp resp = EReaderApplication.getInstance().getBuyCar();
                if(resp == null){
                    resp = new BookOnlyResp();
                }
				resp.getData().add(mBook);
				EReaderApplication.getInstance().saveBuyCar(resp);

				buyNum = Integer.valueOf(((AddBuy) controller.getContext().getBusinessData("AddBuyResp")).getTotal_product_count());
				main_top_right.setText("购物车(" + buyNum+")");
				break;
				case 100:
					tv_book_collection.setText("收藏");
					break;
                case BuyCarActivity.ORDER_SUCCESS:
                    //
                    ProgressDialogUtil.showProgressDialog(BookDetailActivity.this, "", false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.wallet(mHandler);
                            ProgressDialogUtil.closeProgressDialog();
                        }
                    }).start();
                    break;
                case  11:
                    IntentUtil.intent(BookDetailActivity.this, PayActivity.class);
                    break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail_layout);
		controller = AppController.getController(this);
		findView();
		initView();
	}
	/**
	 * 
	  * 方法描述：FindView
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void findView() {
		main_top_right = (Button)findViewById(R.id.main_top_right);
        book_detail_bt_buy = (Button)findViewById(R.id.book_detail_bt_buy);
		bt_book_add_buy = (Button)findViewById(R.id.bt_book_add_buy);
		bt_book_add_friends = (Button)findViewById(R.id.bt_book_add_friends);
		st_book_detail = (ScrollingTabsView)findViewById(R.id.st_book_detail);
		vp_book_store = (ViewPager)findViewById(R.id.vp_book_store);
		tv_book_collection = (TextView)findViewById(R.id.tv_book_collection);
		tv_book_name = (TextView)findViewById(R.id.tv_book_name);
		tv_book_author = (TextView)findViewById(R.id.tv_book_author);
		tv_book_publish = (TextView)findViewById(R.id.tv_book_publish);
		tv_book_price = (TextView)findViewById(R.id.tv_book_price);
		rb_book_star = (RatingBar)findViewById(R.id.rb_book_star);
		iv_book = (ImageView)findViewById(R.id.iv_book);
	}
	

	/**
	 * 
	  * 方法描述：初始化 View
	  * @author: why
	  * @time: 2015-2-10 下午1:37:06
	 */
	private void initView() {
		mBook = (Book)getIntent().getExtras().getSerializable("detailBook");
		bt_book_add_friends.setTag(mBook.getInfo().getProduct_id());
		tv_book_collection.setTag(mBook.getInfo().getProduct_id());
        book_detail_bt_buy.setTag(mBook.getInfo().getProduct_id());
        book_detail_bt_buy.setOnClickListener(this);
		((TextView) findViewById(R.id.tv_main_top_title)).setText("书城");
		BookOnlyResp resp  = (BookOnlyResp)EReaderApplication.getInstance().getBuyCar();
		main_top_right.setText("购物车");
		
		if(resp != null){
			buyNum = resp.getData().size();
			main_top_right.setText("购物车("+buyNum+")");
		}
		Drawable drawable= getResources().getDrawable(R.drawable.b5_03);
		/// 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		main_top_right.setCompoundDrawables(drawable,null,null,null);
		main_top_right.setTextColor(getResources().getColor(R.color.white));
		main_top_right.setOnClickListener(this);
		tv_book_collection.setOnClickListener(this);
		bt_book_add_buy.setOnClickListener(this);
		bt_book_add_friends.setOnClickListener(this);
		mListTitle = new ArrayList<String>();
		mListTitle.add("目录");
		mListTitle.add("内容简介");
		mListTitle.add("作者简介");
		mListTitle.add("书评");
		
		BookDetailFragsAdapter pageAdapter = new BookDetailFragsAdapter(getSupportFragmentManager(),mListTitle.size(),mBook);
		vp_book_store.setAdapter(pageAdapter);
		
		// 设置缓存fragment的数量
		vp_book_store.setOffscreenPageLimit(2);
		vp_book_store.setCurrentItem(0);
		vp_book_store.setPageMargin(4);
		
		
		BookDetailTabsAdapter adapter = new BookDetailTabsAdapter(this,mListTitle);
		st_book_detail.setAdapter(adapter);
		st_book_detail.setViewPager(vp_book_store);
		setBook(mBook);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setBuyCarNum();
	}


	private void setBuyCarNum(){
		BookOnlyResp resp  =  EReaderApplication.getInstance().getBuyCar();
		if(resp != null){
			buyNum = resp.getData().size();
			main_top_right.setText("购物车("+buyNum+")");
		}
	}

	private void setBook(Book book) {

		EReaderApplication.imageLoader.displayImage(book.getInfo().getImage_url(), iv_book, EReaderApplication.options);
		tv_book_name.setText(book.getInfo().getName());
		tv_book_author.setText("作者："+book.getExtra().getAuthor());
		tv_book_publish.setText("出版社：" + book.getExtra().getPress());
		float rating = 0;
		if(RegExpUtil.isNumeric(book.getComment_star())){
			 rating  = Float.valueOf(book.getComment_star());
		}
		rb_book_star.setRating(rating);
		tv_book_price.setText("¥ "+book.getPrice());
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
            case R.id.book_detail_bt_buy:
                if(!EReaderApplication.getInstance().isLogin()){
                    IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
                    return;
                }
                // 生成订单
                try {
                    final String  orderData = getOrderMessage(book_detail_bt_buy.getTag().toString());
                    ProgressDialogUtil.showProgressDialog(this, "", false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.getOrderId(mHandler, orderData);
                            ProgressDialogUtil.closeProgressDialog();
                        }
                    }).start();
                } catch (BusinessException e) {
                    e.printStackTrace();
                }

                break;
		case R.id.bt_book_add_buy:
			if(!EReaderApplication.getInstance().isLogin()){
				IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
				return;
			}
				ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.addBuyCar(mHandler, tv_book_collection.getTag().toString());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			break;
		case  R.id.main_top_right:
			IntentUtil.intent(BookDetailActivity.this, BuyCarActivity.class);
			break;
		case R.id.tv_book_collection:
			if(!EReaderApplication.getInstance().isLogin()){
				IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
				return;
			}
			if("收藏".equals(tv_book_collection.getText().toString())){
				ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						controller.addCollection(mHandler, tv_book_collection.getTag().toString());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			}else{
				ProgressDialogUtil.showProgressDialog(this, "", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						AppController.getController().deleteCollection(mHandler, -1, tv_book_collection.getTag().toString());
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			}
			break;
			case R.id.bt_book_add_friends:
				if(!EReaderApplication.getInstance().isLogin()){
					IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
					return;
				}
				//  推荐给好友
				controller.getContext().addBusinessData("bookSendId",bt_book_add_friends.getTag().toString());
				FriendsActivity.mFriendsSend = true;
				IntentUtil.intent(BookDetailActivity.this,FriendsActivity.class);
				break;
		default:
			break;
		}
	}

    private String getOrderMessage(String bookId) throws BusinessException{
        PayCarList pList = new PayCarList();
        pList.getmPayCarList().add(new PayCar(bookId));
        String jsonData = Json_U.objToJsonStr(pList);
        return jsonData.substring(15,jsonData.length()-1);
    }
}
