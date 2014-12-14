/*
 * Thermometer.c
 *
 * Created: 2014-11-30 19:08:24
 *  Author: rgawron
 */ 

#include <avr/io.h>
#include "typeDefs.h"
#include "display.h"

int main(void)
{
	//DDRB = 0b00111111;
	display_t display = { PIN_0, PIN_1, PIN_2, PIN_3 };
	initDisplay(&display);
	
    while(true)
    {
		updateDisplay(&display, 27);
    }
} 