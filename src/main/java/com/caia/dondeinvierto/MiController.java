package com.caia.dondeinvierto;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.caia.dondeinvierto.auxiliar.ParserCSV;
import com.caia.dondeinvierto.forms.loginForm;
import com.caia.dondeinvierto.models.Usuario;

//import iceblock.connection.ConnectionManager;

@Controller
public class MiController {
	
	//Connection conn = null;
	
	/*public MiController() throws SQLException{
		
		ConnectionManager.create("org.hsqldb.jdbcDriver","jdbc:hsqldb:hsql://localhost/","SA","","hsqldb");
		ConnectionManager.changeConnection("hsqldb");
		this.conn = ConnectionManager.getConnection();
		
	}*/
	
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
	public ModelAndView autentificarLogin(loginForm login, ModelAndView model, HttpSession session) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException{
		
		ModelAndView newModel = new ModelAndView();
		
		// Checkeo de datos
		if(!login.camposVacios()){
				
			if(!login.ilegalChars()){
					
				List<Usuario> listaUsuarios = login.buscaUsuario();
					
				if(listaUsuarios.size()>0){	
					
					Usuario usuario = login.buscaUsuario().get(0);
					
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
	
	// Ir a proyecto
	@RequestMapping(value="proyecto", method={RequestMethod.GET})
	public ModelAndView irAProyecto(HttpSession session){
		
		ModelAndView model = new ModelAndView();
			
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("command",new loginForm());
			
		} else {
			
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			if(usuario.getRango() == 0){
				
				model.setViewName("proyecto");
				model.addObject("usuario",usuario);
				
			} else {
				
				model.setViewName("inicio");
				model.addObject("usuario",usuario);
				
			}
			
		}
		
		return model;
		
	}
	
	// Generar proyecto 
	@RequestMapping(value="generarProyecto", method = RequestMethod.POST)
	public ModelAndView generarProyecto(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		
		System.out.println(file.getContentType());
				
		if(!file.isEmpty()){
			
			if(file.getOriginalFilename().contains(".csv")){
				
				try {
					
					ParserCSV parser = new ParserCSV(file);
					
					if(!parser.csvEsVacio()){
					
						if(parser.csvCompleto()){
							
							if(parser.checkType()){
								
								System.out.println("Todo piola");
								model.setViewName("proyecto");
								model.addObject("msg", 0);
								
							// Error de tipos
							} else {
								
								System.out.println("Todo mal");
								model.setViewName("proyecto");
								model.addObject("msg", 6);
								
							}
						
						// Error CSV incompleto
						} else {
							System.out.println("CSV incompleto");
							model.setViewName("proyecto");
							model.addObject("msg", 5);
						}
					
					// Error CSV vacio
					} else {
						System.out.println("CSV vacio");
						model.setViewName("proyecto");
						model.addObject("msg", 4);
					}
				
				// Error grave IO
				} catch (IOException e) {
					System.out.println("CSV IO");
					model.setViewName("proyecto");
					model.addObject("msg", 3);
				}
				
			// No es un archivo CSV
			} else {
				System.out.println("No es CSV");
				model.setViewName("proyecto");
				model.addObject("msg", 2);
			}
			
		// Campo vacio
		} else {
			System.out.println("Campo vacio");
			model.setViewName("proyecto");
			model.addObject("msg", 1);
		}
		
		return model;
		
	}
}
