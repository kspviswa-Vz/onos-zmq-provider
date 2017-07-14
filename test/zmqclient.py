#!/usr/bin/python

import zmq
import socket
import struct
import signal
import sys

#  Prepare our context and sockets
context = zmq.Context()
zmq_socket = context.socket(zmq.REQ)
zmq_socket.setsockopt(zmq.IDENTITY, b"dev1")
zmq_socket.connect("tcp://localhost:7900")

createCount = 0
modifyCount = 0
deleteCount = 0

def signal_handler(signal, frame):
    zmq_socket.close()
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

for request in range(1, 10000):
    zmq_socket.send(b"Ready")
    message = zmq_socket.recv()

    print('================')
    print('Received message')

    dpn_topic, m_type = struct.unpack('!cB', message[:2])

    print('topic = %s' % dpn_topic)
    print('type = %s' % m_type)

    if m_type == 1:
        createCount += 1
        topic, msgnum, imsi, default_ebi, ue_ip, s1u_sgw_gtpu_teid, s1u_sgw_gtpu_ipv4 = struct.unpack(
            '!cBQBLLL', message[:23])
        ipa = socket.inet_ntoa(struct.pack('!L', ue_ip))
        s1u_sgw_gtpu_ipv4a = socket.inet_ntoa(
            struct.pack('!L', s1u_sgw_gtpu_ipv4))

        print('imsi = %s' % imsi)
        print('ue_ip = %s' % ipa)
        print('default_ebi = %s' % default_ebi)
        print('s1u_sgw_gtpu_ipv4 = %s' % s1u_sgw_gtpu_ipv4a)
        print('s1u_sgw_gtpu_teid = %s' % s1u_sgw_gtpu_teid)
    elif m_type == 2:
        modifyCount += 1
        topic, msgnum, s1u_enb_gtpu_ipv4, s1u_enb_gtpu_teid, s1u_sgw_gtpu_teid = struct.unpack(
            "!cBLLL", message[:14])
        s1u_enb_gtpu_ipv4a = socket.inet_ntoa(
            struct.pack('!L', s1u_enb_gtpu_ipv4))

        print('s1u_enb_gtpu_ipv4 = %s' % s1u_enb_gtpu_ipv4a)
        print('dl s1u_enb_gtpu_teid = %s' % s1u_enb_gtpu_teid)
        print('dl s1u_sgw_gtpu_teid = %s' % s1u_sgw_gtpu_teid)

    elif m_type == 3:
        deleteCount += 1
        topic, msgnum, default_ebi, s1u_sgw_gtpu_teid = struct.unpack("!cBBL", message[
                                                                      :7])
        print('default_ebi = %s' % default_ebi)
        print('s1u_sgw_gtpu_teid = %s' % s1u_sgw_gtpu_teid)

    else:
        print('error type')

    print('================')
    print('Total = %s, create %s, modify %s, delete %s' %
          (request, createCount, modifyCount, deleteCount))

zmq_socket.close()
