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

- Assumiamo che se un film, se particolarmente atteso, potrà essere riprodotto in più sale durante la sua programmazione. In questo scenario la data di inizio fine programmazioni nelle n sale devono coindicere
- Assumiamo che non si possa riproiettare uno stesso film in periodi differenti. Dunque l'inizio proiezione dovrà SEMPRE coincidere con la data di uscita.
- Assumiamo che non tutte le sale devono avere un film in proiezione durante lo stesso periodo. Questo significa che in un periodo x è possibile che ci sia un film proiettato anche in una sola sala, lasciando tutte le altre vuote
- Assumiamo che il DB sia stato popolato a titolo esempificativo. Sono presenti imprecisioni come il genere di un film o le proiezioni durante l'orario notturno
- Assumiamo che per "proiezione settimanale" non si intenda dal lunedì alla domenica, ma si intenda un periodo che parte dal giorno specificato sino al 7° giorno successivo, indipendentemente dal fatto che questo cada all'interno della settimana o a cavallo di due mesi.
  
  
