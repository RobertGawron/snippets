bar:
    int 0
ret

foo:
    call bar
    ret

main:
    push 13
    call foo
    ret
