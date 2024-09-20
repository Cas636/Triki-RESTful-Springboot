package com.johan.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.johan.beans.Student;
import com.johan.beans.Triki;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
public class TrikiController {

	public static List<Triki> trikis = new ArrayList<Triki>();

	static List<int[]> winningCombinations = new ArrayList<>();

	public TrikiController() {

		trikis.add(new Triki(0, ""));
		trikis.add(new Triki(1, ""));
		trikis.add(new Triki(2, ""));
		trikis.add(new Triki(3, ""));
		trikis.add(new Triki(4, ""));
		trikis.add(new Triki(5, ""));
		trikis.add(new Triki(6, ""));
		trikis.add(new Triki(7, ""));
		trikis.add(new Triki(8, ""));

		winningCombinations.add(new int[] { 0, 1, 2 });
		winningCombinations.add(new int[] { 3, 4, 5 });
		winningCombinations.add(new int[] { 6, 7, 8 });
		winningCombinations.add(new int[] { 0, 3, 6 });
		winningCombinations.add(new int[] { 1, 4, 7 });
		winningCombinations.add(new int[] { 2, 5, 8 });
		winningCombinations.add(new int[] { 0, 4, 8 });
		winningCombinations.add(new int[] { 2, 4, 6 });
	}

	@GetMapping("/triki")
	public List<Triki> getTriki() {
		return trikis;
	}

	@PutMapping("/triki/reset")
	public void resetTriki(@RequestBody List<Triki> newTrikiStates) {
	    // Limpiar la lista de trikis existente
	    trikis.clear();

	    // Agregar el nuevo estado que se recibe
	    for (Triki trikie : newTrikiStates) {
	        trikis.add(trikie);
	    }
	}

	@PutMapping("/triki/{cell}")
	public void updateTriki(@PathVariable("cell") int cell, @RequestBody Triki triki) {
		for (Triki trikie : trikis) {
			if (trikie.getCell() == cell) {
				trikie.setStatus(triki.getStatus());
			}
		}
		String currentPlayer = triki.getStatus();
		hasWinningCombination(currentPlayer);
	}
	
	
	public static void hasWinningCombination(String currentPlayer) {
		List<int[]> foundCombinations = new ArrayList<>(); // Lista para almacenar las combinaciones ganadoras
															// encontradas

		for (int[] combination : winningCombinations) {
			if (trikis.get(combination[0]).getStatus().equals(currentPlayer)
					&& trikis.get(combination[1]).getStatus().equals(currentPlayer)
					&& trikis.get(combination[2]).getStatus().equals(currentPlayer)) {
				foundCombinations.add(combination); // Añadir la combinación encontrada
			}
		}
		if (!foundCombinations.isEmpty()) {
			System.out.println("¡Se encontraron combinaciones ganadoras!");
			for (int[] combination : foundCombinations) {
				System.out.println(
						"Combinación ganadora: " + combination[0] + ", " + combination[1] + ", " + combination[2]);
			}
		} else {
			System.out.println("No se encontraron combinaciones ganadoras.");
		}
	}

}
