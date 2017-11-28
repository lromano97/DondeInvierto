package com.caia.dondeinvierto;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.caia.dondeinvierto.auxiliar.EvaluarIndicadores;
import com.caia.dondeinvierto.auxiliar.ParserCSV;
import com.caia.dondeinvierto.auxiliar.ScheduledTask;
import com.caia.dondeinvierto.forms.*;
import com.caia.dondeinvierto.models.Condicion;
import com.caia.dondeinvierto.models.Cotizacion;
import com.caia.dondeinvierto.models.DBCotizacion;
import com.caia.dondeinvierto.models.DBSession;
import com.caia.dondeinvierto.models.Indicador;
import com.caia.dondeinvierto.models.Metodologia;
import com.caia.dondeinvierto.models.PreIndicador;
import com.caia.dondeinvierto.models.Usuario;

import iceblock.connection.ConnectionManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.mongodb.morphia.Datastore;

@Controller
public class MiController {
	
	Connection conn = null;
	DBCotizacion dbCotizacion = null;
	DB db=null;
	
	@SuppressWarnings("deprecation")
	public MiController() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
			
		// Conexion a DB relacional
		ConnectionManager.create("org.hsqldb.jdbcDriver","jdbc:hsqldb:hsql://localhost/","SA","","hsqldb");
		ConnectionManager.changeConnection("hsqldb");
		
		this.conn = ConnectionManager.getConnection();
		this.dbCotizacion = DBCotizacion.getInstance();
		this.dbCotizacion.update();
		
		// Conexion a MongoDB
        MongoClient cliente = new MongoClient("localhost", 27017);
		Datastore ds = new Morphia().createDatastore(cliente, "PreIndicadores");	
		db = cliente.getDB("PreIndicadores");

		// PASO 3: Obtenemos una coleccion para trabajar con ella
		DBCollection collection = db.getCollection("Futbolistas");
		
		// Ivan scheduler
		Timer time = new Timer();		
		ScheduledTask st = new ScheduledTask();
		time.schedule(st, 60000, 120000); // Empieza al minuto y se repite cada 2 minutos
		
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
					
					DBSession dbSession = new DBSession();
					dbSession.updateIndicadores(usuario.getIdUsuario());
					
					session.setAttribute("usuario", usuario);
					session.setAttribute("dbSession", dbSession);
					
					model.setViewName("inicio");
					model.addObject("usuario", usuario);
					model.addObject("cotizaciones",dbCotizacion.getCotizaciones());
				
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
						
			model.setViewName("inicio");
			model.addObject("usuario",usuario);
			
			model.addObject("cotizaciones",dbCotizacion.getCotizaciones());
			
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
	public ModelAndView generarProyecto(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpSession session) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException {
		
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
																
								parser.generarRowsCSV(DBCotizacion.getInstance(),file);
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
			
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");
			
			model.setViewName("gestionIndicadores");
			model.addObject("command",new CrearIndicadorForm());	
			
			model.addObject("indicadores",dbSession.getIndicadores());	
			model.addObject("cuentas",dbCotizacion.getCuentas());	
			
		}
		
