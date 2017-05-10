package it.polito.tdp.metrodeparis.model;

public class CoppiaFermate {
	
	private FermataConLinea fPartenza;
	private FermataConLinea fArrivo;
	private int idLinea;
	
	public FermataConLinea getfPartenza() {
		return fPartenza;
	}
	public FermataConLinea getfArrivo() {
		return fArrivo;
	}
	public int getIdLinea() {
		return idLinea;
	}
	
	public CoppiaFermate(FermataConLinea fPartenza, FermataConLinea fArrivo, int idLinea) {
		this.fPartenza = fPartenza;
		this.fArrivo = fArrivo;
		this.idLinea = idLinea;
	}
	


	

}
