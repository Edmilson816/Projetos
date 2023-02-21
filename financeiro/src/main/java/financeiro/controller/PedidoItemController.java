package financeiro.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Pedido;
import financeiro.model.PedidoItem;
import financeiro.repository.PedidoItemRepository;

@Controller
public class PedidoItemController {
	
	@Autowired
	PedidoItemRepository pedidoItemRepository; 

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarPedidoItem")
	public ModelAndView salvarPedidoItem(@Valid PedidoItem pedidoItem, BindingResult bindingResult) throws IOException{
	    
		ModelAndView modelAndView = new ModelAndView("/pedidoItem");
		Iterable<PedidoItem> pedidoItemIt = null;
		
		if(bindingResult.hasErrors()) {
			
			pedidoItemIt = pedidoItemRepository.findAll(PageRequest.of(0, 5, Sort.by("produto.nome")));
		    modelAndView.addObject("listPedidoItem",pedidoItemIt);
		    modelAndView.addObject("pedidoItemObj", new PedidoItem());
			
			List<String> msgPedidoItem = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msgPedidoItem.add(objectError.getDefaultMessage()); // vem das anotaÃ§Ãµes @NotEmpty e outras
				
     		modelAndView.addObject("msgPedidoItem", msgPedidoItem);
		    
			}
			
			return modelAndView;			
			
		}	
		
		pedidoItemRepository.save(pedidoItem);
	    
	    
	    pedidoItemIt = pedidoItemRepository.findAll(PageRequest.of(0, 5, Sort.by("produto.nome")));
	    modelAndView.addObject("listPedidoItem", pedidoItemIt);
	    modelAndView.addObject("pedidoItemObj", new PedidoItem());
	    
	    return modelAndView; 
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "**/pedidoItem")
	public ModelAndView listaPedidoItem() {
	
		ModelAndView modelAndView = new ModelAndView("/pedidoItem");
		
		Iterable<PedidoItem> pedidoItemlt = pedidoItemRepository.findAll(PageRequest.of(0, 5, Sort.by("produto.nome")));
		
		modelAndView.addObject("listPedidoItem", pedidoItemlt);
		modelAndView.addObject("pedidoItemObj", new PedidoItem());		
		
		return modelAndView;		
		
	}	
	
	
}
