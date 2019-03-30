package uk.co.tomek.orderbook.ui.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Data class representing converted order data, ready to be displayed on the screen.
 */
public final class OrdersItem {

    private final List<OrderRaw> sellList;
    private final String midPointTitle;
    private final List<OrderRaw> buyList;

    public OrdersItem(LinkedList<OrderRaw> sellList, String midPointTitle, List<OrderRaw> buyList) {
        this.sellList = sellList;
        this.midPointTitle = midPointTitle;
        this.buyList = buyList;
    }

    public List<OrderRaw> getSellList() {
        return sellList;
    }

    public String getMidPointTitle() {
        return midPointTitle;
    }

    public List<OrderRaw> getBuyList() {
        return buyList;
    }

    @Override
    public String toString() {
        return "OrdersList{" + "sellList=" + sellList + ", midPointTitle='" + midPointTitle + '\'' + ", buyList=" + buyList + '}';
    }
}
