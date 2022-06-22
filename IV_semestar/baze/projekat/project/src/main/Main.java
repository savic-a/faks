package main;

import java.sql.SQLException;

import configuration.TablesConfigurations;

public class Main {

	public static void main(String[] args) throws SQLException {
		TablesConfigurations configuration = new TablesConfigurations();  // creating tables
		Menu menu = new Menu();
		menu.mainMenu();
	}

}
