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
    public static final StockException EXCEPTION = new StockException("message");

    private StockController stockController;
    private StockService stockService;

    @Before
    public void beforeEach() {
        stockService = mock(StockService.class);
        stockController = new StockController(stockService);
    }

    @Test
    public void getStock_whenServiceThrowException() {
        when(stockService.getStock("1")).thenThrow(EXCEPTION);
        Result result = stockController.getStock("1");
        checkErrorResult(result, 409);
    }

    @Test
    public void getStock_whenReturnResult() {
        when(stockService.getStock("1")).thenReturn(10);
        Result result = stockController.getStock("1");
        checkJsonResult(result, "{\"product\":\"1\",\"stock\":\"10\"}");
    }

    @Test
    public void createStock_whenInvalidQuantity() {
        Result result = stockController.createStock("1", "one");
        checkErrorResult(result, 400);
    }

    @Test
    public void createStock_whenNegativeQuantity() {
        stockController.createStock("1", "-10");
    }

    @Test
    public void createStock_whenServiceThrowException() {
        when(stockService.createStock("1", 10)).thenThrow(EXCEPTION);
        Result result = stockController.createStock("1", "10");
        checkErrorResult(result, 409);
    }

    @Test
    public void createStock_whenReturnResult() {
        when(stockService.createStock("1", 10)).thenReturn(10);
        Result result = stockController.createStock("1", "10");
        checkJsonResult(result, "{\"product\":\"1\",\"stock\":\"10\"}");
    }

    @Test
    public void increaseStock_whenServiceThrowException() {
        when(stockService.increaseStock("2", 20)).thenThrow(EXCEPTION);
        Result result = stockController.increaseStock("2", "20");
        checkErrorResult(result, 409);
    }

    @Test
    public void increaseStock_whenReturnResult() {
        when(stockService.increaseStock("1", 10)).thenReturn(10);
        Result result = stockController.increaseStock("1", "10");
        checkJsonResult(result, "{\"product\":\"1\",\"stock\":\"10\"}");
    }

    @Test
    public void decreaseStock_whenServiceThrowException() {
        when(stockService.decreaseStock("3", 30)).thenThrow(EXCEPTION);
        Result result = stockController.decreaseStock("3", "30");
        checkErrorResult(result, 409);
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

    private void checkErrorResult(Result result, int status) {
        assertEquals(status, result.status());
        assertEquals("text/plain", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
    }

}
