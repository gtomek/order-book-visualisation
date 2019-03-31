package uk.co.tomek.orderbook.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import uk.co.tomek.orderbook.domain.Interactor;
import uk.co.tomek.orderbook.domain.InteractorListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PresenterTest {

    private Presenter presenter;

    @Mock
    private Interactor interactor;

    @Mock
    private MainView mainView;

    @Before
    public void setUp() {
        presenter = new MainPresenter(interactor);
    }

    @Test
    public void verifyThatStartEmulatorIsCalledOnTakeView() {
        // when
        presenter.takeView(mainView);

        // then
        verify(interactor).registerListener((InteractorListener) any());
        verify(interactor).startOrderBook();
    }

    @Test
    public void verifyThatStopEmulatorIsCalledOnDropView() {
        // when
        presenter.dropView();

        // then
        verify(interactor).stopOrderBook();
    }
}