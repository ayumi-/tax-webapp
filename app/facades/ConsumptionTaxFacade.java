package facades;

import java.math.BigDecimal;

import models.trading.TradingTransaction;

import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import utils.DateUtils;

import com.typesafe.config.ConfigFactory;

public class ConsumptionTaxFacade {

	private TradingTransaction transaction;
	private BigDecimal price;

	public ConsumptionTaxFacade(TradingTransaction transaction, BigDecimal price) {
		this.transaction = transaction;
		this.price = price;
	}

	public BigDecimal getConsumptionTaxAmountRounded() {
		// 消費税ライブラリの呼び出し
		String url = ConfigFactory.load().getString("taxlib.url");
		Promise<Response> respons = WS.url(url)
				.setQueryParameter("price", price.toString())
				.setQueryParameter("targetDate", DateUtils.toString(transaction.effectiveDate, "yyyy/MM/dd"))
				.setQueryParameter("isTaxed", transaction.isUntaxed.toString())
				.setQueryParameter("roundingRule", transaction.getContract().consumptionTaxRoundingMethod)
				.setQueryParameter("calculationType", "外税").get();
		JsonNode json = respons.get().asJson();
		
		Logger.info(json.toString());
		String taxAmount = json.findValue("consumptionTaxAmountRounded").asText();
		
		return BigDecimal.valueOf(Double.valueOf(taxAmount));
	}

}
