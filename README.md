# ProgettoNoriArduini
Repository per la consegna del progetto d'esame di programmazione ad oggetti valido per l'appello di gennaio 2021
### Specifiche Progetto d'esame (aggiornato al 04/12/2021)
A partire dalla seguente API e dalla relativa documentazione: https://developers.facebook.com/docs/graph-api/reference/v7.0/post
1. Sviluppare in JAVA una applicazione che modelli il problema ed il dataset attraverso opportune classi che rispecchiano i casi d’uso e le strutture dati del progetto e implementi le seguenti funzionalità utilizzando al meglio i principi della OOP (incapsulamento, ereditarietà, polimorfismo, classi astratte, interfacce)
    * all’avvio: autenticarsi nell’API, gestendo, ove necessario, i parametri del progetto in un file JSON locale opportunamente strutturato;
    * a connessione completata e dopo aver ricevuto i dati: effettui il parsing dei dati creando delle strutture dati opportune sulla base delle classi di cui sopra (ogni record del data-set corrisponde ad un oggetto di una classe) con i seguenti obiettivi: >:  
    <u>Sviluppare un'applicazione Java per effettuare statistiche sui post di un utente; classificandone i contenuti politici verificando la presenza di parole chiave.</u>
2. Su richiesta, mediante API REST GET o POST (per evitare query string complesse) con rotte distinte:
    * restituire i metadati (formato JSON) ovvero elenco degli attributi e dei relativi tipi di dato
    * restituire i dati (formato JSON);
    * restituire statistiche sui dati (formato JSON) con possibilità di effettuare filtri e in particolare: <u>Statistiche sulla frequenza di post nel tempo; filtri per fasce orarie, giorni e parole chiave.</u>
    * Gestire problemi durante il processo di importazione (es. numero di attributi errato in una data riga) e nella fase di richiesta (es. si richiedono statistiche per un attributo inesistente oppure i filtri non sono corretti)
3. Documentare il codice sviluppato (attraverso commenti esaustivi e l’utilizzo di JavaDoc, da pubblicare su github)
4. Creare almeno 2 Unit Test per la verifica della correttezza di uno dei metodi sviluppati e delle relative eccezioni gestite 
5. Usare github per versionare il codice da consegnare e consegnare la documentazione JavaDoc del progetto.
6. Usare il ReadME.md per creare una pagina descrittiva del progetto, inserendo quanto necessario alla comprensione del funzionamento del vostro programma ed alla fase di verifica delle chiamate API realizzate; dovranno essere indicati anche i contributi allo sviluppo del progetto dei singoli membri del gruppo come esempio di suddivisione dei compiti di sviluppo dei vari membri del gruppo.

Tutte le specifiche del progetto saranno validate in fase di correzione con particolare riferimento a: qualità del progetto e della modellazione, qualità del codice, completezza della soluzione, completezza della documentazione, design dei test, adeguatezza del ReadME.md, adeguato contributo dei singoli membri del gruppo anche in base alla gestione di GIT e della suddivisione dei compiti all’interno del gruppo. 


### Progettazione
##### Letteratura
Si vuole realizzare un software che permetta di classificare i post Facebook di un utente verificando la presenza di parole chiave nel titolo del post e nei commenti e di effettuare statistiche sulla frequenza di pubblicazione dei post applicando filtri per fasce orarie, giorni, e parole chiave.
##### Realizzazione
Si ipotizza di realizzare un applicativo java che implementi le funzionalità del framework Spring Boot (https://spring.io/) per lo sviluppo di un servizio di API, ovvero un insieme di definizioni e protocolli con i quali vengono realizzati e integrati software applicativi. 
Si è optato di definire delle API di tipo **REST** la quali devono detenere le seguenti caratteristiche:
* Un'architettura client-server composta da client, server e risorse, con richieste gestite tramite Hypertext Transfer Protocol (alternativa HTTP over Secure Socket Layer).
* Una comunicazione tra client e server di tipo **StateLess** (ovvero che non tiene traccia delle richieste effettuate dal client)
* Una interfaccia comune per i componenti, che permetta che le informazioni siano trasferite in forma standard in modo che il client possa leggerle e rielaborarle.

Verranno integrate al nostro servizio le API di Facebook fornite da Meta per poter gestire le ricerche all'interno del social network.

Il programma dovrà leggere automaticamente le configurazioni base del server spring, come il socket (ip + porta) su cui è stato aperto.

##### Note di sviluppo
* Si è constatato che le API Graph di facebook hanno un limite di chiamate massimo in un dato periodo di tempo. Passato tal periodo tornano a funzionare correttamente.



