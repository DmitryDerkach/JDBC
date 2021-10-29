package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jdbc.dto.TicketFilter;
import jdbc.entity.Flight;
import jdbc.entity.Ticket;
import jdbc.exception.DaoException;
import jdbc.util.СonnectionManager;

public class TicketDao implements Dao<Long, Ticket>{
	private static final TicketDao INSTANCE = new TicketDao();
	private static final String DELETE_SQL = 
			"delete from ticket where id = ?";
	private static final String SAVE_SQL = 
			"insert into ticket(passenger_num, passenger_name, flight_id, seat_num, cost)"
			+ "values (?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL =
			"update ticket set "
			+ "passenger_num = ?, "
			+ "passenger_name = ?, "
			+ "flight_id = ?, "
			+ "seat_num = ?, "
			+ "cost = ? "
			+ "where id = ? ";
	
	private static final String FIND_ALL_SQL = 
			"select "
			+ " ticket.id,"
			+ " passenger_num, "
			+ " passenger_name, "
			+ " flight_id, "
			+ " seat_num, "
			+ " cost, "
			+ " f.flight_num, "
			+ " f.status, "
			+ " f.aircraft_id, "
			+ " f.arrival_airport_code, "
			+ " f.arrival_date, "
			+ " f.departure_airport_code, "
			+ " f.departure_date "
			+ " from ticket "
			+ " join flight f on ticket.flight_id = f.id";
	
	private static final String FIND_BY_ID = FIND_ALL_SQL + " where ticket.id = ?";

	private final FlightDao flightDao = FlightDao.getInstance();
	
	private TicketDao() {
		
	}

	public List<Ticket> findAll(TicketFilter filter) {
		List<Object> parametrs = new ArrayList<Object>();
		parametrs.add(filter.getLimit());
		parametrs.add(filter.getOffset());
		String sql = FIND_ALL_SQL + " limit ? offset ? ";
		try(Connection connection = СonnectionManager.get();
			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			/*Т.к. установка параметров в prepSt начинается с 1, а не с 0, мы должны будем прибавлять единичку тогда,
			 *когда мы устанавливаем какой-то объект*/
			for (int i = 0; i < parametrs.size(); i++) {
				preparedStatement.setObject(i + 1, parametrs.get(i));
				preparedStatement.setObject(i + 1, parametrs.get(i));
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Ticket> tickets = new ArrayList<Ticket>();
			while (resultSet.next()) {
				tickets.add(buildTicket(resultSet));
			}
			return tickets;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	
	public List<Ticket> findAll() {
		try(Connection connection = СonnectionManager.get();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)){
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Ticket> tickets = new ArrayList<Ticket>();
			while (resultSet.next()) {
				tickets.add(buildTicket(resultSet));
			}
			return tickets;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	private Ticket buildTicket(ResultSet resultSet) throws SQLException{
		Flight flight = new Flight(
				resultSet.getLong("flight_id"),
				resultSet.getString("flight_num"),
				resultSet.getTimestamp("departure_date").toLocalDateTime(),
				resultSet.getString("departure_airport_code"),
				resultSet.getTimestamp("arrival_date").toLocalDateTime(),
				resultSet.getString("arrival_airport_code"),
				resultSet.getInt("aircraft_id"),
				resultSet.getString("status")
				);
		return new Ticket(
				resultSet.getLong("id"),
				resultSet.getString("passenger_num"),
				resultSet.getString("passenger_name"),
				flightDao.findById(resultSet.getLong("flight_id")).orElse(null),
				resultSet.getString("seat_num"),
				resultSet.getBigDecimal("cost")
		);
	}

	/*Если есть шанс словить null, возвращаем optional*/
	public Optional<Ticket> findById(Long id) {
		try (Connection connection = СonnectionManager.get();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			Ticket ticket = null;
			if (resultSet.next()) {
				Flight flight = new Flight(
						resultSet.getLong("flight_id"),
						resultSet.getString("flight_num"),
						resultSet.getTimestamp("departure_date").toLocalDateTime(),//+
						resultSet.getString("departure_airport_code"),
						resultSet.getTimestamp("arrival_date").toLocalDateTime(),
						resultSet.getString("arrival_airport_code"),
						resultSet.getInt("aircraft_id"),
						resultSet.getString("status")
						);
				ticket = new Ticket(
						resultSet.getLong("id"),
						resultSet.getString("passenger_num"),
						resultSet.getString("passenger_name"),
						flight,
						resultSet.getString("seat_num"),
						resultSet.getBigDecimal("cost") //+
				);
			}
			return Optional.ofNullable(ticket);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	public void update(Ticket ticket) {
		try(Connection connection = СonnectionManager.get();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)){
			preparedStatement.setString(1, ticket.getPassengerNum());
			preparedStatement.setString(2, ticket.getPassengerName());
			preparedStatement.setLong(3, ticket.getFlightId().getId());
			preparedStatement.setString(4, ticket.getSeatNum());
			preparedStatement.setBigDecimal(5, ticket.getCost());
			preparedStatement.setLong(6, ticket.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	public Ticket save(Ticket ticket) {
		try(Connection connection = СonnectionManager.get();
			PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
			preparedStatement.setString(1, ticket.getPassengerNum());
			preparedStatement.setString(2, ticket.getPassengerName());
			preparedStatement.setLong(3, ticket.getFlightId().getId());
			preparedStatement.setString(4, ticket.getSeatNum());
			preparedStatement.setBigDecimal(5, ticket.getCost());
			
			preparedStatement.executeUpdate();
			/*Получаем идентификатор вновь созданной сущности, т.к. очень часто нам нужно
			 *работать с ней в нашем слое сервисов*/
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				ticket.setId(generatedKeys.getLong("id"));
			}
			
			return ticket;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	public boolean delete(Long id) {
		try (Connection connection = СonnectionManager.get();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)){
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public static TicketDao getInstance() {
		return INSTANCE;
	}
	
}
