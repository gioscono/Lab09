package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLng;

public class FermataConLinea extends Fermata{


	private int codLinea;
	
	public FermataConLinea(int idFermata, String nome, LatLng coords, int codLinea) {
		super(idFermata, nome, coords);
		this.codLinea = codLinea;
		
	}

	public int getCodLinea() {
		return codLinea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + codLinea;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FermataConLinea other = (FermataConLinea) obj;
		if (codLinea != other.codLinea)
			return false;
		return true;
	}

	
	


}
