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
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.caia.dondeinvierto.auxiliar.*;
import com.caia.dondeinvierto.forms.*;
import com.caia.dondeinvierto.models.*;

import iceblock.IBlock;
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
		this.dbCotizacion.init();
		
		// Conexion a MongoDB
        MongoClient cliente = new MongoClient("localhost", 27017);
		db = cliente.getDB("PreIndicadores");
		
		// Ivan scheduler
		//Timer time = new Timer();		
		//ScheduledTask st = new ScheduledTask();
		//time.schedule(st, 60000, 120000); // Empieza al minuto y se repite cada 2 minutos
		
	}
	
	// LOGIN/LOGOUT
	
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
					dbSession.updateMetodologias(usuario.getIdUsuario());
					
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
	
	// Logout
	@RequestMapping(value="logout", method= RequestMethod.GET)
	public ModelAndView logout(HttpSession session){
		
		session.invalidate();
		
		ModelAndView model = new ModelAndView();
		
		model.setViewName("login");
		model.addObject("command",new LoginForm());	
		
		return model;
		
	}
	
	// ERROR WEBPAGES
	
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
		
	
	// INICIO
	
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
	
	// PROYECTO
	
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

	// CUENTAS
	
	// Ir a consultar cuentas
	@RequestMapping(value="consultarCuentas", method={RequestMethod.GET})
	public ModelAndView irAConsultarCuentas(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			model.setViewName("consultarCuentas");
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
			
			model.setViewName("consultarCuentas");
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
	
	// INDICADORES
	
	// Ir a gestion de indicadores
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
	
	// Ir a evaluar indicadores
	@RequestMapping(value="evaluarIndicadores", method={RequestMethod.GET})
	public ModelAndView irAEvaluarIndicadores(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");
			
			model.setViewName("evaluarIndicadores");
			
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
	public ModelAndView generarConsultaIndicador(HttpSession session, FiltroConsultaIndicador filtroConsulta) throws ScriptException, NumberFormatException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException {		
		
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
						
			query.append("indicador", filtroConsulta.getIndicador());
			query.append("empresa", filtroConsulta.getEmpresa());
			query.append("anio",Integer.parseInt(filtroConsulta.getAnio()));
			query.append("usuario",usuario.getIdUsuario());
			DBObject cursor= collection.findOne(query);
			
			String valorIndicador = null;
			
			if(cursor == null) {	
				
				try {
					String formula= indicadorAEvaluar.generarFormula(nombreIndicador, Integer.parseInt(an), filtroConsulta.getEmpresa(), dbSession);
					ScriptEngineManager mgr = new ScriptEngineManager();
					ScriptEngine engine = mgr.getEngineByName("JavaScript");
					valorIndicador = engine.eval(formula).toString();				
					PreIndicador preIndicador=new PreIndicador(nombreIndicador,filtroConsulta.getEmpresa(),Integer.parseInt(an), usuario.getIdUsuario().intValue() ,valorIndicador);
					collection.insert(preIndicador.toDBObjectPreIndicador());
					model.addObject("msg",0);
					model.addObject("valorIndicador",valorIndicador);

				} catch (NoDataException e){
					
					model.addObject("msg",1);
					
				}
				
			} else {
				
				model.addObject("msg",0);
				valorIndicador = (String) cursor.get("valor");
				model.addObject("valorIndicador",valorIndicador);
				
			}

			//MOSTRAR POR INTERFAZ
			model.setViewName("evaluarIndicadores");
			model.addObject("command", new FiltroConsultaIndicador());
			model.addObject("empresas", dbCotizacion.getEmpresas());
			model.addObject("indicadores", dbSession.getIndicadores());
			model.addObject("anios", dbCotizacion.getAnios());

		}
		
		return model;					
	
	}
	
	// Eliminar indicador
	@RequestMapping(value="eliminarIndicador")
	public ModelAndView eliminarIndicador(@RequestParam(value="removeButton") int idIndicador, HttpSession session) throws SQLException {		
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {
			
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");
			
			model.setViewName("gestionIndicadores");
			model.addObject("command",new CrearIndicadorForm());	
			
			model.addObject("indicadores",dbSession.getIndicadores());	
			model.addObject("cuentas",dbCotizacion.getCuentas());
			
			dbSession.eliminarIndicador(idIndicador);
			
		}
		
		return model;					
	
	}
	
	// METODOLOGIAS
	
	// Ir a evaluar metodologias
	@RequestMapping(value="evaluarMetodologias", method={RequestMethod.GET})
	public ModelAndView irAEvaluarMetodologias(HttpSession session){
		
		ModelAndView model = new ModelAndView();
				
		if(session.getAttribute("usuario") == null){
			
			model.setViewName("login");
			model.addObject("msg",1);
			model.addObject("command",new LoginForm());
			
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");

			model.setViewName("evaluarMetodologias");
			model.addObject("usuario",usuario);
			model.addObject("metodologias",dbSession.getMetodologias());
			model.addObject("empresas",dbCotizacion.getEmpresas());
			model.addObject("anios",dbCotizacion.getAnios());

			model.addObject("command", new FiltroConsultaMetodologia());
			
		}
		
		return model;
		
	}
	
	// Generar consulta metodologia
	@RequestMapping(value="generarConsultaMetodologia", method=RequestMethod.POST)
	public ModelAndView generarConsultaMetodologia(HttpSession session, FiltroConsultaMetodologia filtroConsulta) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException, ScriptException {		
		
		ModelAndView model = new ModelAndView();
		
		if(session.getAttribute("usuario") == null){
			model.setViewName("login");
			model.addObject("command",new LoginForm());
		} else {
			
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");
			
			Metodologia met = dbSession.obtenerMetodologia(filtroConsulta.getMetodologia());
			
			model.setViewName("evaluarMetodologias");
			
			try {
				boolean rdo = met.evaluarMetodologia(filtroConsulta, dbSession, dbCotizacion);
				
				if(rdo){
					model.addObject("msg",0);
				} else {
					model.addObject("msg",1);
				}
			
			} catch (NoDataException e) {
				e.printStackTrace();
				model.addObject("msg",2);
			}
			
			model.addObject("usuario",usuario);
			model.addObject("metodologias",dbSession.getMetodologias());
			model.addObject("empresas",dbCotizacion.getEmpresas());
			model.addObject("anios",dbCotizacion.getAnios());
			model.addObject("command", new FiltroConsultaMetodologia());
						
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
			DBSession dbSession = (DBSession) session.getAttribute("dbSession");

			model.setViewName("gestionMetodologias");
			model.addObject("usuario",usuario);
			model.addObject("metodologias",dbSession.getMetodologias());
			
		}
		
		return model;
		
	}

	
}

