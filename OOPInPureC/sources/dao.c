/*! \file dao.c
 * Implementacja klasy DAO
 */
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <malloc.h>
#include "new.h"
#include "postgres-handler.h"
#include "query-result.h"

/**
 * Pola klasy DAO
 *
 * @param class struktura z destruktorem konstruktorem i resztą, 
 * potrzebna, by działy konstruktor i destruktor
 * @param db_type typ bazy danych (mysql, postgres, oracle..)
 * @param db_handler wskaznik do obiektu handlera bazy
 */
struct Dao {
    const void * class;
    char* db_type;
    void * db_handler;
};

static void * Dao_ctor(void * _self, va_list * app) {
    struct Dao * self = _self;

    self -> db_type = va_arg(*app, char *);
    char * nazwa_bazy = va_arg(*app, char *);
    char * login = va_arg(*app, char *);
    char * host = va_arg(*app, char *);
    char * port = va_arg(*app, char*);
    self -> db_handler = new(PostgresHandler, nazwa_bazy, login, host, port);

    return self;
}

static void * Dao_dtor(void * _self) {
    struct Dao * self = _self;
    delete(self -> db_handler);
    return 0;
}

/**
 * Implementacja metody wykonującej zapytanie i zwracającej wynik w obiekcie
 * klasy QueryResult, wywolujaca metode danego wuery (albo podobną) na obiekcie
 * konretnej klasy w zalezności od tego do jakiej bazy łączy się nasz dao.
 * Tutaj to nie działa zbyt dobrze, gdyż zawsze łączy się tylo do jednej, z resztą
 * tylko jeden taki obiekt jest napisany ale idea pzostaje taka sama.
 *
 * @param p obiekt na rzecz którego wykonujemy komunikat
 * @param query_string treść zapytania SQL
 * @param wyniki zwrócone w obiecie QueryResult
 */
void * query(void * _self, void * query_string, void * result) {
    struct Dao * self = _self;
    PostgresHandler_query(self -> db_handler, query_string, result) ;
    return 0;
} 

static const struct Class _Dao = {
    sizeof(struct Dao),
    Dao_ctor,
    Dao_dtor,
    query
};
const void * Dao = &_Dao;

