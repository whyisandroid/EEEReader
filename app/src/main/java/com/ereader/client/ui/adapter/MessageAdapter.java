package com.ereader.client.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ereader.client.R;
import com.ereader.client.entities.MessageFriends;
import com.ereader.client.service.AppController;
import com.ereader.common.util.ProgressDialogUtil;
import com.glview.support.v7.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<MessageFriends> mList;
	private Context mContext;

	public MessageAdapter(Context mContext,List<MessageFriends>  list) {
		inflater=LayoutInflater.from(mContext);
		this.mContext = mContext;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MessageFriends message = mList.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView =inflater.inflate(R.layout.my_message_item, null);
			holder = new ViewHolder();
			holder.findView(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}

		String value = "您的好友"+"<font color = \"#43a8d7\">"+message.getFrom_user_nickname()+"</font>" +"给您推荐"+"<font color = \"#43a8d7\">"+"《"+message.getProduct_name()+"》"+"</font>"+", 点击书名立即查看本书。";
		holder.tv_message_name.setText(Html.fromHtml(value));
		holder.tv_message_name.setMovementMethod(LinkMovementMethod.getInstance());
		CharSequence text = holder.tv_message_name.getText();
		if (text instanceof Spannable) {
			SpannableStringBuilder style = new SpannableStringBuilder(text);
			//style.setSpan(new ForegroundColorSpan(Color.RED), 2, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//实现部分文字颜色改变
			style.setSpan(new TestSpanClick(mContext,message.getProduct_id()), 8+message.getFrom_user_nickname().length(), 10+message.getFrom_user_nickname().length() +message.getProduct_name().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//当然这个2和6不用写死，根据实际需要来取值
			holder.tv_message_name.setText(style);
		}
		holder.tv_message_name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});


		holder.tv_message_time.setText(message.getCreated_at());
		return convertView;
	}
	class ViewHolder{
		private TextView tv_message_name;
		private TextView tv_message_time;

		public void findView(View view){
			tv_message_name = (TextView)view.findViewById(R.id.tv_message_name);
			tv_message_time = (TextView)view.findViewById(R.id.tv_message_time);
		}
	}

	private static class TestSpanClick extends ClickableSpan {
		private Context mContext;
		private String product_id;

		public TestSpanClick(Context mContext, String product_id) {
			this.mContext = mContext;
			this.product_id = product_id;
		}
		@Override
		public void updateDrawState(TextPaint ds) {
			super.updateDrawState(ds);
			ds.setUnderlineText(false);
		}

		@Override
		public void onClick(View widget) {
			ProgressDialogUtil.showProgressDialog(mContext, "", false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					AppController.getController().getBookDetail(product_id);
					ProgressDialogUtil.closeProgressDialog();
				}
			}).start();
		}
	}
}


