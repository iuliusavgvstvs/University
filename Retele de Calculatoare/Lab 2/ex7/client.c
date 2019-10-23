#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

int
main ()
{
  int c;
  struct sockaddr_in server;
  uint16_t a, b, suma;
  char s[200], s2[200];
  int lun, l, i, l2;

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
  printf ("Introduceti sirul de caractere = ");
  fgets (s, 200, stdin);
  printf("i=");
  scanf("%d",&i);
  printf("l=");
  scanf("%d",&l);
  lun = strlen (s);
  send (c, &lun, sizeof (lun), 0);
  send (c, s, lun, 0);
  send(c,&i,sizeof(i),0);
  send(c,&l,sizeof(l),0);

  recv (c, &l2, sizeof (l2), 0);
  recv (c, s2, l2, 0);
  s2[l2]='\0';
  printf ("Subsirul care incepe de pe pozitia %d si are lungimea %d este: %s\n",i,l, s2);
  close (c);
}
