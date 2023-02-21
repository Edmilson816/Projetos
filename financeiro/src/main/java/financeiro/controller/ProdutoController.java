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
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Produto;
import financeiro.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	private final Integer totalRegistroPorPagina = 5;
	
	
	@RequestMapping(method = RequestMethod.GET, value="/produto")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/produto");		
		modelAndView.addObject("listProdutos", produtoRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome"))));
		modelAndView.addObject("produtoObj", new Produto());
		return modelAndView; 
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarProduto")
	public ModelAndView salvarProduto(@Valid Produto produto, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("/produto");
		Iterable<Produto> produtoIt = null;
		
		if(bindingResult.hasErrors()){
			produtoIt = produtoRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
			modelAndView.addObject("listProdutos", produtoIt);
			modelAndView.addObject("produtoObj", new Produto());
			
			List<String> msg = new ArrayList<String>();
			
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			  modelAndView.addObject("msg", msg);	
			}
			
			return modelAndView;
			
		}		
		
		produtoRepository.save(produto);

		produtoIt = produtoRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		modelAndView.addObject("listProdutos", produtoIt);
		modelAndView.addObject("produtoObj", new Produto());
		
		return modelAndView;		
	}
	
	
	@GetMapping("/removeProduto/{idProduto}")
	public ModelAndView removeProduto(@PathVariable("idProduto") Long idProduto){		
		
		ModelAndView modelAndView = new ModelAndView("/produto");
		
		produtoRepository.deleteById(idProduto);
		Iterable<Produto> produtoIt = produtoRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome")));
		modelAndView.addObject("listProdutos", produtoIt);
		modelAndView.addObject("produtoObj", new Produto());
		
		return modelAndView;
		
	} 
	
	@GetMapping("/editarProduto/{idProduto}")
	public ModelAndView editarProduto(@PathVariable("idProduto") Long idProduto) {
		
		ModelAndView modelAndView = new ModelAndView("/produto");
		Optional<Produto> produto = produtoRepository.findById(idProduto);
		Iterable<Produto> produtoIt = produtoRepository.findAll(PageRequest.of(0, totalRegistroPorPagina, Sort.by("nome"))); 
		modelAndView.addObject("produtoObj", produto.get());
		modelAndView.addObject("produtoIt", produtoIt);
		
		return modelAndView;
	}
	
	@GetMapping("**/pesquisaProduto")
	public ModelAndView pesquisaProduto(
			@RequestParam("nomeProdutoPesquisa") String nomeProdutoPesquisa,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/produto");
		Iterable<Produto>produtoIt = produtoRepository.findByNomePage(nomeProdutoPesquisa.trim().toUpperCase(), pageable);
		modelAndView.addObject("nomeProdutoPesquisa", nomeProdutoPesquisa);
		modelAndView.addObject("produtoIt", produtoIt);
		
		return modelAndView;
		
	}
	
	@RequestMapping("/produtopag")
	public ModelAndView carregaProdutoPorPaginacao(
			@PageableDefault(size = 5) Pageable pageable,
			ModelAndView modelAndView,
			@RequestParam("nomeProdutoPesquisa") String nomeProdutoPesquisa) {
		
		Page<Produto> pageProduto = null;
		
		if( nomeProdutoPesquisa != null && !nomeProdutoPesquisa.isEmpty() ){
			pageProduto = produtoRepository.findByNomePage(nomeProdutoPesquisa, pageable);
		}else {
			pageProduto = produtoRepository.findAll(pageable);		
		}
		
		
		modelAndView.addObject("produtoObj", new Produto());
		modelAndView.addObject("listProdutos", pageProduto);
		modelAndView.addObject("nomeProdutoPesquisa", nomeProdutoPesquisa);
		modelAndView.setViewName("/produto");
		
		return modelAndView;
	}

}
