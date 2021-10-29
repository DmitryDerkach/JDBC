package jdbc.entity;

import java.time.LocalDateTime;

public class Flight {
	private Long id;
	private String flightNum;
	private LocalDateTime departureDate;
	private String departureAirportCode;
	private LocalDateTime arrivalDate;
	private String arrivalAirportCode;
	private Integer aircraftId;
	private String status;
	
	public Flight() {
		
	}

	public Flight(Long id, String flightNum, LocalDateTime departureDate, String departureAirportCode,
			LocalDateTime arrivalDate, String arrivalAirportCode, Integer aircraftId, String status) {
		this.id = id;
		this.flightNum = flightNum;
		this.departureDate = departureDate;
		this.departureAirportCode = departureAirportCode;
		this.arrivalDate = arrivalDate;
		this.arrivalAirportCode = arrivalAirportCode;
		this.aircraftId = aircraftId;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}

	public Integer getAircraftId() {
		return aircraftId;
	}

	public void setAircraftId(Integer aircraftId) {
		this.aircraftId = aircraftId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", flightNum=" + flightNum + ", departureDate=" + departureDate
				+ ", departureAirportCode=" + departureAirportCode + ", arrivalDate=" + arrivalDate
				+ ", arrivalAirportCode=" + arrivalAirportCode + ", aircraftId=" + aircraftId + ", status=" + status
				+ "]";
	}
	
}
