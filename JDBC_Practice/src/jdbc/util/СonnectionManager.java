package jdbc.util;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class СonnectionManager {
	/*Стандартный вариант менеджера*/
//	private static final String password_key = "db.password";
//	private static final String username_key = "db.username";
//	private static final String url_key = "db.url";
//	
//	private СonnectionManager() {
//		
//	}
//	
//	public static Connection open() {
//		try {
//			return DriverManager.getConnection(PropertiesUtil.get(url_key),
//											   PropertiesUtil.get(username_key),
//											   PropertiesUtil.get(password_key));
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	/*Менеджер, переписанный с учетом работы с пулом соединений*/
	private static final String password_key = "db.password";
	private static final String username_key = "db.username";
	private static final String url_key = "db.url";
	private static final String pool_size_key = "db.pool.size";
	private static final Integer default_pool_size = 10;
	private static BlockingQueue<Connection> pool;
	private static List<Connection> sourceConnections;
	static {
		loadDriver();
		inintConnectionPool();
	}
	private СonnectionManager() {
	
	}
	private static void inintConnectionPool() {
		String poolSize = PropertiesUtil.get(pool_size_key);
		int size = poolSize == null ? default_pool_size : Integer.parseInt(poolSize);
		pool = new ArrayBlockingQueue<Connection>(size);
		sourceConnections = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Connection connection = open();
			Connection proxyConnection = (Connection) 
					Proxy.newProxyInstance(СonnectionManager.class.getClassLoader(), new Class[] {Connection.class},
					(proxy, method, args) -> method.getName().equals("close")
						? pool.add((Connection) proxy)
						: method.invoke(connection, args));		
			pool.add(proxyConnection);
			sourceConnections.add(connection);
		}
	}
	public static Connection get() {
		try {
			return pool.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	private static Connection open() {
		try {
			return DriverManager.getConnection(PropertiesUtil.get(url_key),
											   PropertiesUtil.get(username_key),
											   PropertiesUtil.get(password_key));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	private static void loadDriver() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL Driver Problem");
			throw new RuntimeException(e);
		}
	}
	public static void closePool() {
		try {
			for (Connection sourceConnection : sourceConnections) {
				sourceConnection.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
