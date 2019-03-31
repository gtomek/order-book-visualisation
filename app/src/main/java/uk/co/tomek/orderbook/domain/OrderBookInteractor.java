package uk.co.tomek.orderbook.domain;

import android.util.Log;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.OrderBookSimulator;
import com.creditsuisse.orderbooksimulation.OrderBookSimulatorListener;

import androidx.annotation.NonNull;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

/**
 * Allows to start stop Order Book events source.
 */
public final class OrderBookInteractor implements Interactor, OrderBookSimulatorListener {

    private static final String TAG = OrderBookInteractor.class.getName();
    private final OrderBookSimulator simulator;
    private final OrderBookMapper mapper;
    private InteractorListener interactorListener;

    public OrderBookInteractor(OrderBookSimulator simulator, OrderBookMapper mapper) {
        this.simulator = simulator;
        this.mapper = mapper;
    }

    @Override
    public void startOrderBook() {
        simulator.startSimulation(this);
    }

    @Override
    public void stopOrderBook() {
        simulator.stopSimulation();
    }

    @Override
    public void registerListener(InteractorListener interactorListener) {
        this.interactorListener = interactorListener;
    }

    @Override
    public void simulationStarts() {
        Log.v(TAG, "simulationStarts");
    }

    @Override
    public void newTick(@NonNull OrderBookData orderBookData) {
        final OrdersViewItem ordersViewItem = mapper.mapOrderBook(orderBookData);
        if (interactorListener != null) {
            interactorListener.onNewFormattedOrderTick(ordersViewItem);
        }
    }

    @Override
    public void simulationCompleted() {
        Log.v(TAG, "simulationCompleted");
    }
}
