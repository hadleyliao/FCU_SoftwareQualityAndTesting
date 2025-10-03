package org.example;

public class Order {
    private final int id;
    private OrderStatus status;
    private final String customerName;
    private final String restaurantName;

    public Order(int id, String customerName, String restaurantName) {
        this.id = id;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.status = OrderStatus.PENDING;
    }

    public int getId() { return id; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public String getCustomerName() { return customerName; }
    public String getRestaurantName() { return restaurantName; }
}

