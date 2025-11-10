![Logo](https://www.uniupo.it/themes/custom/uniupo_2020/uniupo-logo.svg)

## Università del Piemonte Orientale - Corso di Laurea in Informatica 
(A.A. 2024/2025)

## MF0360 - FONDAMENTI, LINGUAGGI E TRADUTTORI

---
## Progetto: AC-DC Compiler

## Descrizione del Progetto

AC-DC Compiler è un compilatore per un linguaggio didattico. Il progetto è suddiviso nelle classiche fasi della compilazione: analisi lessicale, analisi sintattica, generazione dell'albero sintattico astratto (AST), gestione della tabella dei simboli e analisi semantica tramite pattern visitor.

---

## Struttura del Progetto

Il repository è organizzato nelle seguenti directory, ognuna rappresentante una fase o un componente chiave del compilatore:

-   **/scanner**: Contiene il codice per l'analisi lessicale, responsabile della trasformazione del codice sorgente in una sequenza di token.
-   **/token**: Definisce la struttura dei token utilizzati dal compilatore.
-   **/parser**: Include il parser, che si occupa dell'analisi sintattica e della costruzione dell'albero sintattico.
-   **/ast**: Contiene le definizioni per i nodi dell'albero sintattico astratto (AST), che rappresenta la struttura gerarchica del codice.
-   **/visitor**: Implementa il pattern Visitor per attraversare l'AST e realizzare, ad esempio, l'analisi semantica.
-   **/symbolTable**: Contiene le strutture dati e la logica per la gestione della tabella dei simboli, usata per memorizzare informazioni su variabili, funzioni, ecc.
-   **/test**: Contiene i test per le varie componenti del compilatore.

---

## Licenza

Copyright (c) 2025, Università del Piemonte Orientale.

Questo progetto è distribuito sotto la licenza **GNU GPLv2.0**. Per maggiori dettagli, consulta il file `LICENSE`.
