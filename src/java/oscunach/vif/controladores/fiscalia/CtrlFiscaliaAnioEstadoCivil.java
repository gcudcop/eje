/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oscunach.vif.controladores.fiscalia;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import oscunach.vif.busquedas.FBFichaFiscalia;
import oscunach.vif.entidades.FichaVif;

/**
 *
 * @author ICITS SALA5
 */
@ManagedBean
@RequestScoped
public class CtrlFiscaliaAnioEstadoCivil {

    private ArrayList<FichaVif> lst;
    private int aniosel;
    private CartesianChartModel graficarEstadoCivil;
    private CartesianChartModel graficarEstadoCivilGenero;

    public ArrayList<FichaVif> getLst() {
        return lst;
    }

    public void setLst(ArrayList<FichaVif> lst) {
        this.lst = lst;
    }

    public int getAniosel() {
        return aniosel;
    }

    public void setAniosel(int aniosel) {
        this.aniosel = aniosel;
    }

    public CartesianChartModel getGraficarEstadoCivil() {
        return graficarEstadoCivil;
    }

    public void setGraficarEstadoCivil(CartesianChartModel graficarEstadoCivil) {
        this.graficarEstadoCivil = graficarEstadoCivil;
    }

    public CartesianChartModel getGraficarEstadoCivilGenero() {
        return graficarEstadoCivilGenero;
    }

    public void setGraficarEstadoCivilGenero(CartesianChartModel graficarEstadoCivilGenero) {
        this.graficarEstadoCivilGenero = graficarEstadoCivilGenero;
    }

    public CtrlFiscaliaAnioEstadoCivil() {
        this.graficar();
    }

    public void graficar() {
        graficarEstadoCivil = estadoCivil(aniosel);
        graficarEstadoCivilGenero = estadoCivilGenero(aniosel);

    }

    private CartesianChartModel estadoCivil(int anio) {
        CartesianChartModel model = new CartesianChartModel();
        try {
            lst = FBFichaFiscalia.obtenerFchaEstadoCivilDadoAnio(anio);
            ChartSeries estadoCivil = new ChartSeries();
            estadoCivil.setLabel("ESTADO CIVIL");
            for (int i = 0; i < lst.size(); i++) {
                estadoCivil.set(lst.get(i).getEstado_civil_victima(), FBFichaFiscalia.obtenerFichaDadoAnioEstadoCivil(anio, lst.get(i).getEstado_civil_victima()).size());
            }
            model.addSeries(estadoCivil);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

    private CartesianChartModel estadoCivilGenero(int anio) {
        CartesianChartModel model = new CartesianChartModel();
        try {
            lst = FBFichaFiscalia.obtenerFchaEstadoCivilDadoAnio(anio);
            ChartSeries femenino = new ChartSeries();
            femenino.setLabel("Femenino");
            for (int i = 0; i < lst.size(); i++) {

                femenino.set(lst.get(i).getEstado_civil_victima(), FBFichaFiscalia.obtenerFichaDadoAnioEstadoCivilGenero(anio, lst.get(i).getEstado_civil_victima(), "Femenino").size());
            }

            ChartSeries masculino = new ChartSeries();
            masculino.setLabel("Masculino");
            for (int i = 0; i < lst.size(); i++) {
                masculino.set(lst.get(i).getEstado_civil_victima(), FBFichaFiscalia.obtenerFichaDadoAnioEstadoCivilGenero(anio, lst.get(i).getEstado_civil_victima(), "Masculino").size());
            }
            model.addSeries(femenino);
            model.addSeries(masculino);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

}
