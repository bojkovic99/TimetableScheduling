package dz2_1;

import java.util.ArrayList;

public class Raspored {
	private ArrayList<Integer> dan;
	private int ddan;
	private String rok;
	private ArrayList<Ispit> predmeti = new ArrayList<>();
	private ArrayList<String> sviodseci = new ArrayList<>();
	
	public Raspored(int dan, String rok) {
		super();
	//	this.dan = dan;
		this.rok = rok;
		this.ddan= dan;
		sviodseci = new ArrayList<>();
	}
	public ArrayList<Integer> getDan() {
		return dan;
	}
	public void setDan(ArrayList<Integer> dan) {
		this.dan = dan;
	}

	public String getRok() {
		return rok;
	}
	public void setRok(String rok) {
		this.rok = rok;
	}
	public ArrayList<Ispit> getPredmeti() {
		return predmeti;
	}
	public void setPredmeti(ArrayList<Ispit> predmeti) {
		this.predmeti = predmeti;
	}

	
	public void doajPredmet(Ispit is)
	{
		this.predmeti.add(is);
		for(int i = 0; i < is.getOdseci().size(); i++)
		{
		    if(!sviodseci.contains(is.getOdseci().get(i)))
		    {
			sviodseci.add(is.getOdseci().get(i));
		    }
		}
	}
	

	public int getDdan() {
		return ddan;
	}
	public void setDdan(int dan) {
		this.ddan = dan;
	}
	public ArrayList<String> getSviodseci() {
	    return sviodseci;
	}
	public void setSviodseci(ArrayList<String> sviodseci) {
	    this.sviodseci = sviodseci;
	}

	
		

}
