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
    private Ecoin ecoin;
    private Point point;

    public Ecoin getEcoin() {
        return ecoin;
    }

    public void setEcoin(Ecoin ecoin) {
        this.ecoin = ecoin;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
