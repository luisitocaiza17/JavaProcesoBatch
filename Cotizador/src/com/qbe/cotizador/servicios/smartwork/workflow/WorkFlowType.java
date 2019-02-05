
package com.qbe.cotizador.servicios.smartwork.workflow;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WorkFlowType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WorkFlowType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AdjuntaralFile"/>
 *     &lt;enumeration value="Fianzas"/>
 *     &lt;enumeration value="FianzasEsmeraldas"/>
 *     &lt;enumeration value="FianzasGuayaquil"/>
 *     &lt;enumeration value="FianzasMachala"/>
 *     &lt;enumeration value="FianzasManta"/>
 *     &lt;enumeration value="FianzasPortoviejo"/>
 *     &lt;enumeration value="Impresi�nAmbato"/>
 *     &lt;enumeration value="Impresi�nCuenca"/>
 *     &lt;enumeration value="Impresi�nEsmeraldas"/>
 *     &lt;enumeration value="Impresi�nGuayaquil"/>
 *     &lt;enumeration value="Impresi�nIbarra"/>
 *     &lt;enumeration value="Impresi�nLoja"/>
 *     &lt;enumeration value="Impresi�nMachala"/>
 *     &lt;enumeration value="Impresi�nManta"/>
 *     &lt;enumeration value="Impresi�nPortoviejo"/>
 *     &lt;enumeration value="Impresi�nPuyo"/>
 *     &lt;enumeration value="Impresi�nRiobamba"/>
 *     &lt;enumeration value="Impresi�nSantoDomingo"/>
 *     &lt;enumeration value="Impresi�nSolicituddeEmisi�n"/>
 *     &lt;enumeration value="SolicituddeEmisi�n"/>
 *     &lt;enumeration value="SolicituddeEmisi�nAgenciaSur"/>
 *     &lt;enumeration value="SolicituddeEmisi�nContactCenter"/>
 *     &lt;enumeration value="SolicituddeEmisi�nAmbato"/>
 *     &lt;enumeration value="SolicituddeEmisi�nCuenca"/>
 *     &lt;enumeration value="SolicituddeEmisi�nEsmeraldas"/>
 *     &lt;enumeration value="SolicituddeEmisi�nGuayaquil"/>
 *     &lt;enumeration value="SolicituddeEmisi�nIbarra"/>
 *     &lt;enumeration value="SolicituddeEmisi�nLoja"/>
 *     &lt;enumeration value="SolicituddeEmisi�nMachala"/>
 *     &lt;enumeration value="SolicituddeEmisi�nManta"/>
 *     &lt;enumeration value="SolicituddeEmisi�nPortoviejo"/>
 *     &lt;enumeration value="SolicituddeEmisi�nPuyo"/>
 *     &lt;enumeration value="SolicituddeEmisi�nRiobamba"/>
 *     &lt;enumeration value="SolicituddeEmisi�nSantoDomingo"/>
 *     &lt;enumeration value="Visado"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WorkFlowType")
@XmlEnum
public enum WorkFlowType {

