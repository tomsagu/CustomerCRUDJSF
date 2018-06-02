/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customercrud.bean;

import customercrud.ejb.CustomerFacade;
import customercrud.ejb.DiscountCodeFacade;
import customercrud.ejb.MicroMarketFacade;
import customercrud.entity.Customer;
import customercrud.entity.DiscountCode;
import customercrud.entity.MicroMarket;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author Tomas
 */
@Named(value = "customerCrearBean")
@Dependent
public class CustomerCrearBean {
    
    @Inject
    private CustomerBean customerBean;
        
    @EJB
    private CustomerFacade customerFacade;
    
    @EJB
    private MicroMarketFacade microMarketFacade;
    
    @EJB
    private DiscountCodeFacade discountCodeFacade;

    protected Customer cliente;
    protected List<MicroMarket> listaSupermercados;
    protected List<DiscountCode> listaDescuentos;
    protected String supermercadoSeleccionado;
    protected String codeSeleccionado;

    public String getCodeSeleccionado() {
        return codeSeleccionado;
    }

    public void setCodeSeleccionado(String codeSeleccionado) {
        this.codeSeleccionado = codeSeleccionado;
    }

    public String getSupermercadoSeleccionado() {
        return supermercadoSeleccionado;
    }

    public void setSupermercadoSeleccionado(String supermercadoSeleccionado) {
        this.supermercadoSeleccionado = supermercadoSeleccionado;
    }
    
    /**
     * Creates a new instance of CustomerCrearBean
     */
    public CustomerCrearBean() {
    }
    
    @PostConstruct
    public void init(){
        listaSupermercados = this.microMarketFacade.findAll();
        listaDescuentos = this.discountCodeFacade.findAll();
        if(customerBean.getIdCustomerSeleccionado() != -1){ //Editar
           this.cliente = this.customerFacade.find(this.customerBean.getIdCustomerSeleccionado());
        }else{
            cliente = new Customer();
        }
    }

    public List<MicroMarket> getListaSupermercados() {
        return listaSupermercados;
    }

    public void setListaSupermercados(List<MicroMarket> listaSupermercados) {
        this.listaSupermercados = listaSupermercados;
    }

    public List<DiscountCode> getListaDescuentos() {
        return listaDescuentos;
    }

    public void setListaDescuentos(List<DiscountCode> listaDescuentos) {
        this.listaDescuentos = listaDescuentos;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }
    
    public String doGuardar(){
        MicroMarket superm = this.microMarketFacade.find(supermercadoSeleccionado);
        cliente.setZip(superm);
        DiscountCode code = this.discountCodeFacade.find(codeSeleccionado);
        cliente.setDiscountCode(code);
        cliente.setCustomerId(this.customerFacade.buscarCustomerIdSiguiente());
        this.customerFacade.create(cliente);
        return "index";
    }
            
}
