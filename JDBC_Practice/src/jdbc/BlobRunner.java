package jdbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.util.СonnectionManager;

public class BlobRunner {
	public static void main(String[] args) throws SQLException, IOException {
		
		//saveImage();
		getInamge();
	}
	
//	public static void saveImage() throws SQLException, IOException{
//		/*Blob и Clob не имеют реализации в PostgreSQL, поэтому для этой СУБД данный код не релевантен*/
//		String sql = "update aircraft set image = ? where id = 1";
//		try(Connection connection = СonnectionManager.open();
//			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//			connection.setAutoCommit(false);
//			Blob blobObject = connection.createBlob();
//			blobObject.setBytes(1, Files.readAllBytes(Path.of("resources", "Boeing777.jpg")));
//			preparedStatement.setBlob(1, blobObject);
//			preparedStatement.executeUpdate();
//			connection.commit();
//		}
//	}
	
	/*Вариант для PostgreSQL*/
	public static void saveImage() throws SQLException, IOException{
		String sql = "update aircraft set image = ? where id = 1";
		try(Connection connection = СonnectionManager./*open()*/get();
			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "Boeing777.jpg")));
			preparedStatement.executeUpdate();
		}
	}
	
	/*Напишем метод для получения картинки*/
	public static void getInamge() throws SQLException, IOException {
		String sql = "select image from aircraft where id = ?";
		try(Connection connection = СonnectionManager./*open()*/get();
			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				byte[] image = resultSet.getBytes("image");
				Files.write(Path.of("resources", "Boeing777_new.jpg"), image, StandardOpenOption.CREATE);
			}
		}
	}

}