    @XmlEnumValue("AdjuntaralFile")
    ADJUNTARAL_FILE("AdjuntaralFile"),
    @XmlEnumValue("Fianzas")
    FIANZAS("Fianzas"),
    @XmlEnumValue("FianzasEsmeraldas")
    FIANZAS_ESMERALDAS("FianzasEsmeraldas"),
    @XmlEnumValue("FianzasGuayaquil")
    FIANZAS_GUAYAQUIL("FianzasGuayaquil"),
    @XmlEnumValue("FianzasMachala")
    FIANZAS_MACHALA("FianzasMachala"),
    @XmlEnumValue("FianzasManta")
    FIANZAS_MANTA("FianzasManta"),
    @XmlEnumValue("FianzasPortoviejo")
    FIANZAS_PORTOVIEJO("FianzasPortoviejo"),
    @XmlEnumValue("Impresi\u00f3nAmbato")
    IMPRESI�N_AMBATO("Impresi\u00f3nAmbato"),
    @XmlEnumValue("Impresi\u00f3nCuenca")
    IMPRESI�N_CUENCA("Impresi\u00f3nCuenca"),
    @XmlEnumValue("Impresi\u00f3nEsmeraldas")
    IMPRESI�N_ESMERALDAS("Impresi\u00f3nEsmeraldas"),
    @XmlEnumValue("Impresi\u00f3nGuayaquil")
    IMPRESI�N_GUAYAQUIL("Impresi\u00f3nGuayaquil"),
    @XmlEnumValue("Impresi\u00f3nIbarra")
    IMPRESI�N_IBARRA("Impresi\u00f3nIbarra"),
    @XmlEnumValue("Impresi\u00f3nLoja")
    IMPRESI�N_LOJA("Impresi\u00f3nLoja"),
    @XmlEnumValue("Impresi\u00f3nMachala")
    IMPRESI�N_MACHALA("Impresi\u00f3nMachala"),
    @XmlEnumValue("Impresi\u00f3nManta")
    IMPRESI�N_MANTA("Impresi\u00f3nManta"),
    @XmlEnumValue("Impresi\u00f3nPortoviejo")
    IMPRESI�N_PORTOVIEJO("Impresi\u00f3nPortoviejo"),
    @XmlEnumValue("Impresi\u00f3nPuyo")
    IMPRESI�N_PUYO("Impresi\u00f3nPuyo"),
    @XmlEnumValue("Impresi\u00f3nRiobamba")
    IMPRESI�N_RIOBAMBA("Impresi\u00f3nRiobamba"),
    @XmlEnumValue("Impresi\u00f3nSantoDomingo")
    IMPRESI�N_SANTO_DOMINGO("Impresi\u00f3nSantoDomingo"),
    @XmlEnumValue("Impresi\u00f3nSolicituddeEmisi\u00f3n")
    IMPRESI�N_SOLICITUDDE_EMISI�N("Impresi\u00f3nSolicituddeEmisi\u00f3n"),
    @XmlEnumValue("SolicituddeEmisi\u00f3n")
    SOLICITUDDE_EMISI�N("SolicituddeEmisi\u00f3n"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nAgenciaSur")
    SOLICITUDDE_EMISI�N_AGENCIA_SUR("SolicituddeEmisi\u00f3nAgenciaSur"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nContactCenter")
    SOLICITUDDE_EMISI�N_CONTACT_CENTER("SolicituddeEmisi\u00f3nContactCenter"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nAmbato")
    SOLICITUDDE_EMISI�N_AMBATO("SolicituddeEmisi\u00f3nAmbato"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nCuenca")
    SOLICITUDDE_EMISI�N_CUENCA("SolicituddeEmisi\u00f3nCuenca"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nEsmeraldas")
    SOLICITUDDE_EMISI�N_ESMERALDAS("SolicituddeEmisi\u00f3nEsmeraldas"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nGuayaquil")
    SOLICITUDDE_EMISI�N_GUAYAQUIL("SolicituddeEmisi\u00f3nGuayaquil"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nIbarra")
    SOLICITUDDE_EMISI�N_IBARRA("SolicituddeEmisi\u00f3nIbarra"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nLoja")
    SOLICITUDDE_EMISI�N_LOJA("SolicituddeEmisi\u00f3nLoja"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nMachala")
    SOLICITUDDE_EMISI�N_MACHALA("SolicituddeEmisi\u00f3nMachala"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nManta")
    SOLICITUDDE_EMISI�N_MANTA("SolicituddeEmisi\u00f3nManta"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nPortoviejo")
    SOLICITUDDE_EMISI�N_PORTOVIEJO("SolicituddeEmisi\u00f3nPortoviejo"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nPuyo")
    SOLICITUDDE_EMISI�N_PUYO("SolicituddeEmisi\u00f3nPuyo"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nRiobamba")
    SOLICITUDDE_EMISI�N_RIOBAMBA("SolicituddeEmisi\u00f3nRiobamba"),
    @XmlEnumValue("SolicituddeEmisi\u00f3nSantoDomingo")
    SOLICITUDDE_EMISI�N_SANTO_DOMINGO("SolicituddeEmisi\u00f3nSantoDomingo"),
    @XmlEnumValue("Visado")
    VISADO("Visado");
    private final String value;

    WorkFlowType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WorkFlowType fromValue(String v) {
        for (WorkFlowType c: WorkFlowType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
