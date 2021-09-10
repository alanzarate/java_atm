/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.modelo;

import bo.edu.ucb.est.iu.Pantalla;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ecampohermoso
 */
public class Cajero {
    
    private Pantalla pantallaIngreso;
    private Pantalla pantallaMenuPrincipal;
    private Pantalla pantallaError;
    private Pantalla pantallaRealizarDeposito;
    private Pantalla pantallaRealizarRetiro;
    private Banco banco;
    

    public Cajero(Banco banco) {
        this.banco = banco;
        // Inicialización de pantallas y configuración.
        pantallaIngreso = new Pantalla("Cajero automático");
        List ingresoContenido = new ArrayList();
        ingresoContenido.add(" Bienvenido al sistema, por favor ingrese su credenciales");
        pantallaIngreso.setContenido(ingresoContenido);
        pantallaIngreso.definirDatoEntrada("Código de usuario: ", "String");
        pantallaIngreso.definirDatoEntrada("PIN: ", "String");
        
        pantallaMenuPrincipal = new Pantalla("Cajero ATM");
        List menuPrincipalContenido = new ArrayList();
        menuPrincipalContenido.add(" Elija una de las siguientes opciones:");
        menuPrincipalContenido.add(" 1. Ver saldo.");
        menuPrincipalContenido.add(" 2. Retirar dinero.");
        menuPrincipalContenido.add(" 3. Depositar dinero.");
        menuPrincipalContenido.add(" 4. Salir");
        menuPrincipalContenido.add(" ");
        pantallaMenuPrincipal.setContenido(menuPrincipalContenido);
        pantallaMenuPrincipal.definirDatoEntrada("Seleccione una opción: ", "Integer");
        
        // Las siguientes son pantallas dinamicas, es decir su contenido
        // va a cambiar con el comportamiento del programa
        
        // Iniciamos pantalla de error.
        pantallaError = new Pantalla("Ocurrio un error");        
        // Corremos primera pantalla
        
        mostrarPantallaPrincipal();
        
    }
    
    private void mostrarPantallaPrincipal() {
        List<Object> credenciales = pantallaIngreso.desplegar();
        Cliente cliente = banco.buscarClientePorCodigo( (String) credenciales.get(0), 
                (String) credenciales.get(1));
        
        if (cliente == null) { // Significa que las credenciales son incorrectas
            List contenido = new ArrayList();
            contenido.add("No se encontró al usuario.");
            pantallaError.setContenido(contenido);
            pantallaError.desplegar();
            mostrarPantallaPrincipal();
        } else {
            List<Object> opcionListado = pantallaMenuPrincipal.desplegar();
            Integer opcion = (Integer) opcionListado.get(0);
            if (opcion == 1) { // Ver saldo
                verSaldo(cliente);
            } else if (opcion == 2){ // Depositar
            } else {
               mostrarPantallaPrincipal();
            }
        
        }
    }
    
    private void verSaldo(Cliente cliente) {
        List<String> listadoCuentasContenido = new ArrayList();
        listadoCuentasContenido.add(" Elija una sus cuentas:");
        for ( int i = 0 ; i < cliente.getCuentas().size() ; i ++ ) {
            Cuenta cuenta = cliente.getCuentas().get(i);
            listadoCuentasContenido.add( (i + 1) + " " + cuenta.getNroCuenta() 
                    + " " + cuenta.getTipo());
        }
        Pantalla pantallaListadoCuentas = new Pantalla("Sus cuentas");
        pantallaListadoCuentas.definirDatoEntrada("Seleccione una opción: ", "Integer");
        pantallaListadoCuentas.setContenido(listadoCuentasContenido);
        List<Object> datosIntroducidos = pantallaListadoCuentas.desplegar(); // Retorna la cuenta que eligió
        Integer indiceCuenta = (Integer) datosIntroducidos.get(0);
        //TODO validar que el indiceCuenta sea un numero entre 1 y el numero total de cuentas
        // La cuenta para mostrar el saldo
        Cuenta cuenta = cliente.getCuentas().get(indiceCuenta - 1);
        Pantalla pantallaVerSaldo = new Pantalla("Ver saldo");
        List<String> contenidoVerSaldo = new ArrayList();
        contenidoVerSaldo.add("Cliente: " + cliente.getNombre());
        contenidoVerSaldo.add("Nro Cuenta: " + cuenta.getNroCuenta());
        contenidoVerSaldo.add("Saldo: " + cuenta.getMoneda() + " " + cuenta.getSaldo());
        pantallaVerSaldo.setContenido(contenidoVerSaldo);
        pantallaVerSaldo.desplegar();
        mostrarPantallaPrincipal();
        
        
    }
    
    
}
