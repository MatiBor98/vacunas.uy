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
	Paysandu,
	RioNegro {@Override
	public String toString() {
		return "Rio Negro";
	}},
	Rivera,
	Rocha,
	Salto,
	SanJose{@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "San Jose";
	}},
	Soriano,
	Tacuarembo,
	TreintayTres{@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Treinta y Tres";
	}},
}
