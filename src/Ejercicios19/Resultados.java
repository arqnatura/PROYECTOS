package Ejercicios19;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.Equipo;

public class Resultados   {
		
		// 31 de enero 2019
		
		public void muestraPuntosOrdenadoEquipos (HashMap<String, ArrayList<Integer>> resultados){
					// recorrer el HashMap previamente ordenado
			HashMap<String, Integer> mapaOrdenadoPuntos = new HashMap <String, Integer>();
					// obtenemos la lista de claves (K)			
			for (String clave : resultados.keySet()) {
				ArrayList<Integer> datos = resultados.get(clave);
				int puntos = datos.get(0)*3 + datos.get(1);
				mapaOrdenadoPuntos.put (clave , puntos);
			}
					// Ahora si ordenamos por puntos..
			
			//Set<EntrySet> entrada = mapaOrdenadoPuntos
					
			//ArrayList<Integer> valoresPuntos = new ArrayList <Integer>(mapaOrdenadoPuntos , get.values());
			//Collections.sort(valoresPuntos);
			
		}		
				
		
		

		// 30 de enero 2019
		// Prueba de SWING. Visualización de Resultados (MVC. Modelo Visual Controlador)
	
	public void pruebaSWING() {
		
		JFrame ventana;
		
		ventana= new JFrame("Mi primer SWING");
		JButton boton= new JButton ("pulsaMe !");
		JPanel panel = new JPanel ();
		ventana.add(panel);
		panel.add(boton);
		
		ArrayList<Equipo> equipos = this.crearListaEquipos ("ficheros/equipos.txt");
		Equipo[] arrayEquipos = equipos.toArray(new Equipo [equipos.size()]);
		JComboBox lista = new JComboBox(arrayEquipos);
		
		panel.add(lista);
		panel.add(boton);		
		
		ventana.pack();
		ventana.setVisible(true);

		
	}
	
	
	public void muestraPuntosEquipos (HashMap<String, ArrayList<Integer>> resultados)
	{
		// recorrer el HashMap... 
		// obtenemos la lista de claves (K)			
		for (String clave : resultados.keySet()) {
			ArrayList<Integer> datos = resultados.get(clave);
			int puntos = datos.get(0)*3 + datos.get(1);
			System.out.println(clave + " => " + puntos);
		}
				
	}
	
	public ArrayList<Equipo> crearListaEquipos(String rutaFichero) {
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaFichero));
			String registro;
			Equipo equipo = null;
			ArrayList<Equipo> equipos = new ArrayList<Equipo>();
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				equipo = new Equipo(Integer.parseInt(campos[0]), campos[1], campos[2]);
				equipos.add(equipo);
			}
			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return equipos;

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;
	}

	public HashMap<String, ArrayList<Integer>> resultadosEquipos(String rutaPartidos)
	// devuelve un mapa de equipos
	// por cada equipo hay una lista de contadores
	// que representan VICTORIAS, EMPATES Y DERROTAS
	{
		try {
			BufferedReader fichero;
			fichero = new BufferedReader(new FileReader(rutaPartidos));
			String registro;
			HashMap<String, ArrayList<Integer>> equipos = new HashMap<String, ArrayList<Integer>>();
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				if (campos[3].equals("")) // ultimo partido jugado..
					break;
				String eL = campos[2];
				String gL = campos[3];
				String eV = campos[4];
				String gV = campos[5];

				// gracias Byron..!!
				// si no existe eL, eV lo añadimos al mapa..

				if (!equipos.containsKey(eL))
					equipos.put(eL, new ArrayList<Integer>(Arrays.asList(0, 0, 0)));

				if (!equipos.containsKey(eV))
					equipos.put(eV, new ArrayList<Integer>(Arrays.asList(0, 0, 0)));

				// cual fue el resultado ..?

				if (gL.compareTo(gV) > 0) {// gana Local
					equipos.get(eL).set(0, equipos.get(eL).get(0) + 1);
					equipos.get(eV).set(2, equipos.get(eV).get(2) + 1);

				} else if (gL.compareTo(gV) < 0) // gana Visitante
				{// gana Local
					equipos.get(eL).set(2, equipos.get(eL).get(2) + 1);
					equipos.get(eV).set(0, equipos.get(eV).get(0) + 1);
				} else { // empate

					equipos.get(eL).set(1, equipos.get(eL).get(1) + 1);
					equipos.get(eV).set(1, equipos.get(eV).get(1) + 1);
				}

			}
			fichero.close();
			System.out.println("Fin de la lectura del fichero");
			return equipos;

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
		return null;

	}

	public void mostrarNumeroPartidosJugadosTry(String rutaPartidos) {
		BufferedReader fichero = null;
		int contador = 0;
		try {

			fichero = new BufferedReader(new FileReader(rutaPartidos));
			String registro;
			while ((registro = fichero.readLine()) != null) {
				String[] campos = registro.split("#");
				try {
					Integer.parseInt(campos[3]);
					contador++;
				} catch (NumberFormatException e) {
					break;
				}
			}
			fichero.close();
			System.out.println(contador);
			System.out.println("Fin de la lectura del fichero");

		} catch (FileNotFoundException excepcion) {
			System.out.println("fichero no encontrado");

		} catch (IOException e) {
			System.out.println("IO Excepcion");
		}
	}

	public static void main(String[] args) {
		Resultados ejercicios = new Resultados();
		ejercicios.pruebaSWING();
		HashMap<String, ArrayList<Integer>> x = ejercicios.resultadosEquipos("ficheros/partidos.txt");
		ejercicios.muestraPuntosEquipos(x);

	}

}
