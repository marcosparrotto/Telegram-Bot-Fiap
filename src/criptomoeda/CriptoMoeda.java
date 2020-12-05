package criptomoeda;

public class CriptoMoeda {
	private float high;
	private float low;
	private float vol;
	private float last;
	private float buy;
	private float sell;
	private float open;
	private int date;
	
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getVol() {
		return vol;
	}
	public void setVol(float vol) {
		this.vol = vol;
	}
	public float getLast() {
		return last;
	}
	public void setLast(float last) {
		this.last = last;
	}
	public float getBuy() {
		return buy;
	}
	public void setBuy(float buy) {
		this.buy = buy;
	}
	public float getSell() {
		return sell;
	}
	public void setSell(float sell) {
		this.sell = sell;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(float open) {
		this.open = open;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
}
