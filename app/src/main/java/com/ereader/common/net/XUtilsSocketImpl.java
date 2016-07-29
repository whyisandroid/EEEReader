package com.ereader.common.net;

import com.ereader.client.EReaderApplication;
import com.ereader.client.entities.Login;
import com.ereader.client.service.AppController;
import com.ereader.common.exception.BusinessException;
import com.ereader.common.exception.ErrorMessage;
import com.ereader.common.util.Config;
import com.ereader.common.util.Json_U;
import com.ereader.common.util.LogUtil;
import com.ereader.common.util.aes.MD5Test;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.PreferencesCookieStore;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ***************************************
 * 类描述： XUtils 框架实现网络处理 类名称：XUtilsSocketImpl
 *
 * @version: 1.0
 * @author: why
 * @time: 2014-2-12 上午10:46:38
 * ****************************************
 */
public class XUtilsSocketImpl implements AppSocketInterface {
    private static HttpUtils httpUtils;
    public final static int TIMEOUT_SOCKET = 50 * 1000; // 超时时间，默认10秒
    public final static int RETRY_TIME = 3;// 重试次数
    public final static String CHARSET = "UTF-8";

    private PreferencesCookieStore preferencesCookieStore;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T shortConnect(Request<T> request) throws BusinessException {

        if (!NetWorkHelper.isNetworkAvailable(AppController.getController().getCurrentActivity())) {
            throw new BusinessException(new ErrorMessage("网络无法连接"));
        }
        String value = "";
        HttpUtils httpUtils = getHttpUtils();
        try {
            RequestParams params = new RequestParams();
            List<NameValuePair> nameValuePairs = (List<NameValuePair>) request
                    .getParameter(Request.AJAXPARAMS);
            if (nameValuePairs == null) {
                nameValuePairs = new ArrayList<NameValuePair>();
            }

            for (int i = 0; i < nameValuePairs.size(); i++) {
                BasicNameValuePair pair = (BasicNameValuePair) nameValuePairs.get(i);
                if ("signature".equals(pair.getName())) {
                    nameValuePairs.remove(i);
                }
            }
            String sign = MD5Test.md5Sign(nameValuePairs, new BasicNameValuePair("_URI_", request.getUrl().replace(Config.MY_SERVICE, "")));

            List<NameValuePair> nameValuePairsGet = new ArrayList<NameValuePair>();
            nameValuePairsGet.add(0, new BasicNameValuePair("appid", "test"));
            nameValuePairsGet.add(new BasicNameValuePair("signature", sign));

            params.addQueryStringParameter(nameValuePairsGet);
            params.addBodyParameter(nameValuePairs);
            LogUtil.Log("XUtilsSocketImpl", request.getUrl() + nameValuePairs.toString() + nameValuePairsGet.toString());
            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.POST,
                    request.getUrl(), params);

            value = responseStream.readString();
            LogUtil.Log("XUtilsSocketImpl" + request.getUrl(), value);
        } catch (com.lidroid.xutils.exception.HttpException e) {
            e.printStackTrace();
            throw new BusinessException(new ErrorMessage("服务器连接错误(1001)"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(new ErrorMessage("服务器连接错误，请您稍后再试(1002)"));
        }

        if (value == null) {
            throw new BusinessException(new ErrorMessage("服务器连接错误，请您稍后再试(1003)"));
        }
        return Json_U.parseJsonToObj(value, request.getR_calzz());
    }


    @Override
    public <T> T longConnect(Request<T> request) {
        // TODO Auto-generated method stub
        return null;
    }

    public synchronized HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            preferencesCookieStore = new PreferencesCookieStore(AppController.getController().getCurrentActivity());
            httpUtils = new HttpUtils();
            httpUtils.configResponseTextCharset(CHARSET);
            httpUtils.configTimeout(TIMEOUT_SOCKET);
            httpUtils.configSoTimeout(TIMEOUT_SOCKET);
            httpUtils.configRequestRetryCount(RETRY_TIME);
            httpUtils.configCookieStore(preferencesCookieStore);
        }
        return httpUtils;
    }

    public static String getDownURL(Request request) throws Exception {
        RequestParams params = new RequestParams();
        List<NameValuePair> nameValuePairs = (List<NameValuePair>) request
                .getParameter(Request.AJAXPARAMS);
        if (nameValuePairs == null) {
            nameValuePairs = new ArrayList<NameValuePair>();
        }

        for (int i = 0; i < nameValuePairs.size(); i++) {
            BasicNameValuePair pair = (BasicNameValuePair) nameValuePairs.get(i);
            if ("signature".equals(pair.getName())) {
                nameValuePairs.remove(i);
            }
        }
        String sign = MD5Test.md5Sign(nameValuePairs, new BasicNameValuePair("_URI_", request.getUrl().replace(Config.MY_SERVICE, "")));
        nameValuePairs.add(0, new BasicNameValuePair("appid", "test"));
        nameValuePairs.add(new BasicNameValuePair("signature", sign));

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        basestring.append(request.getUrl() + "?");
        basestring.append(nameValuePairs.get(0).getName()).append("=").append(URLEncoder.encode(nameValuePairs.get(0).getValue(), "UTF-8")).append("&");

        for (int i = 1; i < nameValuePairs.size(); i++) {
            BasicNameValuePair pair = (BasicNameValuePair) nameValuePairs.get(i);
            basestring.append(pair.getName()).append("=").append(URLEncoder.encode(pair.getValue() == null ? "" : pair.getValue(), "UTF-8")).append("&");
        }
        basestring.deleteCharAt(basestring.length() - 1);
        return basestring.toString();
    }

}
