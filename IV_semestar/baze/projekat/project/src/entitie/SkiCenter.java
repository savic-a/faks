package entitie;

import java.util.ArrayList;
import java.util.List;

public class SkiCenter {
	private List<Staff> extendedStaffs;
	private int idski;
	private String name;
	private String tel;
	private String email;
	private int total;
	
	public SkiCenter(int idski, String name, String tel, String email) {
		super();
		this.extendedStaffs = new ArrayList<Staff>();
		this.idski = idski;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.total = 0;
	}
	
	public List<Staff> getExtendedStaffs() {
		return extendedStaffs;
	}
	
	

	public void setExtendedStaffs(List<Staff> extendedStaffs) {
		this.extendedStaffs = extendedStaffs;
	}



	public int getIdski() {
		return idski;
	}



	public void setIdski(int idski) {
		this.idski = idski;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTel() {
		return tel;
	}



	public void setTel(String tel) {
		this.tel = tel;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String skiSlopeData = "\n\nSKICENTER{Id:" + idski + ", Name:" + name + ", Tel:" + tel + ", Email:" + email
		+ ", Total employees:" + total;
		sb.append(skiSlopeData);
		
		for(Staff staff : extendedStaffs) {
			sb.append("\n\t" + staff.print());
		}
		
		sb.append("}\n");
		
		return sb.toString();
	}
}
