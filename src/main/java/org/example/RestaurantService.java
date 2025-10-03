package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestaurantService {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public void acceptOrder(Order order) throws OrderException {
        logger.info("餐廳收到訂單，準備接單，訂單編號: {}", order.getId());
        if (order.getStatus() != OrderStatus.PENDING) {
            logger.warn("訂單狀態異常，無法接單，訂單編號: {} 狀態: {}", order.getId(), order.getStatus());
            throw new OrderException("訂單狀態異常，無法接單");
        }
        // 模擬業務邏輯錯誤
        if (order.getRestaurantName().equals("壞掉的餐廳")) {
            logger.error("餐廳系統異常，無法接單，訂單編號: {}", order.getId());
            throw new OrderException("餐廳系統異常");
        }
        order.setStatus(OrderStatus.ACCEPTED);
        logger.info("餐廳已成功接單，訂單編號: {}", order.getId());
    }
}

