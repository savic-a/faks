package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionUtil_Basic;

public class Selecting {
	
	public static void selectDataFromGs() throws SQLException {
		String query = "select * from gs";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			System.out.printf("%-5s %-10s\n", "\nID", "NAZIV");

			while (resultSet.next()) {
				System.out.printf("%-4d %-10s\n", resultSet.getInt(1), resultSet.getString(2));
			}
		}
	}
	
	public static void selectDataFromSkiCentar() throws SQLException {
		String query = "select idski, nazski, telski, nvl(emailski, 'ne postoji'), gsski from skiCentar";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			System.out.printf("%-5s %-15s %-15s %-20s %-4s\n", "\nID", "NAZIV", "TEL", "EMAIL", "GS");

			while (resultSet.next()) {
				System.out.printf("%-4s %-15s %-15s %-20s %-4s\n", resultSet.getInt(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
			}
		}
	}
	
	public static void selectDataFromStaza() {
		String query = "select * from staza";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			System.out.printf("%-5s %-20s %-10s %-5s\n", "\nID", "NAZIV", "DUZINA", "TIP");

			while (resultSet.next()) {
				System.out.printf("%-4s %-20s %-10s %-5s\n", resultSet.getInt(1), resultSet.getString(2),
						resultSet.getInt(3), resultSet.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void selectDataFromRadnik() {
		String query = "select * from radnik";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			System.out.printf("%-5s %-10s %-10s %-10s %-4s\n", "\nID", "IME", "PRZ", "PLT", "GS");

			while (resultSet.next()) {
				System.out.printf("%-4d %-10s %-10s %-10.2f %-4d\n", resultSet.getInt(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getDouble(4), resultSet.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
