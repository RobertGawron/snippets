/*
 * szukacz.c
 *
 * Created: 2014-11-17 19:11:55
 *  Author: rgawron
 */ 

#define F_CPU 1000000UL                                    /* Clock Frequency = 1Mhz */

#include <avr/io.h>
#include <util/delay.h>

#define bool_t uint16_t
typedef enum Direction {back, forward, left, right} Direction_t;

bool_t isMovementDetected()
{
	// Movement detected by PIR sensor output in high state
	return (PINB & _BV(PB4));
}

void stopTank()
{
	// set all outputs connected to motor driver to low = no movement
	PORTB &= !(PB0 | PB1 | PB2 | PB3);
}

void goTank(Direction_t direction)
{
	stopTank();
	switch (direction)
	{
		case back:
		break;
		case forward:
			PORTB |= (_BV(PB0) | _BV(PB2));
		break;
		case left:
			PORTB |= _BV(PB2);
		break;
		case right:
		break;	
		default:
			// should not happened
		break;	
	}
}

int main(void)
{
	DDRB = 0b11101111;
	
	int delay = 50000;
	
    while(1)
    {
        stopTank();
		_delay_ms(delay);
        
		if (isMovementDetected())
		{
			goTank(forward);
			_delay_ms(delay);
			goTank(left);	
			_delay_ms(delay);		
		}
	}
}