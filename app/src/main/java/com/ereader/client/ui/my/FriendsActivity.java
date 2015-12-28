package com.ereader.client.ui.my;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ereader.client.R;
import com.ereader.client.entities.Friend;
import com.ereader.client.entities.Friends;
import com.ereader.client.service.AppController;
import com.ereader.client.ui.BaseActivity;
import com.ereader.client.ui.adapter.FriendsAdapter;
import com.ereader.client.ui.dialog.BasicDialog;
import com.ereader.common.util.ProgressDialogUtil;
import com.ereader.common.util.ToastUtil;

// 我的好友
public class FriendsActivity extends BaseActivity implements OnClickListener {
    private AppController controller;
    private ListView lv_my_friends;
    private Button main_top_right;
    private List<Friend> mList = new ArrayList<Friend>();
    private EditText friends_et_send;
    private FrameLayout friends_fl_send;
    private FriendsAdapter mAdapter;
    public static int mFriendsSend = 0; //   0  添加好友   1 推荐分享  2 送给好友
    private String mFriendId = "";
    public static final int SEND_CLICK = 1;
    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    mList = (List<Friend>) controller.getContext().getBusinessData("FriendsResp");
                    if (mList != null) {
                        mAdapter = new FriendsAdapter(FriendsActivity.this, mList, mHandler);
                        lv_my_friends.setAdapter(mAdapter);
                    }
                    break;
                case SEND_CLICK:
                    Friend friend = (Friend)msg.obj;
                    friends_et_send.setText(friend.getNickname());
                    friends_et_send.setTag(friend.getFriend_id());
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
        setContentView(R.layout.my_friends_layout);
        controller = AppController.getController(this);
        findView();
        initView();
        getFriends();
    }

    private void getFriends() {
        ProgressDialogUtil.showProgressDialog(this, "", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.getFriends(mHandler);
                ProgressDialogUtil.closeProgressDialog();
            }
        }).start();

    }

    /**
     * 方法描述：FindView
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void findView() {
        lv_my_friends = (ListView) findViewById(R.id.lv_my_friends);
        main_top_right = (Button) findViewById(R.id.main_top_right);
        friends_et_send = (EditText) findViewById(R.id.friends_et_send);
        friends_fl_send = (FrameLayout) findViewById(R.id.friends_fl_send);
    }


    /**
     * 方法描述：初始化 View
     *
     * @author: why
     * @time: 2015-2-10 下午1:37:06
     */
    private void initView() {
        if (mFriendsSend == 0) {
            ((TextView) findViewById(R.id.tv_main_top_title)).setText("我的好友");
            friends_fl_send.setVisibility(View.GONE);
            main_top_right.setText("添加好友");
        } else if(mFriendsSend == 1) {
            ((TextView) findViewById(R.id.tv_main_top_title)).setText("推荐给好友");
            main_top_right.setText("发送推荐");
            main_top_right.setTextColor(Color.WHITE);
            friends_fl_send.setVisibility(View.VISIBLE);
        } else if(mFriendsSend == 2) {
            ((TextView) findViewById(R.id.tv_main_top_title)).setText("送给好友");
            main_top_right.setText("完成");
            main_top_right.setTextColor(Color.WHITE);
            friends_fl_send.setVisibility(View.VISIBLE);
        }
        main_top_right.setOnClickListener(this);
        mAdapter = new FriendsAdapter(this, mList, mHandler);
        lv_my_friends.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_top_right:
                String friendName1 = friends_et_send.getText().toString();

                if (TextUtils.isEmpty(friendName1)) {
                    ToastUtil.showToast(FriendsActivity.this, "推荐好友不能为空", ToastUtil.LENGTH_LONG);
                    return;
                }
                boolean flag1 = false;
                for (int i = 0; i < mList.size(); i++) {
                    Friend friend2 = mList.get(i);
                    if (friend2.getNickname().equals(friendName1)) {
                        flag1 = true;
                        mFriendId = friend2.getFriend_id();
                    }
                }
                if (!flag1) {
                    ToastUtil.showToast(FriendsActivity.this, "推荐人不在好友列表内", ToastUtil.LENGTH_LONG);
                    return;
                }

                if(mFriendsSend == 2){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("friendName", friendName1);
                    mIntent.putExtra("friendId", friends_et_send.getTag().toString());
                    // 设置结果，并进行传送
                    this.setResult(1, mIntent);
                    this.finish();
                }else if (mFriendsSend == 1) {
                    String friendName = friends_et_send.getText().toString();

                    if (TextUtils.isEmpty(friendName)) {
                        ToastUtil.showToast(FriendsActivity.this, "推荐好友不能为空", ToastUtil.LENGTH_LONG);
                        return;
                    }
                    boolean flag = false;
                    for (int i = 0; i < mList.size(); i++) {
                        Friend friend2 = mList.get(i);
                        if (friend2.getNickname().equals(friendName)) {
                            flag = true;
                            mFriendId = friend2.getFriend_id();
                        }
                    }
                    if (!flag) {
                        ToastUtil.showToast(FriendsActivity.this, "推荐人不在好友列表内", ToastUtil.LENGTH_LONG);
                        return;
                    }


                    ProgressDialogUtil.showProgressDialog(FriendsActivity.this, "", false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            controller.sendFriends(mHandler, mFriendId);
                            ProgressDialogUtil.closeProgressDialog();
                        }
                    }).start();
                } else if(mFriendsSend == 0) {
                    final BasicDialog dailog = new BasicDialog(FriendsActivity.this, R.layout.dialog_add_friend, R.style.add_dialog);
                    final EditText et_add_friends = (EditText) dailog.findViewById(R.id.et_add_friends);
                    Button bt_dialog_no_title_right = (Button) dailog.findViewById(R.id.bt_dialog_no_title_right);
                    Button bt_dialog_no_title_left = (Button) dailog.findViewById(R.id.bt_dialog_no_title_left);

                    bt_dialog_no_title_left.setText("取消");
                    bt_dialog_no_title_left.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dailog.dismiss();
                        }
                    });
                    bt_dialog_no_title_right.setText("添加");
                    bt_dialog_no_title_right.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(et_add_friends.getText().toString())) {
                                ToastUtil.showToast(FriendsActivity.this, "好友不能为空", ToastUtil.LENGTH_LONG);
                            } else {
                                ProgressDialogUtil.showProgressDialog(FriendsActivity.this, "", false);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dailog.dismiss();
                                        controller.addFriends(et_add_friends.getText().toString());
                                        ProgressDialogUtil.closeProgressDialog();
                                    }
                                }).start();
                            }
                        }
                    });
                    dailog.show();
                    //
                }
                break;
            default:
                break;
        }
    }
}