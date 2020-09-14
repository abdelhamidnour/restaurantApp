package com.restaurantApp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//import org.springframework.format.datetime.DateFormatter;
@Document
public class Table {
	@Id
	private String tableId;
	private Long tableNum;
	private Long numOfSeats;
	private Date from;
	private Date to;
	private Long userId;
	@Field
	private boolean booked = false;

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public Date getFrom() {
		return from;
	}

	public String getFromstr() {
		final String NEW_FORMAT = "yyyy/MM/dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(NEW_FORMAT);
		return sdf.format(from);

	}

	public String getTostr() {
		final String NEW_FORMAT = "yyyy/MM/dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(NEW_FORMAT);
		return sdf.format(to);

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public Long getTableNum() {
		return tableNum;
	}

	public void setTableNum(Long tableNum) {
		this.tableNum = tableNum;
	}

	public Long getNumOfSeats() {
		return numOfSeats;
	}

	public void setNumOfSeats(Long numOfSeats) {
		this.numOfSeats = numOfSeats;
	}

}
