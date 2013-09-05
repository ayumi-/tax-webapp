package facade;

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
		// マイナスの商流を作成する
//		TradingTransaction tt2 = tradingTransaction.clone();
//		tt2.save();
		List<TradingEntry> tes = tradingTransaction.getEntries();
		for (TradingEntry te : tes) {
			TradingEntry te2 = te.clone();
//			te2.transaction = tt2;
			te2.quantity = Math.abs(te.quantity) * -1;
			te2.save();
		}
		
		// 金流のtransaction, entryを削除する
		List<PricingTransaction> pts = PricingTransaction.findByTradingTransaction(tradingTransaction);
		for (PricingTransaction pt : pts) {
			for (PricingEntry pe : pt.getEntries()) {
				pe.delete();
			}
			pt.delete();
		}
	}

	public Contract getCongract() {
		return tradingTransaction.getContract();
	}

}
