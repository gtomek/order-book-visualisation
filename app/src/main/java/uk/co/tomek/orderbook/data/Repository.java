package uk.co.tomek.orderbook.data;

public interface Repository {
    void startSimulator();
    void stopSimulator();
    void registerListener(RepositoryListener listener);
}
