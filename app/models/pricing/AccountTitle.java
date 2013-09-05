package models.pricing;

public enum AccountTitle {
	商品代金 {
		@Override
		public Type getType() {
			return Type.メモ勘定;
		}
	}, 消費税額 {
		@Override
		public Type getType() {
			return Type.メモ勘定;
		}
	};
	
	public abstract AccountTitle.Type getType();

	public enum Type {
		メモ勘定, 一般
	}
}
