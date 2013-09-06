package facades;

import java.util.List;

import models.contract.Contract;
import models.pricing.PricingEntry;
import models.pricing.PricingTransaction;
import models.trading.TradingEntry;
import models.trading.TradingTransaction;

public class TradingFacade {

	private TradingTransaction tradingTransaction;

	public TradingFacade(Long transactionNumber) {
		this.tradingTransaction = TradingTransaction.finndById(transactionNumber);
	}

	public void cancel() {
		// エントリーをマイナスにする
		List<TradingEntry> tes = tradingTransaction.getEntries();
		for (TradingEntry te : tes) {
			TradingEntry te2 = te.clone();
			te2.toRed();
			te2.save();
		}
		List<PricingTransaction> pts = PricingTransaction.findByTradingTransaction(tradingTransaction);
		for (PricingTransaction pt : pts) {
			for (PricingEntry pe : pt.getEntries()) {
				PricingEntry pe2 = pe.clone();
				pe2.toRed();
				pe2.save();
			}
		}
	}

	public Contract getCongract() {
		return tradingTransaction.getContract();
	}

}
