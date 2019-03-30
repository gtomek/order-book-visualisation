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
        String midPriceTitle = Long.toString((minSellPrice + maxBuyPrice) / 2);

        long totalSellQuantity = getTotalQuantity(orderBookData.sellSideData);
        for (PriceLevelData priceData : orderBookData.sellSideData) {
            float priceFriction = (float) priceData.assetCount/totalSellQuantity;
            sellList.add(new OrderRaw(priceFriction, Long.toString(priceData.price)));
        }

        long totalBuyQuantity = getTotalQuantity(orderBookData.buySideData);
        for (PriceLevelData priceData : orderBookData.buySideData) {
            float priceFriction = (float) priceData.assetCount/totalBuyQuantity;
            buyList.add(new OrderRaw(priceFriction, Long.toString(priceData.price)));
        }


        return new OrdersItem(sellList, midPriceTitle, buyList);
    }

    private long findMinPrice(List<PriceLevelData> orderData) {
        long minPrice = orderData.get(0).price;
        for (PriceLevelData priceData : orderData) {
            if (priceData.price < minPrice) {
                minPrice = priceData.price;
            }
        }
        return minPrice;
    }

    private long findMaxPrice(List<PriceLevelData> orderData) {
        long maxPrice = orderData.get(0).price;
        for (PriceLevelData priceData : orderData) {
            if (priceData.price > maxPrice) {
                maxPrice = priceData.price;
            }
        }
        return maxPrice;
    }

    private long getTotalQuantity(List<PriceLevelData> orderData) {
        long totalQuantity = 0;
        for (PriceLevelData priceData : orderData) {
            totalQuantity += priceData.assetCount;
        }
        return totalQuantity;
    }
}
