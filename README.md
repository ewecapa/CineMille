# CineMille
Project for Lascaux

Questo progetto rappresenta un ideale cinema la cui necessità è quella di gestire la programmazione e lo storico di un cinema con 12 sale. 

I requisiti tecnici sono i seguenti:
- Consentire di visualizzare una lista dei film in programmazione, con possibilità di filtrare per data di inizio/data fine. In questo modo è possibile recuperare quali sono i film in programmazione per ciascuna settimana.
- Deve essere possibile per i gestori della piattaforma accedere allo storico completo della programmazione dei film passati
- Ipotizzare uno schema logico dell’applicativo, preferibile in notazione UML
- Realizzare un prototipo applicativo in Spring Boot (JAVA) per l’esposizione di un web service REST in GET per la visualizzazione della lista dei film.
- [Opzionale] Realizzare un prototipo applicativo in Angular per l’interfaccia. Nell’interfaccia deve essere possibile visualizzare l’elenco dei film recuperati dal servizio REST.

<h1>Assunzioni</h1>
Vengono qui riportate le assunzioni fatte durante lo svolgimento del progetto.

- Ogni film è diretto da un solo regista
- Un film, se particolarmente atteso, potrà essere riprodotto in più sale durante la sua programmazione
- Se il punto precedente avviene, la data di inizio e la data di fine delle programmazioni nelle n sale devono coindicere
- Non c'è nessun vincolo sul poter riproiettare uno stesso film in periodi differenti. Ciò significa che "Titanic" potrà essere nella programmazione di Gennaio (dal 1° al 14) e in quella di Marzo (dal 14 al 21)
- A seguito del punto precedente si intende che un film non può essere proiettato per più di 3 settimane filate. Se questo limite venisse superato in sessioni diverse, non sarebbe considerato un problema
- La data di uscita di un film non deve necessariamente coincidere con l'inizio della sua programmazione. Se è la seconda volta che viene proiettato, queste due date non coincideranno.
  
