package it.prova.gestionesatelliti.service;

import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {

	public List<Satellite> listAllElements();
	
	public Satellite caricaSingoloElemento (Long id);
	
	public void aggiorna (Satellite satelliteInstance);

	public void inserisci (Satellite satelliteInstance);
	
	public void rimuovi (Long idSatellite);
	
	public List<Satellite> findByExample(Satellite example);
}