		return model;
		
	}
	
	// Generar indicador 
	@RequestMapping(value="generarIndicador", method = RequestMethod.POST)
	public ModelAndView generarIndicador(HttpSession session, CrearIndicadorForm indicadorForm) throws Exception {
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {
			
			model.setViewName("gestionIndicadores");
			model.addObject("command",new CrearIndicadorForm());
			
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");
			
			if(!indicadorForm.camposVacios()){
				
				if(!indicadorForm.caracteresInvalidos()){
										
					if(!indicadorForm.nombreExistente(dbSession.getIndicadores())){
						
						if(!indicadorForm.existeRecursividad()){
							
							if(indicadorForm.analizarSintaxis()){
								
								// Indicador aceptado
								model.addObject("msg",0);		
								Usuario usuario = (Usuario) session.getAttribute("usuario");
								Indicador nuevoIndicador = new Indicador();
								nuevoIndicador.crearIndicador(indicadorForm.getNombre(),indicadorForm.getExpresion(),usuario);
								dbSession.addIndicador(nuevoIndicador);
								
								
								
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
			model.addObject("indicadores", dbSession.getIndicadores());
			model.addObject("cuentas", dbCotizacion.getCuentas());
			
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
						
			model.addObject("empresas",dbCotizacion.getEmpresas());
			model.addObject("cuentas",dbCotizacion.getCuentas());
			model.addObject("anios",dbCotizacion.getAnios());
			
			ArrayList<Cotizacion> resultados = new ArrayList<Cotizacion>();
			model.addObject("resultados",resultados);
			
			model.addObject("command",new FiltroConsultaCuenta());
			
		}
		
		return model;
		
	}
	
	// Generar consulta cuenta 
	@RequestMapping(value="generarConsultaCuenta", method=RequestMethod.POST)
	public ModelAndView generarConsultaCuenta(HttpSession session, FiltroConsultaCuenta filtroConsulta) {		
				
		ModelAndView model = new ModelAndView();
			
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {

			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			model.setViewName("consultarCuenta");
			model.addObject("command",new FiltroConsultaCuenta());
			
			model.addObject("usuario",usuario);
						
			model.addObject("empresas",dbCotizacion.getEmpresas());
			model.addObject("cuentas",dbCotizacion.getCuentas());
			model.addObject("anios",dbCotizacion.getAnios());
			
			ArrayList<Cotizacion> resultados = dbCotizacion.generarConsultaCuenta(filtroConsulta);
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
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");
			
			model.setViewName("consultarIndicador");
			
			model.addObject("usuario",usuario);
			model.addObject("empresas",dbCotizacion.getEmpresas());
			model.addObject("anios",dbCotizacion.getAnios());
			model.addObject("indicadores",dbSession.getIndicadores());
			
			model.addObject("command", new FiltroConsultaIndicador());
			
		}
		
		return model;
		
	}
	
	// Generar consulta indicador
	@RequestMapping(value="generarConsultaIndicador", method=RequestMethod.POST)
	public ModelAndView generarConsultaIndicador(HttpSession session, FiltroConsultaIndicador filtroConsulta) throws Exception {		
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {
			
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			EvaluarIndicadores indicadorAEvaluar= new  EvaluarIndicadores();
			
			DBCollection collection = db.getCollection("PreIndicadores");
			String nombreIndicador = filtroConsulta.getIndicador();
			String an = filtroConsulta.getAnio();
			BasicDBObject query = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("indicador", filtroConsulta.getIndicador()));
			obj.add(new BasicDBObject("empresa", filtroConsulta.getEmpresa()));
			obj.add(new BasicDBObject("anio", an));
			obj.add(new BasicDBObject("idUsuario", usuario.getIdUsuario()));
			query.put("$and", obj);
			DBObject cursor= collection.findOne(query);
			if(cursor == null) {	
				String formula= indicadorAEvaluar.generarFormula(nombreIndicador, Integer.parseInt(an), filtroConsulta.getEmpresa(), dbSession);
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				double valorIndicador = Double.parseDouble(engine.eval(formula).toString());				
//				double valorIndicador = Double.parseDouble((String) engine.eval(formula));
				PreIndicador preIndicador=new PreIndicador(nombreIndicador,filtroConsulta.getEmpresa(),Integer.parseInt(an), usuario.getIdUsuario().intValue() ,valorIndicador);
				collection.insert(preIndicador.toDBObjectPreIndicador());
			}
			
			System.out.println("arafue");
				System.out.println("atroden");
				System.out.println(cursor);
				DBObject cursor2= collection.findOne(query);
				System.out.println(cursor2);
//				PreIndicador preIndi = new PreIndicador(cursor); 
//				System.out.println(preIndi.getValor());
				
			
			
			
		
			//MOSTRAR POR INTERFAZ
			model.setViewName("consultarIndicador");
			model.addObject("command", new FiltroConsultaIndicador());
						
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
	
	
}

