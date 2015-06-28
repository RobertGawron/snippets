/*
 * display.c
 *
 * Created: 2014-11-30 20:43:39
 *  Author: rgawron
 */
#include <avr/io.h>
#include <util/delay.h>
#include "display.h"

//#define DISLAY_SELFTEST

void display_init(display_t *display)
{
    // set display pins as outputs
    DDRB |=  (_BV(display->clk0) |
              _BV(display->rst0) |
              _BV(display->clk1) |
              _BV(display->rst1) );

    // set display pins to low
    PORTB &= ~(_BV(display->clk0) |
               _BV(display->rst0) |
               _BV(display->clk1) |
               _BV(display->rst1) );
}

bool display_update(display_t *display, int8_t value)
{
#ifndef DISLAY_SELFTEST
    /* TODO: implementation of temperatures below zero. */
    if (value < -100  || value > 100)
    {
        return false;
    }

    uint8_t valueToShow = value % 10U;
    uint8_t secondRowValue = value / 10U; // TODO floating + integer, round errors?
    double delayValue = 0.0005; // TODO maybe some interrupts instead?

    // reset current value
    PORTB |= _BV(display->rst0);
    _delay_ms(delayValue);
    PORTB &= ~_BV(display->rst0);

    for (uint8_t i = 0; i < valueToShow; i++)
    {
        PORTB |= _BV(display->clk0);
        _delay_ms(delayValue);
        PORTB &= ~_BV(display->clk0);
        _delay_ms(delayValue);
    }

    // reset current value
    PORTB |= _BV(display->rst1);
    _delay_ms(delayValue);
    PORTB &= ~_BV(display->rst1);

    for (uint8_t i = 0; i < secondRowValue; i++)
    {
        PORTB |= _BV(display->clk1);
        _delay_ms(delayValue);
        PORTB &= ~_BV(display->clk1);
        _delay_ms(delayValue);
    }


#else
    for(uint8_t valueToShow = 0; valueToShow< 10; valueToShow++)
    {
        double delayValue = 0.0005; // TODO maybe some interrupts instead?

        // reset current value
        PORTB |= _BV(display->rst0);
        _delay_ms(delayValue);
        PORTB &= ~_BV(display->rst0);

        for (uint8_t i = 0; i < valueToShow; i++)
        {
            PORTB |= _BV(display->clk0);
            _delay_ms(delayValue);
            PORTB &= ~_BV(display->clk0);
            _delay_ms(delayValue);
        }

        // reset current value
        PORTB |= _BV(display->rst1);
        _delay_ms(delayValue);
        PORTB &= ~_BV(display->rst1);

        for (uint8_t i = 0; i < valueToShow; i++)
        {
            PORTB |= _BV(display->clk1);
            _delay_ms(delayValue);
            PORTB &= ~_BV(display->clk1);
            _delay_ms(delayValue);
        }

        _delay_ms(500);

    }
#endif

    return true;
}
