# ProgettoNoriArduini

Applicazione che permette di fare statistiche sui post di un utente Facebook e di filtrarli per giorni, fasce orarie e parole chiave.

## Indice
-- qui va l'indice --

## Descrizione dell'applicazione
La nostra applicazione permette di fare delle statistiche sulla frequenza temporale con cui l'utente carica i post sul suo feed di Facebook; inoltre consente anche di ottenere la lista dei suoi post pubblicati e filtrarla in base a:
- data di pubblicazione;
- fascia oraria di pubblicazione;
- parole chiave contenute.

Le parole chiave possono essere sia inserite manualmente dall'utente (*vedere sezione Filtri*), e sia caricate da un dataset di parole, che si trova nel file `config/dataset.json`. In questo modo, se il dataset contiene un certo insieme di parole (in questo caso parole a sfondo politico) il nostro applicativo è in grado di filtrare i post e ottenere solamente quelli a sfondo politico (quindi quelli che contengono le parole contenute nel dataset).

### Cosa permette di fare la nostra app
✅ Statistiche sui post di un utente in base al periodo di pubblicazione<br>
✅ Filtraggio dei post di un utente per giorni, fasce orarie e parole chiave<br>
✅ I filtri si possono combinare per poter ottenere risultati più precisi<br>
✅ Filtraggio dei post di un utente basandosi su un dataset di parole liberamente modificabile<br>
✅ Verrete avvisati immediatamente se l'access token inserito è scaduto o non è valido<br>
✅ Verifica delle funzionalità del programma con JUNIT 5

