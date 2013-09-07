package facades;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.contract.CalculationBase;
import models.contract.Contract;
import models.contract.RoundingMethod;
import models.pricing.AccountTitle;
import models.pricing.PricingAccount;
import models.pricing.PricingEntry;
import models.pricing.PricingTransaction;
import models.trading.TradingAccount;
import models.trading.TradingEntry;
import models.trading.TradingSubjectType;
import models.trading.TradingTransaction;
import models.trading.TransactionType;
import utils.DateUtils;

public class Order {

	public Contract contract;
	public Date effectiveDate;
	public TransactionType transactionType;
	public boolean isUntaxed;
	public TradingSubjectType tradingSubject;
	public List<OrderDetail> details;
	private List<TradingEntry> tradingEntries = new ArrayList<TradingEntry>();
	private List<PricingEntry> pricingEntries = new ArrayList<PricingEntry>();

	public Order(Long contractNumber, String effectiveDate,
			String transactionType, String isUntaxed, String tradingSubject,
			List<OrderDetail> details) {
		this.contract = Contract.findById(contractNumber);
		this.transactionType = TransactionType.valueOf(transactionType);
		this.isUntaxed = isUntaxed != null;
		this.tradingSubject = TradingSubjectType.valueOf(tradingSubject);
		this.details = details;
		try {
			this.effectiveDate = DateUtils.parseDate(effectiveDate, "yyyy-MM-dd");
		} catch (ParseException e) {
			// TODO エラーの追加
		}
	}
	
	public List<PricingEntry> getPricingEntries() {
		return pricingEntries;
	}
	
	public void calcPrice() {
		// 商流
		createTrading();
		
		// 金流
		createPricing();
	}

	private void createPricing() {
		TradingTransaction tt = tradingEntries.get(0).getTransaction();
		CalculationBase base = tt.getContract().getConsumptionTaxCalculationBase();
		if (base == CalculationBase.合計) {
			// transaction（消費税）
			PricingTransaction taxPt = new PricingTransaction(effectiveDate, tt);

			// account, entry（商品代金）
			RoundingMethod priceRoundingMethod = tt.getContract().getPriceRoundingMethod();
			PricingAccount pa = PricingAccount.getAccount(contract.party, AccountTitle.商品代金);
			for (TradingEntry te : tradingEntries) {
				PricingTransaction pt = new PricingTransaction(effectiveDate, te);
				BigDecimal quantity = te.quantity;
				BigDecimal price = priceRoundingMethod.calc(
						te.getAccount().getProduct().getUnitPrice().multiply(quantity));
				taxPt.addTotalPrice(price);
				pricingEntries.add(new PricingEntry(pt, pa, price));
			}

			// account, entry（消費税）
			PricingAccount taxAccount = PricingAccount.getAccount(contract.party, AccountTitle.消費税額);
			pricingEntries.add(new PricingEntry(taxPt, taxAccount, taxPt.calculateConsumptionTax()));
			
		} else if (base == CalculationBase.明細) {
			RoundingMethod priceRoundingMethod = tt.getContract().getPriceRoundingMethod();
			PricingAccount productAccount = PricingAccount.getAccount(contract.party, AccountTitle.商品代金);
			PricingAccount taxAccount = PricingAccount.getAccount(contract.party, AccountTitle.消費税額);

			for (TradingEntry te : tradingEntries) {
				// transaction
				PricingTransaction pt = new PricingTransaction(effectiveDate, te);
				
				// entry（商品代金）
				BigDecimal quantity = te.quantity;
				BigDecimal price = priceRoundingMethod.calc(
						te.getAccount().getProduct().getUnitPrice().multiply(quantity));
				pt.addTotalPrice(price);
				pricingEntries.add(new PricingEntry(pt, productAccount, price));
				
				// entry（消費税）
				pricingEntries.add(new PricingEntry(pt, taxAccount, pt.calculateConsumptionTax()));
			}
		} else if (base == CalculationBase.安い方) {
			// TODO 実装
		}
	}

	private void createTrading() {
		// transaction
		TradingTransaction tt = new TradingTransaction(contract, effectiveDate, transactionType, tradingSubject, isUntaxed);
		
		// account, entry
		for (OrderDetail d : details) {
			TradingAccount ta = TradingAccount.getAccount(contract.party, d.getProduct());
			tradingEntries.add(new TradingEntry(tt, ta, d.getQuantity()));
		}
	}

	public void save() {
		for (TradingEntry te : tradingEntries) {
			te.save();
		}
		for (PricingEntry pe : pricingEntries) {
			pe.save();
		}
	}
}
