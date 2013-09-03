package global;

import models.product.Item;
import models.product.Product;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		
		// マスタデータの登録
		if (Item.findById(Long.valueOf(1)) == null) {
			Item item = new Item("アラ", false);
			Product product1 = new Product(item, "鯛のアラ", 273);
			Product product2 = new Product(item, "ヒラマサのアラ", 126);
			
			item.save();
			product1.save();
			product2.save();
		}
	}
}
