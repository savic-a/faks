package query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionUtil_Basic;

public class Merger {
	
	public void getQuery() {
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(mergeTable());
				ResultSet resultSet = preparedStatement.executeQuery()) {
	
			System.out.println();
			System.out.printf("%-8s %-10s %-8s %-10s\n", "IME", "PREZIME", "GS", "STAZA");
	
			while (resultSet.next()) {
				String ime = resultSet.getString(1);
				String prezime = resultSet.getString("prezime");
				String gs = resultSet.getString("gs");
				String staza = resultSet.getString("staza");
	
				System.out.printf("%-8s %-10s %-8s %-10s\n", ime, prezime, gs, staza);
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// returns the name of the gs in which the employees who maintain one of the tracks work
	private static String mergeTable() {
		String query = "select r.ime, r.prz prezime, gs.nazgs GS, s.nazst STAZA"
				+ " from radnik r, gs, staza s, odrzavanje od"
				+ " where r.gsrad=gs.idgs and r.idrad=od.idrad and od.idst=s.idst"
				+ " order by r.ime";

		return query;	
	}
}
