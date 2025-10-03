package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HW01 {
    private static final Logger logger = LoggerFactory.getLogger(HW01.class);

    public void runLogic() {
        Order order1 = new Order(1, "小明", "好吃餐廳");
        Order order2 = new Order(2, "小華", "壞掉的餐廳");
        RestaurantService restaurantService = new RestaurantService();
        try {
            restaurantService.acceptOrder(order1);
        } catch (OrderException e) {
            logger.error("業務邏輯錯誤: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("未知錯誤", e);
        }
        try {
            restaurantService.acceptOrder(order2);
        } catch (OrderException e) {
            logger.error("業務邏輯錯誤: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("未知錯誤", e);
        }
        // 測試 Unchecked Exception
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.error("程式碼邏輯錯誤: 除以零", e);
        }
    }

    public static void main(String[] args) {
        new HW01().runLogic();
    }
}
