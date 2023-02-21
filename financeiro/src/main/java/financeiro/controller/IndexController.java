package financeiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String index() {
	 return "index";	
	}
	
	@RequestMapping("/principal")
	public String principal() {
		return "principal";
	}
}
