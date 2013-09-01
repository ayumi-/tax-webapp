package controllers;

import static play.data.Form.form;

import java.util.List;

import models.contract.CalculationBase;
import models.contract.ContractType;
import models.contract.RoundingMethod;
import models.party.Party;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.EnumUtils;
import views.html.application.index;
import views.html.contract.createForm;

import com.avaje.ebean.annotation.Transactional;

import facades.ContractFacade;
import facades.PartyFacade;
import forms.ContractForm;

public class ContractController extends Controller {
	public static List<String> contract_type_options = EnumUtils.getList(ContractType.class);
	public static List<String> rounding_method_options = EnumUtils.getList(RoundingMethod.class);
	public static List<String> calculation_base_options = EnumUtils.getList(CalculationBase.class);
	
    public static Result GO_HOME = redirect(
        routes.ContractController.list(0, "name", "asc", "")
    );
    
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(index.render("ok"));
    }
    
    public static Result create() {
    	Form<ContractForm> form = form(ContractForm.class);
        return ok(
        		createForm.render(form)
        );
    }
    
    @Transactional
    public static Result save() {
    	ContractForm f = form(ContractForm.class).bindFromRequest().get();
    
    	// TODO バリデーション
    	PartyFacade pf = new PartyFacade(f.party_name);
    	Party p = pf.save();
    	
    	ContractFacade cf = new ContractFacade(
    			p, f.agreement_date, f.contract_type, f.price_rounding_method,
    			f.consumption_tax_rounding_method, f.consumption_tax_calculation_base);
    	cf.save();
    	
        return GO_HOME;
    }
}
