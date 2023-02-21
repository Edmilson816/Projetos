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

import financeiro.model.Cliente;
import financeiro.model.Empresa;
import financeiro.model.FormaPagamento;
import financeiro.repository.FormaPagamentoRepository;

@Controller
public class FormaPagamentoController {
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/formaPagamento")
	private ModelAndView inicio() {

		ModelAndView modelAndView = new ModelAndView("/formaPagamento");
		
		modelAndView.addObject("formaPagamentoObj", new FormaPagamento());
		modelAndView.addObject("listformaPagamento", formaPagamentoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		
		return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarFormaPagamento")
	public ModelAndView salvarFormaPagamento(@Valid FormaPagamento formaPagamento, BindingResult bidingResult) {
		
		ModelAndView modelAndView = new ModelAndView("/formaPagamento");
		Iterable<FormaPagamento> formaPagamentoIt = null;
		
		if(bidingResult.hasErrors()) {
			formaPagamentoIt = formaPagamentoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
			modelAndView.addObject("listformaPagamento", formaPagamentoIt);
			modelAndView.addObject("formaPagamentoObj", new FormaPagamento());
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bidingResult.getAllErrors()) {
			
				msg.add(objectError.getDefaultMessage());
				
				modelAndView.addObject("msg", msg);				
			}
			
			return modelAndView;			
		}
		
		formaPagamentoRepository.save(formaPagamento);

		formaPagamentoIt = formaPagamentoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
	    
		modelAndView.addObject("listformaPagamento", formaPagamentoIt);
	    modelAndView.addObject("formaPagamentoObj", new FormaPagamento());
	    
	    return modelAndView;
		
	}
	
	@GetMapping("/editarFormaPagamento/{idFormaPagamento}")
	public ModelAndView editarFormaPagamento(@PathVariable("idFormaPagamento") Long idFormaPagamento){
		
		Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(idFormaPagamento);
		
		ModelAndView modelAndView = new ModelAndView("/formaPagamento");

		Iterable<FormaPagamento> formaPagamentoIt = formaPagamentoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		
		modelAndView.addObject("listformaPagamento", formaPagamentoIt);
		modelAndView.addObject("formaPagamentoObj", formaPagamento.get());
		
		
		return modelAndView;
		
	}
	
	@GetMapping("/removerFormaPagamento/{idFormaPagamento}")
	public ModelAndView removerFormaPagamento(@PathVariable("idFormaPagamento") Long idFormaPagamento) {
		
		formaPagamentoRepository.deleteById(idFormaPagamento);
		
		ModelAndView modelAndView = new ModelAndView("/formaPagamento");
		modelAndView.addObject("listformaPagamento", formaPagamentoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("formaPagamentoObj", new FormaPagamento());
		
		return modelAndView;
	} 
	
	@PostMapping("**/pesquisarFormaPagamento")  
	public ModelAndView pesquisarFormaPagamento(
			@RequestParam("nomeFormaPagamento") String nomeFormaPagamento,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/formaPagamento");
        modelAndView.addObject("listformaPagamento", formaPagamentoRepository.findByNomePage(nomeFormaPagamento.trim().toUpperCase(), pageable));
		modelAndView.addObject("formaPagamentoObj", new FormaPagamento());
		return modelAndView;		
	}
	
	@GetMapping("/formaPagamentopag")
	public ModelAndView carregaformaPagamentoPorPaginacao(
			@PageableDefault(size = 5) Pageable pageable, 
			ModelAndView model, 
			@RequestParam("nomeFormaPagamentoPesquisa") String nomeFormaPagamentoPesquisa) {
		
	    Page<FormaPagamento> pageFormaPagamento = null; 
		
		if ((nomeFormaPagamentoPesquisa != null && !nomeFormaPagamentoPesquisa.isEmpty())) {
	      
			pageFormaPagamento = formaPagamentoRepository.findByNomePage(nomeFormaPagamentoPesquisa, pageable);  
		
		}
		
		model.addObject("listformaPagamento", pageFormaPagamento);
		model.addObject("formaPagamentoObj", new FormaPagamento());
		model.addObject("nomeFormaPagamentoPesquisa", nomeFormaPagamentoPesquisa);
		
		model.setViewName("/formaPagamento");
		
		return model;		
		
	}	
	
	
	

}
