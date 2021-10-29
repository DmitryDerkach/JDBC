package jdbc.entity;

import java.math.BigDecimal;

public class Ticket {
	private Long id;
	private String passengerNum;
	private String passengerName;
	/*flightId является внешним ключем на таблицу flight. Пока оставляем как есть, но
	 *очень часто вместо того, чтобы использовать здесь идентификатор используют целую сущность,
	 *т.е. представляется ссылка на объект типа flight*/
//	private Long flightId;
	private Flight flightId;
	private String seatNum;
	private BigDecimal cost;
	
	public Ticket(Long id, String passengerNum, String passengerName, Flight flightId, String seatNum, BigDecimal cost) {
		this.id = id;
		this.passengerNum = passengerNum;
		this.passengerName = passengerName;
		this.flightId = flightId;
		this.seatNum = seatNum;
		this.cost = cost;
	}
	
	public Ticket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassengerNum() {
		return passengerNum;
	}

	public void setPassengerNum(String passengerNum) {
		this.passengerNum = passengerNum;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Flight getFlightId() {
		return flightId;
	}

	public void setFlightId(Flight flightId) {
		this.flightId = flightId;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", passengerNum=" + passengerNum + ", passengerName=" + passengerName
				+ ", flight=" + flightId + ", seatNum=" + seatNum + ", cost=" + cost + "]";
	}
	
}
