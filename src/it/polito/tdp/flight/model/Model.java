package it.polito.tdp.flight.model;

import java.util.*;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import it.polito.tdp.flight.db.FlightDAO;

public class Model {

	private List<Airline> airlines;
	private Set<Airport> airports;
	private DirectedGraph<Airport, DefaultWeightedEdge> graph;
	private Map<Integer, Airport> map;
	
	public List<Airline> getAllArilines(){
		if(airlines==null){
			FlightDAO dao = new FlightDAO();
			airlines= dao.getAllAirlines();
		}
		return airlines;
	}
	
	public Set<Airport> getAllAirports(){
		if(airports ==null){
			map = new HashMap<>();
			FlightDAO dao = new FlightDAO();
			airports=dao.getAllAirports();
			
			for(Airport a: airports)
				map.put(a.getAirportId(), a);
		}
		return airports;
	}
	
	public void creaGrafo(Airline a){
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph, this.getAllAirports());
		
		FlightDAO dao = new FlightDAO();
		List<AirportsConnected> serviti = dao.getAirportsConnected(a);
		
		for(AirportsConnected ac : serviti){
			Airport partenza = map.get(ac.getIdP());
			Airport arrivo = map.get(ac.getIdA());
			
			if(!partenza.equals(arrivo)){
				double distanza = LatLngTool.distance( partenza.getCoords(), arrivo.getCoords(), LengthUnit. KILOMETER);
		
				Graphs.addEdgeWithVertices(graph, partenza, arrivo, distanza);
					
			}
		}
		System.out.println(graph.toString());
	}

	public Set<Airport> getAirportsReachable(Airline a) {
		Set<Airport> raggiunti= new HashSet<>();
	
		FlightDAO dao = new FlightDAO();
		List<AirportsConnected> serviti = dao.getAirportsConnected(a);
		//popolo la mappa di aereoporti
		this.getAllAirports();
		
		System.out.println(serviti.size());
		
		for(AirportsConnected ac : serviti){
			Airport partenza = map.get(ac.getIdP());
			Airport arrivo = map.get(ac.getIdA());
			
			raggiunti.add(partenza);
			raggiunti.add(arrivo);
			}
		return raggiunti;
	}
	
	public List<AirportAndDistance> getPathBetween(Airline a, Airport start) {

		List<AirportAndDistance> list = new ArrayList<>();
		
		for(Airport end: this.getAirportsReachable(a)){
			DijkstraShortestPath<Airport, DefaultWeightedEdge> dj = new DijkstraShortestPath<Airport, DefaultWeightedEdge>(graph, start, end);
		GraphPath<Airport, DefaultWeightedEdge> path = dj.getPath();
		
			if(path !=null){
				list.add(new AirportAndDistance(end, (int) path.getWeight()));
			}

		}
		Collections.sort(list);
		return list;
		
	}
	
	
}
