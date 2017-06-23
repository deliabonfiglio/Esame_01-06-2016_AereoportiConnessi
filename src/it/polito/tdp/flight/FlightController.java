package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportAndDistance;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRaggiungibili(ActionEvent event) {
    	Airline a = boxAirline.getValue();
    	Airport air= boxAirport.getValue();
    	
    	if(a==null || air ==null){
    		txtResult.appendText("Scegliere una compagnia e un aereoporto.\n");
    		return;
    	}
    	txtResult.clear();
    	
    	List<AirportAndDistance> tutti2 = model.getPathBetween(a, air);
    	txtResult.appendText("2\n");
    	for(AirportAndDistance ad: tutti2){
    		txtResult.appendText(ad.toString()+"\n");
    	}
    }

    @FXML
    void doServiti(ActionEvent event) {

    	boxAirport.getItems().clear();
    	Airline a = boxAirline.getValue();
    	
    	if(a==null){
    		txtResult.appendText("Scegliere una compagnia.\n");
    		return;
    	}
    	
    	Set<Airport> raggiungibili = model.getAirportsReachable(a);
    	
    	if(raggiungibili.size()!=0){
    			model.creaGrafo(a);
    			
    			boxAirport.getItems().addAll(raggiungibili);
	    		txtResult.appendText("Aereoporti raggiungibili: \n");
	    	for(Airport air: raggiungibili)
	    		txtResult.appendText(air.toString()+"\n");
    	} else {
    		txtResult.appendText("La compagnia non compre nessun aereoporto\nImpossibilie creare il grafo!");
    	}
    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		this.boxAirline.getItems().addAll(model.getAllArilines());
	}
}
