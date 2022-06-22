package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import connection.ConnectionUtil_Basic;

public class Modifying {
	static Scanner sc = new Scanner(System.in);
	
	public static void modifyRowintoGs() throws SQLException {
		Selecting.selectDataFromGs();
		
		String command = "update gs set nazgs=? where idgs=?";

		System.out.print("\nIDGS: ");
		String idgs = sc.nextLine();
		System.out.print("NAZGS: ");
		String nazgs = sc.nextLine().toUpperCase();

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			preparedStatement.setString(1, nazgs);
			preparedStatement.setInt(2, Integer.parseInt(idgs));

			System.out.printf("%d row affected by update!\n", preparedStatement.executeUpdate());
		}
	}
	
	public static void modifyRowintoSkiCentar() throws SQLException {
		Selecting.selectDataFromSkiCentar();
		
		String command = "update skiCentar set nazski=?, telski=?, emailski=?, gsski=? where idski=?";

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

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			preparedStatement.setString(1, nazski);
			preparedStatement.setString(2, telski);
			preparedStatement.setString(3, emailski);
			preparedStatement.setInt(4, Integer.parseInt(gsski));
			preparedStatement.setInt(5, Integer.parseInt(idski));

			System.out.printf("%d row affected by update!\n", preparedStatement.executeUpdate());
		}
	}
	
	public static void modifyRowintoStaza() throws SQLException {
		Selecting.selectDataFromStaza();
		
		String command = "update staza set nazst=?, duzst=?, tip=? where idst=?";

		System.out.print("\nIDST: ");
		String idst = sc.nextLine();
		System.out.print("NAZST: ");
		String nazst = sc.nextLine();
		nazst = nazst.substring(0, 1).toUpperCase() + nazst.substring(1);
		System.out.print("DUZST: ");
		String duzst = sc.nextLine();
		System.out.print("TIP: ");
		String tip = sc.nextLine().toLowerCase();

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			preparedStatement.setString(1, nazst);			
			preparedStatement.setInt(2, Integer.parseInt(duzst));
			preparedStatement.setString(3, tip);
			preparedStatement.setInt(4, Integer.parseInt(idst));

			System.out.printf("%d row affected by update!\n", preparedStatement.executeUpdate());
		}
	}
	
	public static void modifyRowintoRadnik() throws SQLException {
		Selecting.selectDataFromRadnik();
		
		String command = "update radnik set ime=?, prz=?, plt=?, gsrad=? where idrad=?";

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

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			preparedStatement.setString(1, ime);	
			preparedStatement.setString(2, prz);	
			preparedStatement.setDouble(3, Double.parseDouble(plt));
			preparedStatement.setInt(4, Integer.parseInt(gsrad));
			preparedStatement.setInt(5, Integer.parseInt(idrad));

			System.out.printf("%d row affected by update!\n", preparedStatement.executeUpdate());
		}
	}
}
