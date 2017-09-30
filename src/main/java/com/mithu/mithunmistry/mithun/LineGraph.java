package com.mithu.mithunmistry.mithun;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class LineGraph {

    private GraphicalView view;

    private TimeSeries dataset = new TimeSeries("Temperature statistics-");
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

    private XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph

    public LineGraph()
    {
        // Add single dataset to multiple dataset
        mDataset.addSeries(dataset);

        // Customization time for line 1!
        renderer.setColor(Color.YELLOW);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);

        // Enable Zoom
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setXTitle("Time (in seconds)");
        mRenderer.setYTitle("Temperature");
        //mRenderer.setYAxisMin(10);
        //mRenderer.setYAxisMax(50);
        mRenderer.setAxisTitleTextSize(24);
        mRenderer.setZoomEnabled(true, true);
        mRenderer.setBackgroundColor(Color.BLACK);
        mRenderer.setApplyBackgroundColor(true);
        //mRenderer.setGridColor(Color.WHITE);
        //mRenderer.setShowGrid(true);
        mRenderer.setChartTitle("Temperature variation - ");
        mRenderer.setChartTitleTextSize(26);

        // Add single renderer to multiple renderer
        mRenderer.addSeriesRenderer(renderer);
    }

    public GraphicalView getView(Context context)
    {
        view =  ChartFactory.getLineChartView(context, mDataset, mRenderer);
        return view;
    }

    public void addNewPoints(Point p) {
        dataset.add(p.getX(), p.getY());
    }
}

