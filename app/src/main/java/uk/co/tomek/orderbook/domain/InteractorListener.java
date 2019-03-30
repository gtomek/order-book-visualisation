package uk.co.tomek.orderbook.domain;

import androidx.annotation.NonNull;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

/**
 * Callback interface for objects listening to already mapped orders items.
 */
public interface InteractorListener {
    void onNewFormattedOrderTick(@NonNull OrdersViewItem ordersViewItem);
}
