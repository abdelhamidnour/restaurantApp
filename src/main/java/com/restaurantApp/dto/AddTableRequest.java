package com.restaurantApp.dto;

import java.util.Date;

public class AddTableRequest {
	private String tableId;
	private Long tableNumber;
	private Long numberOfSeats;
	private Date from;
	private Date to;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public Long getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Long tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Long getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Long numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

}
