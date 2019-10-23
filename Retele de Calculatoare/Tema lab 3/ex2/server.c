#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>
  int
countSpace (char a[])
{
  int c = 0;
  for (int i = 0; i < strlen (a); i++)
    {
      if (a[i] == ' ')
	c++;
    }
  return c;
}

void *
serve_client (void *arg)
{
  int *c1 = (int *) arg;
  int c = *c1;
  int marime, count;
  char arr[100];
  printf ("S-a conectat un client.\n");
  // deservirea clientului
  recv (c, &marime, sizeof (marime), MSG_WAITALL);	// citeste din c in a,sizeof(a)
  recv (c, arr, marime * sizeof (char), MSG_WAITALL);
  arr[marime] = '\0';
  count = countSpace (arr);
  send (c, &count, sizeof (count), 0);	// on c send suma with size, 0 = msg_waitall
  close (c);			// close client connection
  free (arg);
}

int
main ()
{
  int s;
  struct sockaddr_in server, client;
  int c, l;

  s = socket (AF_INET, SOCK_STREAM, 0);	// create socket
  if (s < 0)
    {
      printf ("Eroare la crearea socketului server\n");
      return 1;
    }

  memset (&server, 0, sizeof (server));	// face clear la server
  server.sin_port = htons (1234);	//define port and convert from host to network
  server.sin_family = AF_INET;	// family
  server.sin_addr.s_addr = 0;	// accept any connection
  int true = 1;
  setsockopt (s, SOL_SOCKET, SO_REUSEADDR, &true, sizeof (int));
  if (bind (s, (struct sockaddr *) &server, sizeof (server)) < 0)
    {				// bind
      printf ("Eroare la bind\n");	// ocket to port
      return 1;
    }

  listen (s, 5);		// listen and queue depth

  l = sizeof (client);
  memset (&client, 0, sizeof (client));	// clear client memory

  while (1)
    {
      pthread_t thread_id;
      c = accept (s, (struct sockaddr *) &client, &l);	// accept on socket s
      int *ar = (int *) malloc (sizeof (int));
      *ar = c;
      pthread_create (&thread_id, NULL, &serve_client, (void *) ar);
      // sfarsitul deservirii clientului;
    }
}
