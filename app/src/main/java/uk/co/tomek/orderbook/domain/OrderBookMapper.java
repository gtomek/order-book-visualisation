package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.PriceLevelData;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import uk.co.tomek.orderbook.data.OrderBookRepository;
import uk.co.tomek.orderbook.ui.model.OrderRaw;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

/**
 * Provides mapping between Order book events received from the {@link OrderBookRepository}
 * and UI elements.
 */
public final class OrderBookMapper {

    public OrdersViewItem mapOrderBook(OrderBookData orderBookData) {
        LinkedList<OrderRaw> sellViewList = new LinkedList<>();
        LinkedList<OrderRaw> buyViewList = new LinkedList<>();

        final List<PriceLevelData> sellInputData = orderBookData.sellSideData;
        final List<PriceLevelData> buyInputData = orderBookData.buySideData;
        // sort sell data descending
        sellInputData.sort(new PriceComparator());
        buyInputData.sort(new PriceComparator());

        long minSellPrice = sellInputData.get(sellInputData.size() - 1).price;
        long maxBuyPrice = buyInputData.get(0).price;
        String midPriceTitle = Long.toString((minSellPrice + maxBuyPrice) / 2);

        long totalSellQuantity = getTotalQuantity(sellInputData);
        for (PriceLevelData priceData : sellInputData) {
            float priceFriction = (float) priceData.assetCount / totalSellQuantity;
            sellViewList.add(new OrderRaw(priceFriction, Long.toString(priceData.price)));
        }

        long totalBuyQuantity = getTotalQuantity(buyInputData);
        for (PriceLevelData priceData : buyInputData) {
            float priceFriction = (float) priceData.assetCount / totalBuyQuantity;
            buyViewList.add(new OrderRaw(priceFriction, Long.toString(priceData.price)));
        }

        return new OrdersViewItem(sellViewList, midPriceTitle, buyViewList);
    }

    private long getTotalQuantity(List<PriceLevelData> orderData) {
        long totalQuantity = 0;
        for (PriceLevelData priceData : orderData) {
            totalQuantity += priceData.assetCount;
        }
        return totalQuantity;
    }

    private static final class PriceComparator implements Comparator<PriceLevelData> {
        @Override
        public int compare(PriceLevelData price1, PriceLevelData price2) {
            return Long.compare(price2.price, price1.price);
        }

    }
}
