package uk.co.tomek.orderbook.ui;

import androidx.annotation.NonNull;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

public interface MainView {
    void onNewFormattedOrderTick(@NonNull OrdersViewItem ordersViewItem);
}
