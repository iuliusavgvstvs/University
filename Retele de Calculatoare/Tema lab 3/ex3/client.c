#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
int main ()
{
  int c;
  struct sockaddr_in server;
  int l;
  char sir[200], sir2[200];

  c = socket (AF_INET, SOCK_STREAM, 0);
  if (c < 0)
    {
      printf ("Eroare la crearea socketului client\n");
      return 1;
    }

  memset (&server, 0, sizeof (server));
  server.sin_port = htons (1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr ("127.0.0.1");

  if (connect (c, (struct sockaddr *) &server, sizeof (server)) < 0)
    {
      printf ("Eroare la conectarea la server\n");
      return 1;
    }

  printf("Introduceti sirul: ");
  fgets(sir,199,stdin);
  l=strlen(sir);
  send (c, &l, sizeof (l), 0);
  send (c, sir, l, 0);
  recv (c, sir2, l, MSG_WAITALL);
  sir2[l-1]='\0';
  printf("Sirul inversat: %s\n", sir2);
  close (c);
}
