package financeiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import financeiro.model.Servicos;
import financeiro.repository.ServicoRepository;

@Controller
public class ServicosController {
	
	@Autowired
	ServicoRepository servicoRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/servico")
	public ModelAndView inicio() {
		
		ModelAndView modelAndView = new ModelAndView("/servico");
		return modelAndView;
	}
	
	@PostMapping(value="salvarServico")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?>salvarServico(@RequestBody Servicos servicos){
		
		Servicos serv = servicoRepository.save(servicos);
		
		return new ResponseEntity<Servicos>(serv, HttpStatus.OK);
	}	
	
	@PutMapping(value="atualizaServico")
	@ResponseBody
	public ResponseEntity<?>atualizaServico(@RequestBody Servicos servicos){
	    
		Servicos serv = servicoRepository.saveAndFlush(servicos);
	    
	    return new ResponseEntity<Servicos>(serv, HttpStatus.OK);
		
	}
	
	@GetMapping(value="deleteServico")
	public ResponseEntity<String>deleteServico(@RequestParam(name="id") Long id){
		
		servicoRepository.deleteById(id);
		
		return new ResponseEntity<String>("Serviço excluido com sucesso", HttpStatus.OK);
		
	}
	
	@GetMapping(value="buscaServicoId")
	@ResponseBody
	public ResponseEntity<Servicos>buscaServicoId(@RequestParam(name="id") Long id){
		
		Servicos servicos = servicoRepository.findById(id).get();
		return new ResponseEntity<Servicos>(servicos, HttpStatus.OK);		
		
	}
	
	@GetMapping(value="listaServicos")
	public ResponseEntity<List<Servicos>>listaServicos(){
		List<Servicos> servicos = servicoRepository.findAll();
		
		return new ResponseEntity<List<Servicos>>(servicos, HttpStatus.OK);
		
	}
	
	@GetMapping(value="buscarServicoPorNome")
	@ResponseBody
	public ResponseEntity<List<Servicos>>buscarServicoPorNome(@RequestParam(name="name") String name){
		List<Servicos> serv = servicoRepository.buscaServicoPorNome(name.trim().toUpperCase());
		
		return new ResponseEntity<List<Servicos>>(serv, HttpStatus.OK);
		
	}	
	
	
}
