package it.polito.tdp.metrodeparis.model;

import java.util.List;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	public void Model(){
	}
	
	private List<Fermata> listaFermate;
	private WeightedGraph<Fermata, DefaultWeightedEdge> grafo;

	public List<Fermata> getFermate() {
		if(listaFermate==null){
			MetroDAO dao = new MetroDAO();
			listaFermate = dao.getAllFermate();
		}
		return listaFermate;
	}
	
	public WeightedGraph getGrafo(){
		if(grafo==null){
			this.creaGrafo();
		}
		return grafo;
	}

	private void creaGrafo() {
		
		
	}
	
	

}
