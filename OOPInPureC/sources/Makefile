BINARYNAME = handler_test
# use pg_config to find those 
PSQLHEADERS = /usr/include/postgresql
PSQLLIBS = /usr/lib

$(BINARYNAME): main.o dao.o postgres-handler.o new.o query-result.o
	$(CC) -o $(BINARYNAME) main.o dao.o	postgres-handler.o new.o query-result.o -L $(PSQLLIBS) -lpq
	rm -rf *.o *.gch

#postgres-handler.o: postgres-handler.c postgres-handler.h
#	$(CC) -c -I $(PSQLHEADERS) postgres-handler.c postgres-handler.h


postgres-handler.o: postgres-handler.c 
	$(CC) -c -I $(PSQLHEADERS) postgres-handler.c



clean:
	rm -rf *.o *.gch $(BINARYNAME)


