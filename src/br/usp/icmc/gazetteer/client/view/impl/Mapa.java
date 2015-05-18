/**
 *
 *   Copyright 2014 sourceforge.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package br.usp.icmc.gazetteer.client.view.impl;

import java.util.ArrayList;
import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.OpenLayers;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.StyleMap;
import org.gwtopenmaps.openlayers.client.control.DrawFeature;
import org.gwtopenmaps.openlayers.client.control.DrawFeatureOptions;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.control.MousePosition;
import org.gwtopenmaps.openlayers.client.control.MousePositionOptions;
import org.gwtopenmaps.openlayers.client.control.MousePositionOutput;
import org.gwtopenmaps.openlayers.client.control.OverviewMap;
import org.gwtopenmaps.openlayers.client.control.ScaleLine;
import org.gwtopenmaps.openlayers.client.control.SelectFeature;
import org.gwtopenmaps.openlayers.client.event.FeatureHighlightedListener;
import org.gwtopenmaps.openlayers.client.event.VectorFeatureSelectedListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.filter.ComparisonFilter;
import org.gwtopenmaps.openlayers.client.filter.ComparisonFilter.Types;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.handler.PathHandler;
import org.gwtopenmaps.openlayers.client.handler.PathHandlerOptions;
import org.gwtopenmaps.openlayers.client.handler.PointHandler;
import org.gwtopenmaps.openlayers.client.handler.PolygonHandler;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3MapType;
import org.gwtopenmaps.openlayers.client.layer.GoogleV3Options;
import org.gwtopenmaps.openlayers.client.layer.OSM;
import org.gwtopenmaps.openlayers.client.layer.TransitionEffect;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.gwtopenmaps.openlayers.client.layer.WMS;
import org.gwtopenmaps.openlayers.client.layer.WMSOptions;
import org.gwtopenmaps.openlayers.client.layer.WMSParams;
import org.gwtopenmaps.openlayers.client.popup.FramedCloud;
import org.gwtopenmaps.openlayers.client.popup.Popup;
import org.gwtopenmaps.openlayers.client.strategy.AnimatedClusterStrategy;
import org.gwtopenmaps.openlayers.client.strategy.AnimatedClusterStrategyOptions;
import org.gwtopenmaps.openlayers.client.strategy.Strategy;
import org.gwtopenmaps.openlayers.client.style.Rule;
import org.gwtopenmaps.openlayers.client.style.SymbolizerPoint;

import br.usp.icmc.gazetteer.client.view.MapaView;
import br.usp.icmc.gazetteer.shared.Locality;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Mapa extends Composite implements MapaView {

	private Presenter presenter;

	private static MapaUiBinder uiBinder = GWT.create(MapaUiBinder.class);
	@UiField
	VerticalPanel panel_map;
	/**
	 * Open layers buttons
	 * 
	 */
	private Button rbNavigate = new Button("navigate");
	private final Button rbDrawPoint = new Button("draw point");
	private final Button rbDrawLine = new Button( "draw line");
	private final Button rbDrawPolygon = new Button("draw polygon");
	private final Button cbClickOut = new Button("Delete geometry");
	private DrawFeature drawPointFeatureControl = null;
	private DrawFeature drawLineFeatureControl = null;
	private DrawFeature drawPolygonFeatureControl = null;
	private SelectFeature deleteFeatureControl = null; //deleting is realized using a SelectFeature control
	private static String geometry;
	private static  Map map;
	private Vector vectorFeature;

	private List<Popup> popup = new ArrayList<Popup>();

	private final Vector vectorGeo = new Vector("Geometry");
	private HorizontalPanel components = new  HorizontalPanel();

	private  List<Point> points = new ArrayList<Point>();

	interface MapaUiBinder extends UiBinder<Widget, Mapa> {
	}

	public Mapa() {
		initWidget(uiBinder.createAndBindUi(this));

	}
	@Override
	public void clearAllPoint(){
		vectorGeo.destroyFeatures();
		
	}
	@Override
	public boolean countFeature(){
		if(vectorGeo.getFeatures().length>1)
			return true;
		return false;
	}
	@Override
	public String getGeometry(){
		String value="";
		vectorGeo.getFeatures()[0].getGeometry().transform(new Projection(map.getProjection()), DEFAULT_PROJECTION);
		 value = vectorGeo.getFeatures()[0].getGeometry().toString();
		return value;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void PointsFromServer(List<Float[]> points) {
		for(Float s[]: points){
			this.points.add(new Point(s[0],s[1]));
		}
	}

	public  HorizontalPanel getComponents(){
		return components;
	}
	private static final Projection DEFAULT_PROJECTION = new Projection("EPSG:4326");


	@SuppressWarnings("deprecation")
	public void buildPanel() {
		System.out.println("INICIO");
		MapOptions defaultMapOptions = new MapOptions();
		defaultMapOptions.setNumZoomLevels(16);

		//Create a MapWidget and add 2 OSM layers
		MapWidget mapWidget = new MapWidget(this.getParent().getOffsetWidth()+"px", this.getParent().getOffsetHeight()+"px", defaultMapOptions);

		GoogleV3Options gSatelliteOptions = new GoogleV3Options();
		gSatelliteOptions.setIsBaseLayer(true);
		gSatelliteOptions.setType(GoogleV3MapType.G_HYBRID_MAP);
		final GoogleV3 gSatellite = new GoogleV3("Google Satellite",gSatelliteOptions);

		//MAP SERVERS 
		final OSM osm_1 = OSM.Mapnik("Mapnik");
		final OSM osm_2 = OSM.CycleMap("CycleMap");
		osm_1.setIsBaseLayer(true);
		osm_2.setIsBaseLayer(true);
		gSatellite.setIsBaseLayer(true);

		map = mapWidget.getMap();

		map.addLayer(gSatellite);
		map.addLayer(osm_2);
		map.addLayer(osm_1);
		//ADD POINTS LAYER
		vectorFeature = new Vector("popups");
		map.addLayer(vectorFeature);

		//Lets add some default controls to the map
		map.addControl(new LayerSwitcher()); //+ sign in the upperright corner to display the layer switcher
		map.addControl(new OverviewMap()); //+ sign in the lowerright to display the overviewmap
		map.addControl(new ScaleLine()); //Display the scaleline

		/**
		 * Mouse position in map
		 */
		MousePositionOutput mpOut = new MousePositionOutput() {
			@Override
			public String format(LonLat lonLat, Map map) {
				lonLat.transform(map.getProjection(),DEFAULT_PROJECTION.getProjectionCode()); //transform lonlat to OSM coordinate system

				String out = "";
				if(map.getBaseLayer().getId().equals(gSatellite.getId()) || map.getBaseLayer().getId().equals(osm_2.getId())){
					out += "<p id=\"mouseCoord\"> ";
					out += lonLat.lat();
					out += "   ";
					out += lonLat.lon()+"</p>";
				}else {
					out += "<p id=\"normCoord\"> ";
					out += lonLat.lat();
					out += "   ";
					out += lonLat.lon()+"</p>";
				}
				return out;
			}
		};

		MousePositionOptions mpOptions = new MousePositionOptions();

		mpOptions.setFormatOutput(mpOut); // rename to setFormatOutput
		map.addControl(new MousePosition(mpOptions));

		//Center and zoom to a location
		LonLat lonLat = new LonLat(-58, -4);//6.95, 50.94);
		lonLat.transform(DEFAULT_PROJECTION.getProjectionCode(),
				map.getProjection()); //transform lonlat to OSM coordinate system
		map.setCenter(lonLat, 5);

		mapWidget.getElement().getFirstChildElement().getStyle().setZIndex(0); //force the map to fall behind popups
		if(points.size()>0)
			BuildCluster();
		addButtons();
		panel_map.add(mapWidget);
		System.out.println("FIM");
		panel_map.addStyleName("#mapa");
		DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), "");
	}

	public void addButtons(){

		addPoint(map);
		addLineString(map);
		addPolygon(map);
		deleteFeatures(map);
		components.addStyleName("button_map");
		components.add(rbNavigate);
		components.add(rbDrawPoint);
		components.add(rbDrawLine);
		components.add(rbDrawPolygon);
		components.add(cbClickOut);
		final HorizontalPanel hpCbClickOut = new HorizontalPanel();
		hpCbClickOut.add(new HTML("         "));
		components.add(hpCbClickOut);

		rbNavigate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				desativeAll();
			}
		});
		rbDrawPoint.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				desativeAll();
				clearAllPoint();
				drawPointFeatureControl.activate();
			}
		});
		rbDrawLine.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				desativeAll();
				clearAllPoint();
				drawLineFeatureControl.activate();

			}
		});
		rbDrawPolygon.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				desativeAll();
				clearAllPoint();
				drawPolygonFeatureControl.activate();				
			}
		});
		cbClickOut.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				desativeAll();
				clearAllPoint();
				deleteFeatureControl.activate();
			}
		});

	}

	public  final StyleMap ClusterRules(){
		Rule[] rules = new Rule[3];

		rules[0] = new Rule();
		ComparisonFilter filter0 = new ComparisonFilter();
		filter0.setType(Types.LESS_THAN);
		filter0.setProperty("count");
		filter0.setNumberValue(5);

		SymbolizerPoint symbolizer0 = new SymbolizerPoint();
		symbolizer0.setFillColor("green");
		symbolizer0.setFillOpacity(0.9);
		symbolizer0.setStrokeColor("green");
		symbolizer0.setStrokeOpacity(0.5);
		symbolizer0.setStrokeWidth(12);
		symbolizer0.setPointRadius(10);
		rules[0].setFilter(filter0);
		rules[0].setSymbolizer(symbolizer0);

		rules[1] = new Rule();
		ComparisonFilter filter1 = new ComparisonFilter();
		filter1.setType(Types.BETWEEN);
		filter1.setProperty("count");
		filter1.setNumberLowerBoundary(5);
		filter1.setNumberUpperBoundary(20);
		SymbolizerPoint symbolizer1 = new SymbolizerPoint();
		symbolizer1.setFillColor("orange");
		symbolizer1.setFillOpacity(0.9);
		symbolizer1.setStrokeColor("orange");
		symbolizer1.setStrokeOpacity(0.5);
		symbolizer1.setStrokeWidth(12);
		symbolizer1.setPointRadius(10);
		rules[1].setFilter(filter1);
		rules[1].setSymbolizer(symbolizer1);

		rules[2] = new Rule();
		ComparisonFilter filter2 = new ComparisonFilter();
		filter2.setType(Types.GREATER_THAN);
		filter2.setProperty("count");
		filter2.setNumberValue(20);
		SymbolizerPoint symbolizer2 = new SymbolizerPoint();
		symbolizer2.setFillColor("red");
		symbolizer2.setFillOpacity(0.9);
		symbolizer2.setStrokeColor("red");
		symbolizer2.setStrokeOpacity(0.5);
		symbolizer2.setStrokeWidth(12);
		symbolizer2.setPointRadius(10);
		rules[2].setFilter(filter2);
		rules[2].setSymbolizer(symbolizer2);

		Style style = new Style();
		style.setLabel("${count}");
		style.setFontColor("#FFFFFF");
		style.setFontSize("20px");

		StyleMap  styleMap = new StyleMap(style);
		styleMap.addRules(rules, "default");

		return styleMap;
	}


	public void BuildCluster(){
		/**
		 * CLUSTER ANIMATED
		 */
		AnimatedClusterStrategy animatedClusterStrategy = new AnimatedClusterStrategy(new AnimatedClusterStrategyOptions());
		VectorOptions vectorOptions = new VectorOptions();
		vectorOptions.setStrategies(new Strategy[]{animatedClusterStrategy});
		vectorOptions.setRenderers(new String[]{"Canvas", "SVG"});
		final Vector vectorLayer = new Vector("Clusters", vectorOptions);

		animatedClusterStrategy.setDistance(20);
		animatedClusterStrategy.setThreshold(10);

		//plot the points
		VectorFeature[] features = new VectorFeature[points.size()];
		for (int i = 0 ; i< points.size() ; i++) {
			features[i] = new VectorFeature(points.get(i));
		}

		animatedClusterStrategy.setFeatures(features);
		for (Point point : points){
			point.transform(DEFAULT_PROJECTION,new Projection(map.getProjection()));
			vectorLayer.addFeature(new VectorFeature(point));
		}
		vectorLayer.setStyleMap(ClusterRules());
		map.addLayer(vectorLayer);

	}


	private void deleteFeatures(Map map){
		deleteFeatureControl = new SelectFeature(vectorGeo);
		map.addControl(deleteFeatureControl);
		deleteFeatureControl.addFeatureHighlightedListener(new FeatureHighlightedListener() {
			public void onFeatureHighlighted(VectorFeature vectorFeature) {
				vectorFeature.destroy(); //don't do this if you want to use WFS-T
			}
		});

	}

	private void addPolygon(Map map){

		PolygonHandler pathHanlder = new PolygonHandler();
		// Create the DrawFeature control to draw on the map, and pass the DrawFeatureOptions to control the style of the sketch
		drawPolygonFeatureControl = new DrawFeature(vectorGeo, pathHanlder);
		map.addLayer(vectorGeo);
		map.addControl(drawPolygonFeatureControl);
	}

	private void addPoint(Map map){

		PointHandler pathHanlder = new PointHandler();
		// Create the DrawFeature control to draw on the map, and pass the DrawFeatureOptions to control the style of the sketch
		drawPointFeatureControl = new DrawFeature(vectorGeo, pathHanlder);
		map.addLayer(vectorGeo);
		map.addControl(drawPointFeatureControl);
	}


	private void addLineString( Map map){
		final Style drawStyle = new Style(); //create a Style to use
		drawStyle.setFillColor("white");
		drawStyle.setGraphicName("x");
		drawStyle.setPointRadius(4);
		drawStyle.setStrokeWidth(3);
		drawStyle.setStrokeColor("#66FFFF");
		drawStyle.setStrokeDashstyle("dash");

		//create a StyleMap using the Style
		StyleMap drawStyleMap = new StyleMap(drawStyle);

		//Create PathHanlderOptions using this StyleMap
		PathHandlerOptions phOpt = new PathHandlerOptions();
		phOpt.setStyleMap(drawStyleMap);   

		//Create DrawFeatureOptions and set the PathHandlerOptions (that have the StyleMap, that have the Style we wish)
		DrawFeatureOptions drawFeatureOptions = new DrawFeatureOptions();
		drawFeatureOptions.setHandlerOptions(phOpt);

		PathHandler pathHanlder = new PathHandler();

		// Create the DrawFeature control to draw on the map, and pass the DrawFeatureOptions to control the style of the sketch
		drawLineFeatureControl = new DrawFeature(vectorGeo, pathHanlder, drawFeatureOptions);
		map.addLayer(vectorGeo);
		map.addControl(drawLineFeatureControl);

	}

	private void desativeAll(){
		if(drawPointFeatureControl.isActive()){
			drawPointFeatureControl.deactivate();
		}if(drawLineFeatureControl.isActive()){
			drawLineFeatureControl.deactivate();
		}if(drawPolygonFeatureControl.isActive()){
			drawPolygonFeatureControl.deactivate();
		}if(deleteFeatureControl.isActive())
			deleteFeatureControl.deactivate();
	}


	@Override
	public void setName(String helloName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.presenter = listener;

	}

	@Override
	public void buildMap() {
		buildPanel();

	}



	@Override
	public void clear() {
		this.panel_map.clear();
		this.components.clear();

	}

	@Override
	public HorizontalPanel getComponets() {
		return this.components;
	}

	@Override
	public void callServerPoints() {
		if(presenter!=null){
			System.out.println("Entrou no presenter");
			presenter.cluster();
		}

	}


	public float transformFloat(String numero) {
		float valor = 0;
		char n[] = numero.toCharArray();
		numero = "";
		for (int i = 0; i < n.length; i++) {
			if (n[i] == ',') {
				numero += ".";
			}
			numero += n[i];
		}
		try {
			valor = Float.parseFloat(numero);
		} catch (Exception e) {
			return 0;
		}
		return valor;
	}


	@Override
	public void showGeometry(final Locality locality) {

		for(Popup p:this.popup){
			map.removePopup(p);
		}

		vectorFeature.removeAllFeatures();
		vectorFeature.redraw();
		map.getLayerByName("Clusters").setIsVisible(false);
		map.getLayerByName("Clusters").redraw();


		String value = locality.getGeometry();
		double x = transformFloat(value.split(" ")[0]);
		double y = transformFloat(value.split(" ")[1]);
		LonLat lonLat = new LonLat(y, x);
		lonLat.transform(DEFAULT_PROJECTION.getProjectionCode(), map.getProjection());
		Point point = new Point(lonLat.lon(),lonLat.lat());

		/////////////////////////////////////////
		final VectorFeature vf1 =  new VectorFeature(point);
		vectorFeature.addFeature(vf1);
		SelectFeature selectFeature = new SelectFeature(vectorFeature);
		selectFeature.setAutoActivate(true);
		map.addControl(selectFeature);
		vectorFeature.redraw();
		map.updateSize();
		Popup popup = new FramedCloud("id1", vf1.getCenterLonLat(), null, locality.getLocality(), null, true);
		popup.setPanMapIfOutOfView(true); 
		popup.setAutoSize(true);
		this.popup.add(popup);
		for(Popup p:this.popup)
			vf1.setPopup(p);

		map.addPopup(vf1.getPopup());

	}


}

//// Secondly add a VectorFeatureSelectedListener to the feature
//

