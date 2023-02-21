package financeiro.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Lote;
import financeiro.repository.LoteRepository;

@Controller
public class LoteController {
	
	@Autowired
	LoteRepository loteRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/lote")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/lote");
		modelAndView.addObject("listLote", loteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("loteObj", new Lote());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarLote")
	public ModelAndView salvarLote(@Valid Lote lote, BindingResult bindingResult) throws IOException{
	    
		ModelAndView modelAndView = new ModelAndView("/lote");
		Iterable<Lote> loteIt = null;
		
		if(bindingResult.hasErrors()) {
			
			loteIt = loteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		    modelAndView.addObject("listLote",loteIt);
		    modelAndView.addObject("loteObj", new Lote());
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // vem das anotaÃ§Ãµes @NotEmpty e outras
				
     		modelAndView.addObject("msg", msg);
		    
			}
			
			return modelAndView;			
			
		}	
		
		loteRepository.save(lote);
	    
	    
	    loteIt = loteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
	    modelAndView.addObject("listLote", loteIt);
	    modelAndView.addObject("loteObj", new Lote());
	    
	    return modelAndView; 
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/listaLote")
	public ModelAndView listaLote() {
		ModelAndView modelAndView = new ModelAndView("/lote");
		Iterable<Lote> loteIt = loteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		modelAndView.addObject("listLote", loteIt);
		modelAndView.addObject("loteObj", new Lote());
		
		return modelAndView;
		
	}
	
	@GetMapping("/lotepag")
	public ModelAndView carregaLotePorPaginacao(
			@PageableDefault(size = 5) Pageable pageable, 
			ModelAndView model, 
			@RequestParam("nomeLotePesquisa") String nomeLotePesquisa) {
		
	    Page<Lote> pageLote = null; 
		
		if ((nomeLotePesquisa != null && !nomeLotePesquisa.isEmpty())) {
	      
			pageLote = loteRepository.findByNomePage(nomeLotePesquisa, pageable);  
		
		}
		
		model.addObject("listLote", pageLote);
		model.addObject("loteObj", new Lote());
		model.addObject("nomeLotePesquisa", nomeLotePesquisa);
		model.setViewName("/lote");
		
		return model;		
		
	}	
	
	@GetMapping("/editarLote/{idlote}")
    public ModelAndView editarLote(@PathVariable("idlote") Long idlote) {
		
		Optional<Lote> lote = loteRepository.findById(idlote);
		
		ModelAndView modelAndView = new ModelAndView("/lote");
		Iterable<Lote> loteIt = loteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		modelAndView.addObject("listLote", loteIt);
		modelAndView.addObject("loteObj", lote.get());		
		modelAndView.setViewName("/lote");		
    	
    	return modelAndView;
    }	
	
	@GetMapping("/removerLote/{idlote}")
	public ModelAndView removerLote(@PathVariable("idlote") Long idlote) {
		
		loteRepository.deleteById(idlote);
		
		ModelAndView modelAndView = new ModelAndView("/lote");
		modelAndView.addObject("listLote", loteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("loteObj", new Lote());
		return modelAndView;
		
	}
	
	
	@PostMapping("**/pesquisarLote")  
	public ModelAndView pesquisarCliente(
			@RequestParam("nomeLotePesquisa") String nomeLotePesquisa,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/lote");
		modelAndView.addObject("nomeLotePesquisa", nomeLotePesquisa);
        modelAndView.addObject("listLote", loteRepository.findByNomePage(nomeLotePesquisa.trim().toUpperCase(), pageable));
		modelAndView.addObject("loteObj", new Lote());
		return modelAndView;		
	}
	

}
