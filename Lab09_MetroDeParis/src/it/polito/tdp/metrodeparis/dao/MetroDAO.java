package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.CoppiaFermate;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();
		

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<CoppiaFermate> getCoppieFermate() {
		
		String sql = "SELECT f1.nome as nomeP, f1.id_fermata as idP, f1.coordX as coordxP, f1.coordY as coordyP, f2.nome as nomeA, f2.id_fermata as idA, f2.coordX as coordxA, f2.coordY as coordyA, id_linea "+
                     "FROM connessione, fermata f1, fermata f2 "+
                     "WHERE connessione.id_stazP = f1.id_fermata and connessione.id_stazA=f2.id_fermata";
		List<CoppiaFermate> coppie = new ArrayList<CoppiaFermate>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f1 = new Fermata( rs.getInt("idP"),rs.getString("nomeP"),  new LatLng(rs.getDouble("coordxP"), rs.getDouble("coordyP")));
				Fermata f2 = new Fermata( rs.getInt("idA"),rs.getString("nomeA"),  new LatLng(rs.getDouble("coordxA"), rs.getDouble("coordyA")));
				int i = rs.getInt("id_linea");
				coppie.add(new CoppiaFermate(f1,f2, i));
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return coppie;
	}

	public List<Linea> getLinee() {
		
		String sql = "Select id_linea, nome, velocita, intervallo from linea";
		
		List<Linea> linee = new ArrayList<Linea>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea l = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"), rs.getDouble("intervallo"));
				linee.add(l);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}
	
}
