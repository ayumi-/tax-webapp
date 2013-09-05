package models.pricing;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.party.Party;
import models.tea.Account;

@Entity
public class PricingAccount extends Account {

	private static final long serialVersionUID = -6371072362602984574L;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "party_id")
	public Party party;
	
	public String accountTitle;
	
	public String currency = "円";

	public PricingAccount(Party party, AccountTitle accountTitle) {
		this.party = party;
		this.accountTitle = accountTitle.name();
	}

	public static Finder<Long, PricingAccount> find = new Finder<Long, PricingAccount>(Long.class, PricingAccount.class);
	
	public static PricingAccount getAccount(Party party, AccountTitle accountTitle) {
		List<PricingAccount> list = find.where().eq("party_id", party.id).eq("account_title", accountTitle.name()).findList();
		
		// TODO 1個じゃなかったときの対応
		if (list.size() > 0) {
			return list.get(0);
		}
		return new PricingAccount(party, accountTitle);
	}
}
