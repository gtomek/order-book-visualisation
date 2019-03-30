package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.PriceLevelData;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

import static org.junit.Assert.assertEquals;

public class OrderBookMapperTest {

    private final OrderBookMapper mapper = new OrderBookMapper();
    private OrderBookData orderBookData;

    @Before
    public void setUp() {
        PriceLevelData sell1 = new PriceLevelData(10005, 240);
        PriceLevelData sell2 = new PriceLevelData(10004, 142);
        PriceLevelData sell3 = new PriceLevelData(10003 ,122);
        PriceLevelData sell4 = new PriceLevelData(10000, 130);
        List<PriceLevelData> sellSideData = new ArrayList<>(Arrays.asList(sell1, sell2, sell3, sell4));
        PriceLevelData buy1 = new PriceLevelData(9992, 89);
        PriceLevelData buy2 = new PriceLevelData(9991, 85);
        PriceLevelData buy3 = new PriceLevelData(9990 ,189);
        PriceLevelData buy4 = new PriceLevelData(9988, 237);
        List<PriceLevelData> buySideData = new ArrayList<>(Arrays.asList(buy1, buy2, buy3, buy4));
        orderBookData = new OrderBookData(0, sellSideData, buySideData);
    }

    @Test
    public void verifyMidPointPrice() {
        // given
        String expectedMidPointPrince = "9996";

        // when
        OrdersViewItem result = mapper.mapOrderBook(orderBookData);

        // then
        assertEquals(expectedMidPointPrince, result.getMidPointTitle());
    }

    @Test
    public void verifySellQuantities() {
        // given
        float sellFriction1 = 0.38f;

        // when
        OrdersViewItem result = mapper.mapOrderBook(orderBookData);

        // then
        assertEquals(sellFriction1, result.getSellList().get(0).getPriceFriction(), 0.002);
    }

    @Test
    public void verifyBuyQuantities() {
        // given
        float buyFriction1 = 0.395f;

        // when
        OrdersViewItem result = mapper.mapOrderBook(orderBookData);

        // then
        assertEquals(buyFriction1, result.getBuyList().get(3).getPriceFriction(), 0.002);
    }
}