<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head></head>
<body>
<div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
<div style="padding: 2% 16px 2% 16px; flex-direction: column; justify-content: center; align-items: center; display: flex;">
       <img src="https://www.datatools.com.co/userfiles/image/Tr%C3%A1nsito%20y%20Movilidad/DATT.jpg" style="margin: 10% 1% 1% 1%; width: 30%;">
       <h4><b style="flex-direction: column; justify-content: center; align-items: center; display: flex;">Hola ${nombreDelAdmin}</b></h4>
    <p>El empleado ${nombreDelEmpleado}
        solicita unirse a la aplicación de guias del transito,
        si desea autorizar el inicio de sesion por favor haga click en el boton</p>
    <button onClick="confirmar()" style="background-color: #4CAF50; border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px;" ">Confirmar</button>
</div>
</div>
<script>
function confirmar(){
window.open(${direccionDeConfirmacion});
}
</script>

</body>
</html>