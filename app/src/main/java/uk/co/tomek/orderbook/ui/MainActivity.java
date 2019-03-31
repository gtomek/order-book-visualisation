package uk.co.tomek.orderbook.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.creditsuisse.orderbooksimulation.OrderBookSimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import uk.co.tomek.orderbook.R;
import uk.co.tomek.orderbook.domain.Interactor;
import uk.co.tomek.orderbook.domain.OrderBookInteractor;
import uk.co.tomek.orderbook.domain.OrderBookMapper;
import uk.co.tomek.orderbook.ui.custom.BarView;
import uk.co.tomek.orderbook.ui.model.OrdersViewItem;

public class MainActivity extends AppCompatActivity implements MainView {

    private Presenter mainPresenter;
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
        hideSystemUI();
        sellItem1 = findViewById(R.id.barview_sell_1);
        sellItem2 = findViewById(R.id.barview_sell_2);
        sellItem3 = findViewById(R.id.barview_sell_3);
        sellItem4 = findViewById(R.id.barview_sell_4);
        buyItem1 = findViewById(R.id.barview_buy_1);
        buyItem2 = findViewById(R.id.barview_buy_2);
        buyItem3 = findViewById(R.id.barview_buy_3);
        buyItem4 = findViewById(R.id.barview_buy_4);
        midPointView = findViewById(R.id.barview_central);
        // As we cannot use any library we cannot also use Dagger or similar therefore
        // we have to relay on creation of instances somewhere (We could also e.g. create a
        // Dependency Resolver in Application class for application scoped instances)
        Interactor orderBookInteractor = new OrderBookInteractor(new OrderBookSimulator(), new OrderBookMapper());
        mainPresenter = new MainPresenter(orderBookInteractor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.takeView(this);
    }

    @Override
    protected void onPause() {
        mainPresenter.dropView();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mainPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onNewFormattedOrderTick(@NonNull OrdersViewItem ordersViewItem) {
        Log.v("MainActivity", String.format("newTick ordersItems:%s",  ordersViewItem));
        midPointView.setTitle(ordersViewItem.getMidPointTitle());
        sellItem1.setValues(ordersViewItem.getSellList().get(0));
        sellItem2.setValues(ordersViewItem.getSellList().get(1));
        sellItem3.setValues(ordersViewItem.getSellList().get(2));
        sellItem4.setValues(ordersViewItem.getSellList().get(3));
        buyItem1.setValues(ordersViewItem.getBuyList().get(0));
        buyItem2.setValues(ordersViewItem.getBuyList().get(1));
        buyItem3.setValues(ordersViewItem.getBuyList().get(2));
        buyItem4.setValues(ordersViewItem.getBuyList().get(3));
    }
}
