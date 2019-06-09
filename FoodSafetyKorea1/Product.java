package org.dfpl.opendata.foodsafetykorea1;

public class Product {
	private String barcode;
	private String name;
	private String date;
	private String address;

	public String toString() {
		return this.barcode + "\t" + this.name + "\t" + this.date + "\t" + this.address;
	}

	public Product(String barcode, String name, String date, String address) {
		super();
		this.barcode = barcode;
		this.name = name;
		this.date = date;
		this.address = address;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}