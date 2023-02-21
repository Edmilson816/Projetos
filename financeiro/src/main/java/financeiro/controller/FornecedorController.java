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

import financeiro.model.Cliente;
import financeiro.model.Fornecedor;
import financeiro.repository.FornecedorRepository;

@Controller
public class FornecedorController {
	
	@Autowired
	FornecedorRepository fornecedorRepository;
	
	private final Integer totalRegistroPorPagina = 5;
	
	@RequestMapping(method = RequestMethod.GET, value = "/fornecedor")
	public ModelAndView inicio(){
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listFornecedor", fornecedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")))); 		
		modelAndView.addObject("fornecedorObj", new Fornecedor());		
		return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarFornecedor")
	public ModelAndView salvarFornecedor(@Valid Fornecedor fornecedor, BindingResult bindingResult){
		
		ModelAndView modelAndView = new ModelAndView("/fornecedor");
		Iterable<Fornecedor> fornecedorIt = null;		
		
		if(bindingResult.hasErrors()) {
			
		    fornecedorIt = fornecedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		    modelAndView.addObject("listFornecedor",fornecedorIt);
		    modelAndView.addObject("fornecedorObj", new Fornecedor());
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // vem das anotaÃ§Ãµes @NotEmpty e outras
				
     		modelAndView.addObject("msg", msg);
			}
			
			return modelAndView;
		}		
		
		fornecedorRepository.save(fornecedor);
		
		fornecedorIt = fornecedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
	    modelAndView.addObject("listFornecedor",fornecedorIt);
	    modelAndView.addObject("fornecedorObj", new Fornecedor());
	    
		return modelAndView;		
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/listaFornecedor")
	public ModelAndView listaFornecedor() {
		ModelAndView modelAndView = new ModelAndView();
		Iterable<Fornecedor> fornecedorIt = fornecedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));				
		modelAndView.addObject("listFornecedor", fornecedorIt);
		modelAndView.addObject("fornecedorObj", new Fornecedor());
		return modelAndView;
	}
	
	@GetMapping("/fornecedorpag")
	public ModelAndView carregaFornecedorPorPaginacao(
			@PageableDefault(size = 5) Pageable pageable,
			ModelAndView modelAndView,
			@RequestParam("nomeFornecedorPesquisa") String nomeFornecedorPesquisa,
			@RequestParam("inscricaoPesquisa") String inscricaoPesquisa) {
		
		Page<Fornecedor> pageFornecedor = null;
		     
		if ((nomeFornecedorPesquisa != null && !nomeFornecedorPesquisa.isEmpty()) && (inscricaoPesquisa != null && !inscricaoPesquisa.isEmpty())) {
			
			pageFornecedor = fornecedorRepository.findByNomeInscricaoPage(nomeFornecedorPesquisa, inscricaoPesquisa, pageable);
		
		}else if (nomeFornecedorPesquisa != null && nomeFornecedorPesquisa.isEmpty()) {
		
			pageFornecedor = fornecedorRepository.findByNomePage(nomeFornecedorPesquisa, pageable);
		
		}
		
		
		/*if ((nomeFornecedorPesquisa != null && !nomeFornecedorPesquisa.isEmpty()) && (inscricaoPesquisa != null && !inscricaoPesquisa.isEmpty())) {
		      
			pageFornecedor = fornecedorRepository.findByNomeInscricaoPage(nomeFornecedorPesquisa, inscricaoPesquisa, pageable);  
		
		}else if(nomeFornecedorPesquisa != null && nomeFornecedorPesquisa.isEmpty()){
		
			pageFornecedor = fornecedorRepository.findByNomePage(nomeFornecedorPesquisa,pageable);  
		
		}*/
		
		
		modelAndView.addObject("listFornecedor", pageFornecedor);
		modelAndView.addObject("fornecedorObj", new Fornecedor());
		modelAndView.addObject("nomeFornecedorPesquisa", nomeFornecedorPesquisa);
		modelAndView.addObject("inscricaoPesquisa", inscricaoPesquisa);
		modelAndView.setViewName("/fornecedor");
		
		return modelAndView; 
		
	}
	
	@GetMapping("/editarFornecedor/{idfornecedor}")
	public ModelAndView editarFornecedor(@PathVariable("idfornecedor") Long idfornecedor) {
		
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(idfornecedor);
		ModelAndView modelAndView = new ModelAndView("/fornecedor");
		Iterable<Fornecedor> fornecedorIt = fornecedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		modelAndView.addObject("listFornecedor", fornecedorIt);
		modelAndView.addObject("fornecedorObj", fornecedor.get());		
		
		return modelAndView;
	}
	
	@RequestMapping("/removerFornecedor/{idfornecedor}")
	public ModelAndView removeFornecedor(@PathVariable("idfornecedor") Long idfornecedor) {		
		
		fornecedorRepository.deleteById(idfornecedor);
		
		ModelAndView modelAndView = new ModelAndView("/fornecedor");
		Iterable<Fornecedor> fornecedorIt = fornecedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		modelAndView.addObject("listFornecedor", fornecedorIt);
		modelAndView.addObject("fornecedorObj", new Fornecedor());		
		return modelAndView;
		
	}
	
	
	@RequestMapping("**/pesquisarFornecedor")
	public ModelAndView pesquisarFornecedor(
			@RequestParam("nomeFornecedorPesquisa") String nomeFornecedorPesquisa,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
	  ModelAndView modelAndView = new ModelAndView("/fornecedor");
	  modelAndView.addObject("listFornecedor", fornecedorRepository.findByNomePage(nomeFornecedorPesquisa.trim().toUpperCase(), pageable));
      modelAndView.addObject("fornecedorObj", new Fornecedor());
	  
	  return modelAndView;
	}
		

}
