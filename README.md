# CineMille
Questo progetto rappresenta un ideale cinema la cui necessità è quella di gestire la programmazione e lo storico di un cinema con 12 sale. 

<h1>Assunzioni</h1>
<p>Vengono qui riportate le assunzioni fatte durante lo svolgimento del progetto.</p>

- Assumiamo che se un film, se particolarmente atteso, potrà essere riprodotto in più sale durante la sua programmazione. In questo scenario la data di inizio fine programmazioni nelle n sale devono coindicere
- Assumiamo che non si possa riproiettare uno stesso film in periodi differenti. Dunque l'inizio proiezione dovrà SEMPRE coincidere con la data di uscita.
- Assumiamo che non tutte le sale devono avere un film in proiezione durante lo stesso periodo. Questo significa che in un periodo x è possibile che ci sia un film proiettato anche in una sola sala, lasciando tutte le altre vuote
- Assumiamo che il DB sia stato popolato a titolo esempificativo. Sono presenti imprecisioni come il genere di un film o le proiezioni durante l'orario notturno
- Assumiamo che per "proiezione settimanale" non si intenda dal lunedì alla domenica, ma si intenda un periodo che parte dal giorno specificato sino al 7° giorno successivo, indipendentemente dal fatto che questo cada all'interno della settimana o a cavallo di due mesi.

<h1>Tabelle</h1>

<p>In questo paragrafo si andranno a spiegare brevemente le tabelle create nel DB e il loro utilizzo. In certi casi le descrizioni potrebbero risultare ovvie.</p>

- director: Una tabella pseudo-opzionale, non richiesta dalla consegna del progetto. Il suo unico scopo è quella di rendere più verosimile uno scenario in cui un film ha il suo regista. Oltre il constraint di uniqueness su nome, cognome e data di nascita (per evitare di inserire più volte uno stesso regista, non considerando omonimi nati lo stesso giorno dello stesso anno), non ha particolari altri vincoli.
  
- genre: Anche questa una tabella pseudo-opzionale, sempre in ottica di verosimiglianza un film ha uno o più generi. Anche qui l'unico constraint oltre la PK è l'unicità del nome, per evitare tuple ripetute per uno stesso genere.
  
- movie: La tabella core del progetto. Il constraint che ha oltre la PK è quello di unicità tra id e release date, questo ci servirà (e verrà spiegato) più avanti, nella tabella scheduling. Ha una FK ovvero il director_id, dato che ad ogni film abbiamo assunto sia ssociato un solo regista non è stato necessaria una tabella di bound tra film e registi.
  
- movie_genre: E' la tabella di relazione tra film e generi che escplicita il/i genere/i per ogni film. Non ha constraint particolari se non, ovviamente, le due FK verso movie e genre
  
- hall: La tabella che rappresenta le sale del cinema. Ha vari constraint:
  - Il check che controlla che il campo "capacity", che rappresenta la capienza della sala, non sia mai inferiore a 50 o superiore a 250 
  - Il check che controlla che il numero totale di tuple (ovvero di sale) non sia maggiore di 12 (tramite function)
  - Il check che controlla che il numero totale di tuple con "is_imax" = true non sia maggiore di 2 (tramite function)
  - L'unicità del nome di ogni sala

- manager: La tabella senza alcun riferimento in nessun altra. E' una tabella che serve a raccogliere le informazioni dei manager, quelli in grado di accedere allo storico passato delle proiezioni, a differenza degli utenti. Ovviamente questa misura di sicurezza è praticamente nulla, essendo bypassabile conoscendo una mail di un manager. E' stata comunque introdotta per dimostrare di aver comunque considerato l'aspetto della sicurezza, senza però aver implementato un vero e proprio metodo sicuro.

- scheduling: La tabella che ci informa quale sarà/è stato il periodo in cui un determinato film è stato messo in programmazione. Oltre la PK ha il constraint per controllare che un film non venga proiettato per meno di una settimana dalla data di uscita e per più di 3 settimane dalla stessa. Ha inoltre una foreign key sul movie_id e sulla relativa release_date. Quest'ultima consente di avere solo start_date coerenti con le release_date di ogni film, evitando così lo scenario in cui la start_date di scheduling sia successiva o precedente alla release_date del film.

- projection: E' la tabella che rappresenta le varie proiezione dei film nelle sale, in diversi orari. Il constraint, oltre a quello di PK, è quello di unicità di movie_id, hall_id, giorno e orario in modo che ogni film non possa essere riprodotto nella stessa sala, lo stesso giorno, alla stessa ora. Ha due foreign key, ovviamente verso movie e verso hall. Si serve di un trigger che prima dell'inserimento, esegue su ogni tuple il controllo per il quale:
  - Il giorno della proiezione non può essere precedente alla start_date in scheduling (e per la proprietà transitiva alla release_sate del film)
  - Il giorno della proiezione non può essere successivo alla end_date in scheduling (e per la proprietà transitiva, successivo a 21 giorni dall release_date del film)
  - Un film sia proiettato almeno per 7 giorni

<h1>Microservizio</h1>
<p>Per lo sviluppo del codice si è optato per un applicativo SpringBoot come indicato dalle specifiche del progetto. Sono state pensate 3 chiamate GET (di cui una accessoria). Per evitare l'effetto wall of text, verrà data una descrizione breve di cosa fa il microservizio e di come lo fa. Verranno completamente ignorate le classi nei packages model(.dto) e exception.</p>

- getMovies(): Un metodo accessorio, volto al semplice ottenimento delle informazioni sui vari film, indipendentemente dalle loro programmazioni e conseguenti proiezioni. Prende in input una mail, quella mail servirà a controllare se è un manager o meno ad aver fatto la richiesta. Se non è un manager verrà risposto errore, dato che si sta accedendo a potenziali dati sensibili a cui un normale cliente non dovrebbe accedere.
Una volta chiamato l'endpoint con il metodo GET, il controller chiama il service che, come Branduardi ci insegna, chiama il repository per eseguire una query SQL e recuperare i dati che ci interessano. Questi dati restituiti al service vengono impacchettati in un JSONObject dotato di un messaggio informativo, per poi venire consegnati al controller che dopo averlo wrappato in uan ResponseEntity, lo restituisce al client.
- getWeekProjections(): Un metodo che 


  
  
