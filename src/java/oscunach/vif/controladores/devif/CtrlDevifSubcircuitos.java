/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oscunach.vif.controladores.devif;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import oscunach.vif.busquedas.FBDevif;
import recursos.CircuitoSubcircuitoUrbano;

/**
 *
 * @author Oscunach JOSE DUCHI
 */
@ManagedBean
@RequestScoped
public class CtrlDevifSubcircuitos {

    private ArrayList<String> lst;
    private CartesianChartModel graficaSubcircuitos;
    private CartesianChartModel graficaSubcircuitosGenero;
    private int anioSel;

    public CartesianChartModel getGraficaSubcircuitos() {
        return graficaSubcircuitos;
    }

    public void setGraficaSubcircuitos(CartesianChartModel graficaSubcircuitos) {
        this.graficaSubcircuitos = graficaSubcircuitos;
    }

    public CartesianChartModel getGraficaSubcircuitosGenero() {
        return graficaSubcircuitosGenero;
    }

    public void setGraficaSubcircuitosGenero(CartesianChartModel graficaSubcircuitosGenero) {
        this.graficaSubcircuitosGenero = graficaSubcircuitosGenero;
    }

    public ArrayList<String> getLst() {
        return lst;
    }

    public void setLst(ArrayList<String> lst) {
        this.lst = lst;
    }

    public int getAnioSel() {
        return anioSel;
    }

    public void setAnioSel(int anioSel) {
        this.anioSel = anioSel;
    }

    public CtrlDevifSubcircuitos() {
        this.reinit();
    }

    private void reinit() {
        this.lst = new ArrayList<String>();
        this.graficar();
    }

    @PostConstruct
    public void graficar() {
        this.graficaSubcircuitos = circuitos(anioSel);
        this.graficaSubcircuitosGenero = circuitosGenero(anioSel);
    }

    private CartesianChartModel circuitos(int anio) {
        CartesianChartModel model = new CartesianChartModel();
        try {
            ChartSeries circuitos = new ChartSeries();
            circuitos.setLabel("Subcircuitos");
            lst = CircuitoSubcircuitoUrbano.obtenerSubcircuitoUrbano();
            for (int i = 0; i < lst.size(); i++) {
                circuitos.set(lst.get(i), FBDevif.obtenerDatosDadoAnioSubCircuito(anio, lst.get(i)).size());
            }
            model.addSeries(circuitos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

    private CartesianChartModel circuitosGenero(int anio) {
        CartesianChartModel model = new CartesianChartModel();
        try {
            lst = CircuitoSubcircuitoUrbano.obtenerSubcircuitoUrbano();
            ChartSeries femenino = new ChartSeries();
            femenino.setLabel("Femenino");
            for (int i = 0; i < lst.size(); i++) {
                femenino.set(lst.get(i), FBDevif.obtenerDatosDadoAnioSubCircuitoGenero(anio, lst.get(i), "FEMENINO").size());
            }

            ChartSeries masculino = new ChartSeries();
            masculino.setLabel("Masculino");
            for (int i = 0; i < lst.size(); i++) {
                masculino.set(lst.get(i), FBDevif.obtenerDatosDadoAnioSubCircuitoGenero(anio, lst.get(i), "MASCULINO").size());
            }

            model.addSeries(femenino);
            model.addSeries(masculino);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

}
