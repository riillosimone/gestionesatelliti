package it.prova.gestionesatelliti.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

public interface SatelliteRepository extends CrudRepository<Satellite, Long>, JpaSpecificationExecutor<Satellite> {

	@Modifying
	@Query(value = "update Satellite s set s.dataLancio = ?1, s.stato =?2 where id = ?3")
	public void lancio(LocalDate now, StatoSatellite stato, Long id);

	@Modifying
	@Query(value = "update Satellite s set s.dataRientro = ?1 , s.stato =?2 where id = ?3")
	public void rientro(LocalDate now, StatoSatellite stato, Long id);

	@Modifying
	@Query("update Satellite s set s.dataRientro = ?1, s.stato =?2 where s.dataLancio is not null and s.stato in ?3 ")
	public void proceduraEmergenza(LocalDate now, StatoSatellite disattivato, Iterable<StatoSatellite> stati);

	public List<Satellite> findAllByDataLancioLessThanAndStatoNotLike(LocalDate twoYearsAgo,
			StatoSatellite notDisattivato);

	public List<Satellite> findAllByStatoLikeAndDataRientroIsNull(StatoSatellite statoDisattivato);

	public List<Satellite> findAllByDataLancioLessThanAndStatoLike(LocalDate tenYears, StatoSatellite fisso);

	public List<Satellite> findAllByDataLancioIsNotNullAndStatoNotNullAndStatoNotLike(StatoSatellite statoDisattivato);
}
