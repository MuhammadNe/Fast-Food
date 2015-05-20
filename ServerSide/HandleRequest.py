import socket

""""
# server.py 
import socket                                         
import time

# create a socket object
serversocket = socket.socket(
	        socket.AF_INET, socket.SOCK_STREAM) 

# get local machine name
host = socket.gethostname()                           

port = 9999                                           

# bind to the port
serversocket.bind((host, port))                                  

# queue up to 5 requests
serversocket.listen(5)                                           

while True:
    # establish a connection
    clientsocket,addr = serversocket.accept()      

    print("Got a connection from %s" % str(addr))
    currentTime = time.ctime(time.time()) + "\r\n"
    clientsocket.send(currentTime.encode('ascii'))
    clientsocket.close()


def upload(request):
    if request.method == 'POST':
        deviceID = measurement.deviceID = str(request.POST['deviceID'])
        return HttpResponse('Success!')
    else:
        return HttpResponse('Invalid Data')""""
		
	def handle(request):
		host = ''        # Symbolic name meaning all available interfaces
		port = 12345     # Arbitrary non-privileged port
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.bind((host, port))
		s.listen(1)
		conn, addr = s.accept()
		print('Connected by', addr)
	while True:
		data = conn.recv(1024)
		#if(data is new data):
		#	insert into sql
		
		if not data: break
		conn.sendall(data)
	conn.close()
	
	
	
	
	
	
	
	
	
	
	
	