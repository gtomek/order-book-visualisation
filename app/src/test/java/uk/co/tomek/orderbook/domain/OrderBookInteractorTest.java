package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.OrderBookSimulator;
import com.creditsuisse.orderbooksimulation.PriceLevelData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import uk.co.tomek.orderbook.ui.model.OrderRaw;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderBookInteractorTest {

    private OrderBookInteractor interactor;
    private OrderBookMapper mapper = new OrderBookMapper();

    @Mock
    private OrderBookSimulator simulator;

    @Before
    public void setUp() {
        interactor = new OrderBookInteractor(simulator, mapper);
    }

    @Test
    public void verifyStartOrderBookStartsSimulation() {

        // when
        interactor.startOrderBook();

        // then
        verify(simulator).startSimulation((OrderBookInteractor) interactor);
    }

    @Test
    public void verifyStopOrderBookStopsSimulation() {

        // when
        interactor.stopOrderBook();

        // then
        verify(simulator).stopSimulation();
    }

    @Test
    public void verifyOnNewTickGeneratesAnEvent() {
        // given
        long tick = 1;
        PriceLevelData sell1 = new PriceLevelData(10005, 240);
        PriceLevelData sell2 = new PriceLevelData(10004, 160);
        List<PriceLevelData> sellList = new ArrayList<>(Arrays.asList(sell1, sell2));
        PriceLevelData buy1 = new PriceLevelData(9992, 89);
        List<PriceLevelData> buyList = new ArrayList<>(Collections.singletonList(buy1));
        OrderBookData orderBookData = new OrderBookData(tick, sellList, buyList);
        InteractorListener interactorListener = mock(InteractorListener.class);
        LinkedList<OrderRaw> sellViewList = new LinkedList<OrderRaw>();
        sellViewList.add(new OrderRaw(0.6f, "10005"));
        sellViewList.add(new OrderRaw(0.4f, "10004"));
        String midPointTitle = "9998";
        LinkedList<OrderRaw> buyViewList = new LinkedList<OrderRaw>();
        buyViewList.add(new OrderRaw(1.0f, "9992"));
        OrdersViewItem ordersViewItem = new OrdersViewItem(sellViewList, midPointTitle, buyViewList);

        // when
        interactor.registerListener(interactorListener);
        interactor.newTick(orderBookData);

        // then
        verify(interactorListener).onNewFormattedOrderTick(ordersViewItem);
    }
}