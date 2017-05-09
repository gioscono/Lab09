package it.polito.tdp.metrodeparis.model;

import java.util.List;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	public void Model(){
	}
	
	private List<Fermata> listaFermate;

	public List<Fermata> getFermate() {
		if(listaFermate==null){
			MetroDAO dao = new MetroDAO();
			listaFermate = dao.getAllFermate();
		}
		return listaFermate;
	}
	
	

}
