package uk.co.tomek.orderbook.data;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.OrderBookSimulatorListener;

import androidx.annotation.NonNull;
import timber.log.Timber;

/**
 * Emits new order book events.
 */
public class OrderBookRepository implements OrderBookSimulatorListener {

    @Override
    public void simulationStarts() {
        Timber.v("simulationStarts");
    }

    @Override
    public void newTick(@NonNull OrderBookData orderBookData) {
        Timber.v("newTick %s", orderBookData);
    }

    @Override
    public void simulationCompleted() {
        Timber.v("simulationCompleted");
    }
}
