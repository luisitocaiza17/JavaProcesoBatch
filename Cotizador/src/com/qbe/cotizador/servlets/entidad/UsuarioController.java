package com.qbe.cotizador.servlets.entidad;

import com.qbe.cotizador.dao.entidad.EntidadDAO;
import com.qbe.cotizador.dao.entidad.PuntoVentaDAO;
import com.qbe.cotizador.dao.entidad.TipoIdentificacionDAO;
import com.qbe.cotizador.dao.entidad.UsuarioDAO;
import com.qbe.cotizador.dao.entidad.UsuarioXPuntoVentaDAO;
import com.qbe.cotizador.dao.seguridad.CredencialDAO;
import com.qbe.cotizador.dao.seguridad.RolDAO;
import com.qbe.cotizador.dao.seguridad.UsuarioRolDAO;
import com.qbe.cotizador.model.Credencial;
import com.qbe.cotizador.model.Entidad;
import com.qbe.cotizador.model.PuntoVenta;
import com.qbe.cotizador.model.Rol;
import com.qbe.cotizador.model.TipoIdentificacion;
import com.qbe.cotizador.model.Usuario;
import com.qbe.cotizador.model.UsuarioRol;
import com.qbe.cotizador.model.UsuarioXPuntoVenta;
import com.qbe.cotizador.transaction.entidad.EntidadTransaction;
import com.qbe.cotizador.transaction.entidad.UsuarioTransaction;
import com.qbe.cotizador.transaction.entidad.UsuarioXPuntoVentaTransaction;
import com.qbe.cotizador.transaction.seguridad.CredencialTransaction;
import com.qbe.cotizador.transaction.seguridad.UsuarioRolTransaction;
import com.qbe.cotizador.util.Utilitarios;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class Usuario
 */
