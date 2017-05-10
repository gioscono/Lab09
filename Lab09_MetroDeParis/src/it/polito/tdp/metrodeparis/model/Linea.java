package it.polito.tdp.metrodeparis.model;


public class Linea{
	
	private int id;
	private String nome;
	private double velocita;
	private double intervallo;
	
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public double getVelocita() {
		return velocita;
	}
	public double getIntervallo() {
		return intervallo;
	}
	
	public Linea(int id, String nome, double velocita, double intervallo) {
		this.id = id;
		this.nome = nome;
		this.velocita = velocita;
		this.intervallo = intervallo;
	}
	
	
	
}
