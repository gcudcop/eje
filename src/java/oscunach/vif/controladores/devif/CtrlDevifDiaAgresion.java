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
import recursos.MesesDias;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@RequestScoped
public class CtrlDevifDiaAgresion {

    private ArrayList<String> lst;
    private CartesianChartModel graficaDias;
    private CartesianChartModel graficaDiasGenero;
    private int anioSel;

    public ArrayList<String> getLst() {
        return lst;
    }

    public void setLst(ArrayList<String> lst) {
        this.lst = lst;
    }

    public CartesianChartModel getGraficaDias() {
        return graficaDias;
    }

    public void setGraficaDias(CartesianChartModel graficaDias) {
        this.graficaDias = graficaDias;
    }

    public CartesianChartModel getGraficaDiasGenero() {
        return graficaDiasGenero;
    }

    public void setGraficaDiasGenero(CartesianChartModel graficaDiasGenero) {
        this.graficaDiasGenero = graficaDiasGenero;
    }

    public int getAnioSel() {
        return anioSel;
    }

    public void setAnioSel(int anioSel) {
        this.anioSel = anioSel;
    }

    public CtrlDevifDiaAgresion() {
        this.reinit();
    }

    private void reinit() {
        this.graficar();
    }

    @PostConstruct
    public void graficar() {
        graficaDias = diasAgresion(anioSel);
        graficaDiasGenero = diasAgresionGenero(anioSel);

    }

    private CartesianChartModel diasAgresion(int anio) {
        CartesianChartModel model = new CartesianChartModel();
        try {
            ChartSeries dias = new ChartSeries();
            dias.setLabel("Dias de Denuncia");
            lst = MesesDias.enlistarDias();
            for (int i = 0; i < lst.size(); i++) {
                dias.set(lst.get(i), FBDevif.obtenerDatosDadoAnioDiaAgresion(anio, lst.get(i)).size());
            }
            model.addSeries(dias);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

    private CartesianChartModel diasAgresionGenero(int anio) {
        CartesianChartModel model = new CartesianChartModel();
        try {
            lst = MesesDias.enlistarDias();
            ChartSeries femenino = new ChartSeries();
            femenino.setLabel("Femenino");
            for (int i = 0; i < lst.size(); i++) {
                femenino.set(lst.get(i), FBDevif.obtenerDatosDadoAnioDiaAgresionGenero(anio, lst.get(i), "FEMENINO").size());
            }

            ChartSeries masculino = new ChartSeries();
            masculino.setLabel("Masculino");
            for (int i = 0; i < lst.size(); i++) {
                masculino.set(lst.get(i), FBDevif.obtenerDatosDadoAnioDiaAgresionGenero(anio, lst.get(i), "MASCULINO").size());
            }

            model.addSeries(femenino);
            model.addSeries(masculino);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return model;
    }

}
