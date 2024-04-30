package beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class MensajesAlertas {

    public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public static void showInfo(String titulo, String contenido) {
        addMessage(FacesMessage.SEVERITY_INFO, titulo, contenido);
    }

    public static void showWarn(String titulo, String contenido) {
        addMessage(FacesMessage.SEVERITY_WARN, titulo, contenido);
    }

    public static void showError(String titulo, String contenido) {
        addMessage(FacesMessage.SEVERITY_ERROR, titulo, contenido);
    }
}
