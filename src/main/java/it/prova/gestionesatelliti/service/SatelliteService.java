package it.prova.gestionesatelliti.service;

import java.time.LocalDate;
import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

public interface SatelliteService {

	public List<Satellite> listAllElements();
	
	public Satellite caricaSingoloElemento (Long id);
	
	public void aggiorna (Satellite satelliteInstance);

	public void inserisci (Satellite satelliteInstance);
	
	public void rimuovi (Long idSatellite);
	
	public List<Satellite> findByExample(Satellite example);
	
	public void lancio(LocalDate ora, StatoSatellite stato, Long id);
	
	public void rientro(LocalDate ora, StatoSatellite stato, Long id);

	public List<Satellite> lanciatiDaDueAnniNonDisattivati();
	
	public List<Satellite> disattivatiNonRientrati();
	
	public List<Satellite> inOrbitaDa10AnniFissi();
	
	public List<Satellite> listaEmergenza();
	
	public void proceduraEmergenza();
}

