package com.stfonavi.proju.util.helper;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;

public class Constantes {

    public static final String tituloJuzgado = "Listado de Juzgados";
    public static final String tituloEtapaProcesal = "Listado - Etapa Procesal";
    public static final String registroForm = "Nuevo registro";
    public static final String editarForm = "Editar registro";


    public static  final String botonNuevo = "Nuevo Registro";
    public static  final String botonEditar = "Editar Registro";


    public static final String SOMETHING_WENT_WRONG = "Something Went Wrong.";

    public static final String INVALID_DATA = "Invalid Data.";

    public static final String UNAUTHORIZED_ACCESS="Unauthorized access.";


    public static final String FORMATO_FECHA = "dd/MM/yyy";

    public static final String VERSION = "v 1.0.0";

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat(FORMATO_FECHA);
    }

    public static Date parseDate(String dateString) throws ParseException {
        if (dateString != null && !dateString.isEmpty()) {
            return getSimpleDateFormat().parse(dateString);
        } else {
            // Manejar el caso en el que dateString es null o vacío
            return null; // o lanzar una excepción, dependiendo de tus requisitos
        }
    }

    public static final Logger logger = Logger.getLogger(Constantes.class.getName());


    public static final String correoDe = "sifonavi@stfonavi.gob.pe";

    public static final String APELLIDO_PATERNO_ERROR = "Los apellidos y nombres no deben contener números ni caracteres especiales.";
    public static final String APELLIDO_MATERNO_ERROR = "Los apellidos y nombres no deben contener números ni caracteres especiales.";
    public static final String NOMBRE_USUARIO_ERROR = "Los nombres no deben contener números ni caracteres especiales.";

    public static final Date FECHA_ACTUAL = new Date();

    public static String titulo;
    public static String mensajeFlash;
    public static String button;

    public static final String RESULTADO_EXITOSO = "1";
    public static final String RESULTADO_ALTERNATIVO = "2";
    public static final String RESULTADO_ERROR = "3";

    public static String obtenerIP() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String direccionIP = request.getHeader("X-Forwarded-For");

        if (direccionIP == null || direccionIP.isEmpty() || "unknown".equalsIgnoreCase(direccionIP)) {
            direccionIP = request.getHeader("Proxy-Client-IP");
        }
        if (direccionIP == null || direccionIP.isEmpty() || "unknown".equalsIgnoreCase(direccionIP)) {
            direccionIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (direccionIP == null || direccionIP.isEmpty() || "unknown".equalsIgnoreCase(direccionIP)) {
            direccionIP = request.getHeader("HTTP_CLIENT_IP");
        }
        if (direccionIP == null || direccionIP.isEmpty() || "unknown".equalsIgnoreCase(direccionIP)) {
            direccionIP = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (direccionIP == null || direccionIP.isEmpty() || "unknown".equalsIgnoreCase(direccionIP)) {
            direccionIP = request.getRemoteAddr();
        }

        return direccionIP;
    }

}
