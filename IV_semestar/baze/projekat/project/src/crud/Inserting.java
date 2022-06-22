package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import connection.ConnectionUtil_Basic;

public class Inserting {
	static Scanner sc = new Scanner(System.in);
	
	public static void insertRowInGs() throws SQLException {
		Selecting.selectDataFromGs();
		System.out.print("\nIDGS: ");
		String idgs = sc.nextLine();
		System.out.print("NAZGS: ");
		String nazgs = sc.nextLine().toUpperCase();

		String command = "insert into gs (idgs, nazgs) values (?, ?)";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			preparedStatement.setInt(1, Integer.parseInt(idgs));
			preparedStatement.setString(2, nazgs);
			preparedStatement.executeUpdate();
			System.out.println("Row sucessfully inserted");
		}
	}
	
	public static void insertRowInSkiCentar() throws SQLException {
		Selecting.selectDataFromSkiCentar();
		System.out.print("\nIDSKI: ");
		String idski = sc.nextLine();
		System.out.print("NAZSKI: ");
		String nazski = sc.nextLine();
		nazski = nazski.substring(0, 1).toUpperCase() + nazski.substring(1);
		System.out.print("TELSKI: ");
		String telski = sc.nextLine();
		System.out.print("EMAILSKI: ");
		String emailski = sc.nextLine();
		System.out.print("GSSKI: ");
		String gsski = sc.nextLine();
		
		String command = "insert into skiCentar (idski, nazski, telski, emailski, gsski) values (?, ?, ?, ?, ?)";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			preparedStatement.setInt(1, Integer.parseInt(idski));
			preparedStatement.setString(2, nazski);
			preparedStatement.setString(3, telski);
			preparedStatement.setString(4, emailski);
			preparedStatement.setInt(5, Integer.parseInt(gsski));
			preparedStatement.executeUpdate();
			System.out.println("Row sucessfully inserted");
		}
	}
	
	public static void insertRowInStaza() {
		Selecting.selectDataFromStaza();
		System.out.print("\nIDST: ");
		String idst = sc.nextLine();
		System.out.print("NAZST: ");
		String nazst = sc.nextLine();
		nazst = nazst.substring(0, 1).toUpperCase() + nazst.substring(1);
		System.out.print("DUZST: ");
		String duzst = sc.nextLine();
		System.out.print("TIP: ");
		String tip = sc.nextLine().toLowerCase();
		
		Selecting.selectDataFromRadnik();
		System.out.print("\nIDRAD: ");
		String idrad = sc.nextLine();
		System.out.print("BRC: ");
		String brc = sc.nextLine();
		
		String commandSki = "insert into staza (idst, nazst, duzst, tip) values (?, ?, ?, ?)";
		String commandOd = "insert into odrzavanje (idrad, idst, brc) values (?, ?, ?)";
		
		try (Connection connection = ConnectionUtil_Basic.getConnection()) {
			connection.setAutoCommit(false); // disable auto-commit (enabled by default)

			try {
					PreparedStatement preparedStatement = connection.prepareStatement(commandSki);
					preparedStatement.setInt(1, Integer.parseInt(idst));
					preparedStatement.setString(2, nazst);			
					preparedStatement.setInt(3, Integer.parseInt(duzst));
					preparedStatement.setString(4, tip);
					preparedStatement.execute();
					preparedStatement = connection.prepareStatement(commandOd);
					preparedStatement.setInt(1, Integer.parseInt(idrad));
					preparedStatement.setInt(2, Integer.parseInt(idst));
					preparedStatement.setInt(3, Integer.parseInt(brc));
					preparedStatement.execute();
			} catch (SQLException e) {
				System.out.println("it's not possible to update the data");
				connection.rollback(); // if one of the commands fails, roll-back changes
			}

			connection.commit(); // if all commands execute successfully, commit changes
			
		} catch (SQLException e) {
			System.out.println("successful updata");
		}
	}
	
	public static void insertRowInRadnik() throws SQLException {
		Selecting.selectDataFromRadnik();
		System.out.print("\nIDRAD: ");
		String idrad = sc.nextLine();
		System.out.print("IME: ");
		String ime = sc.nextLine();
		ime = ime.substring(0, 1).toUpperCase() + ime.substring(1);
		System.out.print("PRZ: ");
		String prz = sc.nextLine();
		prz = prz.substring(0, 1).toUpperCase() + prz.substring(1);
		System.out.print("PLT: ");
		String plt = sc.nextLine();
		System.out.print("GSRAD: ");
		String gsrad = sc.nextLine();
		
		String command = "insert into radnik (idrad, ime, prz, plt, gsrad) values (?, ?, ?, ?, ?)";

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			preparedStatement.setInt(1, Integer.parseInt(idrad));
			preparedStatement.setString(2, ime);	
			preparedStatement.setString(3, prz);	
			preparedStatement.setDouble(4, Double.parseDouble(plt));
			preparedStatement.setInt(5, Integer.parseInt(gsrad));
			preparedStatement.executeUpdate();
			System.out.println("Row sucessfully inserted");
		}	
	}
}
