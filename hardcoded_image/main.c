#include <stdio.h>
//#include "php_logos.h"
#include "logos.h"

int main() {
    php_info_logos("image/gif", zend_logo, sizeof(zend_logo));
}

