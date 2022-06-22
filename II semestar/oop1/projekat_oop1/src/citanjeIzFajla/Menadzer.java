package citanjeIzFajla;


import podesavanje.Ucitavanje;

public class Menadzer {
	private Ucitavanje ucitavanje;
	private AdmMenadzer admMen;
	private SekMenadzer sekMen;
	private PredMenadzer predMen;
	private JeziciMenadzer jezMen;
	private TestMenadzer testMen;
	private KursMen kursMen;
	private UcenikMen ucenikMen;
	private KursNoviMen kursNoviMen;
	private ZahteviRegMen zahteviReg;
	private ZahteviKursMen zahteviKurs;
	private RezMen rezultatiMen;
	
	public Menadzer(Ucitavanje ucitavanje) {
		super();
		this.ucitavanje = ucitavanje;
		this.admMen = new AdmMenadzer(this.ucitavanje.getAdmFajlNaziv());
		this.sekMen = new SekMenadzer(this.ucitavanje.getSekretarFajlNaziv());
		this.jezMen = new JeziciMenadzer(this.ucitavanje.getJeziciFajlNaziv());
		this.predMen = new PredMenadzer(this.ucitavanje.getPredavacFajlNaziv(), jezMen);
		this.testMen = new TestMenadzer(this.ucitavanje.getTestoviFajlNaziv(), jezMen, predMen);
		this.kursMen = new KursMen(this.ucitavanje.getKurseviFajlNaziv(), jezMen, testMen, predMen);
		this.ucenikMen = new UcenikMen(this.ucitavanje.getUceniciFajlNaziv(), kursMen, testMen);
		this.kursNoviMen = new KursNoviMen(this.ucitavanje.getKursNoviFajlNaziv(), jezMen);
		this.zahteviReg = new ZahteviRegMen(this.ucitavanje.getZahteviRegFajlNaziv(), sekMen);
		this.zahteviKurs = new ZahteviKursMen(this.ucitavanje.getZahteviKursFajlNaziv(), sekMen, ucenikMen, kursMen);
		this.rezultatiMen = new RezMen(this.ucitavanje.getRezultatiFajlNaziv(), ucenikMen, testMen);
	}


	public AdmMenadzer getAdmMen() {
		return admMen;
	}

	
	public SekMenadzer getSekMen() {
		return sekMen;
	}


	public PredMenadzer getPredMen() {
		return predMen;
	}
	

	public JeziciMenadzer getJezMen() {
		return jezMen;
	}


	public TestMenadzer getTestMen() {
		return testMen;
	}


	public KursMen getKursMen() {
		return kursMen;
	}


	public UcenikMen getUcenikMen() {
		return ucenikMen;
	}
	
	public KursNoviMen getKursNoviMen() {
		return kursNoviMen;
	}
	
	public ZahteviRegMen getZahteviRegMen() {
		return zahteviReg;
	}
	
	public ZahteviKursMen getZahteviKursMen() {
		return zahteviKurs;
	}
	
	public RezMen getRezMen() {
		return rezultatiMen;
	}



	public void ucitava() {
		this.admMen.citanje();
		System.out.println();
		
		this.sekMen.citanje();
		System.out.println();
		
		this.jezMen.citanje();
		System.out.println();
		
		this.predMen.citanje();
		System.out.println();
		
		this.testMen.citanje();
		System.out.println();
		
		this.kursMen.citanje();
		System.out.println();
		
		this.ucenikMen.citanje();
		System.out.println();
		
		this.kursNoviMen.citanje();
		this.zahteviReg.citanje();
		this.zahteviKurs.citanje();
		this.rezultatiMen.citanje();
	}
}
