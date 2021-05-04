package datos.entidades;


public enum Departamento{
	Artigas,
	Canelones,
	CerroLargo { 
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Cerro Largo";
		}
	},
	Colonia,
	Durazno,
	Flores,
	Florida,
	Lavalleja,
	Maldonado,
	Montevideo,
	Paysandu {@Override
	public String toString() {
		return "Paysandú";
	}},
	RioNegro {@Override
	public String toString() {
		return "Río Negro";
	}},
	Rivera,
	Rocha,
	Salto,
	SanJose{@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "San José";
	}},
	Soriano,
	Tacuarembo,
	TreintayTres{@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Treinta y Tres";
	}},
}
