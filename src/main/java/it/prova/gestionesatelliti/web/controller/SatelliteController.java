package it.prova.gestionesatelliti.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.service.SatelliteService;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService satelliteService;

	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.listAllElements();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteService.findByExample(example);
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (satellite.getDataLancio() == null && satellite.getDataRientro() != null) {
			result.rejectValue("dataLancio", "error.dataLancio");
		} else if (satellite.getDataRientro()!=null && satellite.getDataLancio().isAfter(satellite.getDataRientro())) {
			result.rejectValue("dataLancio", "error.dataLancioAfter");
			result.rejectValue("dataRientro", "error.dataRientroBefore");
		}  else if (satellite.getDataLancio()!= null && satellite.getDataLancio().isAfter(LocalDate.now()) && satellite.getStato()!=null) {
			result.rejectValue("stato", "error.statoAfter");
		} 

		if (result.hasErrors()) {
			return "satellite/insert";
		}

		satelliteService.inserisci(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";

	}

	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/delete/{idSatellite}")
	public String delete(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("delete_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/delete";
	}

	@PostMapping("/delete")
	public String delete(Long idSatellite, RedirectAttributes redirectAttrs) {
		satelliteService.rimuovi(idSatellite);
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/edit/{idSatellite}")
	public String edit(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("edit_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/edit";
	}

	@PostMapping("/edit")
	public String edit(@Valid @ModelAttribute("edit_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (satellite.getDataLancio() == null && satellite.getDataRientro() != null) {
			result.rejectValue("dataLancio", "error.dataLancio");
		} else if (satellite.getDataLancio().isAfter(satellite.getDataRientro())) {
			result.rejectValue("dataLancio", "error.dataLancioAfter");
			result.rejectValue("dataRientro", "error.dataRientroBefore");
		} else if (satellite.getDataLancio().isAfter(LocalDate.now()) && satellite.getStato()!=null) {
			result.rejectValue("stato", "error.statoAfter");
		}
		

		if (result.hasErrors()) {
			return "satellite/edit";
		}

		satelliteService.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

//	@GetMapping("/lancia/{idSatellite}")
//	public String lancia(@PathVariable(required = true) Long idSatellite, Model model) {
//		model.addAttribute("satellite_list_attribute", satelliteService.caricaSingoloElemento(idSatellite));
//		return "/lancia";
//	}
	
	
	@PostMapping("/lancio")
	public String lancio(Long id, StatoSatellite stato, LocalDate dataLancio, RedirectAttributes redirectAttrs) {
		
		satelliteService.lancio(dataLancio,stato,id);
		redirectAttrs.addFlashAttribute("successMessage", "Satellite Lanciato!");
		return "redirect:/satellite";
	}
	
	@PostMapping("/rientro")
	public String rientro(Long id, StatoSatellite stato, LocalDate dataLancio, RedirectAttributes redirectAttrs) {
		
		satelliteService.rientro(dataLancio,stato,id);
		redirectAttrs.addFlashAttribute("successMessage", "Satellite Rientrato!");
		return "redirect:/satellite";
	}
}
