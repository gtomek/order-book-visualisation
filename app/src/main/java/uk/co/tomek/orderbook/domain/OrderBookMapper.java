package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.PriceLevelData;

import java.util.LinkedList;
import java.util.List;

import uk.co.tomek.orderbook.data.OrderBookRepository;
import uk.co.tomek.orderbook.ui.model.OrderRaw;
import uk.co.tomek.orderbook.ui.model.OrdersItem;

/**
 * Provides mapping between Order book events received from the {@link OrderBookRepository}
 * and UI elements.
 */
public final class OrderBookMapper {

    public OrdersItem mapOrderBook(OrderBookData orderBookData) {
        LinkedList<OrderRaw> sellList = new LinkedList<>();
        LinkedList<OrderRaw> buyList = new LinkedList<>();

        long minSellPrice = findMinPrice(orderBookData.sellSideData);
        long maxBuyPrice = findMaxPrice(orderBookData.buySideData);
        final long minPrice = (minSellPrice + maxBuyPrice) / 2;
        String midPriceTitle = Long.toString(minPrice);
        return new OrdersItem(sellList, midPriceTitle, buyList);
    }

    private long findMinPrice(List<PriceLevelData> orderData) {
        long minPrice = orderData.get(0).price;
        for (PriceLevelData buyData : orderData) {
            if (buyData.price < minPrice) {
                minPrice = buyData.price;
            }
        }
        return minPrice;
    }

    private long findMaxPrice(List<PriceLevelData> orderData) {
        long maxPrice = orderData.get(0).price;
        for (PriceLevelData buyData : orderData) {
            if (buyData.price > maxPrice) {
                maxPrice = buyData.price;
            }
        }
        return maxPrice;
    }
}
