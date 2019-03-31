package uk.co.tomek.orderbook.domain;

import com.creditsuisse.orderbooksimulation.OrderBookData;

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
    private InteractorListener interactorListener;

    public OrderBookInteractor(OrderBookRepository repository, OrderBookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void startOrderBook() {
        repository.registerListener(this);
        repository.startSimulator();
    }

    @Override
    public void stopOrderBook() {
        repository.stopSimulator();
    }

    @Override
    public void onNewTick(@NonNull OrderBookData orderBookData) {
        final OrdersViewItem ordersViewItem = mapper.mapOrderBook(orderBookData);
        if (interactorListener != null) {
            interactorListener.onNewFormattedOrderTick(ordersViewItem);
        }
    }

    @Override
    public void registerListener(InteractorListener interactorListener) {
        this.interactorListener = interactorListener;
    }
}
