package services;

import com.google.inject.ImplementedBy;

@ImplementedBy(StockServiceImpl.class)
public interface StockService {

    int getStock(String productId);

    int increaseStock(String productId, int quantity);

    int decreaseStock(String productId, int quantity);

    int createStock(String productId, int quantity);
}
