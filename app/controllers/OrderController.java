package controllers;

import static play.data.Form.form;

import java.util.List;

import models.contract.Contract;
import models.contract.TradingSubjectType;
import models.product.Product;
import models.trading.TransactionType;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.EnumUtils;
import views.html.order.orderForm;
import forms.OrderForm;

public class OrderController extends Controller {
	public static List<String> transaction_type_options = EnumUtils.getList(TransactionType.class);
	public static List<String> trading_subject_options = EnumUtils.getList(TradingSubjectType.class);
	
//	public static Result GO_HOME = redirect(
//		routes.OrderController.list(0, "id", "asc")			
//	);
	
    public static Result index() {
//        return GO_HOME;
    	return ok();
    }
    
    public static Result list(Long contractNumber, String sortBy, String order) {
//        return ok(
//        	contractList.render(ContractFacade.getList(consortBy, order))
//        );
    	return ok();
    }
    
    public static Result create(Long contractNumber) {
    	Form<OrderForm> form = form(OrderForm.class);
    	Contract contract = Contract.findById(contractNumber);
    	List<Product> products = Product.findAll();
        return ok(
        	orderForm.render(contract, products, form)
        );
    }
    
    public static Result confirm(Long contractNumber) {
    	OrderForm f = form(OrderForm.class).bindFromRequest().get();
    	Logger.info(f.quantities.get(1));
    	
    	return ok();
    }
    
    public static Result save(Long contractNumber) {
    	return ok();
    }

}
