package main;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

import crud.Deleting;
import crud.Inserting;
import crud.Modifying;
import query.Merger;
import query.QueryWithParams;
import query.SimpleQuery;
import report.Report;
import transaction.Transaction;

public class Menu {
	
	static Scanner sc = new Scanner(System.in);
	SimpleQuery query = new SimpleQuery(); 
	Merger merger = new Merger();
	QueryWithParams queryParams = new QueryWithParams(sc);
	Report report = new Report();
	Transaction transaction = new Transaction();
	
	public void mainMenu() throws SQLException
	{
		String response = null;
		
		do {
			System.out.println("\nChoose option:");
			System.out.println("1 - Insert data");
			System.out.println("2 - Modify data");
			System.out.println("3 - Delete data");
			System.out.println("4 - Simple query");
			System.out.println("5 - Merge tables");
			System.out.println("6 - Query with params");
			System.out.println("7 - Transaction");
			System.out.println("8 - Reports");
			System.out.println("X - Exit");

			response = sc.nextLine();

			switch (response) {
			case "1":
				insertMenu();
				break;
			case "2":
				modifyMenu();
				break;
			case "3":
				deleteMenu();
				break;
			case "4":
				query.getQuery();
				break;
			case "5":
				merger.getQuery();
				break;
			case "6":
				queryParams.getQuery();
				break;
			case "7":
				transaction.doTransaction();
				break;
			case "8":
				reportMenu();
				break;
			}

		} while (!response.equalsIgnoreCase("X"));
	}
	
	private void reportMenu() {
		String response = null;
		do {
			System.out.println("\nChoose option:");
			System.out.println("1 - Total salary of employees on ski slopes");
			System.out.println("2 - Number of staffs of the mountain service in the ski center");
			System.out.println("X - Exit");

			response = sc.nextLine();

			switch (response) {
			case "1":
				report.generateFirstReport();
				break;
			case "2":
				report.generateSecondReport();
				break;
			} 

		} while (!response.equalsIgnoreCase("X"));
		
	}

	private void deleteMenu() {
		String response = null;
		do {
			System.out.println("\nChoose option:");
			System.out.println("1 - Delete existing row from gs");
			System.out.println("2 - Delete existing row from skiCentar");
			System.out.println("3 - Delete existing row from staza");
			System.out.println("4 - Delete existing row from radnik");
			System.out.println("X - Exit");

			response = sc.nextLine();

			try {
				switch (response) {
				case "1":
					Deleting.deleteRowFromGs();
					break;
				case "2":
					Deleting.deleteRowFromSkiCentar();
					break;
				case "3":
					Deleting.deleteRowFromStaza();
					break;
				case "4":
					Deleting.deleteRowFromRadnik();
					break;
				}
			}
			catch (SQLIntegrityConstraintViolationException e)
			{
				System.out.println("the row cannot be deleted!");
			}
			catch (SQLException e) {
				System.out.println("the row cannot be deleted!");
			} 

		} while (!response.equalsIgnoreCase("X"));
		
	}

	private void modifyMenu() {
		String response = null;
		do {
			System.out.println("\nChoose option:");
			System.out.println("1 - Modify existing row from gs");
			System.out.println("2 - Modify existing row from skiCentar");
			System.out.println("3 - Modify existing row from staza");
			System.out.println("4 - Modify existing row from radnik");
			System.out.println("X - Exit");

			response = sc.nextLine();

			try {
				switch (response) {
				case "1":
					Modifying.modifyRowintoGs();
					break;
				case "2":
					Modifying.modifyRowintoSkiCentar();
					break;
				case "3":
					Modifying.modifyRowintoStaza();
					break;
				case "4":
					Modifying.modifyRowintoRadnik();
					break;
				}
			} catch (SQLException e) {
				System.out.println("the row cannot be modified!");
			}

		} while (!response.equalsIgnoreCase("X"));
		
	}

		private void insertMenu() {
		String response = null;
		do {
			System.out.println("\nChoose option:");
			System.out.println("1 - Insert row into gs");
			System.out.println("2 - Insert row into skiCentar");
			System.out.println("3 - Insert row into staza");
			System.out.println("4 - Insert row into radnik");
			System.out.println("X - Exit");

			response = sc.nextLine();

			try {
				switch (response) {
				case "1":
					Inserting.insertRowInGs();
					break;
				case "2":
					Inserting.insertRowInSkiCentar();
					break;
				case "3":
					Inserting.insertRowInStaza();
					break;
				case "4":
					Inserting.insertRowInRadnik();
					break;
				}
			} catch (SQLException e) {
				System.out.println("the row cannot be inserted!");
			}

		} while (!response.equalsIgnoreCase("X"));	
	}
}
