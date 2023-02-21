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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Lote;
import financeiro.model.Marca;
import financeiro.repository.MarcaRepository;

@Controller
public class MarcaController {
  
	@Autowired
	MarcaRepository marcaRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/marca")
	public ModelAndView inicio() {
		
		ModelAndView modelAndView = new ModelAndView("/marca");
		
		modelAndView.addObject("listMarca", marcaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("loteObj", modelAndView);		
		
		return modelAndView;		
		
	} 
	
	public ModelAndView salvarMarca(@Valid Marca marca, BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView("/marca");
		Iterable<Marca> marcaIt = null;
		
		if(bindingResult.hasErrors()) {
			
			marcaIt = marcaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
			modelAndView.addObject("listMarca", marcaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			modelAndView.addObject("marcaObj", new Marca());
			
			List<String> msg = new ArrayList<String>();
			
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
				
				modelAndView.addObject("msg", msg);
			}
			
			return modelAndView;
			
		}
		
		marcaRepository.save(marca);
		
		modelAndView.addObject("listMarca", marcaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("marcaObj", new Marca());
		
		return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/marca")
	public ModelAndView listaMarca(){
		
		ModelAndView modelAndView = new ModelAndView("/marca");
		Iterable<Marca> marcaLt = marcaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		modelAndView.addObject("listMarca", marcaLt);
		modelAndView.addObject("marcaObj", new Marca());
		
		return modelAndView;
	}
	
	@GetMapping("/marcapag")
	public ModelAndView carregaMarcaPorPaginacao(			
	  @PageableDefault(size = 5) Pageable pageable, 
	  ModelAndView model, 
	  @RequestParam("nomeMarcaPesquisa") String nomeMarcaPesquisa) {

      Page<Marca> pageMarca = null; 

      if ((nomeMarcaPesquisa != null && !nomeMarcaPesquisa.isEmpty())) {
  
	    pageMarca = marcaRepository.findByNomePage(nomeMarcaPesquisa, pageable);  

      }

      model.addObject("listMarca", pageMarca);
      model.addObject("marcaObj", new Marca());
      model.addObject("nomeLotePesquisa", nomeMarcaPesquisa);
      model.setViewName("/marca");

      return model;
      
	}
	
	@GetMapping("/editarMarca/{id}")
	public ModelAndView editarMarca(@PathVariable(name = "id" ) Long id) {
		
		Optional<Marca>marca = marcaRepository.findById(id);
		
		ModelAndView modelAndView = new ModelAndView("/marca");
		
		Iterable<Marca> marcaIt = marcaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		
		modelAndView.addObject("listMarca", marcaIt);
		modelAndView.addObject("marcaObj", marca.get());
		
		modelAndView.setViewName("/marca");
		
		return modelAndView;
		
	}
	
	@GetMapping("/removerMarca/{id}")
	public ModelAndView removerMarca(@PathVariable(name = "id") Long id){
		
		marcaRepository.deleteById(id);
		 
		ModelAndView modelAndView = new ModelAndView("/marca");
		modelAndView.addObject("listMarca", marcaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("marcaObj", new Marca());
			
		return modelAndView;
		
	}
   	
	@PostMapping("**/pesquisarMarca")
	public ModelAndView pesquisarMarca(
			@RequestParam("nomeMarcaPesquisa") String nomeMarcaPesquisa,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/marca");
		modelAndView.addObject("nomeMarcaPesquisa", nomeMarcaPesquisa);
		modelAndView.addObject("listMarca", marcaRepository.findByNomePage(nomeMarcaPesquisa.trim().toUpperCase(), pageable));
		modelAndView.addObject(nomeMarcaPesquisa, modelAndView);
		
		return modelAndView;
		
	}
	
	
}