@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    	doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject result = new JSONObject();
        CredencialTransaction credencialTransaction = new CredencialTransaction();
        UsuarioRolTransaction usuarioRolTransaction = new UsuarioRolTransaction();
        UsuarioTransaction usuarioTransaction = new UsuarioTransaction();
        EntidadTransaction entidadTransaction = new EntidadTransaction();
        UsuarioXPuntoVentaTransaction usuarioXPuntoVentaTransaction = new UsuarioXPuntoVentaTransaction();
        
        try {
            String codigoEnsurance = request.getParameter("codigoEnsurance") == null ? "" : request.getParameter("codigoEnsurance");
            String codigo = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
            String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre");
            String apellido = request.getParameter("apellido") == null ? "" : request.getParameter("apellido");
            String tipoIdentificacionId = request.getParameter("tipoIdentificacion") == null ? "" : request.getParameter("tipoIdentificacion");
            String identificacion = request.getParameter("identificacion") == null ? "" : request.getParameter("identificacion");
            String password = request.getParameter("password") == null ? "" : request.getParameter("password");
            String login = request.getParameter("login") == null ? "" : request.getParameter("login");
            String mail = request.getParameter("mail") == null ? "" : request.getParameter("mail");
            String activo = request.getParameter("activo") == null ? "" : request.getParameter("activo");
            String tipoConsulta = request.getParameter("tipoConsulta") == null ? "" : request.getParameter("tipoConsulta");
            String entidadId = request.getParameter("entidadId") == null ? "" : request.getParameter("entidadId");
            String rolId = request.getParameter("rol") == null ? "" : request.getParameter("rol");
            String puntoDeVentaId = request.getParameter("puntoDeVenta") == null ? "" : request.getParameter("puntoDeVenta");
            
            JSONObject UsuarioJSONObject = new JSONObject();
            JSONArray UsuarioJSONArray = new JSONArray();

            Usuario usuario = new Usuario();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Entidad entidad = new Entidad();
            EntidadDAO entidadDAO = new EntidadDAO();
            
            Credencial credencial = new Credencial();
            CredencialDAO credencialDAO = new CredencialDAO();
            
            Rol rol = new Rol();
            RolDAO rolDAO = new RolDAO();
            
            PuntoVenta puntoVenta = new PuntoVenta();
            PuntoVentaDAO puntoVentaDAO = new PuntoVentaDAO();
            
            if (!identificacion.equals(""))
                entidad = entidadDAO.buscarEntidadPorIdentificacion(identificacion);

            if (!identificacion.equals(""))
                entidad.setIdentificacion(identificacion);
            
            if(!entidadId.equals("")){
            	entidad=entidadDAO.buscarPorId(entidadId);
            	usuario.setEntidad(entidad);
            }
            
            if(!rolId.equals("")){
            	rol=rolDAO.buscarPorId(rolId);
            //	usuario.setRol(rol);
            }
            
            if(!puntoDeVentaId.equals("")){
            	puntoVenta=puntoVentaDAO.buscarPorId(puntoDeVentaId);
            //	usuario.setRol(rol);
            }

            if (!codigoEnsurance.equals(""))
                entidad.setEntEnsurance(codigoEnsurance);

            if (!tipoIdentificacionId.equals("")) {
                TipoIdentificacionDAO tiDAO = new TipoIdentificacionDAO();
                entidad.setTipoIdentificacion(tiDAO.buscarPorId(tipoIdentificacionId));
            }
            
            if (!codigoEnsurance.equals(""))
                entidad.setEntEnsurance(codigoEnsurance);

            if (!nombre.equals(""))
                entidad.setNombres(nombre);

            if (!apellido.equals(""))
                entidad.setApellidos(apellido);

            if (!nombre.equals("") && !apellido.equals(""))
                entidad.setNombreCompleto(apellido + " " + nombre);

            if (!mail.equals(""))
                entidad.setMail(mail);

            if (!codigo.equals("")){
            	usuario.setId(codigo);}

			if (!login.equals(""))
				usuario.setLogin(login);

			if (!password.equals(""))
				credencial.setClave(Utilitarios.encriptacionClave(password));

            if (activo.equals("1"))
            	usuario.setActivo(true);
            else
            	usuario.setActivo(false);
            
            if(usuario.getId()!=null)
            	credencial=credencialDAO.buscarPorUsuarioId(usuario);


            if (tipoConsulta.equals("encontrarTodos")) {
                List<Usuario> results = usuarioDAO.buscarTodos();
                int i = 0;
                for (i = 0; i < results.size(); i++) {
                    usuario = results.get(i);
                    UsuarioJSONObject.put("codigo", usuario.getId());
                    UsuarioJSONObject.put("codigoEnsurance", usuario.getEntidad().getEntEnsurance());
                    UsuarioJSONObject.put("identificacion", usuario.getEntidad().getIdentificacion());
                    UsuarioJSONObject.put("tipoIdentificacion", usuario.getEntidad().getTipoIdentificacion().getNombre());
                    UsuarioJSONObject.put("nombre", usuario.getEntidad().getNombres());
                    UsuarioJSONObject.put("apellido", usuario.getEntidad().getApellidos());
                    UsuarioJSONObject.put("nombreCompleto", usuario.getEntidad().getNombreCompleto());
                    UsuarioJSONObject.put("login", usuario.getLogin());
                    if(credencialDAO.buscarPorUsuarioId(usuario).getId()!=null)
                    UsuarioJSONObject.put("password", credencialDAO.buscarPorUsuarioId(usuario).getClave());
                    UsuarioJSONObject.put("mail", usuario.getEntidad().getMail());
                    if(usuario.getUsuarioRols().size() ==0)
                    	UsuarioJSONObject.put("rol", "SIN ROL");
                    else
                    	UsuarioJSONObject.put("rol", usuario.getUsuarioRols().get(0).getRol().getNombre());
                    if (usuario.getActivo())
                        UsuarioJSONObject.put("activo", "Si");
                    else
                        UsuarioJSONObject.put("activo", "No");
                    
                    if(usuario.getUsuarioXPuntoVentas().size() ==0)
                    	UsuarioJSONObject.put("puntoDeVenta", "SIN PUNTO DE VENTA");
                    else
                    	UsuarioJSONObject.put("puntoDeVenta", usuario.getUsuarioXPuntoVentas().get(0).getPuntoVenta().getNombre());
                   

                    UsuarioJSONArray.add(UsuarioJSONObject);
                }
                result.put("numRegistros", i);
                result.put("listadoUsuario", UsuarioJSONArray);

                TipoIdentificacionDAO tipoIndentificacionDAO = new TipoIdentificacionDAO();
                TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();

                JSONObject tipoIdentificacionJSONObject = new JSONObject();
                JSONArray tipoIdentificacionJSONArray = new JSONArray();

                List<TipoIdentificacion> listadoTipoIdentificacion = tipoIndentificacionDAO
                        .buscarTodos();

                for (i = 0; i < listadoTipoIdentificacion.size(); i++) {
                    tipoIdentificacion = listadoTipoIdentificacion.get(i);
                    tipoIdentificacionJSONObject.put("codigo", tipoIdentificacion.getId());
                    tipoIdentificacionJSONObject.put("nombre", tipoIdentificacion.getNombre());

                    tipoIdentificacionJSONArray.add(tipoIdentificacionJSONObject);
                }

                result.put("listadoTipoIdentificacion", tipoIdentificacionJSONArray);
                
                JSONObject entidadJSONObject = new JSONObject();
                JSONArray entidadJSONArray = new JSONArray();

                List<Entidad> listadoEntidad = entidadDAO.buscarTodos();

                for (i = 0; i < listadoEntidad.size(); i++) {
                	entidadJSONObject.put("codigo", listadoEntidad.get(i).getId());
                	entidadJSONObject.put("nombre", listadoEntidad.get(i).getNombreCompleto());

                	entidadJSONArray.add(entidadJSONObject);
                }

                result.put("listadoEntidad", entidadJSONArray);

                
                JSONObject rolJSONObject = new JSONObject();
                JSONArray rolJSONArray = new JSONArray();
                RolDAO rDAO=new RolDAO();

                List<Rol> listadoRol = rDAO.buscarTodosActivos();

                for (i = 0; i < listadoRol.size(); i++) {
                	rolJSONObject.put("codigo", listadoRol.get(i).getId());
                	rolJSONObject.put("nombre", listadoRol.get(i).getNombre());

                	rolJSONArray.add(rolJSONObject);
                }

                result.put("listadoRol", rolJSONArray);
                
                JSONObject puntoDeVentaJSONObject = new JSONObject();
                JSONArray puntoDeVentaJSONArray = new JSONArray();
                PuntoVentaDAO pvDAO=new PuntoVentaDAO();

                List<PuntoVenta> listadoPuntoDeVenta = pvDAO.buscarActivos();

                for (i = 0; i < listadoPuntoDeVenta.size(); i++) {
                	puntoDeVentaJSONObject.put("codigo", listadoPuntoDeVenta.get(i).getId());
                	puntoDeVentaJSONObject.put("nombre", listadoPuntoDeVenta.get(i).getNombre());

                	puntoDeVentaJSONArray.add(puntoDeVentaJSONObject);
                }

                result.put("listadoPuntoDeVenta", puntoDeVentaJSONArray);


            }

			if (tipoConsulta.equals("crear")) {
				if (entidad.getId() == null)
					entidad = entidadTransaction.crear(entidad);

				usuario.setEntidad(entidad);
				usuario.setValidacionMail("");
				usuario = usuarioTransaction.crear(usuario);
				credencial.setUsuario(usuario);
				credencial.setClave(Utilitarios.encriptacionClave(password));
				if (password.length() < 25)
					credencialTransaction.crear(credencial);

				if (rol.getId() != null) {
					UsuarioRolDAO usuarioRolDAO = new UsuarioRolDAO();
					UsuarioRol usuarioRol = usuarioRolDAO.buscarPorUsuario(usuario);
					usuarioRolDAO.eliminarPorUsuario(usuario);
					usuarioRol.setRol(rol);
					usuarioRol.setUsuario(usuario);
					usuarioRolTransaction.crear(usuarioRol);

				}

				if (puntoVenta.getId() != null) {
					UsuarioXPuntoVentaDAO usuarioXPuntoVentaDAO = new UsuarioXPuntoVentaDAO();
					UsuarioXPuntoVenta usuarioXPuntoVenta = new UsuarioXPuntoVenta();
					usuarioXPuntoVentaDAO.buscarPorUsuario(usuario);
					usuarioXPuntoVenta.setPuntoVenta(puntoVenta);
					usuarioXPuntoVenta.setUsuario(usuario);
					usuarioXPuntoVentaTransaction.crear(usuarioXPuntoVenta);
				} else {
					UsuarioXPuntoVentaDAO usuarioXPuntoVentaDAO = new UsuarioXPuntoVentaDAO();
					usuarioXPuntoVentaDAO.eliminarPorUsuario(usuario);
				}

			}
			if (tipoConsulta.equals("cambioEstado")) {
				usuario = usuarioDAO.buscarPorId(codigo);
				if (activo.equals("1")) {
					usuario.setActivo(true);
				} else if (activo.equals("0")) {
					usuario.setActivo(false);
				}
				usuarioTransaction.editar(usuario);
			}

			if (tipoConsulta.equals("eliminar")) {
				usuario = usuarioDAO.buscarPorId(codigo);
				usuarioTransaction.eliminar(usuario);
			}

			if (tipoConsulta.equals("actualizar")) {
				entidad = entidadTransaction.editar(entidad);
				usuario.setEntidad(entidad);
				usuario.setValidacionMail("");
				credencial = credencialDAO.buscarPorUsuarioId(usuario);
				if (password.length() < 25)
					credencial.setClave(Utilitarios.encriptacionClave(password));
				if (usuario.getId() != null) {
					usuario = usuarioTransaction.editar(usuario);
					credencialTransaction.editar(credencial);
				} else {
					usuario = usuarioTransaction.crear(usuario);
					credencialTransaction.crear(credencial);
				}
				if (rol.getId() != null) {
					UsuarioRolDAO usuarioRolDAO = new UsuarioRolDAO();
					UsuarioRol usuarioRol = new UsuarioRol();
					usuarioRolDAO.eliminarPorUsuario(usuario);
					usuarioRol.setRol(rol);
					usuarioRol.setUsuario(usuario);
					usuarioRolTransaction.crear(usuarioRol);

				}

				if (puntoVenta.getId() != null) {
					UsuarioXPuntoVentaDAO usuarioXPuntoVentaDAO = new UsuarioXPuntoVentaDAO();
					UsuarioXPuntoVenta usuarioXPuntoVenta = new UsuarioXPuntoVenta();
					usuarioXPuntoVentaDAO.eliminarPorUsuario(usuario);
					usuarioXPuntoVenta.setPuntoVenta(puntoVenta);
					usuarioXPuntoVenta.setUsuario(usuario);
					usuarioXPuntoVentaTransaction.crear(usuarioXPuntoVenta);

				} else {
					UsuarioXPuntoVentaDAO usuarioXPuntoVentaDAO = new UsuarioXPuntoVentaDAO();
					usuarioXPuntoVentaDAO.eliminarPorUsuario(usuario);
				}
            }
			
			if (tipoConsulta.equals("cargarActual")) {
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				if(usuario!=null&&usuario.getId()!=null){
					JSONObject usuarioJSONObject = new JSONObject();
					usuarioJSONObject.put("codigo", usuario.getId());
					usuarioJSONObject.put("nombreCompleto", usuario.getEntidad().getNombreCompleto());
					usuarioJSONObject.put("nombre", usuario.getEntidad().getNombres());
					usuarioJSONObject.put("apellido", usuario.getEntidad().getApellidos());
					usuarioJSONObject.put("identificacion", usuario.getEntidad().getIdentificacion());
					usuarioJSONObject.put("login", usuario.getLogin());
					usuarioJSONObject.put("mail", usuario.getEntidad().getMail());
	                result.put("usuario", usuarioJSONObject);
				}
				else{
					throw new Exception("Sin sesi�n");
				}
			}
			
			if (tipoConsulta.equals("cambiarClave")) {
				usuario=usuarioDAO.buscarPorId(codigo);
				if(usuario!=null&&usuario.getId()!=null){
					if(usuario.getCredencials().size()>0){
						Credencial cre =usuario.getCredencials().get(0);
						CredencialDAO creDAO=new CredencialDAO();
						String password1 = request.getParameter("password1") == null ? "" : request.getParameter("password1");
						String password2 = request.getParameter("password2") == null ? "" : request.getParameter("password2");
			            
						if (password1.equals(password2)) {
							if (Utilitarios.encriptacionClave(password).equals(cre.getClave())) {
								
								cre.setClave(Utilitarios.encriptacionClave(password1));
								cre=credencialTransaction.editar(cre);
								result.put("resultado", "Clave actualizada!");

							}
							else {
								throw new Exception("Clave actual incorrecta");
							}
						} else {
							throw new Exception("No coinciden las claves");
						}
						
					}
				}
			}
			
			if (tipoConsulta.equals("olvidoClave")) {
			
				
				
				usuario=usuarioDAO.buscarPorLogin(login);
				if(usuario!=null&&usuario.getId()!=null){
					entidad=usuario.getEntidad();
						
						//Envio de Correo
						// Obtenemos plantilla de cambioClave	
						String rutaPlantilla = this.getServletContext().getRealPath("")+"/static/plantillas/mailCambioClave.html";
						
						
						// Obtenemos el archivo y obtenemos los datos de la plantilla
						FileReader fr = null;
			            BufferedReader br = null;

			            String cuerpoMail = "";
			            try {
			            File archivo = new File (rutaPlantilla);
			            fr = new FileReader (archivo);
			            br = new BufferedReader(fr);

			            String linea;
			             
			            while((linea=br.readLine())!=null){
			                cuerpoMail = cuerpoMail + linea;
			            }
			            }catch (Exception e) {
							e.printStackTrace();
						}		           
			            br.close();
			            fr.close();
			            //String link = "http://localhost:8084/CotizadorWeb";
			            String link = request.getRequestURL().toString().replace("/EntidadController","");
						
			            String PasswordTemporal = Utilitarios.generarCodigoAleatorioPorLongitud(8);
			            
			            Credencial cre =usuario.getCredencials().get(0);
						CredencialDAO creDAO=new CredencialDAO();
						
						cre.setClave(Utilitarios.encriptacionClave(PasswordTemporal));
						credencialTransaction.editar(cre);
			            cuerpoMail = cuerpoMail.replace("#link#",link.replace("UsuarioController", ""));
						cuerpoMail = cuerpoMail.replace("#password#", PasswordTemporal);
						
						if(usuario.getEntidad()!=null)
			            Utilitarios.envioMail(usuario.getEntidad().getMail(), "Mail de Cambio de Clave", cuerpoMail);

					
				}
				
			}
			
			if (tipoConsulta.equals("olvidoClaveMail")) {
			
				
				entidad=entidadDAO.buscarEntidadPorMail(mail);
				usuario=usuarioDAO.buscarPorEntidad(entidad);
				if(usuario!=null&&usuario.getId()!=null){
					entidad=usuario.getEntidad();
						
						//Envio de Correo
						// Obtenemos plantilla de cambioClave	
						String rutaPlantilla = this.getServletContext().getRealPath("")+"/static/plantillas/mailCambioClave.html";
						
						
						// Obtenemos el archivo y obtenemos los datos de la plantilla
						FileReader fr = null;
			            BufferedReader br = null;

			            String cuerpoMail = "";
			            try {
			            File archivo = new File (rutaPlantilla);
			            fr = new FileReader (archivo);
			            br = new BufferedReader(fr);

			            String linea;
			             
			            while((linea=br.readLine())!=null){
			                cuerpoMail = cuerpoMail + linea;
			            }
			            }catch (Exception e) {
							e.printStackTrace();
						}		           
			            br.close();
			            fr.close();
			            //String link = "http://localhost:8084/CotizadorWeb";
			            String link = request.getRequestURL().toString().replace("/EntidadController","");
						
			            String PasswordTemporal = Utilitarios.generarCodigoAleatorioPorLongitud(8);
			            
			            Credencial cre =usuario.getCredencials().get(0);
						CredencialDAO creDAO=new CredencialDAO();
						
						cre.setClave(Utilitarios.encriptacionClave(PasswordTemporal));
						credencialTransaction.editar(cre);
			            
						cuerpoMail = cuerpoMail.replace("#link#",link.replace("UsuarioController", ""));
						cuerpoMail = cuerpoMail.replace("#password#", PasswordTemporal);
						
						if(usuario.getEntidad()!=null)
			            Utilitarios.envioMail(usuario.getEntidad().getMail(), "Mail de Cambio de Clave", cuerpoMail);

					
				}
				else{
					throw new Exception ("No se tiene registrado ningun usuario con ese correo, por favor contacte al administrador del sistema");
				}
				
			}

			//TODO: Fredyz, Aumento para obtener usuario actual con el rol
			if (tipoConsulta.equals("cargarActualRol")) {
				if(request.getSession().getAttribute("usuario")!=null)
				 usuario = (Usuario)request.getSession().getAttribute("usuario");
				if(usuario!=null&&usuario.getId()!=null){
					JSONObject usuarioJSONObject = new JSONObject();
					usuarioJSONObject.put("codigo", usuario.getId());
					usuarioJSONObject.put("nombreCompleto", usuario.getEntidad().getNombreCompleto());
					usuarioJSONObject.put("nombre", usuario.getEntidad().getNombres());
					usuarioJSONObject.put("apellido", usuario.getEntidad().getApellidos());
					usuarioJSONObject.put("identificacion", usuario.getEntidad().getIdentificacion());
					usuarioJSONObject.put("login", usuario.getLogin());
					usuarioJSONObject.put("mail", usuario.getEntidad().getMail());
					if(usuario.getUsuarioRols().size()!=0){
						for(UsuarioRol userRol:usuario.getUsuarioRols()){
							if(userRol.getRol().getNombre().equals("ADMINISTRADOR_PYMES"))
								usuarioJSONObject.put("rolPermitido", true);
							else
								usuarioJSONObject.put("rolPermitido", false);
						}
					}
					else
						usuarioJSONObject.put("rolPermitido", false);
	                result.put("usuario", usuarioJSONObject);
				}
				else{
					throw new Exception("Sin sesi�n");
				}
			}
			
            result.put("success", Boolean.TRUE);
            response.setContentType("application/json; charset=ISO-8859-1");
            result.write(response.getWriter());
        } catch (Exception e) {
            result.put("success", Boolean.FALSE);
            result.put("error", e.getLocalizedMessage());
            response.setContentType("application/json; charset=ISO-8859-1");
            result.write(response.getWriter());
            e.printStackTrace();

        }
    }

}
