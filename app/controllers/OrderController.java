package controllers;

import static play.data.Form.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import models.contract.Contract;
import models.product.Product;
import models.trading.TradingSubjectType;
import models.trading.TransactionType;
import play.cache.Cache;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import utils.EnumUtils;
import views.html.order.orderConfirm;
import views.html.order.orderForm;
import facades.Order;
import facades.OrderDetail;
import forms.OrderForm;

public class OrderController extends Controller {
	public static List<String> transaction_type_options = EnumUtils.getList(TransactionType.class);
	public static List<String> trading_subject_options = EnumUtils.getList(TradingSubjectType.class);
	
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
    	// TODO バリデーション
    	// TODO ユーザが数量フォームを増やせるようにする（現状だといろいろダメ）
    	// 明細の作成
    	List<Product> products = Product.findAll();
    	List<OrderDetail> details = new ArrayList<OrderDetail>();
    	for (Product p : products) {
    		int id = BigDecimal.valueOf(p.id).intValue();
			String value = f.quantities.get(id);
			if (!value.isEmpty()) {
				Double quantity = Double.valueOf(value);
				details.add(new OrderDetail(p, quantity));
			}
    	}
    	// 注文の作成
    	Order order = new Order(contractNumber, f.effective_date, f.transaction_type, 
    			f.is_untaxed, f.trading_subject, details);
    	// 代金を計算する（TEAの生成）
    	order.calcPrice();
    	// キャッシュする
    	setSessionId();
    	Cache.set(session("uuid"), order);
    	
    	return ok(
    			orderConfirm.render(order)
    		);
    }
    
    @Transactional
    public static Result save(Long contractNumber) {
    	// キャッシュの取得
		Object object = Cache.get(session("uuid"));
		if (object == null) {
			return ok();
		}
    	// 契約の確認
		Order order = (Order) object;
    	Contract contract = Contract.findById(contractNumber);
    	if (contract.getContractNumber() != order.contract.contractNumber) {
    		return ok();
    	}
    	// 永続化
    	order.save();
 
    	return redirect(routes.TradingController.list(contractNumber).url());
    }
    
    public static void setSessionId() {
    	String uuid=session("uuid");
    	if(uuid == null) {
    		uuid=java.util.UUID.randomUUID().toString();
    		session("uuid", uuid);
    	}
    }

}
