// service/implement/FeatureMapper.java
package com.crai.ia.dropoutpredictor.service.implement;

import com.crai.ia.dropoutpredictor.dto.AlumnoDetailResponse;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class FeatureMapper {

  public static Map<String, Object> toFeatures(AlumnoDetailResponse d) {
    Map<String, Object> f = new LinkedHashMap<>();

    // Siempre enviar strings; null -> ""
    putS(f, "STATUS FINAL DE INCORPORACIÓN TESCHI", nv(d.statusFinalIncorporacion(), "ACTIVO"));
    putS(f, "OBSERVACIONES", firstNonBlank(d.observacionesEncuesta(), d.observacionesDetalle()));
    putS(f, "NOMBRE", d.nombreCompleto());
    putS(f, "ESTADO CIVIL", d.estadoCivil());
    putS(f, "PROMEDIO", formatPromedio(d.promedioDetalle(), d.promedioEncuesta()));

    putS(f, "CARRERA", d.carrera());
    putS(f, "TIENE HIJOS", yesNo(d.tieneHijos()));
    putS(f, "VIVE CON", d.viveCon());

    // El modelo usa esta clave exacta en tu ejemplo
    putS(f, "APOYO FINANCIERO DE PROBLEMAS ECONOMICOS", d.apoyoFinanciero());

    putS(f, "CUENTA CON EQUIPO DE COMPUTO", yesNo(d.cuentaEquipoComputo()));
    putS(f, "SITUACIÓN ACTUAL", d.situacionActual());
    putS(f, "SITUACIÓN ESTUDIANTIL", d.situacionEstudiantil());
    putS(f, "HE CONSIDERADO ABANDONAR MIS ESTUDIOS", yesNo(d.consideroAbandonar()));

    putS(f, "METODOS O PROMEBLAS DE APRENDIZAJE", d.metodosOProblemasAprendizaje());
    putS(f, "FORTALEZAS / DEBILIDADES EN EL CAMPO ESTUDIANTIL", d.fortalezasDebilidades());
    putS(f, "TRATAMIENTO MEDICO", d.tratamientoMedico());

    putS(f, "CONSUMO DE NICOTINA", normConsumo(d.consumoNicotina()));
    putS(f, "CONSUMO DE ALCOHOL", normConsumo(d.consumoAlcohol()));
    putS(f, "CONSUMO DE OTRAS SUSTANCIAS", d.consumoOtrasSustancias());

    putS(f, "ESTADO DE ANIMO", normEstadoAnimo(d.estadoAnimo()));
    putS(f, "SALUD MENTAL", d.saludMental());
    putS(f, "PROBLEMAS DE VIOLENCIA", d.problemasViolencia());

    // Tiempo al TESCHI en rangos de texto
    putS(f, "TIEMPO AL TESCHI", bucketTiempo(d.minutosDetalle(), d.minutosEncuesta()));

    System.out.println("Features mapeadas: " + f);
    return f;
  }

  /* ================= Helpers ================= */

  // Inserta SIEMPRE como String; null -> ""
  private static void putS(Map<String, Object> m, String k, Object v){
    m.put(k, v == null ? "" : String.valueOf(v));
  }

  private static String firstNonBlank(String... vs){
    for (String v: vs) if (v != null && !v.isBlank()) return v;
    return "";
  }

  private static String nv(String v, String def){ return (v==null || v.isBlank())? def: v; }

  private static String yesNo(Boolean b){
    if (b == null) return "";
    return b ? "SI" : "NO";
    // Nota: el modelo espera "SI"/"NO" como strings (no booleanos)
  }

  // Devuelve string ("" si no hay valor)
  private static String formatPromedio(BigDecimal... vals){
    for (BigDecimal v: vals) if (v != null) return v.stripTrailingZeros().toPlainString();
    return "";
  }

  // Rangos de tiempo en texto (coalesce de minutos de detalle/encuesta)
  private static String bucketTiempo(Integer... mins){
    Integer m = null;
    for (Integer x: mins) if (x != null) { m = x; break; }
    if (m == null) return "";
    if (m <= 5)   return "0-5 min";
    if (m <= 15)  return "5-15 min aprox";
    if (m <= 30)  return "15-30 min aprox";
    if (m <= 60)  return "30-60 min aprox";
    return "Más de 60 min";
  }

  private static String normConsumo(String v){
    if (v == null) return "";
    String x = v.trim().toLowerCase();
    if (x.startsWith("nunca")) return "Nunca";
    if (x.startsWith("ocas"))  return "Ocasionalmente";
    if (x.startsWith("fre"))   return "Frecuentemente";
    return v;
  }

  private static String normEstadoAnimo(String v){
    if (v == null) return "";
    // Ya viene con guiones; lo dejamos legible por si acaso
    return v.replace('_',' ');
  }
}
