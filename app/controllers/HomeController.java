package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

    private final String MESSAGE =
            "The application is ready. \n\n" +
            "Available endpoints: \n\n" +
            "POST    /stock/:product/:quantity \n" +
            "GET     /stock/:product \n" +
            "PUT     /stock/:product/increment/:quantity \n" +
            "PUT     /stock/:product/decrement/:quantity \n";

    public Result index() {
        return ok(MESSAGE);
    }

}