## Installazione dell'app sul PC
#### Download del progetto
Scaricare il contenuto di questa repository sul proprio PC. Si può fare direttamente dalla pagina GitHub di questa repo cliccando sul tasto verde **Code** e selezionando **Download as ZIP**. In alternativa, per chi possiede il client a riga di comando di Git (reperibile a [questo indirizzo](https://git-scm.com/downloads)), è possibile effettuare il clone del progetto utilizzando:
```
   git clone https://github.com/reirocco/ProgettoNoriArduini.git
```
![Download da GitHub](https://i.ibb.co/SfFBnFW/Immagine-2022-01-17-114121.png "Download da GitHub")
![Clone con Git CLI](https://i.ibb.co/QKHhsPy/Immagine-2022-01-17-114459.png "Clone con GIT CLI")

#### Avvio dell'applicazione
Una volta scaricata l'applicazione, è possibile avviarla direttamente con la riga di comando digitando quanto segue:
```
    mvnw.cmd spring-boot:run
```
(per computer Windows)
```
    ./mvnw spring-boot:run
```
(per macOS o Linux)

Per terminare il programma, in tutti e tre i sistemi operativi, utilizzare CTRL+C.<br>

![Avvio del programma su Windows](https://i.ibb.co/4tBbDh4/Immagine-2022-01-17-115322.png "Avvio del programma su Windows")

#### Apertura con IntelliJ IDEA
Con IntelliJ IDEA è possibile creare un progetto a partire da un progetto esistente su GitHub. Per fare questo fare clic su `File -> New -> Project from Version Control`, inserire l'URL GIT del progetto e cliccare poi su `Clone`.

![Clone del progetto con IntelliJ IDEA](https://i.ibb.co/y6754sh/dsfsdfsd.png "Clone del progetto con IntelliJ IDEA")
![Clone del progetto con IntelliJ IDEA (2)](https://i.ibb.co/zPNqV4R/Immagine-2022-01-17-120035.png "Clone del progetto con IntelliJ IDEA (2)")

## Funzionamento del programma
### Ottenere l'access token
Per poter utilizzare il nostro programma è necessario inserire l'access token dell'utente Facebook dentro il file `config/config.json` e nella chiave `access_token`.
Per generare un access token:
- andare [in questa pagina](https://www.facebook.com/login/?privacy_mutation_token=eyJ0eXBlIjowLCJjcmVhdGlvbl90aW1lIjoxNjQyNDE3ODA0LCJjYWxsc2l0ZV9pZCI6Mjc2MjMwNjIxNzQyMjQ4NX0%3D&next=https%3A%2F%2Fdevelopers.facebook.com%2F) e fare l'accesso col proprio account di Facebook;
- andare su `Le mie App -> Creazione di un app`;
- definire il nome dell'app e un suo ID;
- una volta creata l'app bisogna andare su `Strumenti -> Strumento di esplorazione della API Graph`;
- generare l'access token attraverso il tasto `Generate Access Token` posto nella colonna destra.

![Apertura strumento di esplorazione della API Graph](https://i.ibb.co/k0QZDs4/werwerwe.png "Apertura strumento di esplorazione della API Graph")

### Rotte della nostra API
Per impostazione predefinita il server viene avviato in `localhost:80`, quindi nella porta 80. Per modificare questa impostazione, modificare `server.port` in `src/main/resources/application.properties`.

| #   | Rotta                 | Metodo | Descrizione                                                                 |
|-----|-----------------------|--------|-----------------------------------------------------------------------------|
| 1   | `localhost/`          | GET    | Verifica se il server è raggiungibile. Se lo è risponde con `Hello World!`. |
| 2   | `localhost/tokenTest` | GET    | Verifica se l'access token fornito è valido o no.                           |
| 3   | `localhost/stats`     | GET    | Ottiene le statistiche sulla frequenza temporale dei post dell'utente.      |
| 4   | `localhost/filters`   | POST   | Ottiene una lista di post filtrata in base ai filtri che l'utente applica.  |

#### Statistiche dei post
Permette di fare una statistica dei post che sono stati pubblicati dall'utente in determinati archi di tempo. In particolare, vengono contati i post caricati:
- oggi;
- ieri;
- questa settimana;
- questo mese;
- quest'anno;
- l'anno scorso.

![Esempio di risultato delle statistiche](https://i.ibb.co/2g8CswB/Immagine-2022-01-17-123019.png "Esempio di risultato delle statistiche")

#### Filtraggio dei post
Permette di filtrare la lista dei post per:
- data di pubblicazione;
- fascia oraria di pubblicazione;
- parole chiave contenute nel testo del post.

Per specificare quali filtri adoperare bisogna scriverli in un JSON da scrivere dentro il corpo della richiesta:<br>

![Composizione dei filtri su Postman](https://i.ibb.co/cYz9NHW/Immagine-2022-01-17-123826.png "Composizione dei filtri su Postman")

##### Filtro per data di pubblicazione
- `since`: post pubblicati a partire da quella data;
- `until`: post pubblicati entro quella data.

Il formato delle date è `YYYY-mm-dd` (anno-mese-giorno). Nessuno dei due filtri è obbligatorio, è possibile inserire anche solo uno dei due.

##### Filtro per fascia oraria di pubblicazione
`range` indica la fascia oraria in cui i post sono stati pubblicati. Può contenere una o due ore; nel caso di orario singolo, si scrive ad esempio `12:30`, mentre nell'ultimo caso, i due orari sono scritti secondo il layout `12:00-15:00`.<br>

Il formato degli orari è `hh:mm` (ore:minuti).

##### Filtro per parole chiave
- `keywords`: array che contiene le parole chiave che devono essere incluse nel testo dei post. Se viene specificato un array vuoto (`[]`), indica che le parole chiave verranno prese dal dataset contenuto in `config/dataset.json`.
- `dictionary`: valore di default: `false`. Se impostato a `true` indica che per ogni post viene visualizzato anche quante parole chiave compaiono nel testo di tale post e le loro quantità.
- `full_words`: valore di default: `false`. Se impostato a `true` indica che bisogna includere solamente le parole intere, e non anche le parti di parole.
- `case_sensitive`: valore di default: `false`. Se impostato a `true` indica che verrà fatta distinzione tra le lettere maiuscole e minuscole.

Per poter fare il filtraggio in base alle parole chiave e poter usare le opzioni `dictionary`, `full_words` e `case_sensitive` è obbligatorio includere `keywords`.

*Esempio di filtraggio per parole chiave del dataset, con dizionario e solo parole intere:*
![Esempio di filtraggio](https://i.ibb.co/KWFkM8y/Immagine-2022-01-17-125331.png "Esempio di filtraggio")

## Realizzazione
Per la realizzazione del progetto sono stati usati:
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/)**: IDE per la scrittura del codice;
- **[Spring Boot](https://spring.io/projects/spring-boot)**: framework per la creazione di server HTTP e API RESTful;
- **[Maven](https://maven.apache.org/)**: per la gestione delle dipendenze di Spring;
- **[Postman](https://www.postman.com/)**: per il testing delle rotte della nostra API.
- **[JUnit 5](https://junit.org/junit5/)**: per la stesura dei tester per la funzionalità del programma.

## Autori
- [Federico Arduini](https://github.com/faffolao) (contributo al 50%)
- [Rocco Nori](https://github.com/reirocco) (contributo al 50%)