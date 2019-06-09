package p1;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Drawing {
	static void DrawBarLines(Canvas canvas){
		int vUnit = canvas.getHeight()/13;
		Graphics2D G2D =(Graphics2D) canvas.getGraphics();
		for (int j = 1; j < 13; j++) {
			G2D.setPaint(Color.white);
			G2D.drawLine(0,vUnit*j ,canvas.getWidth(),vUnit*j);
		}
	}
	
	static void DrawChart(Canvas canvas, Sequence chart,Color[] colors){
		Graphics2D G2D =(Graphics2D) canvas.getGraphics();
		Point origin = canvas.getLocation();
		int vUnit = canvas.getHeight()/15;
		int hUnit = canvas.getWidth()/chart.chart[0].length;
		int oSize = Math.min(vUnit,hUnit);
		/*for (int j = 1; j < 13; j++) {
			G2D.setPaint(Color.white);
			G2D.drawLine(0,vUnit*j ,canvas.getWidth(),vUnit*j);
		}*/
		for(int i = 0; i < chart.chart[0].length; i++){
			for (int j = 0; j < 13; j++) {
		        G2D.setPaint(colors[j]);
		        //this looks cool AND USEFUL!
				//G2D.drawOval(origin.x + (i*50),origin.y - (j)*vUnit,20,20);
				if(chart.chart[j][i]){
					boolean foundNext = false;
					// Set Color to note color
			        G2D.setPaint(colors[j]);
					// Draw note circle
			        G2D.fillOval(origin.x + (i*hUnit) - oSize/2 ,(13 - j)*vUnit - oSize/2 ,oSize,oSize);
					if(i != chart.chart[0].length - 1){
						for(int k = 0; k < 13; k++){
							if(chart.chart[k][i+1] == true){
								foundNext = true;
								//Set Stroke Width
								G2D.setStroke(new BasicStroke(6 - (chart.octave)/2));
								//Draw Line
								G2D.setPaint(new GradientPaint(origin.x + (i*hUnit),(13-j)*vUnit,colors[j],origin.x + ((i+1)*hUnit),(13 - k)*vUnit,colors[k]));
								G2D.drawLine(origin.x + (i*hUnit), (13 - j)*vUnit, origin.x + ((i+1)*hUnit), (13 - k)*vUnit);
							}
						}
					}
				}
			}
		}
	}
	//UNUSED 
	static void DrawChartTimes(Canvas canvas, Sequence chart,Color[] colors, int time){
		Graphics2D G2D =(Graphics2D) canvas.getGraphics();
		Point origin = canvas.getLocation();
		int vUnit = canvas.getHeight()/13/time;
		int hUnit = canvas.getWidth()/chart.chart[0].length/time;
		int oSize = Math.min(vUnit,hUnit);
		/*for (int j = 1; j < 13; j++) {
			G2D.setPaint(Color.white);
			G2D.drawLine(0,vUnit*j ,canvas.getWidth(),vUnit*j);
		}*/
		
		for(int t = 0; t < time; t++){
			for(int i = 16*t; i < t*chart.chart[0].length; i++){
				for (int j = 0; j < 12; j++) {
			        G2D.setPaint(colors[j]);
			        //this looks cool AND USEFUL!
					//G2D.drawOval(origin.x + (i*50),origin.y - (j)*vUnit,20,20);
					if(chart.chart[j][i%16]){
						boolean foundNext = false;
						// Set Color to note color
				        G2D.setPaint(colors[j]);
						// Draw note circle
				        G2D.fillOval(origin.x + (i*hUnit) ,(12 - j)*vUnit - oSize/2 ,oSize,oSize);
						if(i != chart.chart[0].length - 1){
							for(int k = 0; k < 12; k++){
								if(chart.chart[k][(i%16)+1] == true){
									foundNext = true;
									//Set Stroke Width
									G2D.setStroke(new BasicStroke(3));
									//Draw Line
									G2D.setPaint(new GradientPaint(origin.x + (i*hUnit),(12-j)*vUnit,colors[j],origin.x + ((i+1)*hUnit),(12 - k)*vUnit,colors[k]));
									G2D.drawLine(origin.x + (i*hUnit), (12 - j)*vUnit, origin.x + ((i+1)*hUnit), (12 - k)*vUnit);
								}
							}
						}
					}
				}
			}
		}
	}
}
