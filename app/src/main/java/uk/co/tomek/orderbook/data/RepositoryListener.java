package uk.co.tomek.orderbook.data;

import com.creditsuisse.orderbooksimulation.OrderBookData;

import androidx.annotation.NonNull;

public interface RepositoryListener {
    void onNewTick(@NonNull OrderBookData orderBookData);
}
