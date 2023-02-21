package financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Estoque;
import financeiro.repository.EstoqueRepository;

@Controller
public class EstoqueController {
	
	@Autowired
	EstoqueRepository estoqueRepository;
	
	@PostMapping("/pesquisaEstoque/{idProduto}")  
	public ModelAndView pesquisarCliente(
			@PathVariable("idProduto") Long idProduto,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/estoque");
        modelAndView.addObject("listEstoque", estoqueRepository.findByIdProdutoPage(idProduto, pageable));
		modelAndView.addObject("estoqueObj", new Estoque());
		return modelAndView;		
	}	

}
