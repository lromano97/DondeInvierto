package com.caia.dondeinvierto;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.caia.dondeinvierto.auxiliar.ParserCSV;
import com.caia.dondeinvierto.forms.*;
import com.caia.dondeinvierto.models.Condicion;
import com.caia.dondeinvierto.models.Cotizacion;
import com.caia.dondeinvierto.models.Database;
import com.caia.dondeinvierto.models.Indicador;
import com.caia.dondeinvierto.models.Metodologia;
import com.caia.dondeinvierto.models.Usuario;

import iceblock.connection.ConnectionManager;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;

@Controller
public class MiController {
	
	Connection conn = null;
	
	
	public MiController() throws SQLException, ClassNotFoundException{
			
		// Conexion a DB relacional
		ConnectionManager.create("org.hsqldb.jdbcDriver","jdbc:hsqldb:hsql://localhost/","SA","","hsqldb");
		ConnectionManager.changeConnection("hsqldb");
		this.conn = ConnectionManager.getConnection();
		
		// Conexion a MongoDB
        MongoClient cliente = new MongoClient("localhost", 27017);
		Datastore ds = new Morphia().createDatastore(cliente, "test1");
		
		
		
	}
	
	// Redirige a formulario login
	@RequestMapping("init")
	public ModelAndView init(HttpSession session){
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("command",new LoginForm());	
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			model.setViewName("inicio");
			model.addObject("usuario",usuario);
			
		}
		
