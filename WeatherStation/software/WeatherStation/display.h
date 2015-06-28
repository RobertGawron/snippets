/*
 * display.h
 *
 * Created: 2014-11-30 20:43:25
 *  Author: rgawron
 */

#ifndef DISPLAY_H_
#define DISPLAY_H_

#include <stdbool.h>

typedef struct display
{
    char rst0;
    char clk0;
    char rst1;
    char clk1;
} display_t;

void display_init(display_t *display);

bool display_update(display_t *display, int8_t value);

#endif /* DISPLAY_H_ */