package com.ereader.client.entities.json;


import com.ereader.client.entities.Ecoin;
import com.ereader.client.entities.Point;

/***************************************
 * 类描述：TODO
 * ${CLASS_NAME}
 * Author: why
 * Date:  2015/10/13 21:00
 ***************************************/
public class WalletData {
    private String ecoin;
    private String point;
    private String p2e_exchange_rate;

    public String getP2e_exchange_rate() {
        return p2e_exchange_rate;
    }

    public void setP2e_exchange_rate(String p2e_exchange_rate) {
        this.p2e_exchange_rate = p2e_exchange_rate;
    }

    public String getEcoin() {
        return ecoin;
    }

    public void setEcoin(String ecoin) {
        this.ecoin = ecoin;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
