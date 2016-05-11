package com.ereader.client.ui.bookstore;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ereader.client.EReaderApplication;
import com.ereader.client.R;
import com.ereader.client.entities.AddBuy;
import com.ereader.client.entities.Book;
import com.ereader.client.entities.Favourite;
import com.ereader.client.entities.PayCar;
import com.ereader.client.entities.PayCarList;
import com.ereader.client.entities.json.BookOnlyResp;
import com.ereader.client.entities.json.FavouriteResp;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseFragmentActivity;
import com.ereader.client.ui.adapter.BookDetailFragsAdapter;
import com.ereader.client.ui.adapter.ForTabsAdapter;
import com.ereader.client.ui.buycar.BuyCarActivity;
import com.ereader.client.ui.login.LoginActivity;
import com.ereader.client.ui.my.FriendsActivity;
import com.ereader.client.ui.pay.PayActivity;
import com.ereader.client.ui.share.ShareActivity;
import com.ereader.client.ui.share.ShareParams;
import com.ereader.client.ui.view.TabsView;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.util.IntentUtil;
import com.ereader.common.util.Json_U;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.RegExpUtil;
import com.ereader.common.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends BaseFragmentActivity implements OnClickListener {
    private TabsView st_book_detail;
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
    private ImageView main_top_image;
    private int buyNum = 0;

    private TextView tv_book_detail_sale_0;
    private LinearLayout ll_book_detail_sale_status;

    private Book mBook;

    public static final int COLLECTION_OK = 10; // 获取收藏状态成功

    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    tv_book_collection.setText("已收藏");
                    ToastUtil.showToast(BookDetailActivity.this, "收藏成功", ToastUtil.LENGTH_LONG);
                    break;
                case 1:
                    //TODO  改变购物车的数量
                    BookOnlyResp resp = EReaderApplication.getInstance().getBuyCar();
                    if (resp == null) {
                        resp = new BookOnlyResp();
                    }
                    boolean flag = true;
                    for (int i = 0; i < resp.getData().size(); i++) {
                        if (mBook.getProduct_id().equals(resp.getData().get(i).getProduct_id())) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        resp.getData().add(mBook);
                    }

                    EReaderApplication.getInstance().saveBuyCar(resp);

                    buyNum = Integer.valueOf(((AddBuy) controller.getContext().getBusinessData("AddBuyResp")).getTotal_product_count());
                    main_top_right.setText("购物车(" + buyNum + ")");
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
                case 11:
                    IntentUtil.intent(BookDetailActivity.this, PayActivity.class);
                    break;
                case COLLECTION_OK:
                    Favourite favourite = (Favourite) controller.getContext().getBusinessData("FavouriteResp");
                    setCollectionState(favourite.is_favourite);
                    break;
                default:
                    break;
            }
        }

        ;
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
     * 方法描述：FindView
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void findView() {
        main_top_right = (Button) findViewById(R.id.main_top_right);
        book_detail_bt_buy = (Button) findViewById(R.id.book_detail_bt_buy);
        bt_book_add_buy = (Button) findViewById(R.id.bt_book_add_buy);
        bt_book_add_friends = (Button) findViewById(R.id.bt_book_add_friends);
        st_book_detail = (TabsView) findViewById(R.id.st_book_detail);
        vp_book_store = (ViewPager) findViewById(R.id.vp_book_store);
        tv_book_collection = (TextView) findViewById(R.id.tv_book_collection);
        tv_book_name = (TextView) findViewById(R.id.tv_book_name);
        tv_book_author = (TextView) findViewById(R.id.tv_book_author);
        tv_book_publish = (TextView) findViewById(R.id.tv_book_publish);
        tv_book_price = (TextView) findViewById(R.id.tv_book_price);
        rb_book_star = (RatingBar) findViewById(R.id.rb_book_star);
        iv_book = (ImageView) findViewById(R.id.iv_book);
        main_top_image = (ImageView) findViewById(R.id.main_top_image);
        ll_book_detail_sale_status = (LinearLayout) findViewById(R.id.ll_book_detail_sale_status);
        tv_book_detail_sale_0 = (TextView) findViewById(R.id.tv_book_detail_sale_0);
    }


    /**
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        mBook = (Book) getIntent().getExtras().getSerializable("detailBook");
        bt_book_add_friends.setTag(mBook.getInfo().getProduct_id());
        tv_book_collection.setTag(mBook.getInfo().getProduct_id());
        book_detail_bt_buy.setTag(mBook.getInfo().getProduct_id());
        book_detail_bt_buy.setOnClickListener(this);
        BookOnlyResp resp = (BookOnlyResp) EReaderApplication.getInstance().getBuyCar();
        main_top_right.setText("购物车");

        setCollectionState(mBook.getIs_favourite());

        ((TextView) findViewById(R.id.tv_main_top_title)).setText("书城");
        if (resp != null) {
            buyNum = resp.getData().size();
            main_top_right.setText("购物车(" + buyNum + ")");
        } else {
            main_top_right.setText("购物车");
        }
        main_top_right.setTextColor(getResources().getColor(R.color.white));
        main_top_right.setOnClickListener(this);
        main_top_image.setVisibility(View.VISIBLE);
        main_top_image.setOnClickListener(this);
        tv_book_collection.setOnClickListener(this);
        bt_book_add_buy.setOnClickListener(this);
        bt_book_add_friends.setOnClickListener(this);
        mListTitle = new ArrayList<String>();
        mListTitle.add("目录");
        mListTitle.add("内容简介");
        mListTitle.add("作者简介");
        mListTitle.add("书评");

        BookDetailFragsAdapter pageAdapter = new BookDetailFragsAdapter(getSupportFragmentManager(), mListTitle.size(), mBook);
        vp_book_store.setAdapter(pageAdapter);

        // 设置缓存fragment的数量
        vp_book_store.setOffscreenPageLimit(2);
        vp_book_store.setCurrentItem(0);
        vp_book_store.setPageMargin(4);


        ForTabsAdapter adapter = new ForTabsAdapter(this, mListTitle);
        st_book_detail.setAdapter(adapter);
        st_book_detail.setViewPager(vp_book_store);
        setBook(mBook);
        // 下架状态处理
        setOnsaleStatus(mBook.getOnsale_status());
    }

    //setonsale_status	char	是否在售 1是 0否
    private void setOnsaleStatus(String onsale_status) {
        if ("0".equals(onsale_status)) { // 下架状态
            tv_book_detail_sale_0.setVisibility(View.VISIBLE);
            ll_book_detail_sale_status.setVisibility(View.GONE);
        } else { // 正常状态
            tv_book_detail_sale_0.setVisibility(View.GONE);
            ll_book_detail_sale_status.setVisibility(View.VISIBLE);
        }

    }


    private void setCollectionState(String state) {
        if ("1".equals(state)) {
            tv_book_collection.setText("已收藏");
        } else {
            tv_book_collection.setText("收藏");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBuyCarNum();
        if (EReaderApplication.getInstance().isLogin()) {
            //  获取收藏状态
            getCollectionState();
        }

    }

    //  获取收藏状态
    private void getCollectionState() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.getCollectionState(mHandler, mBook.getProduct_id());
                ProgressDialogUtil.closeProgressDialog();
            }
        }).start();
    }


    private void setBuyCarNum() {
        BookOnlyResp resp = EReaderApplication.getInstance().getBuyCar();
        if (resp != null) {
            buyNum = resp.getData().size();
            main_top_right.setText("购物车(" + buyNum + ")");
        } else {
            main_top_right.setText("购物车");
        }
    }

    private void setBook(Book book) {

        EReaderApplication.imageLoader.displayImage(book.getInfo().getImage_url(), iv_book, EReaderApplication.options);
        tv_book_name.setText(book.getInfo().getName());
        tv_book_author.setText("作者：" + book.getExtra().getAuthor());
        tv_book_publish.setText("出版社：" + book.getExtra().getPress());
        float rating = 0;
        if (RegExpUtil.isNumeric(book.getComment_star())) {
            rating = Float.valueOf(book.getComment_star());
        }
        rb_book_star.setRating(rating);
        tv_book_price.setText("¥ " + book.getPrice());
    }

    @Override
    public void onClick(View v) {

		switch (v.getId()) {
			case R.id.main_top_image:
				ShareParams shareParams=new ShareParams();
				shareParams.setTitle(mBook.getInfo().getName());
				shareParams.setContent(mBook.getInfo().getDescription());
				shareParams.setShareUrl("http://www.rreadeg.com/index.php?s=/Home/Book/share/id/"+mBook.getPrice()+".html");
				shareParams.setImageUrl(mBook.getImage_url());
				ShareActivity.share(BookDetailActivity.this,shareParams);
				break;
            case R.id.book_detail_bt_buy:
                if (!EReaderApplication.getInstance().isLogin()) {
                    IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
                    return;
                }
                // 生成订单
                try {
                    final String orderData = getOrderMessage(book_detail_bt_buy.getTag().toString());
                    ProgressDialogUtil.showProgressDialog(this, "", false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.getOrderId(mHandler, orderData, "0");
                            ProgressDialogUtil.closeProgressDialog();
                        }
                    }).start();
                } catch (BusinessException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.bt_book_add_buy:
                if (!EReaderApplication.getInstance().isLogin()) {
                    IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
                    return;
                }
                ProgressDialogUtil.showProgressDialog(this, "", false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        controller.addBuyCar(mHandler, mBook);
                        ProgressDialogUtil.closeProgressDialog();
                    }
                }).start();
                break;
            case R.id.main_top_right:
                IntentUtil.intent(BookDetailActivity.this, BuyCarActivity.class);
                break;
            case R.id.tv_book_collection:
                if (!EReaderApplication.getInstance().isLogin()) {
                    IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
                    return;
                }
                if ("收藏".equals(tv_book_collection.getText().toString())) {
                    ProgressDialogUtil.showProgressDialog(this, "", false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.addCollection(mHandler, tv_book_collection.getTag().toString());
                            ProgressDialogUtil.closeProgressDialog();
                        }
                    }).start();
                } else {
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
                if (!EReaderApplication.getInstance().isLogin()) {
                    IntentUtil.intent(BookDetailActivity.this, LoginActivity.class);
                    return;
                }
                //  推荐给好友
                controller.getContext().addBusinessData("bookSendId", bt_book_add_friends.getTag().toString());
                FriendsActivity.mFriendsSend = 1;
                IntentUtil.intent(BookDetailActivity.this, FriendsActivity.class);
                break;
            default:
                break;
        }
    }

    private String getOrderMessage(String bookId) throws BusinessException {
        PayCarList pList = new PayCarList();
        pList.getmPayCarList().add(new PayCar(bookId));
        String jsonData = Json_U.objToJsonStr(pList);
        return jsonData.substring(15, jsonData.length() - 1);
    }
}
