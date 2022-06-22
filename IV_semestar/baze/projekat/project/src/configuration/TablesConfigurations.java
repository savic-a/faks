package configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectionUtil_Basic;

public class TablesConfigurations {
	private static String tablesFile = "tables.sql";
	private static String dataFile = "data.sql";
	private static String dropTablesFile = "drop_tables.sql";

	public TablesConfigurations() {
			ReportUtil.baseResourcePath = "resources/";
			
			dropTables();
			System.out.println("obrisano");

			createTables();
			System.out.println("kreirano");

			populateData();
			System.out.println("dodato");
		}

		private static void createTables() {
			try {
				String[] commands = ReportUtil.getCommandsFromFile(tablesFile);
				ReportUtil.executeCommands(commands);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		private static void populateData() {
			try (Connection connection = ConnectionUtil_Basic.getConnection();
					Statement statement = connection.createStatement()) {
				connection.setAutoCommit(false);
				String[] commands = ReportUtil.getCommandsFromFile(dataFile);

				try {
					for (String command : commands) {
						statement.execute(command);
					}
					connection.commit();
				} catch (SQLException e) {
					connection.rollback();
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

		private static void dropTables() {
			try {
				String[] commands = ReportUtil.getCommandsFromFile(dropTablesFile);
				ReportUtil.executeCommands(commands);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}