		return model;
		
	}
	
	// Error 404
	@RequestMapping(value="/404")
	public ModelAndView error404(HttpSession session){
		
		ModelAndView model = new ModelAndView();
		model.setViewName("error404");
		
		return model;
		
	}
	
	// Error 500
	@RequestMapping(value="/500")
	public ModelAndView error500(HttpSession session){
			
		ModelAndView model = new ModelAndView();
		model.setViewName("error500");
			
		return model;
			
	}
		
		
	// Ir a login
	@RequestMapping(value="login", method={RequestMethod.GET})
	public ModelAndView irALogin(HttpSession session){
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			model.setViewName("inicio");
			model.addObject("usuario",usuario);
			
		}
		
		return model;
		
	}
	
	// Catchea post en login
	@RequestMapping(value="login", method={RequestMethod.POST})
	public ModelAndView autentificarLogin(HttpSession session, LoginForm login) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException{
		
		ModelAndView model = new ModelAndView();
		
		// Checkeo de datos
		if(!login.camposVacios()){
				
			if(!login.ilegalChars()){
					
				List<Usuario> listaUsuarios = login.buscaUsuario();
					
				if(listaUsuarios.size()>0){	
					
					Usuario usuario = login.buscaUsuario().get(0);
					Database database = new Database();
					
					session.setAttribute("usuario", usuario);
					session.setAttribute("database", database);
					
					model.setViewName("inicio");
					model.addObject("usuario", usuario);
												
				// Error usuario no corresponde
				} else {
					
					model.addObject("msg",4);
					model.setViewName("login");
					model.addObject("command", new LoginForm());
						
				}
				
			// Error caracteres ilegales
			} else {
				
				model.addObject("msg",3);
				model.setViewName("login");
				model.addObject("command", new LoginForm());
					
			}
			
		// Error campos vacios
		} else {
				
			model.addObject("msg",2);
			model.setViewName("login");
			model.addObject("command", new LoginForm());
				
		}
		
		return model;
						
	}
	
	// Ir a inicio
	@RequestMapping(value="inicio", method={RequestMethod.GET})
	public ModelAndView irAInicio(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			Database database = (Database) session.getAttribute("database");
			
			model.setViewName("inicio");
			model.addObject("usuario",usuario);
			
			model.addObject("cotizaciones",database.getCotizaciones());
			
		}
		
		return model;
		
	}
	
	// Ir a proyecto
	@RequestMapping(value="proyecto", method={RequestMethod.GET})
	public ModelAndView irAProyecto(HttpSession session){
		
		ModelAndView model = new ModelAndView();
			
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
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
	public ModelAndView generarProyecto(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpSession session) {
		
		ModelAndView model = new ModelAndView();
						
		if(!file.isEmpty()){
			
			String nombreArchivo = file.getOriginalFilename();
			String extension = nombreArchivo.substring(nombreArchivo.length()-4, nombreArchivo.length());
			extension = extension.toLowerCase();
			
			if(extension.equals(".csv")){
				
				try {
										
					ParserCSV parser = new ParserCSV(file);
					
					if(!parser.csvEsVacio()){
					
						if(parser.csvCompleto()){
							
							if(parser.checkColumnTypes()){
								
								Database database = (Database) session.getAttribute("database");
								
								parser.generarRowsCSV(database,file);
								model.addObject("msg", 0);
								
							// Error de tipos en columnas
							} else {
								model.addObject("msg", 6);
							}
						
						// Error Columnas CSV incompletas
						} else {
							model.addObject("msg", 5);
						}
					
					// Error CSV vacio
					} else {
						model.addObject("msg", 4);
					}
				
				// Error grave IO
				} catch (IOException e) {
					model.addObject("msg", 3);
				}
				
			// No es un archivo CSV
			} else {
				model.addObject("msg", 2);
			}
			
		// Campo/Archivo vacio
		} else {
			model.addObject("msg", 1);
		}
		
		model.setViewName("proyecto");
		return model;
		
	}
	
	// Ir a indicadores
	@RequestMapping(value="gestionIndicadores", method={RequestMethod.GET})
	public ModelAndView irAIndicadores(HttpSession session){
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
		} else {
			
			Database database = (Database) session.getAttribute("database");
			
			model.setViewName("gestionIndicadores");
			model.addObject("command",new CrearIndicadorForm());	
			
			model.addObject("indicadores",database.getIndicadores());	
			model.addObject("cuentas",database.getCuentas());	
			
		}
		
		return model;
		
	}
	
	// Generar indicador 
	@RequestMapping(value="generarIndicador", method = RequestMethod.POST)
	public ModelAndView generarIndicador(HttpSession session, CrearIndicadorForm indicadorForm) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, InstantiationException {
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {
			
			model.setViewName("gestionIndicadores");
			model.addObject("command",new CrearIndicadorForm());
			
			Database database = (Database) session.getAttribute("database");
			
			if(!indicadorForm.camposVacios()){
				
				if(!indicadorForm.caracteresInvalidos()){
					
					//Database indicadores = (Database) session.getAttribute("database");
					
					if(!indicadorForm.nombreExistente(database.getIndicadores())){
						
						if(!indicadorForm.existeRecursividad()){
							
							if(indicadorForm.analizarSintaxis()){
								
								// Indicador aceptado
								model.addObject("msg",0);		
								Usuario usuario = (Usuario) session.getAttribute("usuario");
								Indicador nuevoIndicador = new Indicador();
								nuevoIndicador.crearIndicador(indicadorForm.getNombre(),indicadorForm.getExpresion(),usuario);
								database.addIndicador(nuevoIndicador);
							
							// Error sintactico en indicador
							} else {
								model.addObject("msg",5);
							}
						
						// Error indicador recursivo
						} else {
							model.addObject("msg",4);
						}
						
					// Error nombre de indicador ya existe
					} else {
						model.addObject("msg",3);
					}
				
				// Error caracteres ilegales en el nombre
				} else {
					model.addObject("msg",2);
				}

			// Error campos vacios
			} else {
				model.addObject("msg",1);
			}
			
			// CONTROLAR EXISTENCIA DE NOMBRE DE INDICADORES
			
			model.addObject("command",new CrearIndicadorForm());
			model.addObject("indicadores", database.getIndicadores());
			model.addObject("cuentas", database.getCuentas());
			
		}
		
		return model;
		
	}
	
	// Logout
	@RequestMapping(value="logout", method= RequestMethod.GET)
	public ModelAndView logout(HttpSession session){
		
		session.invalidate();
		
		ModelAndView model = new ModelAndView();
		
		model.setViewName("login");
		model.addObject("command",new LoginForm());	
		
		return model;
		
	}
	
	// Ir a consultar cuenta
	@RequestMapping(value="consultarCuenta", method={RequestMethod.GET})
	public ModelAndView irAConsultarCuenta(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			model.setViewName("consultarCuenta");
			model.addObject("usuario",usuario);
			
			Database database = (Database) session.getAttribute("database");
			
			model.addObject("empresas",database.getEmpresas());
			model.addObject("cuentas",database.getCuentas());
			model.addObject("anios",database.getAnios());
			
			ArrayList<Cotizacion> resultados = new ArrayList<Cotizacion>();
			model.addObject("resultados",resultados);
		
			model.addObject("command",new FiltroConsultaCuenta());
						
		}
		
		return model;
		
	}
	
	// Generar consulta cuenta 
	@RequestMapping(value="generarConsultaCuenta", method=RequestMethod.POST)
	public ModelAndView generarConsultaCuenta(HttpSession session, FiltroConsultaCuenta filtroConsulta) {		
		
		System.out.println("pase");
		
		ModelAndView model = new ModelAndView();
			
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {

			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			model.setViewName("consultarCuenta");
			model.addObject("command",new FiltroConsultaCuenta());
			
			model.addObject("usuario",usuario);
			
			Database database = (Database) session.getAttribute("database");
			
			model.addObject("empresas",database.getEmpresas());
			model.addObject("cuentas",database.getCuentas());
			model.addObject("anios",database.getAnios());
			
			ArrayList<Cotizacion> resultados = database.generarConsultaCuenta(filtroConsulta);
			model.addObject("resultados", resultados);
			
		}
			
		return model;
			
	}
	
	// Ir a consultar indicador
	@RequestMapping(value="consultarIndicador", method={RequestMethod.GET})
	public ModelAndView irAConsultarIndicador(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
				
			model.setViewName("consultarIndicador");
			model.addObject("usuario",usuario);
			
		}
		
		return model;
		
	}
	
	// Ir a consultar metodologia
	@RequestMapping(value="consultarMetodologia", method={RequestMethod.GET})
	public ModelAndView irAConsultarMetodologia(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
				
			model.setViewName("consultarMetodologia");
			model.addObject("usuario",usuario);
			
		}
		
		return model;
		
	}
	
	// Ir a gestion de metodologias
	@RequestMapping(value="gestionMetodologias", method={RequestMethod.GET})
	public ModelAndView irAMetodologia(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
				
			model.setViewName("gestionMetodologias");
			model.addObject("usuario",usuario);
			
		}
		
		return model;
		
	}
	
	 public String index(
	            ModelMap map, 
	            HttpSession session, 
	            HttpServletRequest request, 
	            @RequestParam(value="f", required=false) String flush,
	            @RequestParam(value="message", required=false) String message ) {
	 
	        if( flush != null )
	            session.setAttribute("metodologia", getDummyMetodologia());
	        if( session.getAttribute("metodologia") == null )
	            session.setAttribute("metodologia", getDummyMetodologia());
	        map.addAttribute("metodologia", (Metodologia)session.getAttribute("metodologia"));
	        if( message != null )
	            map.addAttribute("message", message);
	        map.addAttribute("cp", request.getContextPath());
	 
	        return "index";
	    }
	 
	    @RequestMapping(value="/editpersonlistcontainer", method= RequestMethod.POST)
	    public String editpersonListContainer(@ModelAttribute Metodologia metodologia, HttpSession session) {
	        for( Condicion p : metodologia.getCondiciones() ) {
	            //System.out.println("Name: " + p.getName());
	            //System.out.println("Age: " + p.getAge());
	        }
	        session.setAttribute("personListContainer",metodologia);
	        return "redirect:/?message=Form Submitted Ok. Number of rows is: ["+metodologia.getCondiciones().size()+"]";
	    }
	 
	    private Metodologia getDummyMetodologia() {
	        List<Condicion> condiciones = new ArrayList<Condicion>();
	        for( int i=0; i<5; i++ ) {
	            condiciones.add( new Condicion());
	        }
	        return new Metodologia();
	    }
}

