package report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionUtil_Basic;
import entitie.SkiCenter;
import entitie.SkiSlope;
import entitie.Staff;

public class Report {
	
	// for each employee, his total salary is calculated
	// for each ski slope, the sum of the salaries of the staffs who maintain it is calculated
	public void generateFirstReport() {
		final String getSkiSlopes = "select idst, nazst, duzst, tip from staza order by nazst";
		final String getStaff = "select r.idrad, ime, prz, plt+brc*100 ukupna_plata"
				+ " from radnik r left outer join odrzavanje od on r.idrad = od.idrad"
				+ " where od.idst = ?"
				+ " order by ukupna_plata desc";

		List<SkiSlope> extendedSkiSlopes = new ArrayList<>();

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement psSkiSlopes = connection.prepareStatement(getSkiSlopes);
				PreparedStatement psStaffs = connection.prepareStatement(getStaff);) {

			ResultSet rsSkiSlopes = psSkiSlopes.executeQuery(); 

			if (!rsSkiSlopes.isBeforeFirst()) {
				System.out.println("No matching ski slope items!");
				return;
			}

			while (rsSkiSlopes.next()) {
				int idst = rsSkiSlopes.getInt("idst");
				String title = rsSkiSlopes.getString("nazst");
				int length = rsSkiSlopes.getInt("duzst");
				String type = rsSkiSlopes.getString("tip");
				
				SkiSlope extendedSkiSlope = new SkiSlope(idst, title, length, type);
				extendedSkiSlopes.add(extendedSkiSlope);

				psStaffs.setInt(1, idst);
				ResultSet rsStaffs = psStaffs.executeQuery();

				if (!rsStaffs.isBeforeFirst()) {
					continue;
				}

				int totalStaffSalaryOnSkiSlopes = 0;

				while (rsStaffs.next()) {
					int id = rsStaffs.getInt(1);
					String name = rsStaffs.getString(2);
					String surname = rsStaffs.getString(3);
					int salaryForStaff = rsStaffs.getInt(4);
					extendedSkiSlope.getExtendedStaffs().add(new Staff(id, name, surname, salaryForStaff));

					totalStaffSalaryOnSkiSlopes += salaryForStaff;
				}

				extendedSkiSlope.setTotalPrice(totalStaffSalaryOnSkiSlopes);
			}
			System.out.println(extendedSkiSlopes);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// for each ski center, the number of workers employed in the mountain service that authorized it was calculated
	public void generateSecondReport() {
		final String getSkiCenters = "select idski, nazski, telski, "
				+ "nvl(emailski, 'does not have') email, gsski "
				+ "from skiCentar order by nazski";
		final String getStaff = "select r.idrad, ime, prz"
				+ " from gs left outer join radnik r on idgs = r.gsrad"
				+ " where idgs = ?";
		
		List<SkiCenter> extendedSkiCenters = new ArrayList<>();

		try (Connection connection = ConnectionUtil_Basic.getConnection();
				PreparedStatement psSkiCenters = connection.prepareStatement(getSkiCenters);
				PreparedStatement psStaffs = connection.prepareStatement(getStaff);) {

			ResultSet rsSkiCenters = psSkiCenters.executeQuery(); 

			if (!rsSkiCenters.isBeforeFirst()) {
				System.out.println("No matching ski slope items!");
				return;
			}

			while (rsSkiCenters.next()) {
				int idst = rsSkiCenters.getInt("idski");
				String title = rsSkiCenters.getString("nazski");
				String tel = rsSkiCenters.getString("telski");
				String email = rsSkiCenters.getString("email");
				int gsski = rsSkiCenters.getInt("gsski");
				
				SkiCenter extendedSkiCenter = new SkiCenter(idst, title, tel, email);
				extendedSkiCenters.add(extendedSkiCenter);

				psStaffs.setInt(1, gsski);
				ResultSet rsStaffs = psStaffs.executeQuery();

				if (!rsStaffs.isBeforeFirst()) {
					continue;
				}

				int staffsNumber = 0;

				while (rsStaffs.next()) {
					int id = rsStaffs.getInt(1);
					String name = rsStaffs.getString(2);
					String surname = rsStaffs.getString(3);
					if(id != 0)
					{
						extendedSkiCenter.getExtendedStaffs().add(new Staff(id, name, surname));
						staffsNumber += 1;
					}
				}

				extendedSkiCenter.setTotal(staffsNumber);
			}
			System.out.println(extendedSkiCenters);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
