package models.contract;

import java.math.BigDecimal;

public enum RoundingMethod {
	切り捨て {
		public BigDecimal calc(BigDecimal d) {
			return d.setScale(0, BigDecimal.ROUND_DOWN);
		}
	},
	切り上げ {
		public BigDecimal calc(BigDecimal d) {
			return d.setScale(0, BigDecimal.ROUND_UP);
		}
	},
	四捨五入 {
		public BigDecimal calc(BigDecimal d) {
			return d.setScale(0, BigDecimal.ROUND_HALF_UP);
		}
	};	
	
	public BigDecimal calc(BigDecimal d) {
		return d;
	}
}
