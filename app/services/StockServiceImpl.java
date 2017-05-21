package services;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StockServiceImpl implements StockService {

    public static final String STOCK_NOT_EXIST = "Stock does not exist for product";
    public static final String STOCK_NOT_ENOUGH = "The Product Stock is not enough";
    public static final String STOCK_ALREADY_EXIST = "Stock already exist for product";

    private final Stock stock;

    @Inject
    public StockServiceImpl(Stock stock) {
        this.stock = stock;
    }

    public int getStock(String productId) {
        if(!stock.containsKey(productId)) {
            throw new StockException(STOCK_NOT_EXIST);
        }
        return stock.get(productId);
    }

    public int increaseStock(String productId, int quantity) {
        if(!stock.containsKey(productId)) {
            throw new StockException(STOCK_NOT_EXIST);
        }
        int stockQuantity = stock.get(productId) + quantity;
        stock.put(productId, stockQuantity);
        return stockQuantity;
    }

    public int decreaseStock(String productId, int quantity) {
        if(!stock.containsKey(productId)) {
            throw new StockException(STOCK_NOT_EXIST);
        }
        int stockQuantity = stock.get(productId);
        if(quantity > stockQuantity) {
            throw new StockException(STOCK_NOT_ENOUGH);
        }
        stockQuantity -= quantity;
        stock.put(productId, stockQuantity);
        return stockQuantity;
    }

    public int createStock(String productId, int quantity) {
        if(stock.containsKey(productId)) {
            throw new StockException(STOCK_ALREADY_EXIST);
        }
        stock.put(productId, quantity);
        return quantity;
    }

}
