/*
 * thermometer.c
 *
 * Created: 2014-12-20 20:18:18
 *  Author: rgawron
 */

#include "thermometer.h"


void thermometer_init_adc()
{
    // set Aref as AVcc
    ADMUX = (1<<REFS0);
    ADCSRA = (1<<ADEN) | (1<<ADPS2) | (1<<ADPS1) | (1<<ADPS0);
}

uint16_t thermometer_read_adc(uint8_t ch)
{
    // select ADC Channel
    ADMUX|=ch;

    // start single conversion
    ADCSRA|=(1<<ADSC);

    // wait for conversion to complete
    while(!(ADCSRA & (1<<ADIF)));

    // clear ADIF
    ADCSRA |= (1<<ADIF);

    return ADC;
}

int8_t thermometer_get_temperature()
{
    // TODO move this somewhere?
    thermometer_init_adc();

    /* The sensor ads 0.5 to the output, so we need to subtract it.
       Assume that the Vcc = 5V.
       0V -> 0
       5V -> 1024
       This gives us:
       1V -> 1024 / 5
       0.5V -> (1024 / 5) * 0.5 = 102.4
    */
    double new = (double)thermometer_read_adc(0) - 102.4;

    /* Resolution of the sensor is 0.01V, also 0.01V = 1C (Celsius degree).
      We need to normalize the input to be in range of 0-xx Celsius degrees.
      0.01V -> (1024 / 5) * 0.01 = 2.048
    */
    new /= 2.048;

    return (int8_t)new;
}