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
  int a, b, suma, i, k;


  c = socket (AF_INET, SOCK_DGRAM, 0);
  if (c < 0)
    {
      printf ("Eroare la crearea socketului client\n");
      return 1;
    }

  memset (&server, 0, sizeof (server));
  server.sin_port = htons (1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr ("127.0.0.1");
  printf("a=");
  scanf("%d",&a);
  printf("b=");
  scanf("%d",&b);
  sendto (c, &a, sizeof (a), 0, (struct sockaddr *) &server, sizeof (server));
  sendto(c, &b, sizeof(b), 0, (struct sockaddr*) &server, sizeof(server));
  int l = sizeof(server);
  recvfrom(c, &suma, sizeof(suma), MSG_WAITALL, (struct sockaddr *) &server, &l);
  printf("suma=%d\n",suma);
  close (c);
}
