/*! \file query-result.c
 * 
 * Wykorzystywana jako moduł dla DAO. 
 */
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <stdarg.h>
#include <string.h>
#include "new.h"

/**
 * Pola klasy PostgresHandler
 *
 * @param class struktura z destruktorem konstruktorem i resztą,
 * @param data tablica z treścią krotek, sztywna, więc ilość 
 * ich jest ograniczona
 * @param total całkowita ilość krotek do tej pory zapisanych 
 * w obiekcie, domyślnie 0
 * @param iteration krotka którą ostatnio zwróciliśmy, domyślnie 0
 *
 */
struct QueryResult {
    const void * class;
    char * data[1000];
    int total;
    int iteration;
};

static void * QueryResult_ctor(void * _self, va_list * app) {
    struct QueryResult * self = _self;

    self -> total  = 0;
    self -> iteration = 0;

    return self;
}

static void * QueryResult_dtor(void * _self) {
    struct QueryResult * self = _self;
    return 0;
}

void * QueryResult_insertRow(void * _self, char * row_string) {
    struct QueryResult * self = _self;

    self -> data[self -> total ] = calloc(strlen(row_string)+1, 
                    sizeof(char));
    // ale hack :P
    memcpy(self -> data[self -> total ], row_string, 
                    1+strlen(row_string)/sizeof(char) );

    self -> total += 1;

    return 0;
}

void * QueryResult_iterator(void * _self, char * val, int * isEmpty){
    struct QueryResult * self = _self;

    strcpy(val, self->data[self->iteration]);
    self->iteration += 1;

    // czy mam jeszcze jakes do zwrocenia? jeli nie zeruje iterator
    *isEmpty = 0;
    if( (self -> total)  == (self -> iteration) ){
        *isEmpty = 1;
        self -> iteration = 0;
    }

    return 0;
}


static const struct Class _QueryResult = {
    sizeof(struct QueryResult),
    QueryResult_ctor,
    QueryResult_dtor,
    QueryResult_insertRow,
    QueryResult_iterator
};
const void * QueryResult = &_QueryResult;

