package lab12T;

import java.util.*;

abstract class Tip{
	abstract public String getTip();
	abstract public String toString();
}

class Intreg extends Tip{
	private int valoare;
	public Intreg(int value) {
		this.valoare = value;
	}
	public String getTip() {
		return "Tip:Intreg";
	}
	public String toString() {
		return String.valueOf(this.valoare);
	}
}

class Sir extends Tip{
	private String valoare;
	/*
	 * public String getTip() { return null; } public String toString() { return
	 * null; }
	 */
	public Sir(String value){
		this.valoare = value;
	}
	public String getTip() {
		return "Tip:Sir";
	}
	public String toString() {
		return this.valoare;
	}
}

class Colectie extends Tip{
	private ArrayList<Tip> colectie;
	//metoda care testeaza egalitatea, same elemnts count, stocate in aceeasi ordine
	//metoda de adaugare a elementelor in colectie
	public Colectie() {
		this.colectie = new ArrayList<Tip>();
	}
	public String getTip() {
		return "Tip:Colectie";
	}
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("( ");
		Iterator<Tip> iterator = colectie.iterator();
		while(iterator.hasNext()) {
			Tip element = iterator.next();
			if(iterator.hasNext())
				output.append(element.toString()).append(", ");
			else
				output.append(element.toString());
		}
		output.append(" )");
		return output.toString();
	}
	public void addElement(Tip element) {
		colectie.add(element);
	}
	public boolean checkEquality(Colectie colectie2) {
		if(this.colectie.size() == colectie2.colectie.size()) {
			Iterator<Tip> iterator = this.colectie.iterator();
			Iterator<Tip> iterator2 = colectie2.colectie.iterator();
			while(iterator.hasNext()) {
				Tip element1 = iterator.next();
				Tip element2 = iterator2.next();
				return element1.toString().equals(element2.toString());
			}
		}
		return false;
	}
}

public class ex3 {
	public static void main(String[] args) {
		Colectie colectie1 = new Colectie();
		Colectie colectie2 = new Colectie();
		colectie1.addElement(new Intreg(6));
		colectie1.addElement(new Sir("testSir1"));
		System.out.println(colectie1.toString());
		System.out.println(new Intreg(7).getTip());
		System.out.println(new Sir("testSir2").getTip());
		colectie2.addElement(new Intreg(7));
		colectie2.addElement(new Sir("testSir1"));
		System.out.println(colectie2.toString());
		System.out.println(colectie2.checkEquality(colectie1));
		colectie1.addElement(colectie2);
		System.out.println(colectie1.toString());
	}
}
