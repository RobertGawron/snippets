/*! \file new.c
 * Dostarcza opisu obiektu, frontowego mechanizmu do tworzenia 
 * obiektu i jego niszczenia
 */
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <stdarg.h>

/**
 * Matryca do tworzenia klas, dzieki niej to wszytko dziala :)
 *
 * @param ctor konstruktor, zdefiniowany dopiero, gdy piszemy cialo klasy
 * @param dctor destruktor, j.w
 * @param clone klonuje obiekty, tzn przyjmuje jako parametry objekt a i b, 
 * a w wyniku b ma takie same pola co a
 * @param diff przyjmuje jako argument dwa obiekty, sprawdza, czy a i b sa 
 * takie same (to co uznajemy za 'takie same' definujemy podczas pisania kodu klasy)
 *
 */
struct Class {
    size_t size;
    void * (* ctor) (void * self, va_list * app);
    void * (* dtor) (void * self);
    void * (* clone) (const void * self);
    void * (* diff) (const void * self, const void * b);
};

/**
 * Zwaraca nowo utworzony obiekt. Przyjmuje jako argumnet typ obiektu oraz
 * liste argumenetow do zainicjowania jeko pól.
 *
 * @param _class - obiekt na rzecz którego wywołujemy funkcję
 * @param ... - parametry (opcjonalne, dowolna ilość), które służą do 
 * zainicjalizowania obiektu, korzystamy z makra w stdarg.h 
 *
 */
void * 
new(const void * _class, ...) {

    const struct Class * class = _class;
    void * p = calloc(1, class -> size);

    if (!p) {
        fprintf(stderr, "memory allocation failed.\n");
        exit(1);
    }
    
    * (const struct Class **) p = class;
    if (class -> ctor) {
        va_list ap;
        va_start(ap, _class);
        p = class -> ctor(p, &ap);
        va_end(ap);
    }

    return p;
}

/**
 *
 * Niszczy obiekt. Nie można po prostu zwolnić pamięci, którą obiekt zajmuje, 
 * gdyż może on posiadać np otwarte deskryptory plikow, połączenia z baza 
 * danych, czy też zaalokowaną pamięć, daletgo wywołujemy jego destruktor 
 * (o ile takowy został napisany przez autra klasy), by elegancko wszystko 
 * posprzątać. Dopiero wtedy zwalniamy pamięć zajmowaną przez obiekt. 
 * Funkcja ta jest rozszerzeniem zwykłego free()
 *
 * @param self - obiekt na rzecz którego wywołujemy funkcję
 *
 */
void * 
delete(void * self) {
    const struct Class ** cp = self;
    if(self && (* cp) && ((* cp) -> dtor)){
        self = (* cp) -> dtor(self);
    }
    free(self);
}
