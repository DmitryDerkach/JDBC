package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import jdbc.entity.Flight;
import jdbc.exception.DaoException;
import jdbc.util.СonnectionManager;

public class FlightDao implements Dao<Long, Flight>{
	private static final FlightDao INSTANSE = new FlightDao();
	private static final String FIND_BY_ID_SQL = 
			"select "
			+ "id, "
			+ "flight_num, "
			+ "departure_date,"
			+ "departure_airport_code, "
			+ "arrival_date, "
			+ "arrival_airport_code, "
			+ "status, "
			+ "aircraft_id "
			+ "from flight where id = ?";
	private FlightDao() {
		
	}
	public static FlightDao getInstance() {
		return INSTANSE;
	}
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Flight save(Flight inputData) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update(Flight inputData) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Optional<Flight> findById(Long id) {
		try(Connection connection = СonnectionManager.get();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)){
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			Flight flight = null;
			if (resultSet.next()) {
				flight = new Flight(
					resultSet.getLong("id"),
					resultSet.getString("flight_num"),
					resultSet.getTimestamp("departure_date").toLocalDateTime(),
					resultSet.getString("departure_airport_code"),
					resultSet.getTimestamp("arrival_date").toLocalDateTime(),
					resultSet.getString("arrival_airport_code"),
					resultSet.getInt("aircraft_id"),
					resultSet.getString("status")
				); 	
			}
			return Optional.ofNullable(flight);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Flight> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
