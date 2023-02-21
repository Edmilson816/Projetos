package financeiro.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Cliente;
import financeiro.repository.ClienteRepository;


@Controller
public class ClienteController {
	
	@Autowired
	ClienteRepository clienteRepository;

	
	@RequestMapping(method = RequestMethod.GET, value = "/cliente")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("/cliente");
		modelAndView.addObject("listClientes", clienteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("clienteObj", new Cliente());
		return modelAndView;
	}	
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarCliente", consumes = {"multipart/form-data"})
	public ModelAndView salvarCliente(@Valid Cliente cliente, BindingResult bindingResult, final MultipartFile file) throws IOException{
	    
		ModelAndView modelAndView = new ModelAndView("/cliente");
		Iterable<Cliente> clienteIt = null;
		
		if(bindingResult.hasErrors()) {
			
		    clienteIt = clienteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		    modelAndView.addObject("listClientes",clienteIt);
		    modelAndView.addObject("clienteObj", new Cliente());
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // vem das anotaÃ§Ãµes @NotEmpty e outras
				
     		modelAndView.addObject("msg", msg);
		    
			}
			
			return modelAndView;			
			
		}	
		
		if (file.getSize() > 0) { /*Cadastrando um curriculo*/
			cliente.setDocumento(file.getBytes());
			cliente.setTipoFiledocumento(file.getContentType());
			cliente.setNomeFiledocumento(file.getOriginalFilename());
		}else {
			if (cliente.getId() != null && cliente.getId() > 0) {// editando
				
				Cliente clienteTemp = clienteRepository.findById(cliente.getId()).get();
				
				cliente.setDocumento(clienteTemp.getDocumento());
				cliente.setTipoFiledocumento(clienteTemp.getTipoFiledocumento());
				cliente.setNomeFiledocumento(clienteTemp.getNomeFiledocumento());
			}
		}		
		
		clienteRepository.save(cliente);
	    
	    
	    clienteIt = clienteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
	    modelAndView.addObject("listClientes",clienteIt);
	    modelAndView.addObject("clienteObj", new Cliente());
	    
	    return modelAndView; 
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "**/listaCliente")
	public ModelAndView listaCliente(){
		ModelAndView modelAndView = new ModelAndView("/cliente");
		Iterable<Cliente> clienteIt = clienteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		modelAndView.addObject("listClientes", clienteIt);
		modelAndView.addObject("clienteObj", new Cliente());
		
		return modelAndView;
		
	}
	
	@GetMapping("/clientespag")
	public ModelAndView carregaPessoaPorPaginacao(
			@PageableDefault(size = 5) Pageable pageable, 
			ModelAndView model, 
			@RequestParam("nomeClientePesquisa") String nomeClientePesquisa,
			@RequestParam("inscricaoPesquisa") String inscricaoPesquisa) {
		
	    Page<Cliente> pageCliente = null; 
		
		if ((nomeClientePesquisa != null && !nomeClientePesquisa.isEmpty()) && (inscricaoPesquisa != null && !inscricaoPesquisa.isEmpty())) {
	      
			pageCliente = clienteRepository.findByNomeInscricao(nomeClientePesquisa, inscricaoPesquisa, pageable);  
		
		}else if(nomeClientePesquisa != null && nomeClientePesquisa.isEmpty()){
		
			pageCliente = clienteRepository.findByNomePage(nomeClientePesquisa,pageable);  
		
		}
		
		model.addObject("listClientes", pageCliente);
		model.addObject("clienteObj", new Cliente());
		model.addObject("nomeClientePesquisa", nomeClientePesquisa);
		model.addObject("inscricaoPesquisa", inscricaoPesquisa);
		model.setViewName("/cliente");
		
		return model;		
		
	}	
	
	@GetMapping("/editarCliente/{idcliente}")
	public ModelAndView editarCliente(@PathVariable("idcliente") Long idcliente) {
		
		Optional<Cliente> cliente = clienteRepository.findById(idcliente);
		
		ModelAndView modelAndView = new ModelAndView("/cliente");
		Iterable<Cliente> clienteIt = clienteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
		modelAndView.addObject("listClientes", clienteIt); 
		modelAndView.addObject("clienteObj", cliente.get());
		return modelAndView;		
		
	}	

	@GetMapping("/removerCliente/{idcliente}")
	public ModelAndView removerCliente(@PathVariable("idcliente") Long idCliente) {
		
		clienteRepository.deleteById(idCliente);
		
		ModelAndView modelAndView = new ModelAndView("/cliente");
		modelAndView.addObject("listClientes", clienteRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("clienteObj", new Cliente());
		return modelAndView;
		
	}
	
	
	@PostMapping("**/pesquisarCliente")  
	public ModelAndView pesquisarCliente(
			@RequestParam("nomeClientePesquisa") String nomeClientePesquisa,
			@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
		
		ModelAndView modelAndView = new ModelAndView("/cliente");
        modelAndView.addObject("listClientes", clienteRepository.findByNomePage(nomeClientePesquisa.trim().toUpperCase(), pageable));
		modelAndView.addObject("clienteObj", new Cliente());
		return modelAndView;		
	}
	
	@GetMapping("**/baixardocumento/{idpessoa}")
	public void baixarcurriculo(@PathVariable("idpessoa") Long idpessoa, 
			HttpServletResponse response) throws IOException {
		
		/*Consultar o obejto pessoa no banco de dados*/
		Cliente cliente = clienteRepository.findById(idpessoa).get();
		if (cliente.getDocumento() != null) {
	
			/*Setar tamanho da resposta*/
			response.setContentLength(cliente.getDocumento().length);
			
			/*Tipo do arquivo para download ou pode ser generica application/octet-stream*/
			response.setContentType(cliente.getTipoFiledocumento());
			
			/*Define o cabeçalho da resposta*/
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", cliente.getNomeFiledocumento());
			response.setHeader(headerKey, headerValue);
			
			/*Finaliza a resposta passando o arquivo*/
			response.getOutputStream().write(cliente.getDocumento());
			
		}
		
	} 	
	
	
		
	

}
