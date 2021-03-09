package dz2_1;

import java.util.ArrayList;
public class Sala  {
	private String naziv;
	private int kapacitet;
	private int racunari;
	private int dezurni;
	private int etf;
	private ArrayList<Integer>  preostalaVremena = new ArrayList<>() ;
	
	public Sala(String naziv, int kapacitet, int racunati, int dezurni, int etf) {
		super();
		this.naziv = naziv;
		this.kapacitet = kapacitet;
		this.racunari = racunati;
		this.dezurni = dezurni;
		this.etf = etf;
		preostalaVremena.add(0);
		preostalaVremena.add(1);
		preostalaVremena.add(2);
		preostalaVremena.add(3);
		
	}
	
	
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getKapacitet() {
		return kapacitet;
	}
	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}
	public int getRacunari() {
		return racunari;
	}
	public void setRacunari(int racunati) {
		this.racunari = racunati;
	}
	public int getDezurni() {
		return dezurni;
	}
	public void setDezurni(int dezurni) {
		this.dezurni = dezurni;
	}
	public int getEtf() {
		return etf;
	}
	public void setEtf(int etf) {
		this.etf = etf;
	}
	public ArrayList<Integer> getPreostalaVremena() {
		return preostalaVremena;
	}
	public void setPreostalaVremena(ArrayList<Integer> preostalaVremena) {
		this.preostalaVremena = preostalaVremena;
	}
	
	
	public double koeficijent()
	{
	    return (dezurni + 1.2 * (etf^1));
	}
	
	public void ukloniSat(Integer i)
	{
	    this.preostalaVremena.remove(i);
//	    System.out.println("UKLANJA SE! " +  i +" "+ this.naziv);
	}
	public void dodajSat(Integer i)
	{
	    this.preostalaVremena.add(i);
//	    System.out.println("dodaje se! " +  i +" "+ this.naziv);
	}

	
	
	@Override
	public boolean equals(Object obj) {
	
		if (this == obj)
		{
//		    System.out.println("Sale iste 1");
//			System.out.println( this.naziv + " ==  " + ((Sala)obj).naziv); 
		    return true;
		   
		}
			
		if (obj == null)
		{
//		    System.out.println("Sale nisu iste 1");
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
//		    System.out.println("Sale nisu iste 2");
		    return false;
		}
			
		Sala other = (Sala) obj;
//		System.out.println(other.naziv);
		if (naziv == null) {
			if (other.naziv != null)
			{	
				return false; 
			}
			
		} 
		
//		if (!preostalaVremena.equals(other.preostalaVremena) || !naziv.equals(other.naziv)  ) return false;
//		
		if ( !naziv.equals(other.naziv)  ) {/* 	System.out.println( this.naziv + " !=  " + other.naziv); */ return false;}
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
