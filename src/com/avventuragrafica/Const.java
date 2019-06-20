package com.avventuragrafica;

/**
 * @author Giulio Salierno
 * Definisce le costanti utilizzate all'interno del gioco.
 */

public enum Const 
{
	ESCE_DA,	// Costanti per i diversi tipi di evento.
	SI_TROVA_IN,
	POSSIEDE,
	DA,
	PARLA_CON,
	USA,
	DOMANDA,
	
	POSSIEDIOGGETTI,	// Costanti per generare un evento appropriato a seconda della domanda posta dal pgGiocante.
	POSSIEDEOGGETTI,
	
	PERSONAGGI,	//Costanti per distinguere le sezioni del parser.
	LOCAZIONI,
	APERTURE,
	OGGETTI,
	SENTIMENTI,
	INVENTARIO_PERSONAGGI,
	LOCAZIONE_OGGETTI,
	PROPRIETARI,
	
	SimpleObj,	// Costanti per il parsing degli oggetti (superclassi).
	ReadObj,
	TransportObj,
	KeyObj,
	ContainerObj,

	Grog,	// Costanti per il parsing degli oggetti.
	Divano,
	Televisore,
	GettoneTelefonico,
	Denaro,
	Computer,
	Libro,
	Lettera,
	Biglietto,
	DischettoPerComputer,
	Mappa,
	Baule,
	Cofanetto,
	Cassaforte,
	Boccale,
	Tappeto,
	CabinaTelefonica,
	Bottone,
	Chiave,
	Leva,
	Pillola,
	Telefono,	
}
