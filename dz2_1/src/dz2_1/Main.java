package dz2_1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.*;

public class Main {

    private static JSONObject rokovi;
    private static JSONObject sale;
    private static int trajanje;
//    private static ArrayList<Raspored> raspored;
    private static ArrayList<Ispit> preostaliIspiti;
    private static ArrayList<Ispit> reseniispiti;
    private static ArrayList<Ispit> kopijaIspita;
    private static ArrayList<Ispit> ispitiSvi;
    private static int nekadUsao = 0;
    private static ArrayList<ArrayList<Sala>> preostaleSale;
    private static ArrayList<ArrayList<Sala>> dodeljeneSale;
    private static ArrayList<Sala> sveKojePostoje;
    private static int trVreme = 0;
    private static FileWriter txtFilee = null;
    private static PrintWriter txtFile = null;
    private static double koeficijent;
    private static long startTime = 0;
    private static long otp = 0;
    private static int cnt = 1;
    private static ArrayList<Integer> duzine = new ArrayList<>();

    public static void main(String[] args) {
	String txtFileName = ("D:\\andjela\\IV\\IS1\\domaci2\\koraci.txt");
	try {
	    startTime = System.currentTimeMillis();

	    txtFilee = new FileWriter(txtFileName, false);
	    txtFile = new PrintWriter(txtFilee, false);

	    inicijalizacija();
	    rasporedjivanje();
	    String filePath = ("D:\\andjela\\IV\\IS1\\domaci2\\domaciZad.csv");

	    writeCsv(filePath);

	    long endTime = System.currentTimeMillis();
	    long duration = (endTime - startTime);
	    System.out.println("Trajanje " + duration + " ms");

	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		txtFile.flush();
		txtFile.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

    }

    public static void writeCsv(String filePath) { // Isipis CSV fajlova

	String[] rok = JSONObject.getNames(rokovi);
	String[] sala = JSONObject.getNames(sale);
	FileWriter fileWriter = null;

	JSONArray jsonArray = rokovi.getJSONArray("ispiti");

	try {
	    fileWriter = new FileWriter(filePath);

	    for (int i = 0; i < trajanje; i++) {

		if (i != 0) {
		    fileWriter.append("");
		    fileWriter.append(",");
		    for (int j = 0; j < sveKojePostoje.size(); j++) {
			fileWriter.append("");
			if (j != sveKojePostoje.size() - 1)
			    fileWriter.append(",");
		    }
		    fileWriter.append("\n");
		}

		fileWriter.append("Dan" + (i + 1) + ", ");
		for (int j = 0; j < sveKojePostoje.size(); j++) {

		    fileWriter.append("" + sveKojePostoje.get(j).getNaziv());
		    if (j != sveKojePostoje.size() - 1)
			fileWriter.append(", ");
		}
		fileWriter.append("\n");
		for (int k = 0; k < 4; k++) {
		    if (k == 0)
			fileWriter.append("08:00 , ");
		    else if (k == 1)
			fileWriter.append("11:30, ");
		    else if (k == 2)
			fileWriter.append("15:00, ");
		    else if (k == 3)
			fileWriter.append("18:30, ");

		    boolean nasao = false;
		    for (int h = 0; h < sveKojePostoje.size(); h++) {
			nasao = false;

			if (nekadUsao > 0 && kopijaIspita.size() > reseniispiti.size()) {
			    reseniispiti = kopijaIspita;
			}

			for (int l = 0; l < reseniispiti.size(); l++) {
			    if (reseniispiti.get(l).getDan() == (i) && reseniispiti.get(l).getVreme() == k
				    && reseniispiti.get(l).getIskorisceneSale().contains(sveKojePostoje.get(h))) {
				fileWriter.append(reseniispiti.get(l).getSifra());
				koeficijent += sveKojePostoje.get(h).koeficijent();
				nasao = true;
			    }

			}
			if (nasao == false)
			    fileWriter.append("     X ");
			if (h < (sveKojePostoje.size() - 1)) {
			    fileWriter.append(", ");

			}

		    }
		    fileWriter.append("\n");
		}
//		if (i != trajanje - 1)
//		    fileWriter.append("\n");
	    }
	    System.out.println("KOEFICIJENT = " + koeficijent);
	    if (preostaliIspiti.size() == 0) {
		System.out.println("SVE JE USPESNO RESENO! \n");
	    } else {
		System.out.println("Ne moze se uspesno zavrsiti rasporedjivanje! \n ");
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	} finally {
	    try {
		fileWriter.flush();
		fileWriter.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

    }

    public static void inicijalizacija() { // citanje iz JSON fajlova i smestanje u pomocne promenljive
	rokovi = JSONUtils.getJSONObjectFromFile("/rok3.json");
	sale = JSONUtils.getJSONObjectFromFile("/sale3.json");
	String[] rok = JSONObject.getNames(rokovi);
	String[] sala = JSONObject.getNames(sale);
	JSONArray sviIspiti = rokovi.getJSONArray("ispiti");
	JSONArray sveSale = sale.getJSONArray("");

	trajanje = rokovi.getInt("trajanje_u_danima");
	preostaliIspiti = new ArrayList<>();
	reseniispiti = new ArrayList<>();
	kopijaIspita = new ArrayList<>();
	dodeljeneSale = new ArrayList<>();
	preostaleSale = new ArrayList<>();
	sveKojePostoje = new ArrayList<>();
	ispitiSvi = new ArrayList<>();

	for (int j = 0; j < trajanje; j++) {

	    preostaleSale.add(new ArrayList<>());
	    dodeljeneSale.add(new ArrayList<>());

	}

	for (int i = 0; i < sviIspiti.length(); i++) {
	    ArrayList<String> pom = new ArrayList<>();
	    Ispit is = new Ispit(sviIspiti.getJSONObject(i).get("sifra").toString(),
		    (Integer) (sviIspiti.getJSONObject(i).get("prijavljeni")),
		    (Integer) sviIspiti.getJSONObject(i).get("racunari"));
	    JSONArray pomocni = sviIspiti.getJSONObject(i).getJSONArray("odseci");
	    for (int j = 0; j < pomocni.length(); j++) {
		pom.add(pomocni.getString(j));
	    }
	    is.dodajOdseke(pom);

	    preostaliIspiti.add(is);
	    ispitiSvi.add(is);

	}
	for (int j = 0; j < trajanje; j++) {
	    for (int i = 0; i < sveSale.length(); i++) {
		Sala s = new Sala(sveSale.getJSONObject(i).get("naziv").toString(),
			(int) sveSale.getJSONObject(i).get("kapacitet"), (int) sveSale.getJSONObject(i).get("racunari"),
			(int) sveSale.getJSONObject(i).get("dezurni"), (int) sveSale.getJSONObject(i).get("etf"));

		preostaleSale.get(j).add(s);
	    }

	}

	for (int i = 0; i < sveSale.length(); i++) {
	    Sala s = new Sala(sveSale.getJSONObject(i).get("naziv").toString(),
		    (int) sveSale.getJSONObject(i).get("kapacitet"), (int) sveSale.getJSONObject(i).get("racunari"),
		    (int) sveSale.getJSONObject(i).get("dezurni"), (int) sveSale.getJSONObject(i).get("etf"));

	    sveKojePostoje.add(s);
	}

	ArrayList<Integer> dan = new ArrayList<>();
	dan.add(1);

    }

    public static int prioriteti() { // odredjivanje prioriteta za ispite

	int max = preostaliIspiti.get(0).getPrijavljeni();
	int index = 0;
	for (int i = 1; i < preostaliIspiti.size(); i++) {

	    if (preostaliIspiti.get(i).getPrijavljeni() > max) {

		max = preostaliIspiti.get(i).getPrijavljeni();
		index = i;
	    }
	}
	return index;

    }

    public static int prioritet2(int tip) { // odredjivanje prioriteta u backtrack rezimu
	System.out.println("BACKTRACK");
	if (tip == 0) {
	    if (preostaliIspiti.size() < 0)
		return -1;
	    int min = preostaliIspiti.get(0).getPrijavljeni();
	    int index = 0;
	    for (int i = 1; i < preostaliIspiti.size(); i++) {

		if (preostaliIspiti.get(i).getPrijavljeni() < min) {

		    min = preostaliIspiti.get(i).getPrijavljeni();
		    index = i;
		}
	    }
	    return index;

	}
	if (tip == 1) {
	    return prioriteti();

	} else {
	    return prioriteti();
	}
//	else {
//	    int max = preostaliIspiti.size() - 1;
//	    int min = 0;
//	    int random_int = (int) (Math.random() * (max - min + 1) + min);
//	    return random_int;
//	}

    }

    // Najpre se krece od prvog dana, pronadje se najoptimalniji termin u odnosu na
    // broj prijavljenih studenata i u tom terminu sala koja odgovara broju
    // prijavljenih studenata, ukoliko se u terminu ne pronadje sala, pokusava se
    // drugim terminima, pa sa drugim danom, ako ni tada pretraga nije uspesna
    // poziva se funkcija backtrack()

    public static void rasporedjivanje() {
	if (preostaliIspiti.isEmpty()) {
	    return;
	}

	txtFile.flush();

	int indexMin = 0;
	if (nekadUsao > 0) {
	    indexMin = prioritet2(nekadUsao % 3);
	} else {
	    indexMin = prioriteti();
	}

	ArrayList<Integer> izabraniDani = new ArrayList<>();
	Ispit trenutno = preostaliIspiti.get(indexMin);
	txtFile.print("Odabran ispit za rasporedjivanje: " + trenutno.getSifra() + "\n");
	int kapa = trenutno.getPrijavljeni();
	ArrayList<Sala> iskoriscene = new ArrayList<>();

	Integer vr;
	boolean zavrsio = false;
	while (!zavrsio) {
	    for (int ddan = 0; ddan < trajanje; ddan++) {
		txtFile.print("DAN " + (ddan + 1) + "\n");

		if (!proveraZaOdseke(trenutno, ddan)) {
		    txtFile.print("U ovom danu vec postoji ispit na istoj godini za dati smer \n");
		    continue;

		}

		int pomVreme = -1;
		int i = -1;
		for (int ii = trVreme; ii < 4; ii++) {

		    if (pomVreme == -1) {
			pomVreme = najboljiSat(ddan, trenutno);
			i = pomVreme;

		    } else if (pomVreme != 0 && ii == pomVreme && pomVreme != -1) {
			i = 0;
		    } else {
			i = ii;
		    }

		    if (pomVreme == -2) {
			pomVreme = 0;
			i = ii;
		    }

		    if (i == -1) {

			txtFile.print("  \n" + "Ne postoji odgovarajuci termin u ovom danu => " + "  \n");
			pomVreme = -2;
			i = 0;
		    }

		    txtFile.print("Izabrani termin:  " + (i + 1) + ". " + "  \n");
		    if (!proveraZaGodineiOdseke(trenutno, ddan, i)) {
			txtFile.print("U ovom terminu je vec smesten ispit sa susedne godine! \n");

			continue;

		    }
		    vr = (Integer) i;
		    kapa = trenutno.getPrijavljeni();
		    iskoriscene = new ArrayList<>();
		    Sala sala1;

		    for (int f = 0; f < preostaleSale.get(ddan).size(); f++) {
			if (nekadUsao == 0) {
			    sala1 = izaberiSalu(trenutno, kapa, ddan, i, iskoriscene);
			} else {
			    sala1 = izaberiSalu2(trenutno, kapa, ddan, i, iskoriscene);
			}

			if (sala1 == null) {
			    txtFile.print("Ne postoji odgovarajuca sala u ovom terminu  \n");

			    break;
			}
			txtFile.print("Smestanje u salu " + sala1.getNaziv() + " \n");

			if (!sala1.getPreostalaVremena().contains(vr)) {
			    txtFile.print("Sala nije slobodna u  zeljenom terminu  \n");
			    continue;
			}
			if (trenutno.getRacunari() != sala1.getRacunari()) {
			    txtFile.print("Sala ne zadovoljava ogranicenja u posedovanju racunara  \n");

			    continue;
			}

			if (kapa > 0) {

			    kapa = kapa - sala1.getKapacitet();

			    iskoriscene.add(sala1);

			    if (kapa <= 0) {

				break;
			    }

			    continue;
			}
			if (kapa <= 0) {
			    txtFile.print("gotovo  \n");

			}

		    }

		    if (kapa <= 0) {
			txtFile.print(
				"Uspesno je zavrsen raspored za ispit: " + trenutno.getSifra() + " \n" + " \n" + " \n");
			trenutno.setIskorisceneSale(iskoriscene);
			trenutno.setVreme(i);
			trenutno.setDan(ddan);
			if (nijeVisePreostalo(iskoriscene, i, ddan)) {
			    txtFile.print(" " + " \n" + " \n");
			} else {
			    txtFile.print("Greska " + " \n" + " \n" + " \n");
			    backtrack();
			}

			ArrayList<Integer> dan = new ArrayList<>();
			dan.add(ddan);

			reseniispiti.add(trenutno);
			preostaliIspiti.remove(trenutno);
			if (otp == 0) {
			    otp = System.currentTimeMillis() - startTime;
			}
			rasporedjivanje();
			return;

		    } else {
			txtFile.print("Pokusava se smestanje u drugi termin " + "\n");
			if (i == 3 && ddan == trajanje) {
			    txtFile.print("Ulazi u backtrack " + " \n");
			    if (backtrack()) {
				indexMin = prioritet2(nekadUsao % 3);
				trenutno = preostaliIspiti.get(indexMin);
				txtFile.print("Odabran ispit za rasporedjivanje: " + trenutno.getSifra() + "\n");

				kapa = trenutno.getPrijavljeni();
				iskoriscene = new ArrayList<>();
				zavrsio = false;
			    } else
				zavrsio = true;
			}

		    }

		}

	    }
	    if (!preostaliIspiti.isEmpty()) {

		if (backtrack()) {
		    if (preostaliIspiti.isEmpty()) {
			return;
		    }
		    indexMin = prioritet2(nekadUsao % 3);
		    if (indexMin > preostaliIspiti.size() - 1) {
			return;
		    }
		    trenutno = preostaliIspiti.get(indexMin);
		    txtFile.print("Odabran ispit za rasporedjivanje: " + trenutno.getSifra() + "\n");

		    kapa = trenutno.getPrijavljeni();
		    iskoriscene = new ArrayList<>();
		    zavrsio = false;
		}

		else
		    zavrsio = true;
	    }

	}

    }
    // Kada se neki predmet rasporedi, ova funkcija se koristi kako bi se termini u
    // salama oznacili kao zauzeti

    public static boolean nijeVisePreostalo(ArrayList<Sala> sale, int i, int ddan) {

	Integer p = (Integer) i;

	for (int i1 = 0; i1 < preostaleSale.get(ddan).size(); i1++) {
	    for (int j = 0; j < sale.size(); j++) {
		if (preostaleSale.get(ddan).get(i1).equals(sale.get(j))) {
		    if (preostaleSale.get(ddan).get(i1).getPreostalaVremena().contains(p)) {
			preostaleSale.get(ddan).get(i1).getPreostalaVremena().remove(p);
		    } else
			return false;
		    if (preostaleSale.get(ddan).get(i1).getPreostalaVremena().isEmpty()) {
			dodeljeneSale.get(ddan).add(preostaleSale.get(ddan).get(i1));
			preostaleSale.get(ddan).remove(preostaleSale.get(ddan).get(i1));
		    }
		}
	    }
	}

	return true;

    }

    // Ova funkcija se poziva kako bi se vracali unazad, sve dok se ne pronadje sala
    // za neki ispitm uzimaju se ispiti iz liste resenih ispita
    // i pokusava se njihovo ponocno smestanje
    public static boolean backtrack() {

	if (nekadUsao == 0) {
	    nekadUsao = 1;
	    kopijaIspita = new ArrayList<>();
	    for (int i = 0; i < reseniispiti.size(); i++) {
		kopijaIspita.add(reseniispiti.get(i));
	    }

	}
	nekadUsao++;
	if (nekadUsao % 2 == 0) {
	    txtFile.flush();

	}
	if (nekadUsao >= 2 * cnt) {

	    for (int j = 0; j < cnt; j++) {
		if (reseniispiti != null && reseniispiti.size() > 0) {
		    Ispit pogresan1 = reseniispiti.remove(reseniispiti.size() - 1);
		    preostaliIspiti.add(pogresan1);
		    txtFile.print(" Dodaje se i  " + pogresan1.getSifra() + " \n");
		    for (int i = 0; i < pogresan1.getIskorisceneSale().size(); i++) {
			pogresan1.getIskorisceneSale().get(i).dodajSat((Integer) pogresan1.getVreme());
			if (!preostaleSale.get(pogresan1.getDan()).contains(pogresan1.getIskorisceneSale().get(i))) {
			    preostaleSale.get(pogresan1.getDan()).add(pogresan1.getIskorisceneSale().get(i));
			}

		    }

		    pogresan1.setIskorisceneSale(new ArrayList<>());
		    pogresan1.setVreme(-1);
		    pogresan1.setDan(-1);
		}
	    }

	    cnt++;
	}

	long endTime = System.currentTimeMillis();
	long duration = (endTime - startTime);
	if (preostaliIspiti.isEmpty() || reseniispiti.isEmpty() || duration > otp * 3) {
	    return false;
	}

	txtFile.print("BACKTRACK \n");
	if (reseniispiti.size() == 0)
	    return false;
	Ispit pogresan = reseniispiti.remove(reseniispiti.size() - 1);
	preostaliIspiti.add(pogresan);
	txtFile.print(" Ispit za koji se pokusava raspodela u drugom terminu je  " + pogresan.getSifra() + " \n");
	ArrayList<Sala> pokusaj = pogresan.getIskorisceneSale();
	ArrayList<Sala> iskoriscene = new ArrayList<>();
	int kap = pogresan.getPrijavljeni();
	boolean kraj = false;
	for (int j = 0; j < trajanje; j++) {
	    for (int termin = 0; termin < 4; termin++) {
		iskoriscene = new ArrayList<>();

		if (termin != pogresan.getVreme()) {
		    kraj = false;
		    while (!kraj) {
			Sala nova = izaberiSalu2(pogresan, kap, j, termin, iskoriscene);
			if (nova != null) {
			    txtFile.print("Uspesno je da nadjena nova sala " + nova.getNaziv() + " " + (j + 1)
				    + ". dana " + " \n");
			    if (kap > 0) {
				kap = kap - nova.getKapacitet();
				iskoriscene.add(nova);
			    }

			    if (kap <= 0) {

				txtFile.print(
					"Uspesno je zavrsen raspored za ispit" + pogresan.getSifra() + " \n" + " \n");
				for (int i = 0; i < pogresan.getIskorisceneSale().size(); i++) {
				    pogresan.getIskorisceneSale().get(i).dodajSat((Integer) pogresan.getVreme());
				    if (!preostaleSale.get(pogresan.getDan())
					    .contains(pogresan.getIskorisceneSale().get(i))) {
					preostaleSale.get(pogresan.getDan()).add(pogresan.getIskorisceneSale().get(i));
				    }

				}

				pogresan.setIskorisceneSale(iskoriscene);
				pogresan.setVreme(termin);
				pogresan.setDan(j);
				if (nijeVisePreostalo(iskoriscene, termin, j)) {
				    txtFile.print(" \n" + " \n");
				} else {
				    txtFile.print("Ne moze " + " \n" + " \n" + " \n");
				    backtrack();
				}

				ArrayList<Integer> dan = new ArrayList<>();
				dan.add(j);

				reseniispiti.add(pogresan);
				preostaliIspiti.remove(pogresan);
				kraj = true;
				rasporedjivanje();
				return true;
			    }

//				txtFile.print(" Postoji druga sala  " + ":( \n" + ":( \n" + ":( \n");

			} else {
			    txtFile.print("Nema vise slobodnih za ovaj ispit " + " \n");
			    kraj = true;

			}
		    }

		}

	    }

	}

	if (!reseniispiti.contains(pogresan)) {
	    txtFile.print("Vraca se ponovo u BACKTRACK " + pogresan.getSifra() + " \n" + " \n");
	    for (int i = 0; i < pogresan.getIskorisceneSale().size(); i++) {
		txtFile.print("Dodaje vreme za salu " + pogresan.getIskorisceneSale().get(i).getNaziv() + " \n");
		pogresan.getIskorisceneSale().get(i).dodajSat((Integer) pogresan.getVreme());
		if (!preostaleSale.get(pogresan.getDan()).contains(pogresan.getIskorisceneSale().get(i))) {
		    preostaleSale.get(pogresan.getDan()).add(pogresan.getIskorisceneSale().get(i));
		}

	    }

	    backtrack();
	}

	return false;
    }

    public static boolean proveraZaOdseke(Ispit is, int dan) { // provera da dva ispita sa istog smera i iste godinu ne
							       // budu istog dana
	boolean nasao = true;

	for (int i = 0; i < reseniispiti.size(); i++) {
	    if (reseniispiti.get(i).getDan() == dan) {
		for (int j = 0; j < is.getOdseci().size(); j++) {
		    if (reseniispiti.get(i).getOdseci().contains(is.getOdseci().get(j))
			    && (reseniispiti.get(i).getSifra().charAt(5) == is.getSifra().charAt(5))) {
			nasao = false;
			return false;
		    }
		}
	    }
	}

	return nasao;

    }

    public static boolean proveraZaGodineiOdseke(Ispit is, int dan, int sat) { // provera da u istom terminu ne bude
									       // ispita sa istih smerova a susednih
									       // godina
	boolean nasao = true;
	for (int i = 0; i < reseniispiti.size(); i++) {
	    if (reseniispiti.get(i).getDan() == dan) {
		for (int j = 0; j < is.getOdseci().size(); j++) {
		    if ((reseniispiti.get(i).getOdseci().contains(is.getOdseci().get(j))
			    && (reseniispiti.get(i).getSifra().charAt(5) == is.getSifra().charAt(5)))) {
			nasao = false;
			return false;
		    } else if (reseniispiti.get(i).getOdseci().contains(is.getOdseci().get(j))
			    && reseniispiti.get(i).getVreme() == sat
			    && ((Character.getNumericValue(reseniispiti.get(i).getSifra()
				    .charAt(5)) == (Character.getNumericValue(is.getSifra().charAt(5)) + 1))
				    || ((Character.getNumericValue(reseniispiti.get(i).getSifra().charAt(5))
					    + 1) == Character.getNumericValue(is.getSifra().charAt(5))))) {
			nasao = false;
			return false;
		    }
		}
	    }
	}

	return nasao;
    }
    // bira se najbolja sala, tako sto se radi best fit ako je preostalo da se
    // rasporedi manje studenata koji staju u bar neku salu, a ako ne mogu da se
    // smeste ni u jednu salu onda se uzima najveca sala

    public static Sala izaberiSalu(Ispit ispit, int kap, int dan, int vreme, ArrayList<Sala> iskoriscene) {

	int najviseOdgovara = -1;
	boolean nasao = false;
	int najvecaRalika = 5000;

	Sala najmanja = null;
	Sala sala = null;
	if (preostaleSale.get(dan) == null)
	    return null;
	najmanja = najmanjaSalaUTerminu(ispit, dan, vreme, iskoriscene);
	for (int f = 0; f < preostaleSale.get(dan).size(); f++) {

	    if (preostaleSale.get(dan).get(f).getPreostalaVremena().contains(vreme)
		    && !iskoriscene.contains(preostaleSale.get(dan).get(f)) && proveraZaGodineiOdseke(ispit, dan, vreme)
		    && preostaleSale.get(dan).get(f).getRacunari() == ispit.getRacunari()) {
		if (sala == null) {
		    sala = preostaleSale.get(dan).get(f);
		    najvecaRalika = 5000;
		    najviseOdgovara = preostaleSale.get(dan).get(f).getKapacitet();

		}

		if ((preostaleSale.get(dan).get(f).getKapacitet()) >= kap) {

		    nasao = true;
		    if (sala != null
			    && (sala.koeficijent() + najvecaRalika) > (preostaleSale.get(dan).get(f).koeficijent()
				    + preostaleSale.get(dan).get(f).getKapacitet() - kap)) {

			najvecaRalika = preostaleSale.get(dan).get(f).getKapacitet() - kap;
			sala = preostaleSale.get(dan).get(f);

		    }

		} else if (preostaleSale.get(dan).get(f).getKapacitet() < kap
			&& (preostaleSale.get(dan).get(f).getKapacitet() > najviseOdgovara && nasao == false)) {
		    if ((sala.koeficijent() + najviseOdgovara - ispit.getPrijavljeni()) < (preostaleSale.get(dan).get(f).koeficijent()
			    + preostaleSale.get(dan).get(f).getKapacitet()- ispit.getPrijavljeni())
			    && ((kap - preostaleSale.get(dan).get(f).getKapacitet()) >= najmanja.getKapacitet())) {
			najviseOdgovara = preostaleSale.get(dan).get(f).getKapacitet();
			sala = preostaleSale.get(dan).get(f);

		    }
		}
	    }
	}

	return sala;
    }
    // pdredjuje se koji termin najvise odgovara ispitu na osnovu sala koje se u
    // datom terminu mogu iskoristiti

    public static int najboljiSat(int dan, Ispit is) {
	int min = is.getPrijavljeni();
// dole je <= a ove 50000
	int najbolji = -1;
	int mink = -1;
	for (int i = 0; i <= 4; i++) {
	    int pom = 0;
	    int koe = 0;

	    for (int j = 0; j < preostaleSale.get(dan).size(); j++) {
		if (preostaleSale.get(dan).get(j).getPreostalaVremena().contains((Integer) i)
			&& is.getRacunari() == preostaleSale.get(dan).get(j).getRacunari()) {
		    pom += preostaleSale.get(dan).get(j).getKapacitet();
		    koe += preostaleSale.get(dan).get(j).koeficijent();
		    if (preostaleSale.get(dan).get(j).getKapacitet() == is.getPrijavljeni()) {
			najbolji = i;
			return najbolji;
		    }
		}

	    }
//	    System.out.println(" pom "+ pom + " ispit " + is.getSifra() + " min " + min + " najbolji " + najbolji);
	    if (pom > is.getPrijavljeni()
		    && ((pom  - is.getPrijavljeni()) < (min +  is.getPrijavljeni()))) {
		min = pom;
		mink = koe;
		najbolji = i;

	    }
// 	    if (pom > min ) {
// 		min = pom;
// 		najbolji = i;
// 		
// 	    }

	}

	return najbolji;

    }
    // Izbor sale prilikom backtrackinga, trazi se optimalaniji raspored

    public static Sala izaberiSalu2(Ispit ispit, int kap, int dan, int vreme, ArrayList<Sala> iskoriscene) {
	int najviseOdgovara = 50000;
	boolean nasao = false;
	int najvecaRalika = 50000;
	int doSad = 0;
	Sala sala = null;
	ArrayList<Integer> smestanje = new ArrayList<>();
	ArrayList<Sala> najbolja = new ArrayList<>();
	int pom = 0;
	if (preostaleSale.get(dan).size() == 0 || preostaleSale == null || preostaleSale.size() - 1 < dan)
	    return null;
	for (int f = 0; f < preostaleSale.get(dan).size(); f++) {

	    smestanje = new ArrayList<>();
	    if (preostaleSale.get(dan).get(f).getPreostalaVremena().contains((Integer) vreme)
		    && !iskoriscene.contains(preostaleSale.get(dan).get(f)) && proveraZaGodineiOdseke(ispit, dan, vreme)
		    && preostaleSale.get(dan).get(f).getRacunari() == ispit.getRacunari()) {
		pom += preostaleSale.get(dan).get(f).getKapacitet();
		if (sala == null) {
		    sala = preostaleSale.get(dan).get(f);
		    najvecaRalika = 5000;
		    najviseOdgovara = preostaleSale.get(dan).get(f).getKapacitet();

		}

		pom = preostaleSale.get(dan).get(f).getKapacitet();

		if ((preostaleSale.get(dan).get(f).getKapacitet() >= kap)
			&& ((preostaleSale.get(dan).get(f).getKapacitet() - kap) <= najvecaRalika)) {
		    nasao = true;
		    if (sala != null
			    && (sala.koeficijent() + najvecaRalika) > (preostaleSale.get(dan).get(f).koeficijent()
				    + preostaleSale.get(dan).get(f).getKapacitet() - kap)) {

			najvecaRalika = preostaleSale.get(dan).get(f).getKapacitet() - kap;
			sala = preostaleSale.get(dan).get(f);

		    }

		} else if (preostaleSale.get(dan).get(f).getKapacitet() < kap && nasao == false) {
		    ArrayList<Integer> niz = new ArrayList<>();
		    int pomocno = 0;
		    for (int petlja = 0; petlja < preostaleSale.get(dan).size(); petlja++) {
			pom = preostaleSale.get(dan).get(petlja).getKapacitet();
			for (int petlja2 = petlja; petlja2 < preostaleSale.get(dan).size(); petlja2++) {
			    if (preostaleSale.get(dan).get(petlja).getPreostalaVremena().contains(vreme)
				    && !iskoriscene.contains(preostaleSale.get(dan).get(petlja))
				    && proveraZaGodineiOdseke(ispit, dan, vreme)
				    && preostaleSale.get(dan).get(petlja).getRacunari() == ispit.getRacunari()) {

				if (pom >= kap && ((pom - kap) < najviseOdgovara)) {
				    najviseOdgovara = pom - kap;
				    sala = preostaleSale.get(dan).get(petlja);
				}

				int pom2 = 0;
				for (int petlja3 = 0; petlja3 < petlja; petlja3++) {
				    pom2 = preostaleSale.get(dan).get(petlja).getKapacitet();
				    if ((pom - pom2) >= kap && (pom - pom2 - kap) <= najviseOdgovara) {
					najviseOdgovara = pom - kap;
					sala = preostaleSale.get(dan).get(petlja);
				    }
				}

			    }

			}

		    }

		}
	    }

	}

	return sala;
    }

//bira se sala najmanjeg kapaciteta u datom terminu
    public static Sala najmanjaSalaUTerminu(Ispit ispit, int dan, int vreme, ArrayList<Sala> iskoriscene) {
	Sala sala = null;
	int min = 5000;
	if (preostaleSale.get(dan) == null)
	    return null;
	for (int f = 0; f < preostaleSale.get(dan).size(); f++) {

	    if (preostaleSale.get(dan).get(f).getPreostalaVremena().contains(vreme)
		    && !iskoriscene.contains(preostaleSale.get(dan).get(f)) && proveraZaGodineiOdseke(ispit, dan, vreme)
		    && preostaleSale.get(dan).get(f).getRacunari() == ispit.getRacunari()) {
		if (min > preostaleSale.get(dan).get(f).getKapacitet()) {
		    min = preostaleSale.get(dan).get(f).getKapacitet();
		    sala = preostaleSale.get(dan).get(f);
		}
	    }
	}

	return sala;
    }

}