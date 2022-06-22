package citanjeIzFajla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import entiteti.Administrator;
import entiteti.Pol;

public class AdmMenadzer {
	private String administratorFajl;
	private Administrator adm;
	
	public AdmMenadzer(String administratorFajl) {
		super();
		this.administratorFajl = administratorFajl;
		this.adm = new Administrator();
	}

	public void setAdm(Administrator adm) {
		this.adm = adm;
	}

	//da li je administrator?
    public Administrator ProveraAdministratoraPoUsername(String username)
    {
    	Administrator retVal = null;
        if (this.adm.getUsername().equals(username))
            {
                retVal = adm;
            }
        return retVal;
    }
    
    public Administrator login(String username, String lozinka) {
    	Administrator a = null;
    	if (this.adm.getUsername().equals(username) && this.adm.getLozinka().equals(lozinka)) {
    		a = adm;
    	}
    	return a;
    }

	public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.administratorFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				int polInt = Integer.parseInt(tokeni[2]);
				Pol pol = Pol.values()[polInt];
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate datum = LocalDate.parse(tokeni[3],format);
				Administrator a = new Administrator(tokeni[0], tokeni[1], pol, datum, tokeni[4], tokeni[5], tokeni[6], tokeni[7]);
				System.out.println(a);
//				if (tokeni.length > 4) {
//					for (String pId : tokeni[4].split(";")) {
//						int id = Integer.parseInt(pId);
//						s.getPredmeti().add(this.predmetMng.PronadjiPredmetPoId(id));						
//					}
//				}
				this.adm = a;
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
//	public boolean cuvanje() {
//		PrintWriter pw = null;
//		try {
//			pw = new PrintWriter(new FileWriter(this.administratorFajl, false));
//			for (Administrator a : sekretari) {
//				pw.println(s.toFileString());
//			}
//			pw.close();
//		} catch (IOException e) {
//			return false;
//		}
//		return true;
//	}
}
