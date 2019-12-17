#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
  int
main ()
{
  int c, spatii, size;
  struct sockaddr_in server;
  char a[100];

  c = socket (AF_INET, SOCK_STREAM, 0);	// create socket
  if (c < 0)
    {
      printf ("Eroare la crearea socketului client\n");
      return 1;
    }

  memset (&server, 0, sizeof (server));
  server.sin_port = htons (1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr ("127.0.0.1");	// set server ip

  if (connect (c, (struct sockaddr *) &server, sizeof (server)) < 0)
    {
      printf ("Eroare la conectarea la server\n");
      return 1;
    }
  printf("Introduceti sirul: ");
  fgets (a, 100, stdin);
  size = strlen (a);
  send (c, &size, sizeof (size), 0);
  send (c, &a, sizeof (a), 0);
  recv (c, &spatii, sizeof (spatii), 0);
  printf ("Numarul de spatii este %d\n", spatii);
  close (c);
}
