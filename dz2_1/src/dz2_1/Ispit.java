package dz2_1;

import java.util.ArrayList;

public class Ispit {
	private String sifra;
	private int prijavljeni;
	private int racunari;
	private ArrayList<String> odseci = new ArrayList<>();
	private int prioritet = 0;
	private int dan;

	private ArrayList<Sala> dostupneSale = new ArrayList<>();
	private ArrayList<Sala> iskorisceneSale = new ArrayList<>();
	private Sala poslednjaOdabrana;


	public Ispit(String sifra, int prijavljeni, int racunari, ArrayList<String> odseci) {
		super();
		this.sifra = sifra;
		this.prijavljeni = prijavljeni;
		this.racunari = racunari;
		this.odseci = odseci;
		this.dostupneSale = new ArrayList<>();
	}
	public Sala getPoslednjaOdabrana() {
		return poslednjaOdabrana;
	}

	public void setPoslednjaOdabrana(Sala poslednjaOdabrana) {
		this.poslednjaOdabrana = poslednjaOdabrana;
	}

	private int vreme;
	
	public Ispit(String sifra, int prijavljeni, int racunari) {
		super();
		this.sifra = sifra;
		this.prijavljeni = prijavljeni;
		this.racunari = racunari;
	}

	public void dodajOdseke(ArrayList<String> odseci) {
		this.odseci = odseci;

	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public int getPrijavljeni() {
		return prijavljeni;
	}

	public void setPrijavljeni(int prijavljeni) {
		this.prijavljeni = prijavljeni;
	}

	public int getRacunari() {
		return racunari;
	}

	public void setRacunari(int racunari) {
		this.racunari = racunari;
	}

	public int getPrioritet() {
		return dostupneSale.size() ;
	}

	public void setPrioritet(int prioritet) {
		this.prioritet = prioritet;
	}
	


	public ArrayList<String> getOdseci() {
		return odseci;
	}

//	public  ArrayList<Sala> getDostupneSale() {
//		return dostupneSale;
//	}
//
//	public void setDostupneSale( ArrayList<Sala> dostupneSale) {
//		this.dostupneSale = dostupneSale;
//	}

	public int getVreme() {
		return vreme;
	}

	public void setVreme(int vreme) {
		this.vreme = vreme;
	}

	public ArrayList<Sala> getIskorisceneSale() {
		return iskorisceneSale;
	}

	public void setIskorisceneSale(ArrayList<Sala> iskorisceneSale) {
		this.iskorisceneSale = iskorisceneSale;
	}
	public int getDan() {
		return dan;
	}
	public void setDan(int dan) {
		this.dan = dan;
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if (this == obj)
		{

		    return true;
		   
		}
			
		if (obj == null)
		{

			return false;
		}
		
		if (getClass() != obj.getClass())
		{

		    return false;
		}
			
		Ispit other = (Ispit) obj;
//		System.out.println(other.naziv);
		if (sifra == null) {
			if (other.sifra != null)
			{	
				return false; 
			}
			
		} 
		
//		if (!preostalaVremena.equals(other.preostalaVremena) || !naziv.equals(other.naziv)  ) return false;
//		
		if ( !sifra.equals(other.sifra)  ) {/* 	System.out.println( this.naziv + " !=  " + other.naziv); */ return false;}
//			return false;
//		if (name == null) {
//		System.out.print("usao " +this.naziv + "   ");
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		System.out.println( this.naziv + " ==  " + other.naziv); 
		return true;
	}

	
	
}
