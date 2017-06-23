package it.polito.tdp.flight.model;

public class AirportsConnected {

		private int idP;
		private int idA;
		private int airlineId;
		
		public AirportsConnected(int idP, int idA, int air) {
			super();
			this.idP = idP;
			this.idA = idA;
			this.airlineId=air;
		}
		public int getAirlineId() {
			return airlineId;
		}
		public void setAirlineId(int airlineId) {
			this.airlineId = airlineId;
		}
		public int getIdP() {
			return idP;
		}
		public void setIdP(int idP) {
			this.idP = idP;
		}
		public int getIdA() {
			return idA;
		}
		public void setIdA(int idA) {
			this.idA = idA;
		}
		
		
	}

