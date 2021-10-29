package jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jdbc.dao.TicketDao;
import jdbc.dto.TicketFilter;
import jdbc.entity.*;
public class DaoRunner {
	public static void main(String[] args) {
		/*Insert*/
//		TicketDao ticketDao = TicketDao.getInstance();
//		Ticket ticket = new Ticket();
//		ticket.setPassengerNum("1234567");
//		ticket.setPassengerName("test");
//		ticket.setFlightId(4L);
//		ticket.setSeatNum("B3");
//		ticket.setCost(BigDecimal.TEN);
//		Ticket savedTicket = ticketDao.save(ticket);
//		System.out.println(savedTicket);
		/*Удаление*/
//		TicketDao ticketDao = TicketDao.getInstance();
//		boolean result = ticketDao.delete(60L);
//		System.out.println(result);
		/*Поиск по ID*/
//		TicketDao ticketDao = TicketDao.getInstance();
//		Optional<Ticket> maybeTicket = ticketDao.findById(2L);
//		System.out.println(maybeTicket);
//		/*Обновление*/
//		maybeTicket.ifPresent(ticket -> {
//			ticket.setCost(BigDecimal.valueOf(188.88));
//			ticketDao.update(ticket);
//		});
		/*Поиск всех*/
//		TicketDao ticketDao = TicketDao.getInstance();
//		List<Ticket> tickets = ticketDao.findAll();
//		for (Ticket i : tickets) {
//			System.out.println(i);
//		}
		/*Поиск всех по фильтру*/
//		TicketDao ticketDao = TicketDao.getInstance();
//		TicketFilter ticketFilter = new TicketFilter(10, 2);
//		List<Ticket> tickets = ticketDao.findAll(ticketFilter);
//		for (Ticket i : tickets) {
//			System.out.println(i);
//		}
		/*Добавили сущность Flight*/
		/*Insert*/
		Optional<Ticket> ticket = TicketDao.getInstance().findById(5L);
		System.out.println(ticket);
		
		
	}
}
