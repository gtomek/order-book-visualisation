package uk.co.tomek.orderbook.ui;

import android.os.Bundle;

import com.creditsuisse.orderbooksimulation.OrderBookSimulator;
import com.creditsuisse.orderbooksimulation.OrderBookSimulatorListener;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.tomek.orderbook.R;
import uk.co.tomek.orderbook.data.OrderBookRepository;
import uk.co.tomek.orderbook.domain.OrderBookInteractor;

public class MainActivity extends AppCompatActivity {

    private OrderBookInteractor orderBookInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // As we cannot use any library we cannot also use Dagger or similar therefore
        // we have relay on creation of instances somewhere (probably will need to create
        // Dependency Resolver in Application class
        OrderBookSimulator simulator = new OrderBookSimulator();
        OrderBookSimulatorListener listener = new OrderBookRepository();
        orderBookInteractor = new OrderBookInteractor(simulator, listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderBookInteractor.startOrderBook();
    }

    @Override
    protected void onPause() {
        orderBookInteractor.startOrderBook();
        super.onPause();
    }
}
