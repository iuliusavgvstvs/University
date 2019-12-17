#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

int
main ()
{
  int c, nr=0;
  struct sockaddr_in server;
  uint16_t a, b, suma;
  char s[200], s2[200];
  int l,l2;

  c = socket (AF_INET, SOCK_STREAM, 0);
  if (c < 0)
    {
      printf ("Eroare la crearea socketului client\n");
      return 1;
    }

  memset (&server, 0, sizeof (server));
  server.sin_port = htons (1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr ("172.0.0.1");

  if (connect (c, (struct sockaddr *) &server, sizeof (server)) < 0)
    {
      printf ("Eroare la conectarea la server\n");
      return 1;
    }
  printf ("Introduceti sirul de caractere = ");
  fgets(s,199,stdin);
  l=strlen(s);
  send(c,&l,sizeof(l),0);
  send(c,s,l,0);
  recv(c,&nr,sizeof(nr),0);
  //recv(c,s2,l2,0);
 // printf("Sirul fara spatii este: %s\n",s2);
  printf("Spatii in sir: %d\n",nr);
  close (c);
}
