package entitie;

import java.util.ArrayList;
import java.util.List;

public class SkiSlope {
	private List<Staff> extendedStaffs;
	private int idst;
	private String name;
	private int length;
	private String type;
	private int total;
	
	public SkiSlope(int idst, String name, int length, String type) {
		super();
		this.extendedStaffs = new ArrayList<Staff>();
		this.idst = idst;
		this.name = name;
		this.length = length;
		this.type = type;
		this.total = 0;
	}
	
	public List<Staff> getExtendedStaffs() {
		return extendedStaffs;
	}

	public void setExtendedStaffs(List<Staff> extendedStaffs) {
		this.extendedStaffs = extendedStaffs;
	}

	public int getIdst() {
		return idst;
	}

	public void setIdst(int idst) {
		this.idst = idst;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTotalPrice(int totalComputerPrice) {
		this.total = totalComputerPrice;
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String skiSlopeData = "\n\nSKISLOPE{Id:" + idst + ", Name:" + name
		+ ", Total salary of employees:" + total;
		sb.append(skiSlopeData);
		
		for(Staff staff : extendedStaffs) {
			sb.append("\n\t" + staff.toString());
		}
		sb.append("}\n");
		return sb.toString();
	}

}
