package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import connection.ConnectionUtil_Basic;

public class Deleting {
	static Scanner sc = new Scanner(System.in);
	
	public static void deleteRowFromGs() throws SQLException
	{
		Selecting.selectDataFromGs();
		System.out.print("\nIDGS: ");
		String idgs = sc.nextLine();
		String command = "delete from gs where idgs=?";
		tryDelete(idgs, command, true);
	}

	public static void deleteRowFromSkiCentar() throws SQLException
	{
		Selecting.selectDataFromSkiCentar();
		System.out.print("\nIDSKI: ");
		String idski = sc.nextLine();
		String command = "delete from skiCentar where idski=?";
		tryDelete(idski, command, true);
	}
	
	public static void deleteRowFromStaza() throws SQLException
	{
		Selecting.selectDataFromStaza();
		System.out.print("\nIDST: ");
		String idst = sc.nextLine();
		deleteRowFromOdrzavanjeForStaza(idst);
		String command = "delete from staza where idst=?";
		tryDelete(idst, command, true);
	}

	public static void deleteRowFromRadnik() throws SQLException
	{
		Selecting.selectDataFromRadnik();
		System.out.print("\nIDRAD: ");
		String idrad = sc.nextLine();
		deleteRowFromOdrzavanjeForRadnik(idrad);
		String command = "delete from radnik where idrad=?";
		tryDelete(idrad, command, true);
	}
	
	public static void deleteRowFromOdrzavanjeForStaza(String id) throws SQLException
	{
		String command = "delete from odrzavanje where idst=?";
		tryDelete(id, command, false);
	}
	
	public static void deleteRowFromOdrzavanjeForRadnik(String id) throws SQLException
	{
		String command = "delete from odrzavanje where idrad=?";
		tryDelete(id, command, false);
	}
	
	private static void tryDelete(String value, String command, Boolean print) throws SQLException
	{
		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(command);) {
			int id = 0;
			
			try {
				id = Integer.parseInt(value);  // if input is invalid
			}catch (Exception e) {
				System.out.println("invalid input!");
				return;
			}
			
			preparedStatement.setInt(1,id);
			
			if(print)
				System.out.printf("%d row affected by delete!\n", preparedStatement.executeUpdate());	
			else
				preparedStatement.executeUpdate();
		}	
	}
}
