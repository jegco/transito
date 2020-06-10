package com.tesis.transito.persistencia.utils.notificaciones;

import com.tesis.transito.dominio.modelos.Notificacion;
import com.tesis.transito.dominio.modelos.Usuario;
import com.tesis.transito.dominio.utils.ServicioDeNotification;
import com.tesis.transito.dominio.utils.exceptions.NotificationNotFoundException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicioDeNotificacionPorCorreo implements ServicioDeNotification {

    private final NotificacionConf notificacionConf;

    private Configuration freemarkerConfig;

    @Value("classpath:/DATT.png")
    Resource logo;

    public ServicioDeNotificacionPorCorreo(NotificacionConf notificacionConf, Configuration freemarkerConfig) {
        this.notificacionConf = notificacionConf;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public Mono<Usuario> enviarNotificacionAlAdmin(Notificacion notificacion) {
        return enviarAlAdmin(notificacion) != null ? Mono.just(notificacion.getPara())
                : Mono.error(new NotificationNotFoundException("Hubo un inconveniente enviando la notificacion, intente de nuevo"));
    }

    private Usuario enviarAlAdmin(Notificacion notificacion) {
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.addAttachment("logo.png", new ClassPathResource("DATT.png"));
            Template template = freemarkerConfig.getTemplate("MailAdminTemplate.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("nombreDelAdmin", notificacion.getPara().getNombreDeUsuario());
            model.put("nombreDelEmpleado", notificacion.getInvolucrado().getNombreDeUsuario());
            model.put("logo", new ClassPathResource("DATT.png"));
            model.put("direccionDeConfirmacion",
                    "https://transito-admin-app.herokuapp.com/dashboard/usuarios");


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
    public Mono<Boolean> enviarNotificacionAlUsuario(Notificacion notificacion) {
        return enviarAlUsuario(notificacion) ? Mono.just(true) :
                Mono.error(new NotificationNotFoundException("Sucedio un problema con la notificacion"));
    }

    private boolean enviarAlUsuario(Notificacion notificacion) {
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            Template template = freemarkerConfig.getTemplate("MailUserTemplate.ftl");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("nombreDelAdmin", notificacion.getPara().getNombreDeUsuario());
            model.put("nombreEmpleado", notificacion.getPara().getNombreDeUsuario());
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
