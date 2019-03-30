package uk.co.tomek.orderbook.data;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.OrderBookSimulator;
import com.creditsuisse.orderbooksimulation.OrderBookSimulatorListener;

import androidx.annotation.NonNull;
import timber.log.Timber;

/**
 * Emits new order book events.
 */
public final class OrderBookRepository implements Repository, OrderBookSimulatorListener {

    private final OrderBookSimulator simulator;
    private final RepositoryListener listener;

    public OrderBookRepository(OrderBookSimulator simulator, RepositoryListener listener) {
        this.simulator = simulator;
        this.listener = listener;
    }

    @Override
    public void simulationStarts() {
        Timber.v("simulationStarts");
    }

    @Override
    public void newTick(@NonNull OrderBookData orderBookData) {
        Timber.v("newTick %s", orderBookData);
        listener.onNewTick(orderBookData);
    }

    @Override
    public void simulationCompleted() {
        Timber.v("simulationCompleted");
    }

    @Override
    public void startSimulator() {
        simulator.startSimulation(this);
    }

    @Override
    public void stopSimulator() {
        simulator.stopSimulation();
    }
}
