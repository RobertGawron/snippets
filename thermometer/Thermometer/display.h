/*
 * display.h
 *
 * Created: 2014-11-30 20:43:25
 *  Author: rgawron
 */ 


#ifndef DISPLAY_H_
#define DISPLAY_H_

#include "typeDefs.h"

typedef struct display 
{
	unsigned clk1;
	unsigned rst1;
	unsigned clk2;
	unsigned rst2;	
} display_t;

void initDisplay(display_t *display);

bool_t updateDisplay(display_t *display, short value);

#endif /* DISPLAY_H_ */