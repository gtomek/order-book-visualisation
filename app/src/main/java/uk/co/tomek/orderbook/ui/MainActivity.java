package uk.co.tomek.orderbook.ui;

import android.os.Bundle;

import com.creditsuisse.orderbooksimulation.OrderBookData;
import com.creditsuisse.orderbooksimulation.OrderBookSimulator;
import com.creditsuisse.orderbooksimulation.OrderBookSimulatorListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;
import uk.co.tomek.orderbook.R;
import uk.co.tomek.orderbook.domain.OrderBookInteractor;
import uk.co.tomek.orderbook.domain.OrderBookMapper;
import uk.co.tomek.orderbook.ui.custom.BarView;
import uk.co.tomek.orderbook.ui.model.OrdersItem;

public class MainActivity extends AppCompatActivity implements OrderBookSimulatorListener {

    // As we cannot use any library we cannot also use Dagger or similar therefore
    // we have relay on creation of instances somewhere (probably will need to create
    // Dependency Resolver in Application class
    OrderBookSimulator simulator = new OrderBookSimulator();
    OrderBookMapper mapper = new OrderBookMapper();
    private OrderBookInteractor orderBookInteractor = new OrderBookInteractor(simulator, this);
    private BarView midPointView;
    private BarView sellItem1;
    private BarView sellItem2;
    private BarView sellItem3;
    private BarView sellItem4;
    private BarView buyItem1;
    private BarView buyItem2;
    private BarView buyItem3;
    private BarView buyItem4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sellItem1 = findViewById(R.id.barview_sell_1);
        sellItem2 = findViewById(R.id.barview_sell_2);
        sellItem3 = findViewById(R.id.barview_sell_3);
        sellItem4 = findViewById(R.id.barview_sell_4);
        buyItem1 = findViewById(R.id.barview_buy_1);
        buyItem2 = findViewById(R.id.barview_buy_2);
        buyItem3 = findViewById(R.id.barview_buy_3);
        buyItem4 = findViewById(R.id.barview_buy_4);
        midPointView = findViewById(R.id.barview_central);
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

    @Override
    public void simulationStarts() {
        Timber.v("simulationStarts");
    }

    @Override
    public void newTick(@NonNull OrderBookData orderBookData) {
        final OrdersItem ordersItems = mapper.mapOrderBook(orderBookData);
        Timber.v("newTick %s ordersItems:%s", orderBookData.tick, ordersItems);
        midPointView.setTitle(ordersItems.getMidPointTitle());
        sellItem1.setValues(ordersItems.getSellList().get(0));
        sellItem2.setValues(ordersItems.getSellList().get(1));
        sellItem3.setValues(ordersItems.getSellList().get(2));
        sellItem4.setValues(ordersItems.getSellList().get(3));
        buyItem1.setValues(ordersItems.getBuyList().get(0));
        buyItem2.setValues(ordersItems.getBuyList().get(1));
        buyItem3.setValues(ordersItems.getBuyList().get(2));
        buyItem4.setValues(ordersItems.getBuyList().get(3));
    }

    @Override
    public void simulationCompleted() {
        Timber.v("simulationCompleted");
    }
}
