#!/usr/bin/env python3
import http.server
import socketserver
import urllib.request
import urllib.parse
import json
from urllib.error import URLError, HTTPError

class ProxyHTTPRequestHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        # Check if this is an API request
        if self.path.startswith('/api/'):
            self.handle_api_request()
        else:
            # Handle static files
            super().do_GET()
    
    def do_POST(self):
        if self.path.startswith('/api/'):
            self.handle_api_request()
        else:
            super().do_POST()
    
    def handle_api_request(self):
        # Backend server URL
        backend_url = 'http://localhost:8081'
        
        # Construct the full backend URL
        full_url = backend_url + self.path
        
        try:
            # Create request to backend
            if self.command == 'GET':
                req = urllib.request.Request(full_url, method='GET')
            elif self.command == 'POST':
                # Read POST data
                content_length = int(self.headers.get('Content-Length', 0))
                post_data = self.rfile.read(content_length)
                req = urllib.request.Request(full_url, data=post_data, method='POST')
                req.add_header('Content-Type', self.headers.get('Content-Type', 'application/json'))
            
            # Forward the request to backend
            with urllib.request.urlopen(req, timeout=10) as response:
                # Send response status
                self.send_response(response.getcode())
                
                # Forward response headers
                for header, value in response.headers.items():
                    if header.lower() not in ['server', 'date']:
                        self.send_header(header, value)
                self.end_headers()
                
                # Forward response body
                self.wfile.write(response.read())
                
        except HTTPError as e:
            self.send_response(e.code)
            self.send_header('Content-Type', 'application/json')
            self.end_headers()
            error_response = {'error': f'Backend error: {e.reason}'}
            self.wfile.write(json.dumps(error_response).encode())
            
        except URLError as e:
            self.send_response(503)
            self.send_header('Content-Type', 'application/json')
            self.end_headers()
            error_response = {'error': f'Backend unavailable: {str(e)}'}
            self.wfile.write(json.dumps(error_response).encode())
            
        except Exception as e:
            self.send_response(500)
            self.send_header('Content-Type', 'application/json')
            self.end_headers()
            error_response = {'error': f'Proxy error: {str(e)}'}
            self.wfile.write(json.dumps(error_response).encode())

if __name__ == '__main__':
    PORT = 3000
    
    with socketserver.TCPServer(("", PORT), ProxyHTTPRequestHandler) as httpd:
        print(f"Serving at http://localhost:{PORT}")
        print(f"Proxying API requests to http://localhost:8081")
        httpd.serve_forever()