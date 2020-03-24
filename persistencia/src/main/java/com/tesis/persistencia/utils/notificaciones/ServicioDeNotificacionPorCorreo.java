package com.tesis.persistencia.utils.notificaciones;

import com.tesis.dominio.modelos.Notificacion;
import com.tesis.dominio.modelos.Usuario;
import com.tesis.dominio.utils.ServicioDeNotification;
import com.tesis.dominio.utils.exceptions.NotificationNotFoundException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import reactor.core.publisher.Flux;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicioDeNotificacionPorCorreo implements ServicioDeNotification {

    private final NotificacionConf notificacionConf;

    private Configuration freemarkerConfig;

    public ServicioDeNotificacionPorCorreo(NotificacionConf notificacionConf, Configuration freemarkerConfig) {
        this.notificacionConf = notificacionConf;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public Flux<Usuario> enviarNotificacionAlAdmin(Notificacion notificacion) {
        return enviarAlAdmin(notificacion) != null ? Flux.just(notificacion.getPara())
                : Flux.error(new NotificationNotFoundException("Hubo un inconveniente enviando la notificacion, intente de nuevo"));
    }

    private Usuario enviarAlAdmin(Notificacion notificacion) {
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            Template template = freemarkerConfig.getTemplate("MailAdminTemplate.ftl");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("nombreDelEmpleado", notificacion.getPara().getNombreDeUsuario());
            model.put("direccionDeConfirmacion",
                    "hppt://" + InetAddress.getLoopbackAddress().getHostName()
                            + ":4200/usuarios/confirmarEmail?id=" + notificacion.getInvolucrado().getId());


            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setTo(notificacion.getPara().getCorreoElectronico());
            helper.setSubject(notificacion.getAsunto());
            helper.setText(html, true);
        };

        try {
            notificacionConf.obtenerEnviadorDelEmail().send(messagePreparator);
            return notificacion.getInvolucrado();
        } catch (MailException e) {
            return null;
        }
    }

    @Override
    public Flux<Boolean> enviarNotificacionAlUsuario(Notificacion notificacion) {
        return enviarAlUsuario(notificacion) ? Flux.just(true) :
                Flux.error(new NotificationNotFoundException("Sucedio un problema con la notificacion"));
    }

    private boolean enviarAlUsuario(Notificacion notificacion) {
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            Template template = freemarkerConfig.getTemplate("MailUserTemplate.ftl");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("nombreDelAdmin", notificacion.getPara().getNombreDeUsuario());
            model.put("nombreDelEmpleado", notificacion.getPara().getNombreDeUsuario());
            model.put("direccionDeConfirmacion",
                    "hppt://" + InetAddress.getLoopbackAddress().getHostName()
                            + ":4200/usuarios/confirmarEmail?id=" + notificacion.getPara().getId());

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setTo(notificacion.getPara().getCorreoElectronico());
            helper.setSubject(notificacion.getAsunto());
            helper.setText(html, true);
        };

        try {
            notificacionConf.obtenerEnviadorDelEmail().send(messagePreparator);
            return true;
        } catch (MailException e) {
            return false;
        }
    }
}
