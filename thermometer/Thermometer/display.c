/*
 * display.c
 *
 * Created: 2014-11-30 20:43:39
 *  Author: rgawron
 */ 
#include <avr/io.h>
#include "display.h"

void initDisplay(display_t *display)
{
	DDRB |= (1 << display->clk1);
	DDRB |= (1 << display->clk2);
	DDRB |= (1 << display->rst1);
	DDRB |= (1 << display->rst2);
}

bool_t updateDisplay(display_t *display, short value)
{
	if (value < -100  || value > 100)
	{
		return false;
	}
/*
	updateDisplayRow(first, (value / 10));
	updateDisplayRow(second, (value % 10));
	*/
	return true;
}
