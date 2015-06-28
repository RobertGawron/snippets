#include <stdio.h>
#include "query-result.h"
#include "dao.h"

int main(){
    // tworze obiekt klasy dao i inicjuje go
    void * dao = new(Dao, "psql", "testrollback","rgawron", "", "");
    // tworze obiekt bedacy wynikiem zapytania i inicjuje go
    void * result = new(QueryResult);

    // na obiekcie dao wykonuje zapytaie do bazy, a wynik umieszczam 
    // w obiekcie klsay wynik zapytania
    query(dao, "select nazwisko from Osoba", result);

    int isEmpty;
    for(isEmpty = 0; isEmpty!=1; ) {
        char answer[1000];
        // iteruje po krotkach ktore zawiera wynik zapytania
        // by wysietlic wszytkie wyniki ktore zwrocilo zapytanie
        QueryResult_iterator(result, &answer[0], &isEmpty);
        printf("%s\n", answer);
    }

    // nie ptrzeba mi juz polaczenia z baza danych ani wynikow 
    // zaptania usuwam je wiec by nie bylo wycieku pameci i by 
    // nie obciazac bazy danych zbednym polaczeniem
    delete(result);
    delete(dao);

    printf("La fin de mon monde..\n"); 
}

