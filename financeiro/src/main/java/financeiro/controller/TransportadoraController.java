package financeiro.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Transportadora;
import financeiro.repository.TransportadoraRepository;

@Controller
public class TransportadoraController {
	
	@Autowired
	TransportadoraRepository transportadoraRepository;
	
	private final Integer totalRegistroPorPagina = 5;
	
	@RequestMapping(method = RequestMethod.GET, value = "/transportadora")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("listTransportadora", transportadoraRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome"))));
		modelAndView.addObject("transportadoraObj", new Transportadora());
		return modelAndView;
		
	}	
		
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarTransportadora")
	private ModelAndView salvarTransportadora(@Valid Transportadora transportadora, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("/transportadora");
		
		Iterable<Transportadora> transportadoraIt = null;
		transportadoraIt = transportadoraRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));

		if (bindingResult.hasErrors()) {
		  modelAndView.addObject("listTransportadora", transportadoraIt);
		  modelAndView.addObject("transportadoraObj", new Transportadora());
		  
		  List<String> msg = new ArrayList<String>();
		  for (ObjectError objectError : bindingResult.getAllErrors()) {
			  msg.add(objectError.getDefaultMessage()); // vem das anotaÃ§Ãµes @NotEmpty e outras
			  
			  modelAndView.addObject("msg", msg);			  
		  }
		  
		  
		  return modelAndView;			
		
		}
		
		transportadoraRepository.save(transportadora);
		transportadoraIt = transportadoraRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		modelAndView.addObject("listTransportadora", transportadoraIt);
		modelAndView.addObject("transportadoraObj", new Transportadora());
		
		return modelAndView;		
		
	}

	@RequestMapping("/removerTransportadora/{idTransportadora}")
	public ModelAndView removeTransportadora(@PathVariable(name = "idTransportadora") Long idTransportadora) {
		
		transportadoraRepository.deleteById(idTransportadora);		
		Iterable<Transportadora> transportadoraIt;		
		transportadoraIt = transportadoraRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		
		ModelAndView modelAndView = new ModelAndView("/transportadora");
		modelAndView.addObject("listTransportadora", transportadoraIt);
		modelAndView.addObject("transportadoraObj", new Transportadora());		
		
		return modelAndView;
		
	}
	
		
	@GetMapping("/editarTransportadora/{idTransportadora}")	
	public ModelAndView editarTransportadora(@PathVariable(name="idTransportadora") Long idTransportadora ) {
	
		ModelAndView modelAndView = new ModelAndView("/transportadora");
		Optional<Transportadora> transportadora = transportadoraRepository.findById(idTransportadora);
		Iterable<Transportadora> transportadoraIt = transportadoraRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome"))); 
		modelAndView.addObject("listTransportadora", transportadoraIt);
		modelAndView.addObject("transportadoraObj", transportadora.get());
		
		return modelAndView;
	}
	
	@RequestMapping("**/pesquisarTransportadora")
	public ModelAndView listaTransportadora(
			@RequestParam("nomeTransportadoraPesquisa") String nomeTransportadoraPesquisa,
			@RequestParam("numeroInscricaoPesquisa") String numeroInscricaoPesquisa,
            @PageableDefault(size = 5, sort={"nome"}) Pageable pageable){
		
		ModelAndView modelAndView = new ModelAndView("/transportadora");

		Boolean pesquisarPorNomeAndInscricao = (numeroInscricaoPesquisa != null) && (!numeroInscricaoPesquisa.isEmpty()) &&
				                               (nomeTransportadoraPesquisa != null) && (!nomeTransportadoraPesquisa.isEmpty());
		
		Iterable<Transportadora> transportadoraIt = null;		
		  
		transportadoraIt = transportadoraRepository.findByNomePage(nomeTransportadoraPesquisa.trim().toUpperCase(), pageable);		
		
		if (pesquisarPorNomeAndInscricao) {
		  transportadoraIt = transportadoraRepository.findByNomeInscricaoPage(nomeTransportadoraPesquisa.trim().toUpperCase(), 
				                                                              numeroInscricaoPesquisa.trim().toUpperCase(),  
				                                                              pageable);
		} 
		
		modelAndView.addObject("numeroInscricaoPesquisa", numeroInscricaoPesquisa);
		modelAndView.addObject("nomeTransportadoraPesquisa", nomeTransportadoraPesquisa);
		modelAndView.addObject("listTransportadora", transportadoraIt);
		modelAndView.addObject("transportadoraObj", new Transportadora());
		
		
		return modelAndView;

    }
	
	@GetMapping("/transportadorapag")
	public ModelAndView carregaTransportadoraPorPaginacao(
			@PageableDefault(size = 5) Pageable pageable,
			ModelAndView modelAndView,
		    @RequestParam("nomeTransportadoraPesquisa") String nomeTransportadoraPesquisa,
		    @RequestParam("numeroInscricaoPesquisa") String numeroInscricaoPesquisa){
		
		Page<Transportadora> pageTransportadora = null;
		
		Boolean pesquisarPorNomeAndInscricao = (numeroInscricaoPesquisa != null) && (!numeroInscricaoPesquisa.isEmpty()) &&
                (nomeTransportadoraPesquisa != null) && (!nomeTransportadoraPesquisa.isEmpty());

        Boolean pesquisarPorNome = (nomeTransportadoraPesquisa != null) && (!nomeTransportadoraPesquisa.isEmpty());
		
		if(pesquisarPorNomeAndInscricao) {
			pageTransportadora = transportadoraRepository.findByNomeInscricaoPage(nomeTransportadoraPesquisa, numeroInscricaoPesquisa, pageable);
		}else if(pesquisarPorNome){
			pageTransportadora = transportadoraRepository.findByNomePage(nomeTransportadoraPesquisa, pageable);	
		}
		
		
		modelAndView.addObject("transportadoraObj", new Transportadora());
		modelAndView.addObject("listTransportadora", pageTransportadora);
		modelAndView.addObject("numeroInscricaoPesquisa", numeroInscricaoPesquisa);
		modelAndView.addObject("nomeTransportadoraPesquisa", nomeTransportadoraPesquisa);
		modelAndView.setViewName("/transportadora");		
		
		return modelAndView;
	
	}
	

}
