package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.util.СonnectionManager;

public class TransactionRunner {
	public static void main(String[] args) throws SQLException {	
		long flightId = 8;
		String deleteFlightSql = "delete from flight where id = " + flightId;
		String deleteTicketsSql = "delete from ticket where flight_id = " + flightId;
		Connection connection = null;
//		PreparedStatement deleteFlightStatement = null;
//		PreparedStatement deleteTicketsStatement = null;
		Statement statement = null;
		try {
			connection = СonnectionManager./*open()*/get();
			connection.setAutoCommit(false);
//			deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
//			deleteTicketsStatement = connection.prepareStatement(deleteTicketsSql);
			statement = connection.createStatement();	
			statement.addBatch(deleteTicketsSql);
			statement.addBatch(deleteFlightSql);
			int[] executeResult = statement.executeBatch();
//			deleteFlightStatement.setLong(1, flightId);
//			deleteTicketsStatement.setLong(1, flightId);
			
			//int deletedTicketsResult = deleteTicketsStatement.executeUpdate();
//			if (true) {
//				throw new RuntimeException("Oops");
//			}
//			int deletedFlightResult = deleteFlightStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback();
			}
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
	}
}
