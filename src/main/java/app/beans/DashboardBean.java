package app.beans;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import app.entities.Filiere;
import app.services.EtudiantService;

@ManagedBean
@Component
@Scope("session")
public class DashboardBean{
	
	@Autowired
	private EtudiantService etudiantService;
	private BarChartModel barModel;
	private PieChartModel pieModel;
	String[] colors = {"rgb(255, 99, 132)", "rgb(54, 162, 235)", 
			"rgb(255, 205, 86)",
			"rgba(255, 99, 132, 0.2)",
			"rgba(255, 159, 64, 0.2)",
			"rgba(255, 205, 86, 0.2)",
			"rgba(75, 192, 192, 0.2)",
			"rgba(54, 162, 235, 0.2)",
			"rgba(153, 102, 255, 0.2)"};

    public DashboardBean() {
    }
    
    public void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int i=0;
        for(Object[] objects : this.etudiantService.distributionByEntity()) {
    		values.add((Long)objects[1]);
    		bgColors.add(this.colors[i]);
    		labels.add(((Filiere)objects[0]).getCode());
    		
    		i++;
		}
        dataSet.setData(values);
        dataSet.setBackgroundColor(bgColors);
        data.addChartDataSet(dataSet);
        data.setLabels(labels);
        pieModel.setData(data);
    }

	public void createBarModel() {
	        barModel = new org.primefaces.model.charts.bar.BarChartModel();
	        ChartData data = new ChartData();

	        BarChartDataSet barDataSet = new BarChartDataSet();
	        barDataSet.setLabel("Nombre Des Etudiants");
	        List<Number> values = new ArrayList<>();
	        List<String> bgColor = new ArrayList<>();
	        List<String> borderColor = new ArrayList<>();
	        List<String> labels = new ArrayList<>();
	        int i = 0;
	        for(Object[] objects : this.etudiantService.distributionByEntity()) {
	        	values.add((Long)objects[1]);
	        	bgColor.add(this.colors[i]);
	        	borderColor.add(this.colors[i]);
	        	labels.add(((Filiere)objects[0]).getCode());
	        	i++;
			}
	        barDataSet.setData(values);

	        
	        barDataSet.setBackgroundColor(bgColor);

	        barDataSet.setBorderColor(borderColor);
	        
	        barDataSet.setBorderWidth(1);

	        data.addChartDataSet(barDataSet);

	        data.setLabels(labels);
	        barModel.setData(data);

	        //Options
	        BarChartOptions options = new BarChartOptions();
	        CartesianScales cScales = new CartesianScales();
	        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
	        linearAxes.setOffset(true);
	        CartesianLinearTicks ticks = new CartesianLinearTicks();
	        ticks.setBeginAtZero(true);
	        linearAxes.setTicks(ticks);
	        cScales.addYAxesData(linearAxes);
	        options.setScales(cScales);

	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Nombre des Etudiants Par Filiere");
	        options.setTitle(title);

	        Legend legend = new Legend();
	        legend.setDisplay(true);
	        legend.setPosition("top");
	        LegendLabel legendLabels = new LegendLabel();
	        legendLabels.setFontStyle("bold");
	        legendLabels.setFontColor("#2980B9");
	        legendLabels.setFontSize(24);
	        legend.setLabels(legendLabels);
	        options.setLegend(legend);

	        barModel.setOptions(options);
	}

	public BarChartModel getBarModel() {
		createBarModel();
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public PieChartModel getPieModel() {
        createPieModel();
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	
	
}