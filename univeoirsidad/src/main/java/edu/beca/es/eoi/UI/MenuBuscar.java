package edu.beca.es.eoi.UI;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import edu.beca.es.eoi.controller.PersonController;
import edu.beca.es.eoi.entity.Pas;
import edu.beca.es.eoi.entity.Person;
import edu.beca.es.eoi.entity.Professor;
import edu.beca.es.eoi.entity.Student;

public class MenuBuscar {
	private static final PersonController PERSONCONTROLLER = new PersonController();
	private static final MenuPrincipal MENU = new MenuPrincipal();
	private static final Logger logger = Logger.getLogger(MenuBuscar.class);

	private static final int STUDENT = 1;
	private static final int PROFESSOR = 2;
	private static final int PAS = 3;

	private static final Scanner ESCANERSTRINGS = new Scanner(System.in);
	private static final Scanner ESCANERENTEROS = new Scanner(System.in);

	private static final int USERNAME = 1;
	private static final int DNI = 2;

	public void printMenuBuscar() {
		logger.info("Entro en el buscador");
		System.out.println("   Bienvenido al buscador   ");
		System.out.println("* * * * * * * * * * * * * * ");
		System.out.println("   1.- Buscar por usuario   " + "\n   2.- Buscar por dni       ");
		System.out.println("* * * * * * * * * * * * * * ");
		int userOption = 0;
		try {
			userOption = ESCANERENTEROS.nextInt();
		} catch (InputMismatchException e) {
			e.printStackTrace();
			System.out.println("El valor introducido no es correcto, por favor, vuelva a intentarlo");
			printMenuBuscar();
		}
		switch (userOption) {
		case USERNAME:
			readByUsername();
			break;

		case DNI:
			readByDni();
			break;
		}
		ESCANERENTEROS.close();
		ESCANERSTRINGS.close();
	}

	public void readByUsername() {
		System.out.print("Introduzca el usuario: ");
		String username = ESCANERSTRINGS.nextLine();
		Student s = null;
		Professor p = null;
		Pas pa = null;
		Person e = null;

		try {
			logger.debug("Se busca al usuario: " + username);
			e = PERSONCONTROLLER.read(username, null);
		} catch (Exception e1) {
			System.out.println("No se encuentra el usuario que desea buscar");
			e1.printStackTrace();
			MENU.printMainMenu();
		}
		System.out.println(e.getName() + " " + e.getSurname() + " " + e.getDni() + " " + e.getMail() + ". ");
		if (e.getTipoPersona() == STUDENT) {
			try {
				s = (Student) PERSONCONTROLLER.readByUsername(e.getUsername());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("");
			System.out.println("FACULTAD: " + s.getFaculty());
		} else if (e.getTipoPersona() == PROFESSOR) {
			try {
				p = (Professor) PERSONCONTROLLER.readByUsername(username);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("OFICINA: " + p.getOffice());
		} else if (e.getTipoPersona() == PAS) {
			try {
				pa = (Pas) PERSONCONTROLLER.readByUsername(username);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.out.println("UNIDAD: " + pa.getUnit());
		}
	}

	public void readByDni() {
		System.out.print("Introduzca el dni: ");
		String dni = ESCANERSTRINGS.nextLine();
		Person e = null;

		try {
			logger.debug("Se busca al usuario con dni: " + dni);
			e = PERSONCONTROLLER.readByDni(dni);
		} catch (Exception e1) {
			System.out.println("No se encuentra el usuario que desea buscar");
			e1.printStackTrace();
			MENU.printMainMenu();
		}
		System.out.println(e.getName() + " " + e.getSurname() + " " + e.getUsername() + " " + e.getMail() + ". ");
		if (e.getTipoPersona() == STUDENT) {
			System.out.println("FACULTAD: " + ((Student) e).getFaculty());
		} else if (e.getTipoPersona() == PROFESSOR) {
			System.out.println("OFICINA: " + ((Professor) e).getOffice());
		} else if (e.getTipoPersona() == PAS) {
			System.out.println("UNIDAD: " + ((Pas) e).getUnit());
		}
	}

}
