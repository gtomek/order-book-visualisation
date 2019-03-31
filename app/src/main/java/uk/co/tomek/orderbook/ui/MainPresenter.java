package uk.co.tomek.orderbook.ui;

import androidx.annotation.NonNull;
import uk.co.tomek.orderbook.domain.Interactor;
import uk.co.tomek.orderbook.domain.InteractorListener;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

/**
 * Presenter for the MainView.
 */
public final class MainPresenter implements Presenter, InteractorListener {

    private final Interactor interactor;
    private MainView view;

    public MainPresenter(Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void takeView(MainView view) {
        this.view = view;
        interactor.startOrderBook();
        interactor.registerListener(this);
    }

    @Override
    public void dropView() {
        interactor.stopOrderBook();
        view = null;
    }

    @Override
    public void onNewFormattedOrderTick(@NonNull OrdersViewItem ordersViewItem) {
        view.onNewFormattedOrderTick(ordersViewItem);
    }
}
