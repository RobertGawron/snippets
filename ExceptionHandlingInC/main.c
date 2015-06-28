#include <stdio.h>
#include <setjmp.h>

// comes from: Zend/zend.h 

#ifdef HAVE_SIGSETJMP
#   define SETJMP(a) sigsetjmp(a, 0)
#   define LONGJMP(a,b) siglongjmp(a, b)
#   define JMP_BUF sigjmp_buf
#else
#   define SETJMP(a) setjmp(a)
#   define LONGJMP(a,b) longjmp(a, b)
#   define JMP_BUF jmp_buf
#endif

#define zend_try                                                \
    {                                                           \
        JMP_BUF __bailout;                                      \
                                                                \
        if (SETJMP(__bailout)==0) {
#define zend_catch                                              \
        } else {                                                
#define zend_end_try()                                          \
        }                                                       \
    }

// my macros
#define EXCEPTION_RISE do { LONGJMP(__bailout,-1); } while(0)

void function_that_may_rise_exception(JMP_BUF __bailout) {
    EXCEPTION_RISE;
}

int main() {
    int retval = 0;

    zend_try {
        printf("flow of instructions..\n");

        zend_try {
            function_that_may_rise_exception(__bailout);
        } zend_catch {
            printf("nested try/catch\n");
        } zend_end_try();

        function_that_may_rise_exception(__bailout);

        printf("more flow..");
    } zend_catch {
        retval = 3;
    } zend_end_try();

    printf("retval: %d\n", retval);
}

