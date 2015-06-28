/*
   +----------------------------------------------------------------------+
   | PHP Version 5                                                        |
   +----------------------------------------------------------------------+
   | Copyright (c) 1997-2010 The PHP Group                                |
   +----------------------------------------------------------------------+
   | This source file is subject to version 3.01 of the PHP license,      |
   | that is bundled with this package in the file LICENSE, and is        |
   | available through the world-wide-web at the following url:           |
   | http://www.php.net/license/3_01.txt                                  |
   | If you did not receive a copy of the PHP license and are unable to   |
   | obtain it through the world-wide-web, please send a note to          |
   | license@php.net so we can mail you a copy immediately.               |
   +----------------------------------------------------------------------+
   | Author: Hartmut Holzgraefe <hholzgra@php.net>                        |
   +----------------------------------------------------------------------+
*/
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

typedef struct _php_info_logo { 
	const char *mimetype;
	int mimelen;
	const unsigned char *data; 
	int size; 
} php_info_logo;

#define CONTENT_TYPE_HEADER "Content-Type: "
int php_info_logos(const char *mimetype, const unsigned char *data, int size)
{
    php_info_logo logo_image;
    char *content_header;
    int len;

	logo_image.mimetype = mimetype;
	logo_image.mimelen  = strlen(mimetype);
	logo_image.data     = data;
	logo_image.size     = size;



    len = sizeof(CONTENT_TYPE_HEADER) - 1 + logo_image.mimelen;
 	content_header = malloc(len + 1);
	memcpy(content_header, CONTENT_TYPE_HEADER, sizeof(CONTENT_TYPE_HEADER) - 1);
	memcpy(content_header + sizeof(CONTENT_TYPE_HEADER) - 1 , logo_image.mimetype, logo_image.mimelen);
	content_header[len] = '\0';



    printf("HTTP/1.0 200 OK\r\n");
    printf("%s\r\n\r\n", content_header);

    int i=0;
    for(i=0; i<logo_image.size; i++) {
        printf("%c", logo_image.data[i]);
    }

	return 1;
}


/*
 * Local variables:
 * tab-width: 4
 * c-basic-offset: 4
 * End:
 * vim600: sw=4 ts=4 fdm=marker
 * vim<600: sw=4 ts=4
 */
