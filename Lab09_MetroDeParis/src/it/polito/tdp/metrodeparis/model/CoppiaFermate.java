package it.polito.tdp.metrodeparis.model;

public class CoppiaFermate {
	
	private Fermata fPartenza;
	private Fermata fArrivo;
	private int idLinea;
	public Fermata getfPartenza() {
		return fPartenza;
	}
	public Fermata getfArrivo() {
		return fArrivo;
	}
	public int getIdLinea() {
		return idLinea;
	}
	
	public CoppiaFermate(Fermata fPartenza, Fermata fArrivo, int idLinea) {
		this.fPartenza = fPartenza;
		this.fArrivo = fArrivo;
		this.idLinea = idLinea;
	}
	


	

}
