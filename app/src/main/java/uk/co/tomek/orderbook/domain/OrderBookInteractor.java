package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookSimulator;
import com.creditsuisse.orderbooksimulation.OrderBookSimulatorListener;

/**
 * Allows to start stop Order Book events source.
 */
public class OrderBookInteractor implements Interactor {

    private final OrderBookSimulator simulator;
    private final OrderBookSimulatorListener listener;

    public OrderBookInteractor(OrderBookSimulator simulator, OrderBookSimulatorListener listener) {
        this.simulator = simulator;
        this.listener = listener;
    }

    @Override
    public void startOrderBook() {
        simulator.startSimulation(listener);
    }

    @Override
    public void stopOrderBook() {
        simulator.stopSimulation();
    }
}
