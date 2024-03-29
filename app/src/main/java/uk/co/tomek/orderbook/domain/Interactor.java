package uk.co.tomek.orderbook.domain;

public interface Interactor {
    void startOrderBook();
    void stopOrderBook();
    void registerListener(InteractorListener interactorListener);
}
