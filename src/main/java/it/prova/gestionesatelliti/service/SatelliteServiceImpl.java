package it.prova.gestionesatelliti.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	private SatelliteRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllElements() {
		return (List<Satellite>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Satellite caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Satellite satelliteInstance) {
		repository.save(satelliteInstance);

	}

	@Override
	@Transactional
	public void inserisci(Satellite satelliteInstance) {
		repository.save(satelliteInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idSatellite) {
		repository.deleteById(idSatellite);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> findByExample(Satellite example) {
		Specification<Satellite> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getCodice()))
				predicates.add(cb.like(cb.upper(root.get("codice")), "%" + example.getCodice().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getDenominazione()))
				predicates.add(
						cb.like(cb.upper(root.get("cognome")), "%" + example.getDenominazione().toUpperCase() + "%"));

			if (example.getDataLancio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLancio"), example.getDataLancio()));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getDataRientro() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataRientro"), example.getDataRientro()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		return repository.findAll(specificationCriteria);
	}

	@Override
	@Transactional
	public void lancio(LocalDate ora, StatoSatellite stato, Long id) {

		ora = LocalDate.now();
		stato = StatoSatellite.IN_MOVIMENTO;
		repository.lancio(ora,stato, id);
		

	}

	@Override
	@Transactional
	public void rientro(LocalDate ora,StatoSatellite stato, Long id) {
		ora = LocalDate.now();
		stato = StatoSatellite.DISATTIVATO;
		repository.rientro(ora, stato, id);
		;

	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> lanciatiDaDueAnniNonDisattivati() {

		LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
		StatoSatellite notDisattivato = StatoSatellite.DISATTIVATO;
		return repository.findAllByDataLancioLessThanAndStatoNotLike(twoYearsAgo, notDisattivato);
	}

	@Override
	public List<Satellite> disattivatiNonRientrati() {
		StatoSatellite statoDisattivato = StatoSatellite.DISATTIVATO;
		return repository.findAllByStatoLikeAndDataRientroIsNull(statoDisattivato);
	}

	@Override
	public List<Satellite> inOrbitaDa10AnniFissi() {
		LocalDate tenYears = LocalDate.now().minusYears(10);
		StatoSatellite fisso = StatoSatellite.FISSO;
		return repository.findAllByDataLancioLessThanAndStatoLike(tenYears, fisso);
	}

}
