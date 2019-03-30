package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookSimulator;
import com.creditsuisse.orderbooksimulation.OrderBookSimulatorListener;

import java.lang.ref.WeakReference;

/**
 * Allows to start stop Order Book events source.
 */
public final class OrderBookInteractor implements Interactor {

    private final OrderBookSimulator simulator;
    private final WeakReference<OrderBookSimulatorListener> listener;

    public OrderBookInteractor(OrderBookSimulator simulator, OrderBookSimulatorListener listener) {
        this.simulator = simulator;
        this.listener = new WeakReference<>(listener);
    }

    @Override
    public void startOrderBook() {
        final OrderBookSimulatorListener listener = this.listener.get();
        if (listener != null) {
            simulator.startSimulation(listener);
        }
    }

    @Override
    public void stopOrderBook() {
        simulator.stopSimulation();
    }
}
