package query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionUtil_Basic;

public class SimpleQuery {
	
	public void getQuery() {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(generateQuery());
				ResultSet resultSet = preparedStatement.executeQuery()) {
	
			System.out.println("Radnici: ");
			System.out.printf("%-4s %-8s %-10s %-10s %-2s\n", "ID", "IME", "PREZIME", "PLATA", "GS");
	
			while (resultSet.next()) {
				int idrad = resultSet.getInt("idrad");
				String ime = resultSet.getString(2);
				String prezime = resultSet.getString("prezime");
				double plata = resultSet.getDouble("plata");
				int gsrad = resultSet.getInt("gs");
	
				System.out.printf("%-4d %-8.8s %-10s %-10.2f %-2d\n", idrad, ime, prezime, plata, gsrad);
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


    // select workers whose surname contains letter e or is another letter in the last name a
	private static String generateQuery() {
		String query = "select idrad, ime, prz prezime, plt plata, gsrad gs"
						+ " from radnik"
						+ " where prz like '_a%' or prz like '%E%' or prz like '%e%' order by prz asc";
	
		return query;
	}
}
