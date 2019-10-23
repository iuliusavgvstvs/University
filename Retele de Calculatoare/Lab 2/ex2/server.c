#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>

void
deservire (int c)
{
  int l, lungime, i, j;
  char sir[200];
  printf ("S-a conectat un client.\n");
  // deservirea clientului
  recv (c, &l, sizeof (l), MSG_WAITALL);
  recv (c, sir, l - 1, MSG_WAITALL);
  i = 0;
  while (i < l)
    {
      if (sir[i] == ' ')
	{
	  for (j = i; j < l - 1; j++)
	    {
	      sir[j] = sir[j + 1];
	    }
	  l--;
	}
      else
	i++;
    }
  send (c, &l, sizeof (l), 0);
  send (c, sir, l, 0);
  close (c);
  exit(0);
}

int
main ()
{
  int s;
  struct sockaddr_in server, client;
  int c, l;
  int lungime, i, j;
  char sir[200];

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
  memset (&client, 0, sizeof (client));

  while (1)
    {
      uint16_t a, b, suma;
      c = accept (s, (struct sockaddr *) &client, &l);
     if(fork()==0);
	  deservire_client(c);
}
