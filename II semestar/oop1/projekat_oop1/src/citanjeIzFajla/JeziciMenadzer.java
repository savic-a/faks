package citanjeIzFajla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import entiteti.Jezik;

public class JeziciMenadzer {
	private List<Jezik> jezici;

	private String jeziciFajl;

	public JeziciMenadzer(String jeziciFajl) {
		super();
		this.jeziciFajl = jeziciFajl;
		this.jezici = new ArrayList<Jezik>();
	}
	
	public List<Jezik> getJezici() {
		return jezici;
	}

    // pronadji jezik
    public Jezik pronadjiJezikPoId(String id)
    {
        Jezik retVal = null;
        for (int i = 0; i < jezici.size(); i++)
        {
            Jezik j = jezici.get(i);
            if (j.getId().equals(id))
            {
                retVal = j;
                break;
            }
        }
        return retVal;
    }
    
    public Jezik pronadjiJezikPoNazivu(String naziv)
    {
        Jezik retVal = null;
        for (int i = 0; i < jezici.size(); i++)
        {
            Jezik j = jezici.get(i);
            if (j.getNaziv().equals(naziv))
            {
                retVal = j;
                break;
            }
        }
        return retVal;
    }

   public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.jeziciFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				this.jezici.add(new Jezik(tokeni[0], tokeni[1]));
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean cuvanje() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.jeziciFajl, false));
			for (Jezik j : jezici) {
				pw.println(j.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void dodaj(String id, String naziv) {
		this.jezici.add(new Jezik(id, naziv));	
		this.cuvanje();
	}
	
	
	public void izmena(String id, String naziv) {
		Jezik j = this.pronadjiJezikPoId(id);
		j.setId(id);
		j.setNaziv(naziv);
		this.cuvanje();
	}
	
	public void brisanje(String id) {
		Jezik j = pronadjiJezikPoId(id);
		this.jezici.remove(j);
		this.cuvanje();
	}
}
