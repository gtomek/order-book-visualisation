package uk.co.tomek.orderbook.ui.model;

import java.util.Objects;

/**
 * Data class describing a particular bar item in the view.
 */
public final class OrderRaw {

    private final float priceFriction;
    private final String title;

    public OrderRaw(float priceFriction, String title) {
        this.priceFriction = priceFriction;
        this.title = title;
    }

    public float getPriceFriction() {
        return priceFriction;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRaw orderRaw = (OrderRaw) o;
        return Float.compare(orderRaw.priceFriction, priceFriction) == 0 && Objects.equals(title, orderRaw.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceFriction, title);
    }

    @Override
    public String toString() {
        return "OrderItem{" + "priceFriction=" + priceFriction + ", title='" + title + '\'' + '}';
    }
}
