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

import financeiro.model.Pedido;
import financeiro.model.PedidoItem;
import financeiro.repository.ClienteRepository;
import financeiro.repository.FormaPagamentoRepository;
import financeiro.repository.PedidoItemRepository;
import financeiro.repository.PedidoRepository;
import financeiro.repository.StatusRepository;

@Controller
public class PedidoController {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	PedidoItemRepository pedidoItemRepository;
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	StatusRepository statusRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	public void AtualizaCombo(ModelAndView modelAndView) {
		
		if(modelAndView != null) {
			modelAndView.addObject("clientes", clienteRepository.findAll());
			modelAndView.addObject("formaPagamento", formaPagamentoRepository.findAll());
			modelAndView.addObject("status", statusRepository.findAll());
		}
		
	}
	
	public void AtualizaGrid(ModelAndView modelAndView) {
		if(modelAndView != null) {
		  modelAndView.addObject("listPedido", pedidoRepository.findAll(PageRequest.of(0, 5)));
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pedido")
	public ModelAndView inicio() {
		
		ModelAndView modelAndView = new ModelAndView("/pedido");
		
		AtualizaGrid(modelAndView);
		AtualizaCombo(modelAndView);		
		
		modelAndView.addObject("pedidoObj", new Pedido());
		
		return modelAndView;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarPedido")
	public ModelAndView salvarPedido(@Valid Pedido pedido, BindingResult bindingResult) throws IOException{
	    
		ModelAndView modelAndView = new ModelAndView("/pedido");
		Iterable<Pedido> pedidoIt = null;
		
		if(bindingResult.hasErrors()) {
			
			pedidoIt = pedidoRepository.findAll(PageRequest.of(0, 5));
		    modelAndView.addObject("listPedido",pedidoIt);
		    modelAndView.addObject("pedidoObj", new Pedido());
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // vem das anotaÃ§Ãµes @NotEmpty e outras
				
     		modelAndView.addObject("msg", msg);
		    
			}

			AtualizaGrid(modelAndView);
			AtualizaCombo(modelAndView);		
			
			return modelAndView;			
			
		}	
		
		pedidoRepository.save(pedido);
	    
	    
	    pedidoIt = pedidoRepository.findAll(PageRequest.of(0, 5));
	    modelAndView.addObject("listPedido", pedidoIt);
	    modelAndView.addObject("pedidoObj", new Pedido());
	    
		AtualizaGrid(modelAndView);
		AtualizaCombo(modelAndView);		
	    
	    return modelAndView; 
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/listaPedido")
	public ModelAndView listaPedido() {
	
		ModelAndView modelAndView = new ModelAndView("/pedido");
		
		Iterable<Pedido> pedidolt = pedidoRepository.findAll(PageRequest.of(0, 5));
		
		modelAndView.addObject("listPedido", pedidolt);
		modelAndView.addObject("pedidoObj", new Pedido());		
		
		return modelAndView;		
		
	}
	
	@GetMapping("/pedidopag")
	public ModelAndView carregaPedidoPorPaginacao(
			@PageableDefault(size = 5) Pageable pageable, 
			ModelAndView model, 
			@RequestParam("numeroPedidoPesquisa") String numeroPedidoPesquisa) {
		
	    Page<Pedido> pagePedido = null; 
	    
		if ((numeroPedidoPesquisa != null && !numeroPedidoPesquisa.isEmpty())) {
	      
			pagePedido = pedidoRepository.findByNumeroPage(numeroPedidoPesquisa, pageable);  
		
		}
		
		model.addObject("listPedido", pagePedido);
		model.addObject("pedidoObj", new Pedido());
		model.addObject("nomePedidoPesquisa", numeroPedidoPesquisa);
		model.setViewName("/pedido");
		
		return model;		
		
	}	
	
	@GetMapping("/editarPedido/{idPedido}")
	public ModelAndView editarPedido(@PathVariable(name="idPedido") Long idPedido) {
		
		Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
		
		Iterable<Pedido> pedidoIt = pedidoRepository.findAll(PageRequest.of(0, 5));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("listPedido", pedidoIt);
		modelAndView.addObject("pedidoObj", pedido.get());
		
		AtualizaGrid(modelAndView);
		AtualizaCombo(modelAndView);		
		
		modelAndView.setViewName("/pedido");
		
		return modelAndView;
		
	}
	
	@GetMapping("/removerPedido/{idPedido}")
	public ModelAndView removerPedido(@PathVariable(name="idPedido") Long idPedido) {
		
		pedidoRepository.deleteById(idPedido);
		
		ModelAndView modelAndView = new ModelAndView("/pedido");
		modelAndView.addObject("listPedido", pedidoRepository.findAll(PageRequest.of(0, 5)));
		modelAndView.addObject("pedidoObj", new Pedido());
		
		AtualizaGrid(modelAndView);
		AtualizaCombo(modelAndView);		
		
		return modelAndView;
	}
	
	@PostMapping("**/pesquisarPedido")  
	public ModelAndView pesquisarPedido(
			@RequestParam("numeroPedidoPesquisa") String numeroPedidoPesquisa,
			@PageableDefault(size = 5, sort = {"numero_pesquisa"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/pedido");
		modelAndView.addObject("numeroPedidoPesquisa", numeroPedidoPesquisa);
        modelAndView.addObject("listPedido", pedidoRepository.findByNumeroPage(numeroPedidoPesquisa.trim().toUpperCase(), pageable));
		modelAndView.addObject("pedidoObj", new Pedido());
		return modelAndView;		
	}
	
	//Link aula mestre detalhe e criação da aba de cadastro para os telefones 
	//https://projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/4c564325-6df2-42ef-bfbf-14104944b934/idcurso/1/idvideoaula/693
	//O asterisco antes da barra despres o que vem antes na url importando apenas o mapeamento
	@PostMapping("**/salvarItemPedido/{idPedido}")
	public ModelAndView salvarItemPedido(PedidoItem pedidoItem, @PathVariable("idPedido") Long idPedido ){
		
		Pedido pedido = pedidoRepository.findById(idPedido).get();
		
		pedidoItem.setPedido(pedido);
		
		pedidoItemRepository.save(pedidoItem);
		
		ModelAndView modelAndView = new ModelAndView("/pedido/pedidoItem");
		modelAndView.addObject("pedidoIt", pedidoItemRepository.findByPedidoItem(idPedido));
		modelAndView.addObject("pedidoObj", pedido);
		
		return modelAndView;
		
	}
	
	
	// Link da aula deste ponto
	//https://projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/676ca5f7-eac6-40e1-9480-32d6317eb577/idcurso/1/idvideoaula/694
	@GetMapping("/pedidoItem/{idPedido}")//Este metodo carrega os itens do pedido
	public ModelAndView pedidoItem(@PathVariable(name="idPedido") Long idPedido) {
		
		Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
		
		ModelAndView modelAndView = new ModelAndView("/pedido/pedidoItem");
		modelAndView.addObject("pedidoIt", pedidoItemRepository.findByPedidoItem(idPedido));
		modelAndView.addObject("pedidoItemObj", pedido.get());		
		
		return modelAndView;
	}	
	
	@GetMapping("**/removerItem/{idPedidoItem}/{idPedido}")
	public ModelAndView removerItem(
			@PathVariable("idPedido") Long idPedido,
			@PathVariable("idPedidoItem") Long idPedidoItem) {
		
		Pedido pedido = pedidoRepository.findById(idPedido).get();
		
		Optional<PedidoItem> pedidoItemObj = pedidoItemRepository.findById(idPedidoItem);
		
		Iterable<PedidoItem> pedidoIt = pedidoItemRepository.findAll(PageRequest.of(0, 5));
		
		ModelAndView modelAndView = new ModelAndView("/pedido/pedidoItem");
		
		modelAndView.addObject("pedidoIt", pedidoIt);		
		modelAndView.addObject("pedidoItemObj", pedidoItemObj);		
		modelAndView.addObject("pedidoObj", pedido);		
		
		return modelAndView;
	}
	
	@GetMapping("**/editarItem/{idPedidoItem}/{idPedido}")
	public ModelAndView editarItem(
			@PathVariable("idPedido") Long idPedido,
			@PathVariable("idPedidoItem") Long idPedidoItem) {
		
		Pedido pedido = pedidoRepository.findById(idPedido).get();
		
		Optional<PedidoItem> pedidoItemObj = pedidoItemRepository.findById(idPedidoItem);
		
		Iterable<PedidoItem> pedidoIt = pedidoItemRepository.findAll(PageRequest.of(0, 5));
		
		ModelAndView modelAndView = new ModelAndView("/pedido/pedidoItem");
		
		modelAndView.addObject("pedidoIt", pedidoIt);
		modelAndView.addObject("pedidoItemObj", pedidoItemObj);		
		modelAndView.addObject("pedidoObj", pedido);
		
		return modelAndView;
		
		
		
	}
		

}
