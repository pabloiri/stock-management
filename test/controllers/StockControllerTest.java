package controllers;

import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import services.StockException;
import services.StockService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;


public class StockControllerTest
{
    private StockController stockController;
    private StockService stockService;

    @Before
    public void beforeEach() {
        stockService = mock(StockService.class);
        stockController = new StockController(stockService);
    }

    @Test(expected = StockException.class)
    public void getStock_whenServiceThrowException() {
        when(stockService.getStock("1")).thenThrow(StockException.class);
        stockController.getStock("1");
    }

    @Test
    public void getStock_whenReturnResult() {
        when(stockService.getStock("1")).thenReturn(10);
        Result result = stockController.getStock("1");
        checkJsonResult(result, "{\"product\":\"1\",\"stock\":\"10\"}");
    }

    @Test(expected = NumberFormatException.class)
    public void createStock_whenInvalidQuantity() {
        stockController.createStock("1", "one");
    }

    @Test(expected = StockException.class)
    public void createStock_whenNegativeQuantity() {
        stockController.createStock("1", "-10");
    }

    @Test(expected = StockException.class)
    public void createStock_whenServiceThrowException() {
        when(stockService.createStock("1", 10)).thenThrow(StockException.class);
        stockController.createStock("1", "10");
    }

    @Test
    public void createStock_whenReturnResult() {
        when(stockService.createStock("1", 10)).thenReturn(10);
        Result result = stockController.createStock("1", "10");
        checkJsonResult(result, "{\"product\":\"1\",\"stock\":\"10\"}");
    }

    @Test(expected = StockException.class)
    public void increaseStock_whenServiceThrowException() {
        when(stockService.increaseStock("2", 20)).thenThrow(StockException.class);
        stockController.increaseStock("2", "20");
    }

    @Test
    public void increaseStock_whenReturnResult() {
        when(stockService.increaseStock("1", 10)).thenReturn(10);
        Result result = stockController.increaseStock("1", "10");
        checkJsonResult(result, "{\"product\":\"1\",\"stock\":\"10\"}");
    }

    @Test(expected = StockException.class)
    public void decreaseStock_whenServiceThrowException() {
        when(stockService.decreaseStock("3", 30)).thenThrow(StockException.class);
        stockController.decreaseStock("3", "30");
    }

    @Test
    public void decreaseStock_whenReturnResult() {
        when(stockService.decreaseStock("1", 10)).thenReturn(10);
        Result result = stockController.decreaseStock("1", "10");
        checkJsonResult(result, "{\"product\":\"1\",\"stock\":\"10\"}");
    }

    private void checkJsonResult(Result result, String jsonString) {
        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().get());
        assertEquals("UTF-8", result.charset().get());
        assertTrue(contentAsString(result).equals(jsonString));
    }


}
