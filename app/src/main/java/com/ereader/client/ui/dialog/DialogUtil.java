package com.ereader.client.ui.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.ereader.client.EReaderApplication;
import com.ereader.client.entities.BookShow;
import com.ereader.client.ui.bookshelf.SearchBuyActivity;
import com.ereader.common.util.IntentUtil;

/**
 * ****************************************
 * 类描述： 对话框统一管理 类名称：DialogUtil
 *
 * @version: 1.0
 * @author: GHZ
 * @time: 2014-5-8 下午5:43:09
 * ****************************************
 */
public class DialogUtil {
    /**
     * 方法描述：支持失败 弹窗提示 失败原因
     *
     * @param context
     * @return
     * @author: GHZ
     * @time: 2014-6-26 下午7:15:31
     */
    public static void showError(Context context, String message) {
        final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_ONE);
        dialog.setmTitle("失败原因").setMessage(message);
        dialog.setOne("确定", new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.closeDialog();
            }
        });
    }

    /**
     * 方法描述：支持失败 弹窗提示 失败原因
     *
     * @param context
     * @return
     * @author: GHZ
     * @time: 2014-6-26 下午7:15:31
     */
    public static void showError(Context context, String message, final int HomeActivityIndexTab) {
        final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_ONE);
        dialog.setCancelable(false);
        dialog.setmTitle("失败原因").setMessage(message);
        dialog.setOne("确定", new OnClickListener() {
            @Override
            public void onClick(View v) {
                //IntentUtil.intent(dialog.getContext(),toWhere);
                IntentUtil.AppHomePageByIndex(dialog.getContext(), HomeActivityIndexTab);
                dialog.closeDialog();

            }
        });
    }

    public static void exit(Context context, final Handler mHandler) {
        final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_TWO);
        dialog.setCancelable(false);
        dialog.setMessage("确定退出此账号");
        dialog.setRight("确定", new OnClickListener() {
            @Override
            public void onClick(View v) {
                EReaderApplication.getInstance().setLogin(false);
                mHandler.obtainMessage(0).sendToTarget();
                dialog.closeDialog();

            }
        });
        dialog.setLeft("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.closeDialog();
            }
        });

    }

    public static void delBookById(Context context, BookShow book, final Handler mHandler) {
        final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_TWO);
        dialog.setCancelable(false);
        dialog.setMessage("确定要删除《" + book.getName() + "》？");
        dialog.setRight("确定", new OnClickListener() {
            @Override
            public void onClick(View v) {

                mHandler.obtainMessage(SearchBuyActivity._DELETE).sendToTarget();
                dialog.closeDialog();

            }
        });
        dialog.setLeft("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.closeDialog();
            }
        });
    }
}
