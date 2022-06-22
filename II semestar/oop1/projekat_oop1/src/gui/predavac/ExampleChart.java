package gui.predavac;

import org.knowm.xchart.internal.chartpart.Chart;


public interface ExampleChart<C extends Chart<?, ?>>{
	 public C getChart();
}
