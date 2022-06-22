package query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connection.ConnectionUtil_Basic;

public class QueryWithParams {

	private Scanner sc;
	
	public QueryWithParams(Scanner sc) {
		this.sc = sc;
	}
	
	public void getQuery() {

		String query = "select od.idrad, r.ime, r.prz, count(od.idst) BROJ_STAZA"
				+ " from odrzavanje od, radnik r"
				+ " where r.idrad = od.idrad"
				+ " group by od.idrad, r.ime, r.prz"
				+ " having count(od.idst)>=?";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			String answer;

			do {
				System.out.println("\nChoose option:");
				System.out.println("1 - Input value");
				System.out.println("X - Exit");

				answer = sc.nextLine();

				if (answer.equalsIgnoreCase("1")) {
					selectRadnikByImeAndPrz(sc, preparedStatement);
				}

			} while (!answer.equalsIgnoreCase("X"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void selectRadnikByImeAndPrz(Scanner sc, PreparedStatement preparedStatement) throws SQLException {
		System.out.print("\nInput minimum number of ski slopes: ");
		try {
			preparedStatement.setInt(1, Integer.parseInt(sc.nextLine()));
		} catch(Exception e) {
			preparedStatement.setInt(1, 1);  // default number of ski slopes is 1
		}

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			if (!resultSet.isBeforeFirst()) {
				System.out.println("No data found!");
			} else {
				System.out.printf("%-4s %-10s %-10s %-4s\n", "ID", "IME", "PREZIME", "BROJ_STAZA");

				while (resultSet.next()) {
					int idrad = resultSet.getInt(1);
					String ime = resultSet.getString(2);
					String prezime = resultSet.getString(3);
					int staze = resultSet.getInt(4);
					System.out.printf("%-4d %-10s %-10s %-4d\n", idrad, ime, prezime, staze);
				}
			}
		}
	}
}
