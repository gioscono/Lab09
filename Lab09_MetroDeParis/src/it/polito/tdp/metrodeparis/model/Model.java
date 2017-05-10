package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
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
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	public void Model(){
	}
	
	private List<FermataConLinea> listaFermate;
	private List<Fermata> fermateSemplici;
	private WeightedGraph<Fermata, Tratta> grafo;
	private List<CoppiaFermate> coppie;
	private List<Linea> linee;
	
	public List<FermataConLinea> getFermate() {
		if(listaFermate==null){
			MetroDAO dao = new MetroDAO();
			listaFermate = dao.getAllFermate();
		}
		return listaFermate;
	}
	public List<Fermata> fermateSemplici(){
		if(fermateSemplici == null){
			MetroDAO dao = new MetroDAO();
			fermateSemplici = dao.getAllFermateController();
		}
		return fermateSemplici;
	}
	
	public WeightedGraph getGrafo(){
		if(grafo==null){
			this.creaGrafo();
		}
		return grafo;
	}

	private void creaGrafo() {
		grafo = new DirectedWeightedMultigraph<Fermata, Tratta>(Tratta.class);
		//AGGIUNGO I VERTICI
		for(FermataConLinea f : this.getFermate()){
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
		
		for(FermataConLinea f: listaFermate){
			for(FermataConLinea f1: listaFermate){
				if(f.getIdFermata()==f1.getIdFermata() && f1.getCodLinea()!=f.getCodLinea()){
					Tratta t = grafo.addEdge(f, f1);
					t.setLinea(this.cercaLinea(f1.getCodLinea()));
					grafo.setEdgeWeight(t, this.cercaLinea(f1.getCodLinea()).getIntervallo());
				}
			}
		}
		//System.out.println(grafo);
		
	}

	private double calcolaPeso(FermataConLinea partenza, FermataConLinea arrivo, Linea linea) {
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

	public String getPercorso(Fermata partenza, Fermata destinazione) {
		 if(grafo==null)
			this.creaGrafo();
		 List<FermataConLinea> partenze = this.cercaFermateSpecifiche(partenza);
		 List<FermataConLinea> destinazioni = this.cercaFermateSpecifiche(destinazione);
		 DijkstraShortestPath<Fermata,Tratta> percorso = new  DijkstraShortestPath<Fermata,Tratta>(grafo, fermata, fermata2); 
		 List<Tratta> lista = percorso.getPathEdgeList();
		 LinkedHashSet<FermataConLinea> fermate = new LinkedHashSet<FermataConLinea>();
		 String s = "";
		 
		 for(Tratta  t :lista ){
			 fermate.add(grafo.getEdgeSource(t));
			 fermate.add(grafo.getEdgeTarget(t));
		 }

		 Iterator<FermataConLinea> it = fermate.iterator();
		 
		 while(it.hasNext()){
			 s+=it.next().toString()+"\n";
		 }
		 s+="Tempo totale: ";
		 double tempo = 0;
		 tempo += 0.5*(fermate.size()-2);
		 tempo += percorso.getPathLength()*30;
		 s+=tempo;
		 return s;
		 
	}
	private List<FermataConLinea> cercaFermateSpecifiche(Fermata fermata) {
		List<FermataConLinea> fermate = new ArrayList<FermataConLinea>();
		for(FermataConLinea l : listaFermate){
			if(l.getIdFermata()==fermata.getIdFermata())
				fermate.add(l);
		}
		return fermate;
	}
	
	

}
