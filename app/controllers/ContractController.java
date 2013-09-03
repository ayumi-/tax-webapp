package controllers;

import static play.data.Form.form;

import java.util.List;

import models.contract.CalculationBase;
import models.contract.ContractType;
import models.contract.RoundingMethod;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.EnumUtils;
import views.html.contract.contractForm;
import views.html.contract.contractList;

import com.avaje.ebean.annotation.Transactional;

import facades.ContractFacade;
import facades.PartyFacade;
import forms.ContractForm;

public class ContractController extends Controller {
	public static List<String> contract_type_options = EnumUtils.getList(ContractType.class);
	public static List<String> rounding_method_options = EnumUtils.getList(RoundingMethod.class);
	public static List<String> calculation_base_options = EnumUtils.getList(CalculationBase.class);
	
    public static Result GO_HOME = redirect(
        routes.ContractController.list("id", "asc")
    );
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result list(String sortBy, String order) {
        return ok(
        	contractList.render(ContractFacade.getList(sortBy, order))
        );
    }
    
    public static Result create() {
    	Form<ContractForm> form = form(ContractForm.class);
        return ok(
        	contractForm.render(form)
        );
    }
    
    @Transactional
    public static Result save() {
    	ContractForm f = form(ContractForm.class).bindFromRequest().get();
    	
    	// TODO バリデーション
    	PartyFacade pf = new PartyFacade(f.party_name);    	
    	ContractFacade cf = new ContractFacade(
    			pf.getParty(), f.agreement_date, f.contract_type, f.price_rounding_method,
    			f.consumption_tax_rounding_method, f.consumption_tax_calculation_base);
    	cf.save();
    	
        return GO_HOME;
    }
}
