package com.johan.beans;

public class Triki {
	
	private int cell;
	private String status;
	
	public Triki(int cell, String status) {
		super();
		this.cell = cell;
		this.status = status;
	}

	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Triki [cell=" + cell + ", status=" + status + "]";
	}
}
