#!/usr/bin/python

import zmq
import socket
import struct
import signal
import sys
import threading
import requests
import json
from random import randint


class ZmqClient():
    '''
    Zeromq Client class to connect to the ONOS router socket and receive incoming messages
    '''

    def __init__(self, devId, url='localhost', port='7900'):
        self.url = url
        self.port = port
        self.devId = devId
        self.createCount = 0
        self.modifyCount = 0
        self.deleteCount = 0

    def _print_info(self, msg):
        print('zmq:%s | %s' % (self.devId, msg))

    def _unpack_create(self, msg):
        self.createCount += 1
        topic, msgnum, imsi, default_ebi, ue_ip, s1u_sgw_gtpu_teid, s1u_sgw_gtpu_ipv4 = struct.unpack(
            '!cBQBLLL', msg[:23])
        ipa = socket.inet_ntoa(struct.pack('!L', ue_ip))
        s1u_sgw_gtpu_ipv4a = socket.inet_ntoa(
            struct.pack('!L', s1u_sgw_gtpu_ipv4))

        # print('imsi = %s' % imsi)
        # print('ue_ip = %s' % ipa)
        # print('default_ebi = %s' % default_ebi)
        # print('s1u_sgw_gtpu_ipv4 = %s' %
        # s1u_sgw_gtpu_ipv4a)
        # print('s1u_sgw_gtpu_teid = %s' %
        # s1u_sgw_gtpu_teid)

    def _unpack_modify(self, msg):
        self.modifyCount += 1
        topic, msgnum, s1u_enb_gtpu_ipv4, s1u_enb_gtpu_teid, s1u_sgw_gtpu_teid = struct.unpack(
            '!cBLLL', msg[:14])
        s1u_enb_gtpu_ipv4a = socket.inet_ntoa(
            struct.pack('!L', s1u_enb_gtpu_ipv4))

        # print('s1u_enb_gtpu_ipv4 = %s' %
        # s1u_enb_gtpu_ipv4a)
        # print('dl s1u_enb_gtpu_teid = %s' %
        # s1u_enb_gtpu_teid)
        # print('dl s1u_sgw_gtpu_teid = %s' %
        # s1u_sgw_gtpu_teid)

    def _unpack_delete(self, msg):
        self.deleteCount += 1
        topic, msgnum, default_ebi, s1u_sgw_gtpu_teid = struct.unpack('!cBBL', msg[
                                                                      :7])
        # print('default_ebi = %s' % default_ebi)
        # print('s1u_sgw_gtpu_teid = %s' %
        # s1u_sgw_gtpu_teid)

    def _grab_requests(self, num_of_messages):
        context = zmq.Context()
        zmq_socket = context.socket(zmq.REQ)
        zmq_socket.setsockopt(zmq.IDENTITY, self.devId)
        zmq_socket.connect(str('tcp://%s:%s' % (self.url, self.port)))

        for request in range(num_of_messages * 3):
            self._print_info('Sending ready..')

            zmq_socket.send(b'Ready')
            message = zmq_socket.recv()

            msg_len = len(message)

            self._print_info('Message received len %s' %
                             (msg_len))

            if msg_len != 7 and msg_len != 23 and msg_len != 14:
                self._print_info('[!] weird length received %s [!]' % msg_len)
                continue

            dpn_topic, m_type = struct.unpack('!cB', message[:2])
            # print('topic = %s, type = %s' % (dpn_topic, m_type))

            if m_type == 1:
                self._unpack_create(message)
            elif m_type == 2:
                self._unpack_modify(message)
            elif m_type == 3:
                self._unpack_delete(message)
            else:
                print('error type')

            self._print_info('Total = %s, create %s, modify %s, delete %s' %
                             (request + 1, self.createCount, self.modifyCount, self.deleteCount))

        zmq_socket.close()

    def connect(self, num_of_messages):
        t = threading.Thread(target=self._grab_requests,
                             args=(num_of_messages,))
        t.daemon = True
        t.start()


class ZmqTest():
    '''
    Simple test class which sends via REST new FPC requests
    '''

    def __init__(self, devId, num_of_messages):
        self.types = [
            json.load(open('createFPC.json')),
            json.load(open('modifyFPC.json')),
            json.load(open('deleteFPC.json'))
        ]

        for t in self.types:
            t['DeviceId'] = 'zmq:' + devId

        # set different imsi on each device to avoid problems with modify and
        # delete when multiple devices and threads are run
        imsi = randint(1000000000, 9999999999)

        self.types[0]['Payload']['input']['contexts'][0]['imsi'] = str(imsi)
        self.types[0]['Payload']['input']['contexts'][
            0]['context-id'] = 'imsi-' + str(imsi)
        self.types[1]['Payload']['input']['contexts'][0]['imsi'] = str(imsi)
        self.types[1]['Payload']['input']['contexts'][
            0]['context-id'] = 'imsi-' + str(imsi)
        self.types[2]['Payload']['input']['targets'][0][
            'target'] = '/ietf-dmm-fpcagent:tenants/tenant/default/fpc-mobility/contexts/imsi-' + str(imsi)

        self.devId = devId
        self.url = 'http://localhost:8181/onos/zeromqprovider/zmq/flows'
        self.headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': 'Basic b25vczpyb2Nrcw=='
        }

        self.num_of_messages = num_of_messages

    def connectDevice(self):
        self.client = ZmqClient(self.devId)
        self.client.connect(self.num_of_messages)

    def runTests(self):
        for i in range(self.num_of_messages):
            for t in self.types:
                requests.post(self.url, data=json.dumps(t),
                              headers=self.headers)


def main():
    num_of_devices = 2
    num_of_messages = 100

    devices = []
    for i in range(num_of_devices):
        devId = str('dev%s' % (str(i)))
        c1 = ZmqTest(devId, num_of_messages)
        c1.connectDevice()
        devices.append(c1)

    threads = []
    for d in devices:
        t = threading.Thread(target=d.runTests, args=())
        t.daemon = True
        t.start()
        threads.append(t)

    for t in threads:
        t.join()

if __name__ == '__main__':
    main()
