/*! \file postgres-handler.c
 * Implementacja handlera postgresa, korzysta z biblioteki libpq. 
 * Wykorzystywana jako jeden z modułów/wtyczek dla DAO. 
 */
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <stdarg.h>
#include <string.h>
#include "libpq-fe.h"
#include "new.h"

/**
 * Pola klasy PostgresHandler
 *
 * @param class struktura z destruktorem konstruktorem i resztą, 
 * potrzebna, by działy konstruktor i destruktor
 * @param PGconn handler połączenie z bazą postgresql, handler w handlerrze :)
 * @param PGresult wyniki ostatniego zapytania
 * @param state_code status wykonania zapytania przez baze danych
 */
struct PostgresHandler {
    const void * class;
    PGresult * res;
    PGconn * conn;
    char state_code[3];
};

/**
 * Pomocnicza funkcja pobierająca dwa stringi i zwracająca ich konkatenację.
 * Nie sprawdza wyjątkowych sytuacji, nie zwraca statusu.
 * @param a string pierwszy do połączenia
 * @param a string drugi do połączenia
 */
char * joinStrings(const char * a, const char * b){
    int l = strlen(a) + strlen(b);
    char * buff =(char *)calloc(l, sizeof(char));
    strcat(buff, a); 
    strcat(buff, b); 
    return buff;
}


static void * PostgresHandler_ctor(void * _self, va_list * app) {
    struct PostgresHandler * self = _self;

    char * prefix[] = {"dbname=", " user=", " port=", " host=", ""};
    char *  db_connection_str = "";
    int i=0;

    while( strcmp(prefix[i], "") ){
        char* p = va_arg(*app, char *);
        if( strcmp(p, "") ){
            char * buff = joinStrings(prefix[i], p);
            db_connection_str = joinStrings(db_connection_str, buff);
        }
        i++;
    }

    if( !strcmp(db_connection_str, "") ){
        fprintf(stderr, "Insuficient number of arguments to connect: %d.\n", db_connection_str);
        exit(1);
    }

    self -> conn = PQconnectdb( db_connection_str);
    if (PQstatus(self -> conn) == CONNECTION_BAD) {
        fprintf(stderr, "Connection to database failed.\n");
        fprintf(stderr, "%s", PQerrorMessage(self -> conn));
        exit(1);
    }

    return self;
}

static void * PostgresHandler_dtor(void * _self) {
    struct PostgresHandler * self = _self;
    PQfinish(self -> conn);
    return 0;
}

void * PostgresHandler_query(void * _self, void * query_string, void * result) {
    struct PostgresHandler * self = _self;
    self -> res = PQexec(self -> conn, query_string);

    if (PQresultStatus(self -> res) != PGRES_TUPLES_OK) {
        char errmsg[100]; // BO
        sprintf(errmsg, "Query failed err. %d\n", 
                                PQresultStatus(self -> res));
        fprintf(stderr, errmsg);
        PQclear(self -> res);
        return ;
    }

    int i;
    for (i = 0; i < PQntuples(self -> res); i++){
        char * r = PQgetvalue(self -> res, i, 0);
        QueryResult_insertRow(result, r);
    }

    PQclear(self -> res);

    return 0;
} 

static const struct Class _PostgresHandler = {
    sizeof(struct PostgresHandler),
    PostgresHandler_ctor,
    PostgresHandler_dtor,
    PostgresHandler_query
};
const void * PostgresHandler = &_PostgresHandler;

