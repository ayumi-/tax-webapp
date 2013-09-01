package models.contract;

import java.math.BigDecimal;

public enum RoundingMethod {
	切り捨て {
		public double calc(double d) {
			return BigDecimal.valueOf(d).setScale(0, BigDecimal.ROUND_DOWN).doubleValue();
		}
	},
	切り上げ {
		public double calc(double d) {
			return BigDecimal.valueOf(d).setScale(0, BigDecimal.ROUND_UP).doubleValue();
		}
	},
	四捨五入 {
		public double calc(double d) {
			return BigDecimal.valueOf(d).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	};	
	
	public double calc(double d) {
		return d;
	}
}
