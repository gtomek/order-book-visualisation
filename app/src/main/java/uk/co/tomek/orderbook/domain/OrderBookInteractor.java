package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.OrderBookSimulator;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import uk.co.tomek.orderbook.data.OrderBookRepository;
import uk.co.tomek.orderbook.data.Repository;
import uk.co.tomek.orderbook.data.RepositoryListener;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

/**
 * Allows to start stop Order Book events source.
 */
public final class OrderBookInteractor implements Interactor, RepositoryListener {

    private final Repository repository;
    private final OrderBookMapper mapper;
    private WeakReference<InteractorListener> listener;

    public OrderBookInteractor(OrderBookMapper mapper, InteractorListener listener) {
        this.repository = new OrderBookRepository(new OrderBookSimulator(), this);
        this.mapper = mapper;
        this.listener = new WeakReference<>(listener);
    }

    @Override
    public void startOrderBook() {
        repository.startSimulator();
    }

    @Override
    public void stopOrderBook() {
        repository.stopSimulator();
    }

    @Override
    public void onNewTick(@NonNull OrderBookData orderBookData) {
        final OrdersViewItem ordersViewItem = mapper.mapOrderBook(orderBookData);
        final InteractorListener interactorListener = listener.get();
        if (interactorListener != null) {
            interactorListener.onNewFormattedOrderTick(ordersViewItem);
        }
    }
}
