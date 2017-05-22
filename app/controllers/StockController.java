package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.StockException;
import services.StockService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class StockController extends Controller {

    private final StockService stockService;

    @Inject
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    public Result getStock(String productId) {
        try{
            int productStock = stockService.getStock(productId);
            JsonNode response = createStockResponse(productId, productStock);
            return ok(response);
        } catch(StockException ex) {
            return status(409, ex.getMessage());
        }
    }

    public Result createStock(String productId, String quantityString) {
        try{
            int quantity = parseQuantity(quantityString);
            int productStock = stockService.createStock(productId, quantity);
            JsonNode response = createStockResponse(productId, productStock);
            return ok(response);
        } catch(ParameterException ex) {
            return badRequest(ex.getMessage());
        } catch(StockException ex) {
            return status(409, ex.getMessage());
        }
    }

    public Result increaseStock(String productId, String quantityString) {
        try{
            int quantity = parseQuantity(quantityString);
            int productStock = stockService.increaseStock(productId, quantity);
            JsonNode response = createStockResponse(productId, productStock);
            return ok(response);
        } catch(ParameterException ex) {
            return badRequest(ex.getMessage());
        } catch(StockException ex) {
            return status(409, ex.getMessage());
        }
    }

    public Result decreaseStock(String productId, String quantityString) {
        try{
            int quantity = parseQuantity(quantityString);
            int productStock = stockService.decreaseStock(productId, quantity);
            JsonNode response = createStockResponse(productId, productStock);
            return ok(response);
        } catch(ParameterException ex) {
            return badRequest(ex.getMessage());
        } catch(StockException ex) {
            return status(409, ex.getMessage());
        }
    }

    private int parseQuantity(String quantityString) {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch(NumberFormatException ex) {
            throw new ParameterException("Invalid quantity");
        }
        if(quantity <= 0) {
            throw new ParameterException("Invalid quantity");
        }
        return quantity;
    }

    private JsonNode createStockResponse(String productId, int productStock) {
        Map<String, String> response = new HashMap<>();
        response.put("product", productId);
        response.put("stock", String.valueOf(productStock));
        return Json.toJson(response);
    }

}
