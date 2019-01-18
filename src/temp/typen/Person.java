/*
 * Created on 15.12.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package temp.typen;

/**
 * @author Axel
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Person {

	/* Anmerkung: Bei jeder Änderung müssen Anpassungen gemacht werden in 
	 * 	- den Settern/Gettern
	 * 	- der Eingabemaske (inkl. Events bei Änderung)
	 * 	- dem Speicherformat	
	 * Für eine allgemeine Beschreibung (z.B. in XML) benötigt man
	 * 
	 * bei Eigenschaften
	 * 	- Variablenname
	 * 	- Anzeigename
	 * 	- Datentyp (bei String zusätzlich Muster)	*/
	
	//Name
	private String vorname;
	private String nachname;
	private String anzeigename;
	private String spitzname;
	
	//Internet
	private String emailDienstlich;
	private String emailPrivat;
	private String messenger;
	
	//Telefon
	private String telefonDienstlich;
	private String telefonPrivat;
	private String fax;
	private String pager;
	private String mobil;
	
	//Adresse Privat
	private String adressePrivat;
	private String stadtPrivat;
	private String bundeslandPrivat;
	private String postleitzahlPrivat;
	private String landPrivat;
	private String webseitePrivat;
	
	//Adresse Dienstlich
	private String titel;
	private String abteilung;
	private String firma;
	private String adresseDienstlich;
	private String stadtDienstlich;
	private String bundeslandDienstlich;
	private String postleitzahlDienstlich;
	private String landDienstlich;
	private String webseiteDienstlich;
	
	public Person() {
		// alles auf "" setzen!!!
	}
	
	public Person(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
	}
	
	// ----- Getter/Setter -----//
	public String getAbteilung() {
		return abteilung;
	}
	public void setAbteilung(String abteilung) {
		this.abteilung = abteilung;
	}
	public String getAdresseDienstlich() {
		return adresseDienstlich;
	}
	public void setAdresseDienstlich(String adresseDienstlich) {
		this.adresseDienstlich = adresseDienstlich;
	}
	public String getAdressePrivat() {
		return adressePrivat;
	}
	public void setAdressePrivat(String adressePrivat) {
		this.adressePrivat = adressePrivat;
	}
	public String getAnzeigename() {
		return anzeigename;
	}
	public void setAnzeigename(String anzeigename) {
		this.anzeigename = anzeigename;
	}
	public String getBundeslandDienstlich() {
		return bundeslandDienstlich;
	}
	public void setBundeslandDienstlich(String bundeslandDienstlich) {
		this.bundeslandDienstlich = bundeslandDienstlich;
	}
	public String getBundeslandPrivat() {
		return bundeslandPrivat;
	}
	public void setBundeslandPrivat(String bundeslandPrivat) {
		this.bundeslandPrivat = bundeslandPrivat;
	}
	public String getEmailDienstlich() {
		return emailDienstlich;
	}
	public void setEmailDienstlich(String emailDienstlich) {
		this.emailDienstlich = emailDienstlich;
	}
	public String getEmailPrivat() {
		return emailPrivat;
	}
	public void setEmailPrivat(String emailPrivat) {
		this.emailPrivat = emailPrivat;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getLandDienstlich() {
		return landDienstlich;
	}
	public void setLandDienstlich(String landDienstlich) {
		this.landDienstlich = landDienstlich;
	}
	public String getLandPrivat() {
		return landPrivat;
	}
	public void setLandPrivat(String landPrivat) {
		this.landPrivat = landPrivat;
	}
	public String getMessenger() {
		return messenger;
	}
	public void setMessenger(String messenger) {
		this.messenger = messenger;
	}
	public String getMobil() {
		return mobil;
	}
	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getPager() {
		return pager;
	}
	public void setPager(String pager) {
		this.pager = pager;
	}
	public String getPostleitzahlDienstlich() {
		return postleitzahlDienstlich;
	}
	public void setPostleitzahlDienstlich(String postleitzahlDienstlich) {
		this.postleitzahlDienstlich = postleitzahlDienstlich;
	}
	public String getPostleitzahlPrivat() {
		return postleitzahlPrivat;
	}
	public void setPostleitzahlPrivat(String postleitzahlPrivat) {
		this.postleitzahlPrivat = postleitzahlPrivat;
	}
	public String getSpitzname() {
		return spitzname;
	}
	public void setSpitzname(String spitzname) {
		this.spitzname = spitzname;
	}
	public String getStadtDienstlich() {
		return stadtDienstlich;
	}
	public void setStadtDienstlich(String stadtDienstlich) {
		this.stadtDienstlich = stadtDienstlich;
	}
	public String getStadtPrivat() {
		return stadtPrivat;
	}
	public void setStadtPrivat(String stadtPrivat) {
		this.stadtPrivat = stadtPrivat;
	}
	public String getTelefonPrivat() {
		return telefonPrivat;
	}
	public void setTelefonPrivat(String telefonPrivat) {
		this.telefonPrivat = telefonPrivat;
	}
	public String getTelefonDienstlich() {
		return telefonDienstlich;
	}
	public void setTelefonDienstlich(String telfonDienstlich) {
		this.telefonDienstlich = telfonDienstlich;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getWebseiteDienstlich() {
		return webseiteDienstlich;
	}
	public void setWebseiteDienstlich(String webseiteDienstlich) {
		this.webseiteDienstlich = webseiteDienstlich;
	}
	public String getWebseitePrivat() {
		return webseitePrivat;
	}
	public void setWebseitePrivat(String webseitePrivat) {
		this.webseitePrivat = webseitePrivat;
	}
	
//	public int hashCode()  {
//		int code = 0;
//		
//		return code;
//	}
	
	public boolean equals (Object o) {
		if (!(o instanceof Person))
			return false;
		Person p = (Person) o;
		return this.vorname.equals(p.vorname) && this.nachname.equals(p.nachname);
	}
	
	public String toString() {
		return vorname + " " + nachname;
	}
	
}
