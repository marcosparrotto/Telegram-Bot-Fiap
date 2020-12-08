package criptomoeda;

/** Classe que representa o retorno da API do Mercado Bitcoin 
 * @author Rafael Ferr�s
 *  @version 1.0 08 Dez 2020
 *  */

public class CriptoMoeda {
	/**Propriedade Pre�o mais alto*/
	private float high;
	/**Propriedade Pre�o mais baixo*/
	private float low;
	/**Propriedade Volume de vendas*/
	private float vol;
	/**Propriedade Pre�o da ultima venda*/
	private float last;
	/**Propriedade Pre�o de compra*/
	private float buy;
	/**Propriedade Pre�o de venda*/
	private float sell;
	/**Propriedade Pre�o da abertura*/
	private float open;
	/**Propriedade Data de atualiza��o*/
	private int date;
	
	/**Recupera a propriedade high
	 * @return high*/
	public float getHigh() {
		return high;
	}
	/**Atribui a propriedade high
	 * @param high*/
	public void setHigh(float high) {
		this.high = high;
	}
	/**Recupera a propriedade low
	 * @return low*/
	public float getLow() {
		return low;
	}
	/**Atribui a propriedade low
	 * @param low*/
	public void setLow(float low) {
		this.low = low;
	}
	/**Recupera a propriedade vol
	 * @return vol*/
	public float getVol() {
		return vol;
	}
	/**Atribui a propriedade vol
	 * @param vol*/
	public void setVol(float vol) {
		this.vol = vol;
	}
	/**Recupera a propriedade last
	 * @return last*/
	public float getLast() {
		return last;
	}
	/**Atribui a propriedade last
	 * @param last*/
	public void setLast(float last) {
		this.last = last;
	}
	/**Recupera a propriedade buy
	 * @return buy*/
	public float getBuy() {
		return buy;
	}
	/**Atribui a propriedade buy
	 * @param buy*/
	public void setBuy(float buy) {
		this.buy = buy;
	}
	/**Recupera a propriedade sell
	 * @return sell*/
	public float getSell() {
		return sell;
	}
	/**Atribui a propriedade sell
	 * @param sell*/
	public void setSell(float sell) {
		this.sell = sell;
	}
	/**Recupera a propriedade open
	 * @return open*/
	public float getOpen() {
		return open;
	}
	/**Atribui a propriedade open
	 * @param open*/
	public void setOpen(float open) {
		this.open = open;
	}
	/**Recupera a propriedade date
	 * @return date*/
	public int getDate() {
		return date;
	}
	/**Atribui a propriedade date
	 * @param date*/
	public void setDate(int date) {
		this.date = date;
	}
}
