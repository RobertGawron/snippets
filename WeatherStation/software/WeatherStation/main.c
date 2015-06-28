/*
 * Thermometer.c
 *
 * Created: 2014-11-30 19:08:24
 *  Author: rgawron
 */

#include <avr/io.h>
#include <util/delay.h>
#include <stdbool.h>

#include "display.h"
#include "thermometer.h"

int main(void)
{
    static double refresh_delay  = 1500;
    int8_t newTemperature = 0;
    display_t display = { PORTB0, PORTB1, PORTB2, PORTB3 };

    display_init(&display);

    while(true)
    {
        int8_t newTemperature = thermometer_get_temperature();
        display_update(&display, newTemperature);
        _delay_ms(refresh_delay);
    }
}