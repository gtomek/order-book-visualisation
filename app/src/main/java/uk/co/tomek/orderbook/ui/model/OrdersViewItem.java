package uk.co.tomek.orderbook.ui.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Data class representing converted order data, ready to be displayed on the screen.
 */
public final class OrdersViewItem {

    private final List<OrderRaw> sellList;
    private final String midPointTitle;
    private final List<OrderRaw> buyList;

    public OrdersViewItem(LinkedList<OrderRaw> sellList, String midPointTitle, LinkedList<OrderRaw> buyList) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersViewItem that = (OrdersViewItem) o;
        return Objects.equals(sellList, that.sellList) && Objects.equals(midPointTitle, that.midPointTitle) && Objects
                .equals(buyList, that.buyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellList, midPointTitle, buyList);
    }

    @Override
    public String toString() {
        return "OrdersList{" + "sellList=" + sellList + ", midPointTitle='" + midPointTitle + '\'' + ", buyList=" + buyList + '}';
    }
}
