package financeiro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import financeiro.model.Vendedor;
import financeiro.repository.VendedorRepository;

@Controller
public class VendedorController {
	
	@Autowired
	VendedorRepository vendedorRepository;
	
	private final Integer totalRegistroPorPagina = 5;
	
	@RequestMapping(method = RequestMethod.GET, value = "/vendedor")
	public ModelAndView inicio(){
		ModelAndView modelAndView = new ModelAndView("/vendedor");		
		modelAndView.addObject("listVendedor", vendedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome"))));
		modelAndView.addObject("vendedorObj", new Vendedor());
		
		return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarVendedor")
	public ModelAndView salvarVendedor(@Valid Vendedor vendedor, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("/vendedor");
		
		if(bindingResult.hasErrors()) {
			  Iterable<Vendedor> vendedorIt = vendedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
			  modelAndView.addObject("vendedorObj", new Vendedor());
			  modelAndView.addObject("listVendedor", vendedorIt);
			
			  List<String> msg = new ArrayList<String>();
			
			  for(ObjectError objectError : bindingResult.getAllErrors()) {
			    msg.add(objectError.getDefaultMessage());
				 
			  modelAndView.addObject("msg", msg);	
			  }
			return modelAndView;
		}
		
		vendedorRepository.save(vendedor);
		
		Iterable<Vendedor> vendedorIt = vendedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		modelAndView.addObject("listVendedor", vendedorIt);
		modelAndView.addObject("vendedorObj", new Vendedor());
		
		return modelAndView;
	}
	
	@GetMapping("/removerVendedor/{idvendedor}")
	public ModelAndView removeVendedor(@PathVariable(name = "idvendedor") Long idvendedor) {
		
		vendedorRepository.deleteById(idvendedor);
		Iterable<Vendedor> vendedorIt;
		vendedorIt = vendedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome"))); 
		
		ModelAndView modelAndView = new ModelAndView("/vendedor");
		modelAndView.addObject("listVendedor", vendedorIt);
		modelAndView.addObject("vendedorObj", new Vendedor());
		
		return modelAndView;
	}
	
	@GetMapping("/editarVendedor/{idvendedor}")
	public ModelAndView editarVendedor(@PathVariable(name="idvendedor") Long idvendedor) {
		
		Optional<Vendedor> vendedor = vendedorRepository.findById(idvendedor);
		Iterable<Vendedor> vendedorIt = vendedorRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		
		ModelAndView modelAndView = new ModelAndView("/vendedor");
		modelAndView.addObject("vendedorObj", vendedor.get());
		modelAndView.addObject("listVendedor", vendedorIt);
        
		return modelAndView;
	} 
	
	@RequestMapping("**/pesquisarVendedor")
	public ModelAndView listaVendedor(
			@RequestParam("vendedorNomePesquisa") String vendedorNomePesquisa,
			@RequestParam("numeroInscricao") String numeroInscricao,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
		
		ModelAndView modelAndView = new ModelAndView("/vendedor");
		
		Boolean pesquisarPorNomeInscricao = (vendedorNomePesquisa != null) && (!vendedorNomePesquisa.isEmpty() &&
				                            (numeroInscricao != null) && (!numeroInscricao.isEmpty()));
		
		Boolean pesquisarPorNome = (vendedorNomePesquisa != null) && (!vendedorNomePesquisa.isEmpty());
		
		Iterable<Vendedor> vendedorIt = null;
		
		if (pesquisarPorNomeInscricao) {
			vendedorRepository.findByNomeVendedorInscricao(vendedorNomePesquisa, numeroInscricao, pageable);			
		}else if (pesquisarPorNome) {
			vendedorRepository.findByNomeVendedor(vendedorNomePesquisa, pageable);
		}
		
		modelAndView.addObject("numeroInscricao", numeroInscricao);
		modelAndView.addObject("vendedorNomePesquisa", vendedorNomePesquisa);
		modelAndView.addObject("listVendedor", vendedorIt);
		modelAndView.addObject("vendedorObj", new Vendedor());
		
		return modelAndView;
		
	}
	

}
