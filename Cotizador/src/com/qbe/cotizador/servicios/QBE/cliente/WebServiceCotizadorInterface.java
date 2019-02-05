
package com.qbe.cotizador.servicios.QBE.cliente;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "WebServiceCotizadorInterface", targetNamespace = "http://cotizador.interfaces.servicios.qbe.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WebServiceCotizadorInterface {


    /**
     * 
     * @param nombreParametro
     * @param parametro
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerDatosVehiculo(
        @WebParam(name = "parametro", partName = "parametro")
        String parametro,
        @WebParam(name = "nombreParametro", partName = "nombreParametro")
        String nombreParametro);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerCoberturasVH();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obteneProductosVH();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerDeducibles();

    /**
     * 
     * @param identificacion
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String datosUsuario(
        @WebParam(name = "identificacion", partName = "identificacion")
        String identificacion);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerPlanes();

    /**
     * 
     * @param placa
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public int obtenerNumeroReclamosPorPlaca(
        @WebParam(name = "placa", partName = "placa")
        String placa);

    /**
     * 
     * @param numeroFactura
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerEndosoIdPorNumerodeFactura(
        @WebParam(name = "numeroFactura", partName = "numeroFactura")
        String numeroFactura);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerCoberturaDetalleProducto();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerConfiguracionProductosVH();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerGruposCoberturaVH();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obteneConjuntoCoberturasVH();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerCoberturasConjuntoVH();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerDetallesProducto();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    public String obtenerCoberturasConjunto();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerConfiguracionProducto();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerConjuntoCoberuras();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerEntidadesJr();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerFirmasDigitales();
    
    /**
     * 
     * @param sucursal
     * @param entidad
     * @param ramo
     * @return
     *     returns byte[]
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public byte[] obtenerFirmasDigitalesParametros(
        @WebParam(name = "sucursal", partName = "sucursal")
        String sucursal,
        @WebParam(name = "entidad", partName = "entidad")
        String entidad,
        @WebParam(name = "ramo", partName = "ramo")
        String ramo);

    /**
     * 
     * @return
     *     returns String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerAurotizacionesSRI();
    
    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerMarcasVehiculo();
    
    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerColoresVehiculo();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
    public String obtenerTipoExtra();
    
    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "retorno", partName = "retorno")
	public String obtenerProductosGanadero();
    
    
}