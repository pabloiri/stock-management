package services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class StockServiceImplTest
{

    private StockService stockService;
    private Stock stock;

    @Before
    public void beforeEach() {
        stock = new Stock();
        stockService = new StockServiceImpl(stock);
    }

    @Test
    public void getStock_whenProductStockNotFound() {
        try {
            stockService.getStock("1");
        } catch(StockException ex) {
            assertEquals(StockServiceImpl.STOCK_NOT_EXIST, ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void getStock_whenProductStockFound() {
        stock.put("1", 10);
        int stockQuantity = stockService.getStock("1");
        assertEquals(10, stockQuantity);
    }

    @Test
    public void increaseStock_whenProductStockFound() {
        stock.put("1", 10);
        int result = stockService.increaseStock("1", 100);
        assertEquals(110, result);
        int stockQuantity = stock.get("1");
        assertEquals(110, stockQuantity);
    }

    @Test
    public void increaseStock_whenProductStockNotFound() {
        try {
            stockService.increaseStock("1", 10);
        } catch(StockException ex) {
            assertEquals(StockServiceImpl.STOCK_NOT_EXIST, ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void decreaseStock_whenProductStockNotFound() {
        try {
            stockService.decreaseStock("1", 10);
        } catch(StockException ex) {
            assertEquals(StockServiceImpl.STOCK_NOT_EXIST, ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void decreaseStock_whenNotEnoughStock() {
        stock.put("1", 10);
        try {
            stockService.decreaseStock("1", 100);
        } catch(StockException ex) {
            assertEquals(StockServiceImpl.STOCK_NOT_ENOUGH, ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void decreaseStock_whenEnoughStock() {
        stock.put("1", 100);
        int result = stockService.decreaseStock("1", 10);
        assertEquals(90, result);
        int stockQuantity = stock.get("1");
        assertEquals(90, stockQuantity);
    }

    @Test
    public void createStock_whenProductExist() {
        stock.put("1", 100);
        try {
            stockService.createStock("1", 10);
        } catch(StockException ex) {
            assertEquals(StockServiceImpl.STOCK_ALREADY_EXIST, ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void createStock_whenProductDoesNotExist() {
        int result = stockService.createStock("1", 10);
        assertEquals(10, result);
        int stockQuantity = stock.get("1");
        assertEquals(10, stockQuantity);
    }

}
