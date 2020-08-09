#!/usr/bin/env python
import smtplib, ssl

port = 465  # For SSL
smtp_server = "smtp.gmail.com"
sender_email = "vikasverma.new1@gmail.com"  # Enter your address
receiver_email = "vikasverma250999@gmail.com"  # Enter receiver address
password = 'vikas@250999' # Enter password

message = """\
Subject: Success 
Congratulations code is deployed successfully."""
    
context = ssl.create_default_context()
with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
    server.login(sender_email, password)
    server.sendmail(sender_email, receiver_email, message)