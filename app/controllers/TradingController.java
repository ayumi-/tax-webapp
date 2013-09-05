package controllers;

import facade.TradingFacade;
import models.contract.Contract;
import models.trading.TradingTransaction;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.trading.tradingList;

public class TradingController extends Controller {
	
    public static Result GO_HOME = redirect(
            routes.ContractController.list("id", "asc")
        );
        
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result list(Long contractNumber) {
		Contract contract = Contract.findById(contractNumber);
        return ok(
        	tradingList.render(contract, TradingTransaction.findByContract(contract))
        );
    }
    
    @Transactional
    public static Result cancel(Long transactionNumber) {
    	TradingFacade facade = new TradingFacade(transactionNumber);
    	facade.cancel();
    	Contract contract = facade.getCongract();
    	
    	return redirect(routes.TradingController.list(contract.contractNumber));
    }
}
