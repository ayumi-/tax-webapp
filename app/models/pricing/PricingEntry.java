package models.pricing;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.tea.Entry;

@Entity
public class PricingEntry extends Entry {
	
	private static final long serialVersionUID = 471382520085873896L;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "transaction_number")
	public PricingTransaction transaction;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "account_id")
	public PricingAccount account;

	public Double price;
	
	public String currency = "円";

	public PricingEntry(PricingTransaction transaction, PricingAccount account, BigDecimal price) {
		this.transaction = transaction;
		this.account = account;
		this.price = price.doubleValue();
	}
	
	public PricingAccount getAccount() {
		return account;
	}

	public PricingTransaction getTransaction() {
		return transaction;
	}

	public static Finder<Long, PricingEntry> find = 
			new Finder<Long, PricingEntry>(Long.class, PricingEntry.class);
	
	public static List<PricingEntry> findByTransaction(PricingTransaction transaction) {
		return find.where().eq("transaction_number", transaction.transactionNumber).findList();
	}
}
