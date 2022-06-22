package gui.predavac;

import java.awt.Color;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;

import citanjeIzFajla.Menadzer;
import entiteti.Rezultati;
import entiteti.Test;

public class Grafik implements ExampleChart<PieChart>{
	
	private Menadzer menadzer;
	private Test t;
	
	public Grafik(Menadzer menadzer, Test t) {
		super();
		this.menadzer = menadzer;
		this.t = t;
	}

	@Override
	public PieChart getChart() {
		 PieChart chart = new PieChartBuilder().width(550).height(350).title("Rezultati testa").build();
		 
		    // Customize Chart
		    Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
		    chart.getStyler().setSeriesColors(sliceColors);
		 
		    // Series
		    int jedan = 0;
		    int dva = 0;
		    int tri = 0;
		    int cetiri = 0;
		    int pet = 0;
		    for(Rezultati r : menadzer.getRezMen().getRezultati()) {
		    	if(r.getTest() == t) {
		    		if(r.getOcena() == 1)
		    			jedan++;
		    		else if(r.getOcena() == 2)
		    			dva++;
		    		else if(r.getOcena() == 3)
		    			tri++;
		    		else if(r.getOcena() == 4)
		    			cetiri++;
		    		else
		    			pet++;
		    	}
		    }
		    chart.addSeries("1", jedan);
		    chart.addSeries("2", dva);
		    chart.addSeries("3", tri);
		    chart.addSeries("4", cetiri);
		    chart.addSeries("5", pet);
		 
		    return chart;
		  }
}
