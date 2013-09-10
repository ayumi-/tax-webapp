package global;

import java.math.BigDecimal;

import models.product.Item;
import models.product.Product;
import models.tea.Unit;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		
		// マスタデータの登録
		if (Item.findById(Long.valueOf(1)) == null) {
			Item item = new Item("アラ", false);
			item.save();
			
			saveProduct(item, "鯛のアラ", 273, Unit.キログラム);
			saveProduct(item, "ヒラマサのアラ", 126, Unit.キログラム);
			saveProduct(item, "太麺", 3300, Unit.箱);
			saveProduct(item, "細麺", 3000, Unit.箱);
		}
	}

	private void saveProduct(Item item, String name, Integer unitPrice, Unit unit) {
		Product p = new Product(item, name, BigDecimal.valueOf(unitPrice), unit);
		p.save();
	}
}
