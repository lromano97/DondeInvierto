package com.caia.dondeinvierto;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.caia.dondeinvierto.forms.loginForm;
import com.caia.dondeinvierto.models.Usuario;

@Controller
public class MiController {
	
	// Redirige a formulario login
	@RequestMapping("init")
	public ModelAndView init(HttpSession session){
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("command",new loginForm());	
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			model.setViewName("inicio");
			model.addObject("usuario",usuario);

		}
		
		return model;
		
	}
	
	// Ir a login
	@RequestMapping(value="login", method={RequestMethod.GET})
	public ModelAndView irALogin(HttpSession session){
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new loginForm());
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			model.setViewName("inicio");
			model.addObject("usuario",usuario);
			
		}
		
		return model;
		
	}

	// Catchea post en login
	@RequestMapping(value="login", method={RequestMethod.POST})
	public ModelAndView autentificarLogin(loginForm login, ModelAndView model, HttpSession session){
		
		ModelAndView newModel = new ModelAndView();
		
		// Checkeo de datos
		if(!login.camposVacios()){
				
			if(!login.ilegalChars()){
					
				// Todo OK
				if(login.existeUsuario()){
					
					Usuario usuario = new Usuario();
						
					usuario.setUsername(login.getUsername());
					usuario.setPassword(login.getPassword());
					
					session.setAttribute("usuario", usuario);
					
					newModel.setViewName("inicio");
					newModel.addObject("usuario", usuario);
												
				// Error usuario no corresponde
				} else {
					
					System.out.println("Usuario no existe");
					newModel.setViewName("login");
					newModel.addObject("command", new loginForm());
						
				}
				
			// Error caracteres ilegales
			} else {
					
				System.out.println("Caracteres ilegales");
				newModel.setViewName("login");
				newModel.addObject("command", new loginForm());
					
			}
			
		// Error campos vacios
		} else {
				
			System.out.println("Campos vacios");
			newModel.setViewName("login");
			newModel.addObject("command", new loginForm());
				
		}
		
		return newModel;
						
	}
	
	// Ir a inicio
	@RequestMapping(value="inicio", method={RequestMethod.GET})
	public ModelAndView irAInicio(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("command",new loginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
				
			model.setViewName("inicio");
			model.addObject("usuario",usuario);

			
		}
		
		return model;
		
	}
	
}
