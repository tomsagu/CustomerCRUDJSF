/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customercrud.bean;

import customercrud.ejb.CustomerFacade;
import customercrud.entity.Customer;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author guzman
 */
@Named(value = "customerBean")
@SessionScoped
public class CustomerBean implements Serializable{
    

    
    @EJB
    private CustomerFacade customerFacade;
    
    private List<Customer> listaClientes;
    
    private Integer idCustomerSeleccionado = -1;

    public Integer getIdCustomerSeleccionado() {
        return idCustomerSeleccionado;
    }

    public void setIdCustomerSeleccionado(Integer idCustomerSeleccionado) {
        this.idCustomerSeleccionado = idCustomerSeleccionado;
    }
    /**
     * Creates a new instance of CustomerBean
     */
    public CustomerBean() {
    }

    public List<Customer> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Customer> listaClientes) {
        this.listaClientes = listaClientes;
    }
    
    @PostConstruct
    public void init () {
        this.listaClientes = this.customerFacade.findAll();
    }
       
    public String doBorrar(Integer id){
        Customer c = this.customerFacade.find(id);
        this.customerFacade.remove(c);
        return "index";
    }
    
    public String doEditar(Integer id){
        this.idCustomerSeleccionado = id;
        return "cliente";
    }
    
}
