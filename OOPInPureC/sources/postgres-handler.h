/*! \file postgres-handler.h
 * Implementacja handlera postgresa, korzysta z biblioteki libpq. 
 * Wykorzystywana jako jeden z modułów/wtyczek dla DAO. 
 */

extern void const * PostgresHandler;

/**
 * Metoda wykonująca zapytanie do postgesowej bazy danych przy użyciu funkcji z 
 * libpq. Korzysta z połączenia którego handler zapisany jest w polu prywatnym 
 * klasy.
 *
 * @param p obiekt do którego przesyłamy komunikat
 * @param query_string treść zaptania SQL
 * @param obiekt klasy QueryResult do którego zwrócone zostaną wynik zapytania
*/
void * PostgresHandler_query(void * postgreshandler, char * query, void * result);

