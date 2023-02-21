package financeiro.controller;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Cliente;
import financeiro.model.Empresa;
import financeiro.repository.EmpresaRepository;

@Controller
public class EmpresaController {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/empresa")
	private ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/empresa");
		modelAndView.addObject("empresaObj", new Empresa());
		modelAndView.addObject("listEmpresa", empresaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		return modelAndView;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarEmpresa")
	public ModelAndView salvarEmpresa(@Valid Empresa empresa, BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView("/empresa");
		Iterable<Empresa> empresaIt = null;
		
		if(bindingResult.hasErrors()) {
			empresaIt = empresaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
			modelAndView.addObject("listEmpresa", empresaIt);
			modelAndView.addObject("empresaObj", new Empresa());
			
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				
				msg.add(objectError.getDefaultMessage()); // vem das anotações @NotEmpty e outras
				
				modelAndView.addObject("msg", msg);
			
			}
			
			return modelAndView;
			
		}
		
		empresaRepository.save(empresa);
	    
	    empresaIt = empresaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
	    
		modelAndView.addObject("listEmpresa", empresaIt);
	    modelAndView.addObject("empresaObj", new Empresa());
	    
	    return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/empresa")
	public ModelAndView listaEmpresa(){
		
		ModelAndView modelAndView = new ModelAndView("/empresa");
		
		Iterable<Empresa> empresIt = empresaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		modelAndView.addObject("listEmpresa", empresIt);
		modelAndView.addObject("empresaObj", new Empresa());
		
		return modelAndView;
		
	}
	
	public ModelAndView carregaPorPaginacao(
			@PageableDefault(size = 5) Pageable pageable,
			ModelAndView model,
			@RequestParam("nomeEmpresaPesquisa") String nomeEmpresaPesquisa,
			@RequestParam("inscricaoPesquisa") String inscricaoPesquisa) {
		
		Page<Empresa> pageEmpresa = null;
		
		if ((nomeEmpresaPesquisa != null && !nomeEmpresaPesquisa.isEmpty()) &&
		  (inscricaoPesquisa != null && !inscricaoPesquisa.isEmpty())){
		  
			pageEmpresa = empresaRepository.findByNomeInscricaoPage(nomeEmpresaPesquisa, inscricaoPesquisa, pageable);
			
		}else if(nomeEmpresaPesquisa != null && !nomeEmpresaPesquisa.isEmpty()) {
			pageEmpresa = empresaRepository.findByNomePage(nomeEmpresaPesquisa, pageable);
			
		}else if(inscricaoPesquisa != null && !inscricaoPesquisa.isEmpty()) {
			pageEmpresa = empresaRepository.findByInscricaoPage(inscricaoPesquisa, pageable);
		}
		
		model.addObject("listEmpresa", pageEmpresa);
		model.addObject("empresaObj", new Empresa());
		model.addObject("nomeEmpresaPesquisa", nomeEmpresaPesquisa);
		model.addObject("inscricaoPesquisa", inscricaoPesquisa);
		model.setViewName("/empresa");
		
		return model; 
		
	}
	
	@GetMapping("/editarEmpresa/{idempresa}")
	public ModelAndView editarEmpresa(@PathVariable("idempresa") Long idempresa) {
		
		Optional<Empresa> empresa = empresaRepository.findById(idempresa);
		
		ModelAndView modelAndView = new ModelAndView("/empresa");
		
		Iterable<Empresa> empresaIt = empresaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		modelAndView.addObject("empresaIt", empresaIt);
		modelAndView.addObject("empresaObj", empresa.get());
		
		return modelAndView;
	}
	
	@GetMapping("/removerEmpresa/{idempresa}")
	public ModelAndView removerEmpresa(@PathVariable("idempresa") Long idempresa) {
		
		empresaRepository.deleteById(idempresa);
		
		ModelAndView modelAndView = new ModelAndView("/empresa");
		modelAndView.addObject("listEmpresa", empresaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("empresaObj", new Empresa());
		return modelAndView;
		
	}
	
	@PostMapping("**/pesquisarEmpresa")  
	public ModelAndView pesquisarEmpresa(
			@RequestParam("nomeEmpresaPesquisa") String nomeEmpresaPesquisa,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/empresa");
        modelAndView.addObject("listEmpresa", empresaRepository.findByNomePage(nomeEmpresaPesquisa.trim().toUpperCase(), pageable));
		modelAndView.addObject("empresaObj", new Empresa());
		return modelAndView;		
	}
		

}
