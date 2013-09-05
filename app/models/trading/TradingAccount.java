package models.trading;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.party.Party;
import models.product.Product;
import models.tea.Account;

@Entity
public class TradingAccount extends Account {

	private static final long serialVersionUID = -6371072362602984574L;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "party_id")
	public Party party;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	public Product product;
	
	public TradingAccount(Party party, Product product) {
		this.party = party;
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}
	
	public static Finder<Long, TradingAccount> find = new Finder<Long, TradingAccount>(Long.class, TradingAccount.class);
	
	public static TradingAccount getAccount(Party party, Product product) {
		List<TradingAccount> list = find.where().eq("party_id", party.id).eq("product_id", product.id).findList();
		
		// TODO 1個じゃなかったときの対応
		if (list.size() > 0) {
			return list.get(0);
		}
		return new TradingAccount(party, product);
	}
}
