#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <unistd.h>
#include <pthread.h>

void deservire_client (int c)
{
  // deservirea clientului
  int i, l, suma=0, nr[100];
  recv (c, &l, sizeof (l), MSG_WAITALL);
  recv (c, nr, l*sizeof(int), MSG_WAITALL);
  for(i=0;i<l;i++){
	suma+=nr[i];
 }
   send(c,&suma, sizeof(suma),0);
	close(c);
	exit(0);
  // sfarsitul deservirii clientului;
}

int
main ()
{
  int s;
  struct sockaddr_in server, client;
  int c, l;

  s = socket (AF_INET, SOCK_STREAM, 0);
  if (s < 0)
    {
      printf ("Eroare la crearea socketului server\n");
      return 1;
    }

  memset (&server, 0, sizeof (server));
  server.sin_port = htons (1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;

  if (bind (s, (struct sockaddr *) &server, sizeof (server)) < 0)
    {
      printf ("Eroare la bind\n");
      return 1;
    }

  listen (s, 5);

  l = sizeof (client);
  memset(&client, 0, sizeof(client));
 
  while (1)
    {
      c = accept (s, (struct sockaddr *) &client, &l);
      printf("S-a conectat un client.\n");
		if(fork()==0){
			deservire_client(c);
			return 0;
    }
}
}
