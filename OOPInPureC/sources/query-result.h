/*! \file query-result.h
 * Moduł zwraac wyniki zapytań
 */
#ifndef QUERY_RESULT
#define QUERY_RESULT

extern void const * QueryResult;

/**
 * Wysyła do obiektu na rzecz którego zostałą wywołana informację,
 * by zapisał do siebie kolejną krotkę zapytania. Działa to tak, iż
 * zapytanie jest listą krotek, każada taak krotka jest zapisywana 
 * z osoba przy wykorzystaniu tej właśnie funkcji. Ilość krotek jest
 * ograniczona. Ogólnie to pownna być tablica zamiast stringa, np
 * gdy zwracamy w zapytaniu SQL imie,nazwisko,wiek to każda krotka
 * powinna być tablicą trójelementową. Cóż, nie jest :P
 *
 * @param q obiekt na rzecz którego wykinujemy komunikat
 * @param query treść krotki
 */
void * QueryResult_insertRow(void * q, char* query);

/**
 * Iterator wyników zapytania. Po wykonaniu zapytania i pobraniu wynków
 * do obiektu klasy QueryResults chcemy się do nich dostać, np by je
 * wyświetlić w aplikacji. Do tego służy poniższy iterator, możemy nim
 * zwrócić każdą krotkę, pod pierwszej. Gdy przejżymy wszytkie w isEmpty
 * mamy info, wracamy do punktu wyjścia.
 *
 * @param q obiekt na rzecz którego wykinujemy komunikat
 * @param  val łańcuch tekstowy z treścią krotki
 * @param isEmpty jeżeli 1 zwrócony wynik jest oststnim ze zbioru
 */
void * QueryResult_iterator(void * q,  char * val, int * isEmpty);


#endif
