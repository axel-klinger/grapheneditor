package test;

public class Person {

	//Name
	private String vorname;
	private String nachname;
	
	//Internet
	private String emailDienstlich;
	
	
	public Person () {
		vorname = "";
		nachname = "";
		emailDienstlich = "";
	}
	
	public String getEmailDienstlich() {
		return emailDienstlich;
	}
	public void setEmailDienstlich(String emailDienstlich) {
		this.emailDienstlich = emailDienstlich;
	}

	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String toString() {
		return "(" + vorname + " " + nachname + ", " + emailDienstlich + ")";	
	}
}
