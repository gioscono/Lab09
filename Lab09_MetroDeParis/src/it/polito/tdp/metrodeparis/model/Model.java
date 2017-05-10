package it.polito.tdp.metrodeparis.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	public void Model(){
	}
	
	private List<Fermata> listaFermate;
	private WeightedGraph<Fermata, Tratta> grafo;
	private List<CoppiaFermate> coppie;
	private List<Linea> linee;
	
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
		grafo = new WeightedMultigraph<Fermata, Tratta>(Tratta.class);
		//AGGIUNGO I VERTICI
		for(Fermata f : this.getFermate()){
			grafo.addVertex(f);
		}
		
		MetroDAO dao = new MetroDAO();
		coppie = dao.getCoppieFermate();
		linee = dao.getLinee();
		
		for(CoppiaFermate cf : coppie){
			Tratta t = grafo.addEdge(cf.getfPartenza(), cf.getfArrivo());
			t.setLinea(this.cercaLinea(cf.getIdLinea()));
			grafo.setEdgeWeight(t, calcolaPeso(cf.getfPartenza(), cf.getfArrivo(), t.getLinea()));
		}
		//System.out.println(grafo);
		
	}

	private double calcolaPeso(Fermata partenza, Fermata arrivo, Linea linea) {
		double spazio = LatLngTool.distance(partenza.getCoords(), arrivo.getCoords(), LengthUnit.KILOMETER);
		return (spazio/linea.getVelocita());
	}

	private Linea cercaLinea(int idLinea) {
		for(Linea l : linee){
			if(l.getId()==idLinea)
				return l;
		}
		return null;
	}

	public String getPercorso(Fermata partenza, Fermata arrivo) {
		 if(grafo==null)
			this.creaGrafo();
		 DijkstraShortestPath<Fermata,Tratta> percorso = new  DijkstraShortestPath<Fermata,Tratta>(grafo, partenza, arrivo); 
		 List<Tratta> lista = percorso.getPathEdgeList();
		 LinkedHashSet<Fermata> fermate = new LinkedHashSet<Fermata>();
		 String s = "";
		 
		 for(Tratta  t :lista ){
			 fermate.add(grafo.getEdgeSource(t));
			 fermate.add(grafo.getEdgeTarget(t));
		 }

		 Iterator<Fermata> it = fermate.iterator();
		 
		 while(it.hasNext()){
			 s+=it.next().toString()+"\n";
		 }
		 s+="Tempo totale: ";
		 double tempo = 0;
		 tempo += 0.5*(fermate.size()-2);
		 tempo += percorso.getPathLength()*60;
		 s+=tempo;
		 return s;
		 
	}
	
	

}
