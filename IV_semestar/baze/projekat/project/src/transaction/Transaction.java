package transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import connection.ConnectionUtil_Basic;
import crud.Selecting;

public class Transaction {
	
	public void doTransaction() throws SQLException
	{
		// unsuccessfully
		System.out.println("\nTABLE BEFORE UPDATE");
		Selecting.selectDataFromRadnik();
		
		executeTransactional("update radnik set plt = plt * 0.5", "update staza set duzst = duzst * 0.2");
		
		System.out.println("\nTABLE AFTER UPDATE");
		Selecting.selectDataFromRadnik();
		
		System.out.println("=========================================");
		
		// successfully
		System.out.println("\nTABLE BEFORE UPDATE");
		Selecting.selectDataFromRadnik();
		
		executeTransactional("update radnik set plt = plt * 0.5", "update staza set duzst = duzst * 0.5");
		
		System.out.println("\nTABLE AFTER UPDATE");
		Selecting.selectDataFromRadnik();
		
	}
	
	private static void executeTransactional(String... commands) {
		try (Connection connection = ConnectionUtil_Basic.getConnection()) {
			connection.setAutoCommit(false); // disable auto-commit (enabled by default)

			try {
				for (String command : commands) {
					PreparedStatement preparedStatement = connection.prepareStatement(command);
					preparedStatement.execute(command);
				}
			} catch (SQLException e) {
				System.out.println("it's not possible to update the data");
				connection.rollback(); // if one of the commands fails, roll-back changes
			}

			connection.commit(); // if all commands execute successfully, commit changes
			
		} catch (SQLException e) {
			System.out.println("successful updata");
		}
	}
}
