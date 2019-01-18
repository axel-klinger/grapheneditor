package test;

import java.io.File;

import org.w3c.dom.Document;

public class DynamischerTypTest {

	public static void main(String[] args) {

		// TODO Typ einlesen "Person.xml"
		//Document doc = loadXML("Person.xml");
		
		// TODO Klasse generieren "Person.java"
		//File datei = generateJAVA(doc);
		
		// TODO Klasse compilieren "Person.class"
		//compileJAVA(datei);
		try {
			Compiler.compileClass(Class.forName("Person"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Klasse instanzieren "Person"
		// Person p = ...
		
		try {
			Object p = Class.forName("Person").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void compileJAVA(File datei) {
		// TODO Auto-generated method stub
		
	}

	private static File generateJAVA(Document doc) {

		return null;
	}

	public static Document loadXML(String typ) {
		Document doc = null;
		
		return doc;
	}
}
